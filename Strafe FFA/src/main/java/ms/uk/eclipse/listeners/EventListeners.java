package ms.uk.eclipse.listeners;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ms.uk.eclipse.FFAPlugin;
import ms.uk.eclipse.config.FileConfiguration;
import ms.uk.eclipse.managers.FFAManager;
import ms.uk.eclipse.managers.PlayerManager;
import ms.uk.eclipse.scoreboard.SetScoreboard;

public class EventListeners implements Listener {
    final PlayerManager playerManager = FFAPlugin.getInstance().getPlayerManager();
    final FFAManager ffaManager = FFAPlugin.getInstance().getFFAManager();

    @EventHandler
    public void onBreakingBlocks(BlockBreakEvent e) { // Stops breaking blocks
        e.setCancelled(!e.getPlayer().isOp());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) { // Stops Item drops on death
        e.getDrops().clear();
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) { // Stops draging Items around
        e.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) { // It does the teleport aka pvplounge Death animation

        if (!(e.getEntity() instanceof Player || e.getDamager() instanceof Player)) {
            return;
        }

        Player attacker = (Player) e.getDamager();
        Player victim = (Player) e.getEntity();

        if (e.getFinalDamage() >= victim.getHealth()) {
            victim.teleport(victim.getWorld().getSpawnLocation());
            victim.setHealth(20);
            victim.setLevel(0);
            DecimalFormat df = new DecimalFormat("#.##");
            victim.sendMessage("§f§lYou got killed by §4§l" + e.getDamager().getName() + " "
                    + df.format(attacker.getHealth()) + " §l\u2764 §4");

            attacker.giveExpLevels(1);
            attacker.setHealth(20);
            attacker.sendMessage("§8§lYou killed  §4§l" + e.getEntity().getName());
            attacker.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 5));
            ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE, 2);
            ItemMeta gapplesp = gapple.getItemMeta();
            gapplesp.setDisplayName("§7§lYum");
            gapple.setItemMeta(gapplesp);
            attacker.getInventory().addItem(gapple);

            attacker.sendMessage("§cHere you go gapples refilled");

            FileConfiguration victimFile = playerManager.getPlayerConfig(victim.getUniqueId());
            FileConfiguration attackerFile = playerManager.getPlayerConfig(attacker.getUniqueId());

            int killCount = playerManager.getKills(attacker.getUniqueId()) + 1;
            int deathCount = playerManager.getDeaths(attacker.getUniqueId()) + 1;
            playerManager.updateKills(attacker.getUniqueId(), killCount);
            playerManager.updateDeaths(attacker.getUniqueId(), deathCount);

            attackerFile.set("Kills", killCount);
            victimFile.set("Deaths", deathCount);

            attackerFile.save();
            victimFile.save();

            SetScoreboard sets = new SetScoreboard();
            sets.setScoreboard(victim, Bukkit.getOnlinePlayers().size(), killCount, deathCount);
            sets.setScoreboard(attacker, Bukkit.getOnlinePlayers().size(), killCount, deathCount);

            if (attacker.getLevel() % 5 == 0) {
                attacker.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, attacker.getLevel() * 10, 2));
                attacker.sendMessage("§7§lCongratulations here is your reward");
                Bukkit.broadcastMessage("  §e§l" + attacker.getName() + " §bmade a total killstreak of "
                        + attacker.getLevel() + " Players!");
            }

            playerManager.setKit(victim);

            if (victim.isOp()) {
                ItemStack special = new ItemStack(Material.WATCH);
                ItemMeta specialmeta = special.getItemMeta();
                specialmeta.setDisplayName("§4§lOwner-Gui");
                specialmeta.spigot().setUnbreakable(true);
                special.setItemMeta(specialmeta);

                victim.getInventory().setItem(7, special);

            }

            e.setCancelled(true);
        }

        if (victim.getInventory().getItemInHand().getType() == Material.STICK) {
            attacker.teleport(new Location(victim.getWorld(), 0, 0, 0));
            victim.teleport(new Location(victim.getWorld(), 0, 0, 0));
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent e) { // Disable Fall-Damage
        e.setCancelled(e.getEntity() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.FALL);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        FileConfiguration fileConfiguration = new FileConfiguration(p.getUniqueId() + ".yml", "plugins/UUIDS");
        int kills = fileConfiguration.getInt("Kills", 0);
        int deaths = fileConfiguration.getInt("Deaths", 0);
        playerManager.updateKills(p.getUniqueId(), kills);
        playerManager.updateDeaths(p.getUniqueId(), deaths);
        playerManager.setPlayerConfig(p.getUniqueId(), fileConfiguration);

        SetScoreboard sets = new SetScoreboard();
        sets.setScoreboard(p, Bukkit.getOnlinePlayers().size(), kills, deaths);

        e.setJoinMessage(null);
        p.setFoodLevel(20);
        p.setHealth(20);
        p.setLevel(0);
        p.setMaximumNoDamageTicks(ffaManager.getNoDamageTicks());

        playerManager.setKit(p);

        if (p.isOp()) {
            ItemStack special = new ItemStack(Material.WATCH);
            ItemMeta specialmeta = special.getItemMeta();
            specialmeta.setDisplayName("§4§lOwner-Gui");
            specialmeta.spigot().setUnbreakable(true);
            special.setItemMeta(specialmeta);
            p.getInventory().setItem(7, special);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) { // Player Interact Event

        Player p = e.getPlayer();

        if (p.getItemInHand().getItemMeta() == null) {
            return;
        }

        Inventory inv;

        switch (p.getItemInHand().getItemMeta().getDisplayName()) {
        case "§4§lOwner-Gui":
            inv = Bukkit.createInventory(null, 9, "§4§lMenu");

            ItemStack navi = new ItemStack(Material.STICK);
            ItemMeta spawnmeta = navi.getItemMeta();
            spawnmeta.setDisplayName("§c1v1");
            navi.setItemMeta(spawnmeta);
            inv.setItem(0, navi);

            ItemStack fly = new ItemStack(Material.FEATHER);
            ItemMeta flymeta = fly.getItemMeta();
            flymeta.setDisplayName("§cFly");
            fly.setItemMeta(flymeta);
            inv.setItem(1, fly);

            ItemStack glas = new ItemStack(Material.SNOW_BALL);
            ItemMeta glasmeta = glas.getItemMeta();
            glasmeta.setDisplayName("§cDome");
            glas.setItemMeta(glasmeta);
            inv.setItem(2, glas);

            ItemStack NoHit = new ItemStack(Material.RAW_FISH, 1, (short) 3);
            ItemMeta NoHitmeta = NoHit.getItemMeta();
            NoHitmeta.setDisplayName("§cNoHitDelay");
            NoHit.setItemMeta(NoHitmeta);
            inv.setItem(3, NoHit);

            ItemStack StopNoHit = new ItemStack(Material.REDSTONE);
            ItemMeta StopNoHitmeta = StopNoHit.getItemMeta();
            StopNoHitmeta.setDisplayName("§cReset-Hitdelay");
            StopNoHit.setItemMeta(StopNoHitmeta);
            inv.setItem(8, StopNoHit);

            p.openInventory(inv);

            break;

        case "§7§lSettings":
            inv = Bukkit.createInventory(null, 9, "§4§lDeath Animation");

            ItemStack Instant = new ItemStack(Material.SUGAR);
            ItemMeta Instantmeta = Instant.getItemMeta();
            Instantmeta.setDisplayName("§cInstant");
            Instant.setItemMeta(Instantmeta);
            inv.setItem(0, Instant);

            ItemStack Default = new ItemStack(Material.PAPER);
            ItemMeta Defaultmeta = Default.getItemMeta();
            Defaultmeta.setDisplayName("§cDefault");
            Default.setItemMeta(Defaultmeta);
            inv.setItem(1, Default);

            ItemStack Thunder = new ItemStack(Material.NETHER_STAR);
            ItemMeta Thundermeta = Thunder.getItemMeta();
            Thundermeta.setDisplayName("§cThunder");
            Thunder.setItemMeta(Thundermeta);
            inv.setItem(2, Thunder);

            p.openInventory(inv);

            break;
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) { // Make things alive by clicking or having it in hand

        Player p = (Player) e.getWhoClicked();

        switch (e.getInventory().getName()) {
        case "§4§lMenu":
            if (e.getCurrentItem() == null) {
                return;
            }

            if (e.getCurrentItem().getItemMeta() == null) {
                return;
            }

            switch (e.getCurrentItem().getItemMeta().getDisplayName()) {

            case "§c1v1":

                ItemStack eins = new ItemStack(Material.STICK);
                ItemMeta einsmeta = eins.getItemMeta();
                einsmeta.setDisplayName("§c§l1v1");
                einsmeta.spigot().setUnbreakable(true);
                eins.setItemMeta(einsmeta);
                p.getInventory().setItem(7, eins);
                p.sendMessage("§c§lHave fun 1v1 people");
                p.closeInventory();
                return;

            case "§cFly":
                String str = p.getAllowFlight() ? "off" : "on";
                p.setAllowFlight(!p.getAllowFlight());
                p.sendMessage("§cFly " + str);
                p.closeInventory();
                return;

            case "§cDome":
                p.performCommand("/hsphere glass 7");
                p.closeInventory();
                return;

            case "§cNoHitDelay":
                Bukkit.broadcastMessage("§c§lNoHitDelay Mode is on!");
                ffaManager.setNoDamageTicks(0);

                for (Player all : Bukkit.getOnlinePlayers()) {
                    all.setMaximumNoDamageTicks(0);
                }

                p.closeInventory();
                return;

            case "§cReset-Hitdelay":
                p.sendMessage("§c§lNoHitDelay Mode is off!");
                ffaManager.setNoDamageTicks(0);

                for (Player all : Bukkit.getOnlinePlayers()) {
                    all.setMaximumNoDamageTicks(20);
                }

                p.closeInventory();
                return;
            }

            break;

        case "§4§lDeath Animation":
            switch (e.getCurrentItem().getItemMeta().getDisplayName()) {

            case "§cInstant":
                p.sendMessage("§c§lIn developement");
                p.closeInventory();
                return;
            case "§cDefault":
                p.sendMessage("§c§lIn developement");
                p.closeInventory();
                return;
            case "§cThunder":
                p.sendMessage("§c§lIn developement");
                p.closeInventory();
                return;
            }
            break;

        case "§c§lDuel":
            switch (e.getCurrentItem().getItemMeta().getDisplayName()) {

            case "§c§lHitDelay":
                p.sendMessage("§c§lIn developement");
                return;
            case "§c§lKit":
                p.sendMessage("§c§lFor now you can only use the FFA Kit do duel");
                return;
            case "§c§lMaps":
                Inventory invis = Bukkit.createInventory(null, 9, "§e§lChoose your duel map");
                p.openInventory(invis);
                return;
            case "§7FFA":
                return;
            case "§2§lSent Duel":
                return;
            }

            break;
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) { // Disable Item Drops
        e.setCancelled(!e.getPlayer().isOp());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) { // Quit Listener
        playerManager.removePlayerConfig(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent e) { // Disable hunger
        e.setCancelled(true);
    }
}