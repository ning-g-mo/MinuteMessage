package cn.ningmo.minutemessage.manager;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.ChatColor;

import cn.ningmo.minutemessage.MinuteMessage;
import me.clip.placeholderapi.PlaceholderAPI;

import java.util.*;

public class MessageManager {
    private final MinuteMessage plugin;
    private final Map<String, BukkitTask> tasks;
    private final Random random;

    public MessageManager(MinuteMessage plugin) {
        this.plugin = plugin;
        this.tasks = new HashMap<>();
        this.random = new Random();
    }

    public void startTasks() {
        ConfigurationSection messagesSection = plugin.getConfig().getConfigurationSection("messages");
        if (messagesSection == null) return;

        for (String key : messagesSection.getKeys(false)) {
            ConfigurationSection group = messagesSection.getConfigurationSection(key);
            if (group == null) continue;

            int interval = group.getInt("interval", 15);
            String type = group.getString("type", "BROADCAST");
            List<String> content = group.getStringList("content");
            List<String> worlds = group.getStringList("worlds");

            startMessageTask(key, interval, type, content, worlds);
        }
    }

    private void startMessageTask(String key, int interval, String type, List<String> messages, List<String> worlds) {
        if (tasks.containsKey(key)) {
            tasks.get(key).cancel();
            tasks.remove(key);
        }

        BukkitTask task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            if (messages.isEmpty()) return;
            
            final String finalMessage = parsePlaceholders(messages.get(random.nextInt(messages.size())));

            switch (type.toUpperCase()) {
                case "CONSOLE":
                    Bukkit.getConsoleSender().sendMessage(finalMessage);
                    break;
                case "CHAT":
                    Bukkit.getOnlinePlayers().forEach(player -> 
                        player.sendMessage(finalMessage));
                    break;
                case "BROADCAST":
                    Bukkit.broadcastMessage(finalMessage);
                    break;
                case "WORLD":
                    for (String worldName : worlds) {
                        World world = Bukkit.getWorld(worldName);
                        if (world != null) {
                            world.getPlayers().forEach(player -> 
                                player.sendMessage(finalMessage));
                        }
                    }
                    break;
            }
        }, 20L * 60L * interval, 20L * 60L * interval);

        tasks.put(key, task);
    }

    public void stopTasks() {
        tasks.values().forEach(BukkitTask::cancel);
        tasks.clear();
    }

    private String parsePlaceholders(String message) {
        message = ChatColor.translateAlternateColorCodes('&', 
                 message.replace("\\n", "\n")
                       .replace("§", "&"));
        
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            try {
                if (!Bukkit.getOnlinePlayers().isEmpty()) {
                    message = PlaceholderAPI.setPlaceholders(
                        Bukkit.getOnlinePlayers().iterator().next(), 
                        message
                    );
                } else {
                    message = PlaceholderAPI.setPlaceholders(null, message);
                }
            } catch (Exception e) {
                plugin.getLogger().warning("处理占位符时出错: " + e.getMessage());
            }
        }
        return message;
    }
} 