package ms.uk.eclipse.commands;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InfoCommand extends Command {

    public InfoCommand() {
        super("strafeffainfo");
        setAliases(Arrays.asList("info"));
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                p.sendMessage("§c§lThis Plugin was made by Jeffrey / Vuzle | Forked by Jaiden / OpaqueEclipse");
            }

        }

        return false;
    }
}
