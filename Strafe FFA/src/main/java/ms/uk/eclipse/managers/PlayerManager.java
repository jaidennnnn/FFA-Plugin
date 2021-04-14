package ms.uk.eclipse.managers;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ms.uk.eclipse.config.FileConfiguration;

public class PlayerManager {
    HashMap<UUID, FileConfiguration> playerConfigFiles = new HashMap<>();
    HashMap<UUID, Integer> playerKills = new HashMap<>();
    HashMap<UUID, Integer> playerDeaths = new HashMap<>();
    final ItemStack[] armourContents = new ItemStack[4];
    final ItemStack[] contents = new ItemStack[36];

    public PlayerManager() {
        armourContents[0] = new ItemStack(Material.DIAMOND_BOOTS, 1);
        armourContents[1] = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
        armourContents[2] = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
        armourContents[3] = new ItemStack(Material.DIAMOND_HELMET, 1);

        for (ItemStack itemStack : armourContents) {
            itemStack.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            ItemMeta meta = itemStack.getItemMeta();
            meta.spigot().setUnbreakable(true);
            itemStack.setItemMeta(meta);
        }

        ItemStack ffa = new ItemStack(Material.DIAMOND_SWORD);
        ffa.addEnchantment(Enchantment.DAMAGE_ALL, 3);
        ItemMeta spawnmeta = ffa.getItemMeta();
        spawnmeta.setDisplayName("§7§lFFA");
        spawnmeta.spigot().setUnbreakable(true);
        ffa.setItemMeta(spawnmeta);

        contents[0] = ffa;

        ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE, 10);
        ItemMeta gapplesp = gapple.getItemMeta();
        gapplesp.setDisplayName("§7§lYUM");
        gapple.setItemMeta(gapplesp);

        contents[1] = gapple;

        ItemStack special = new ItemStack(Material.BLAZE_ROD);
        ItemMeta specialmeta = special.getItemMeta();
        specialmeta.setDisplayName("§7§lSettings");
        specialmeta.spigot().setUnbreakable(true);
        special.setItemMeta(specialmeta);

        contents[8] = special;
    }

    public void setPlayerConfig(UUID u, FileConfiguration f) {
        playerConfigFiles.put(u, f);
    }

    public FileConfiguration getPlayerConfig(UUID u) {
        return playerConfigFiles.get(u);
    }

    public FileConfiguration removePlayerConfig(UUID u) {
        return playerConfigFiles.remove(u);
    }

    public void updateKills(UUID u, int i) {
        playerKills.put(u, i);
    }

    public void updateDeaths(UUID u, int i) {
        playerDeaths.put(u, i);
    }

    public int getDeaths(UUID u) {
        return playerDeaths.getOrDefault(u, 0);
    }

    public int getKills(UUID u) {
        return playerDeaths.getOrDefault(u, 0);
    }

    public void setKit(Player p) {
        p.getInventory().setArmorContents(armourContents);
        p.getInventory().setContents(contents);
    }
}
