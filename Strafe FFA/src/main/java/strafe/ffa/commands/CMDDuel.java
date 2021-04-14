package strafe.ffa.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CMDDuel implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("duel")) {

            if (args.length == 1) {
                if (!args[0].equals(sender.getName())) {
                    if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0]))) {

                       

                        Inventory inv = Bukkit.createInventory(null, 36, "§c§lDuel");

                        ItemStack navi = new ItemStack(Material.BOOK);
                        ItemMeta spawnmeta = navi.getItemMeta();
                        spawnmeta.setDisplayName("§c§lKit");
                        navi.setItemMeta(spawnmeta);
                        inv.setItem(1, navi);


                        ItemStack kit = new ItemStack(Material.GOLDEN_APPLE);
                        ItemMeta kitmeta = kit.getItemMeta();
                        kitmeta.setDisplayName("§7FFA");
                        kit.setItemMeta(kitmeta);
                        inv.setItem(10, kit);




                        ItemStack send = new ItemStack(Material.WOOL, 1, (short) 5);
                        ItemMeta sendmeta = kit.getItemMeta();
                        sendmeta.setDisplayName("§2§lSent duel");
                        send.setItemMeta(sendmeta);
                        inv.setItem(31, send);

                        ItemStack map = new ItemStack(Material.PAPER);
                        ItemMeta mapmeta = map.getItemMeta();
                        mapmeta.setDisplayName("§c§lMaps");
                        map.setItemMeta(mapmeta);
                        inv.setItem(4, map);



                        ItemStack NH = new ItemStack(Material.RAW_FISH, 1, (short) 3);
                        ItemMeta NHmeta = map.getItemMeta();
                        NHmeta.setDisplayName("§c§lHitDelay");
                        NH.setItemMeta(NHmeta);
                        inv.setItem(7, NH);


                        ((Player) sender).openInventory(inv);
                        return true;
                    }

                } else {
                    sender.sendMessage("§c§lYou can't duel yourself");
                }


            } else {
                sender.sendMessage("§eUsage /duel [player]");
            }


        }


        return false;
    }
}
