package strafe.ffa.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDArenas implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("FFAarena")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if(args.length == 0){
                    if (p.hasPermission("strafe.ffa")) {

                        p.sendMessage("§f------------------------");
                        p.sendMessage("§7[§c§lStrafeFFA Arena setup Help§7]");
                        p.sendMessage("§f------------------------");
                        p.sendMessage("§4§lArena-create: " + "§7§l/arenacreate");
                        p.sendMessage("§4§lArena-delete: " + "§7§l/arenadelete");
                        p.sendMessage("§4§lSetspawn1: " + "§7§l/arenasetspawn1");
                        p.sendMessage("§4§lSetspawn2: " + "§7§l/arenasetspawn2");
                        p.sendMessage("§4§lArena-list: " + "§7§l/arenalist");
                        p.sendMessage("§f------------------------");




                    }
                }
            }

        }



        return false;
    }
}
