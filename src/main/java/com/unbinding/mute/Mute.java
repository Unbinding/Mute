package com.unbinding.mute;

import com.sun.corba.se.pept.transport.ListenerThread;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.material.Command;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

/**
 * Created by broct on 6/19/2016.
 */
public class Mute extends JavaPlugin implements Listener {

    ArrayList<String> muted;

    public void onEnable(){
        muted = new ArrayList<String>();
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        if(commandLabel.equalsIgnoreCase("mute")) {
            if (!sender.hasPermission("mute.mute")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to perform this command.");
                return true;
            }
            if(args.length == 0){
                sender.sendMessage(ChatColor.RED + "/mute <player>");
                return true;
            }

            Player t = this.getServer().getPlayer(args[0]);
            if(t == null){
                sender.sendMessage(ChatColor.RED + "That player isn't online!");
                return true;
            }
            if(muted.contains(t.getName())){
                muted.remove(t.getName());
                sender.sendMessage(ChatColor.RED + "Unmuted player " + t.getName());
            }else{
                muted.add(t.getName());
                sender.sendMessage(ChatColor.RED + "Muted player " + t.getName());
            }
        }
        return true;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        if(muted.contains(e.getPlayer().getName())){
            e.getPlayer().sendMessage(ChatColor.RED + "Nope! You are muted.");
            e.setCancelled(true);
        }
    }



}
