package strafe.ffa.main;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import strafe.ffa.commands.*;
import strafe.ffa.eventlistener.EventListeners;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {

    public static File arenas = new File("plugins/FFA/arenas.yml");
    public static FileConfiguration arena = (FileConfiguration) YamlConfiguration.loadConfiguration(arenas);


    public static File path = new File("plugins/FFA");
    public static FileConfiguration dir = (FileConfiguration) YamlConfiguration.loadConfiguration(path);




    public void onEnable() {
        if (!path.exists()) {
            path.mkdir();
            try {
                arenas.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




        System.out.println("StrafeFFa is enabled");


        this.getCommand("duel").setExecutor(new CMDDuel());
        this.getCommand("strafeffainfo").setExecutor(new CMDInfo());
        this.getCommand("ffa").setExecutor(new CMDFFA());
        this.getCommand("FFAarena").setExecutor(new CMDArenas());
        this.getCommand("arenacreate").setExecutor(new CMDArenacreate());


        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new EventListeners(), this);


    }



    public void onDisable() {

        System.out.println("StrafeFFA is disabled");
    }



}
