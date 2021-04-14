package ms.uk.eclipse.commands;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class SpectateCommand extends Command {

    public SpectateCommand() {
        super("spectate");
        setAliases(Arrays.asList("spec"));
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {

        return false;
    }

}
