package cn.ningmo.minutemessage.listener;

import cn.ningmo.minutemessage.MinuteMessage;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.Bukkit;

public class PlayerListener implements Listener {
    private final MinuteMessage plugin;

    public PlayerListener(MinuteMessage plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!plugin.getConfig().getBoolean("settings.player.join.enabled")) {
            return;
        }

        Player player = event.getPlayer();
        String message = plugin.getConfigManager().formatMessage(
            plugin.getConfig().getString("settings.player.join.message", "")
        );
        
        message = message.replace("%player_name%", player.getName());
        if (plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            message = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, message);
        }
        
        // 发送消息给玩家
        player.sendMessage(message);
        
        // 如果启用了广播，发送给其他玩家
        if (plugin.getConfig().getBoolean("settings.player.join.broadcast")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p != player) {
                    p.sendMessage(message);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (!plugin.getConfig().getBoolean("settings.player.quit.enabled")) {
            return;
        }

        Player player = event.getPlayer();
        String message = plugin.getConfigManager().formatMessage(
            plugin.getConfig().getString("settings.player.quit.message", "")
        );
        
        message = message.replace("%player_name%", player.getName());
        if (plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            message = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, message);
        }
        
        // 如果启用了广播，发送给其他玩家
        if (plugin.getConfig().getBoolean("settings.player.quit.broadcast")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p != player) {
                    p.sendMessage(message);
                }
            }
        }
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        if (!plugin.getConfig().getBoolean("settings.player.dimension.enabled")) {
            return;
        }

        Player player = event.getPlayer();
        Environment env = player.getWorld().getEnvironment();
        String messageKey;

        switch (env) {
            case NETHER:
                messageKey = "settings.player.dimension.messages.to_nether";
                break;
            case THE_END:
                messageKey = "settings.player.dimension.messages.to_end";
                break;
            case NORMAL:
                messageKey = "settings.player.dimension.messages.to_overworld";
                break;
            default:
                return;
        }

        String message = plugin.getConfigManager().formatMessage(
            plugin.getConfig().getString(messageKey, "")
        );
        
        if (plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            message = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, message);
        }
        
        // 发送消息给玩家
        player.sendMessage(message);
        
        // 如果启用了广播，发送给其他玩家
        if (plugin.getConfig().getBoolean("settings.player.dimension.broadcast")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p != player) {
                    p.sendMessage(message);
                }
            }
        }
    }
} 