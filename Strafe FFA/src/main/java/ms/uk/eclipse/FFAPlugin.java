package ms.uk.eclipse;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import ms.uk.eclipse.commands.DuelCommand;
import ms.uk.eclipse.commands.FFACommand;
import ms.uk.eclipse.commands.InfoCommand;
import ms.uk.eclipse.commands.SpectateCommand;
import ms.uk.eclipse.config.FileConfiguration;
import ms.uk.eclipse.listeners.EventListeners;
import ms.uk.eclipse.managers.FFAManager;
import ms.uk.eclipse.managers.PlayerManager;
import net.minecraft.server.v1_8_R3.MinecraftServer;

public class FFAPlugin extends JavaPlugin {

    static FFAPlugin instance;
    FileConfiguration arenaConfig;
    FFAManager ffaManager;
    PlayerManager playerManager;

    public void onEnable() {
        instance = this;

        arenaConfig = new FileConfiguration("arenas.yml", "plugins/FFA");
        ffaManager = new FFAManager();
        playerManager = new PlayerManager();

        System.out.println("StrafeFFA is enabled");

        registerCommands(new InfoCommand(), new FFACommand(), new DuelCommand(), new SpectateCommand());

        registerListeners(new EventListeners());

    }

    public void onDisable() {

        System.out.println("StrafeFFA is disabled");
    }

    public FileConfiguration getArenaConfig() {
        return arenaConfig;
    }

    public static FFAPlugin getInstance() {
        return instance;
    }

    public void registerCommands(Command... cmds) {
        for (Command c : cmds) {
            MinecraftServer.getServer().server.getCommandMap().register(c.getName(), "FFA", c);
        }
    }

    public void registerListeners(Listener... listeners) {
        for (Listener l : listeners) {
            Bukkit.getPluginManager().registerEvents(l, this);
        }
    }

    public FFAManager getFFAManager() {
        return ffaManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }
}
