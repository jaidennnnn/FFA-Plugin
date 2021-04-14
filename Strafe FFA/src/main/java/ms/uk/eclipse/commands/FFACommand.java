package ms.uk.eclipse.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FFACommand extends Command {

    public FFACommand() {
        super("ffa");
        setPermission("strafe.ffa");
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                if ((p.hasPermission("strafe.ffa"))) {

                    p.sendMessage("§f------------------------");
                    p.sendMessage("§7[§c§lStrafeFFA duels Help§7]");
                    p.sendMessage("§f------------------------");
                    p.sendMessage("§4§lArena-setup: " + "§7§l/FFAarena");
                    p.sendMessage("§4§lSpec: " + "§7§l/FFAspecgui");
                    p.sendMessage("§4§lInfo: " + "§7§l/strafeffainfo");
                    p.sendMessage("§f------------------------");

                } else {
                    p.sendMessage("§4§lYou have no perms!");
                }
            }
        }

        return false;
    }
}
