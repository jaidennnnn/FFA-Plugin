package de.strafe.profiles.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventListeners implements Listener {


    @EventHandler

    public void onJoin(PlayerJoinEvent e) {

        Player p = (Player) e.getPlayer();

        p.sendMessage("TEST");




    }

    
}
