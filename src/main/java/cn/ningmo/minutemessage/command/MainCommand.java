package cn.ningmo.minutemessage.command;

import cn.ningmo.minutemessage.MinuteMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class MainCommand implements CommandExecutor, TabCompleter {
    private final MinuteMessage plugin;

    public MainCommand(MinuteMessage plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("minutemessage.admin")) {
            sender.sendMessage(plugin.getConfigManager().getMessage("commands.no-permission"));
            return true;
        }

        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                plugin.getConfigManager().reload();
                plugin.getMessageManager().stopTasks();
                plugin.getMessageManager().startTasks();
                sender.sendMessage(plugin.getConfigManager().getMessage("commands.reload"));
                break;
            case "list":
                // TODO: 实现消息组列表显示
                break;
            case "interval":
                // TODO: 实现间隔修改功能
                break;
            case "toggle":
                if (args.length < 2) {
                    sendHelp(sender);
                    return true;
                }
                switch (args[1].toLowerCase()) {
                    case "join":
                        boolean joinEnabled = !plugin.getConfig().getBoolean("settings.player.join.enabled");
                        plugin.getConfig().set("settings.player.join.enabled", joinEnabled);
                        plugin.saveConfig();
                        sender.sendMessage(plugin.getConfigManager().getPrefix() + 
                                         "玩家加入消息已" + (joinEnabled ? "启用" : "禁用"));
                        break;
                    case "quit":
                        boolean quitEnabled = !plugin.getConfig().getBoolean("settings.player.quit.enabled");
                        plugin.getConfig().set("settings.player.quit.enabled", quitEnabled);
                        plugin.saveConfig();
                        sender.sendMessage(plugin.getConfigManager().getPrefix() + 
                                         "玩家退出消息已" + (quitEnabled ? "启用" : "禁用"));
                        break;
                    case "dimension":
                        boolean dimEnabled = !plugin.getConfig().getBoolean("settings.player.dimension.enabled");
                        plugin.getConfig().set("settings.player.dimension.enabled", dimEnabled);
                        plugin.saveConfig();
                        sender.sendMessage(plugin.getConfigManager().getPrefix() + 
                                         "维度切换消息已" + (dimEnabled ? "启用" : "禁用"));
                        break;
                    default:
                        sendHelp(sender);
                        break;
                }
                break;
            default:
                sendHelp(sender);
                break;
        }
        return true;
    }

    private void sendHelp(CommandSender sender) {
        // 修改这里，使用 getMessage 方法获取帮助信息
        List<String> helpMessages = plugin.getConfigManager().getStringList("commands.help");
        for (String line : helpMessages) {
            sender.sendMessage(line);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("reload");
            completions.add("list");
            completions.add("interval");
            completions.add("toggle");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle")) {
            completions.add("join");
            completions.add("quit");
            completions.add("dimension");
        }
        return completions;
    }
} 