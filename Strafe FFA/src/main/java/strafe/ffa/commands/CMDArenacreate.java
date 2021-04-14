package strafe.ffa.commands;

import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import strafe.ffa.main.Main;

public class CMDArenacreate implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("arenacreate")) {
            Player p = (Player) sender;
            if (args.length == 1) {



                   Main.arena.set("Arena " + args[0], "");




                   p.sendMessage("Created TEST");
                   try {
                       Main.arena.save(Main.arenas);
                   } catch (IOException e) {
                       e.printStackTrace();
                   }


                }
            }






        return false;
    }
}
