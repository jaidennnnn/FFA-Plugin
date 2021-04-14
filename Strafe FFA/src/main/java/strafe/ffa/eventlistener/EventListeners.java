package strafe.ffa.eventlistener;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
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

import strafe.ffa.methode.setscreen;

public class EventListeners implements Listener {
    private int killCount;
    private int deathCount;
    public static Inventory invis = Bukkit.createInventory(null, 9, "§e§lChoose your duel map");

    @EventHandler

    public void onBreakingBlocks(BlockBreakEvent e) {          //Stops breaking blocks


        if (e.getPlayer().isOp() == false) {
            e.setCancelled(true);

        } else {
            if (e.getPlayer().isOp() == true) {
                e.setCancelled(false);
            }
        }
    }


    @EventHandler

    public void onDeath(PlayerDeathEvent e) {        //Stops Item drops on death

        e.getDrops().clear();


    }


    @EventHandler

    public void onDrag(InventoryDragEvent e) {       //Stops draging Items around
        e.setCancelled(true);
    }


    @EventHandler

    public void onDamage(EntityDamageByEntityEvent e) {         // It does the teleport aka pvplounge Death animation
        Player p = (Player) e.getEntity();
        Player killer = (Player) e.getDamager();
        if (e.getFinalDamage() >= p.getHealth()) {
            p.teleport(p.getWorld().getSpawnLocation());
            p.setHealth(20);
            p.giveExpLevels(-1000);
            DecimalFormat df = new DecimalFormat("#.##");
            p.sendMessage("§f§lYou got killed by §4§l" + e.getDamager().getName() + " " + df.format(killer.getHealth())
                    + " §l\u2764 §4");
            p.getInventory().clear();

            killer.giveExpLevels(1);
            killer.setHealth(20);
            killer.sendMessage("§8§lYou killed  §4§l" + e.getEntity().getName());
            killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 5));
            ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE);
            gapple.setAmount(10);
            ItemMeta gapplesp = gapple.getItemMeta();
            gapplesp.setDisplayName("§7§lYum");
            gapple.setItemMeta(gapplesp);

            p.setFoodLevel(100);

            p.getInventory().setItem(1, gapple);
            gapple.setAmount(2);
            killer.getInventory().addItem(gapple);

            p.sendMessage("§cHere you go gapples refilled");


            File fileplayer = new File("plugins//UUIDS//" + p.getUniqueId() + ".yml");
            File filekiller = new File("plugins//UUIDS//" + killer.getUniqueId() + ".yml");
            YamlConfiguration yamlConfigurationplayer = YamlConfiguration.loadConfiguration(fileplayer);
            YamlConfiguration yamlConfigurationkiller = YamlConfiguration.loadConfiguration(filekiller);

            killCount = yamlConfigurationkiller.getInt("Kills") + 1;
            deathCount = yamlConfigurationplayer.getInt("Deaths") + 1;


            yamlConfigurationkiller.set("Kills", killCount);
            yamlConfigurationplayer.set("Deaths", deathCount);


            try {
                yamlConfigurationkiller.save(filekiller);
                yamlConfigurationplayer.save(fileplayer);
            } catch (IOException e1) {

                e1.printStackTrace();
            }
            setscreen sets = new setscreen();
            sets.setScoreboard(p, Bukkit.getOnlinePlayers().size(), yamlConfigurationplayer.getInt("Kills"), deathCount);
            sets.setScoreboard(killer, Bukkit.getOnlinePlayers().size(), killCount, yamlConfigurationkiller.getInt("Deaths"));


            if (p.isOp() == true) {
                ItemStack special = new ItemStack(Material.WATCH);
                ItemMeta specialmeta = special.getItemMeta();
                specialmeta.setDisplayName("§4§lOwner-Gui");
                specialmeta.spigot().setUnbreakable(true);
                special.setItemMeta(specialmeta);

                p.getInventory().setItem(7, special);


            }

            if (killer.getLevel() == 5) {
                killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 2));
                killer.sendMessage("§7§lCongratulations here is your reward");
                Bukkit.broadcastMessage("  §e§l" + killer.getName() + " §bmade a total killstreak of 5 Players!");
            }

            if (killer.getLevel() == 10) {
                killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 2));
                killer.sendMessage("§7§lCongratulations here is your reward");
                Bukkit.broadcastMessage("  §e§l" + killer.getName() + " §bmade a total killstreak of 10 Players!");
            }

            if (killer.getLevel() == 15) {
                killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 2));
                killer.sendMessage("§7§lCongratulations here is your reward");
                Bukkit.broadcastMessage("  §e§l" + killer.getName() + " §bmade a total killstreak of 15 Players!");
            }

            if (killer.getLevel() == 20) {
                killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 2));
                killer.sendMessage("§7§lCongratulations here is your reward");
                Bukkit.broadcastMessage("  §e§l" + killer.getName() + " §bmade a total killstreak of 20 Players!");
            }


            
            

           

            ItemStack special = new ItemStack(Material.BLAZE_ROD);
            ItemMeta specialmeta = special.getItemMeta();
            specialmeta.setDisplayName("§7§lSettings");
            specialmeta.spigot().setUnbreakable(true);
            special.setItemMeta(specialmeta);

            p.getInventory().setItem(8, special);


            ItemStack ffa = new ItemStack(Material.DIAMOND_SWORD);
            ffa.addEnchantment(Enchantment.DAMAGE_ALL, 3);
            ItemMeta spawnmeta = ffa.getItemMeta();
            spawnmeta.setDisplayName("§7§lFFA");
            spawnmeta.spigot().setUnbreakable(true);
            ffa.setItemMeta(spawnmeta);

            p.getInventory().setItem(0, ffa);

            ItemStack[] armor = new ItemStack[4];
            armor[0] = new ItemStack(Material.DIAMOND_BOOTS, 1);
            armor[1] = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
            armor[2] = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
            armor[3] = new ItemStack(Material.DIAMOND_HELMET, 1);


            for (ItemStack itemStack : armor) {
                itemStack.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                ItemMeta meta = itemStack.getItemMeta();
                meta.spigot().setUnbreakable(true);
                itemStack.setItemMeta(meta);


            }

            p.getInventory().setArmorContents(armor);
            e.setCancelled(true);
        }

        Player player1 = p.getPlayer();
        Player player2 = p.getKiller();

        if (p.getInventory().getItemInHand().getType() == Material.STICK) {
            player2.teleport(new Location(p.getWorld(), 0, 0, 0));
            player1.teleport(new Location(p.getWorld(), 0, 0, 0));
            e.setCancelled(true);


        }

    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent e) {             //Disable Fall-Damage


        if (e.getEntity() instanceof Player) {
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {


                e.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {             //Gives all start Items aka JoinEvent
        File file = new File("plugins//UUIDS//" + e.getPlayer().getUniqueId() + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {


            yamlConfiguration.set("Kills", 0);
            yamlConfiguration.set("Deaths", 0);

            try {
                yamlConfiguration.save(file);
            } catch (IOException e1) {

                e1.printStackTrace();
            }
        }
        Player p = e.getPlayer();
        setscreen sets = new setscreen();
        sets.setScoreboard(p, Bukkit.getOnlinePlayers().size(), yamlConfiguration.getInt("Kills"), yamlConfiguration.getInt("Deaths"));


        e.setJoinMessage("FULL ALPHA");
        p.setFoodLevel(20);
        p.setHealth(20);
        p.giveExpLevels(-1000);

        if (p.isOp() == true) {
            ItemStack special = new ItemStack(Material.WATCH);
            ItemMeta specialmeta = special.getItemMeta();
            specialmeta.setDisplayName("§4§lOwner-Gui");
            specialmeta.spigot().setUnbreakable(true);
            special.setItemMeta(specialmeta);
            p.getInventory().setItem(7, special);

        }



        ItemStack special = new ItemStack(Material.BLAZE_ROD);
        ItemMeta specialmeta = special.getItemMeta();
        specialmeta.setDisplayName("§7§lSettings");
        specialmeta.spigot().setUnbreakable(true);
        special.setItemMeta(specialmeta);

        p.getInventory().setItem(8, special);

        ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE);
        gapple.setAmount(10);
        ItemMeta gapplesp = gapple.getItemMeta();
        gapplesp.setDisplayName("§7§lYUM");
        gapple.setItemMeta(gapplesp);

        ItemStack ffa = new ItemStack(Material.DIAMOND_SWORD);
        ffa.addEnchantment(Enchantment.DAMAGE_ALL, 3);
        ItemMeta spawnmeta = ffa.getItemMeta();
        spawnmeta.setDisplayName("§7§lFFA");
        spawnmeta.spigot().setUnbreakable(true);
        ffa.setItemMeta(spawnmeta);


        ItemStack[] armor = new ItemStack[4];
        armor[0] = new ItemStack(Material.DIAMOND_BOOTS, 1);
        armor[1] = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
        armor[2] = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
        armor[3] = new ItemStack(Material.DIAMOND_HELMET, 1);


        for (ItemStack itemStack : armor) {
            itemStack.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            ItemMeta meta = itemStack.getItemMeta();
            meta.spigot().setUnbreakable(true);
            itemStack.setItemMeta(meta);


        }

        // meta.setDisplayName("Yum-Boots");

        p.getInventory().setArmorContents(armor);
        p.getInventory().setItem(0, ffa);
        p.getInventory().setItem(1, gapple);


    }


    @EventHandler
    public void onInteract(PlayerInteractEvent e) {                         //Player Interact Event

        Player p = e.getPlayer();

        if(p.getItemInHand().getItemMeta()!= null) {
            if (p.getItemInHand().getItemMeta().getDisplayName().equals("§4§lOwner-Gui")) {
                Inventory inv = Bukkit.createInventory(null, 9, "§4§lMenu");

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


            }

            if (p.getItemInHand().getItemMeta().getDisplayName().equals("§7§lSettings")) {
                Inventory inv = Bukkit.createInventory(null, 9, "§4§lDeath Animation");

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


            }
         

            }

        }



    @EventHandler

    public void onClick(InventoryClickEvent e) {            //Make things alive by clicking or having it in hand


        Player p = (Player) e.getWhoClicked();
        if (e.getInventory().getName().equalsIgnoreCase("§4§lMenu")) {

            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c1v1")) {

                ItemStack eins = new ItemStack(Material.STICK);
                ItemMeta einsmeta = eins.getItemMeta();
                einsmeta.setDisplayName("§c§l1v1");
                einsmeta.spigot().setUnbreakable(true);
                eins.setItemMeta(einsmeta);
                p.getInventory().setItem(7, eins);
                p.sendMessage("§c§lHave fun 1v1 people");
                p.closeInventory();
            }


            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cFly")) {
                if (p.getAllowFlight()) {
                    p.setAllowFlight(false);
                    p.setFlying(false);
                    p.sendMessage("§cFly off");
                    p.closeInventory();
                    return;
                } else {

                    p.setAllowFlight(true);
                    p.setFlying(true);
                    p.sendMessage("§cfly on");
                    p.closeInventory();
                    return;
                }
            }


            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cDome")) {
                p.performCommand("/hsphere glass 7");
                p.closeInventory();

            }


            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cNoHitDelay")) {
                Bukkit.broadcastMessage("§c§lNoHitDelay Mode is on!");
                for (Player all : Bukkit.getOnlinePlayers()) {
                    all.setMaximumNoDamageTicks(0);
                for(OfflinePlayer all2 : Bukkit.getOfflinePlayers()) {
                    all.setMaximumNoDamageTicks(0);
                    p.closeInventory();

                    }

                }

            }

            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cReset-Hitdelay")) {
                p.sendMessage("§c§lNoHitDelay Mode is off!");
                for (Player all : Bukkit.getOnlinePlayers()) {
                all.setMaximumNoDamageTicks(20);
                for (OfflinePlayer all2 : Bukkit.getOfflinePlayers()) {
                    all.setMaximumNoDamageTicks(20);
                e.setCancelled(true);
                p.closeInventory();


                }

                }

            }

        }

        Player p2 = (Player) e.getWhoClicked();
        if (e.getInventory().getName().equalsIgnoreCase("§4§lDeath Animation")) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cInstant")) {
                p2.sendMessage("§c§lIn developement");
                p2.closeInventory();


            }

            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cDefault")) {
                p2.sendMessage("§c§lIn developement");
                p2.closeInventory();


            }

            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cThunder")) {
                p2.sendMessage("§c§lIn developement");
                p2.closeInventory();

            }

        }

        Player p3 = (Player) e.getWhoClicked();
        if (e.getInventory().getName().equalsIgnoreCase("§c§lDuel")) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c§lHitDelay")) {
                p3.sendMessage("§c§lIn developement");
               e.setCancelled(true);

            }

            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c§lKit")) {
                p3.sendMessage("§c§lFor now you can only use the FFA Kit do duel");

                e.setCancelled(true);
            }

             if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c§lMaps")) {
                Inventory invis = Bukkit.createInventory(null, 9, "§e§lChoose your duel map");
                p.openInventory(invis);


            }

            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7FFA")) {
                e.setCancelled(true);
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§2§lSent Duel")) {
                e.setCancelled(true);
            }


        }


    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {   // Disable Item Drops


        if(e.getPlayer().isOp()== false)

            e.setCancelled(true);
    }


    @EventHandler
    public void onQuit(PlayerQuitEvent e) {         //Quit Listener


        e.getPlayer().getInventory().clear();



    }


    @EventHandler
    public void onFood(FoodLevelChangeEvent e) {   //Disable hunger

        e.setCancelled(true);




    }



}