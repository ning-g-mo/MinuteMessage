package cn.ningmo.minutemessage.manager;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import cn.ningmo.minutemessage.MinuteMessage;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigManager {
    private final MinuteMessage plugin;
    private FileConfiguration langConfig;
    private File langFile;

    public ConfigManager(MinuteMessage plugin) {
        this.plugin = plugin;
        loadConfig();
        loadLang();
    }

    public void loadConfig() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
    }

    public void loadLang() {
        String lang = plugin.getConfig().getString("settings.language", "zh_CN");
        if (langFile == null) {
            langFile = new File(plugin.getDataFolder(), "lang/" + lang + ".yml");
        }

        if (!langFile.exists()) {
            langFile.getParentFile().mkdirs();
            plugin.saveResource("lang/" + lang + ".yml", false);
        }

        langConfig = YamlConfiguration.loadConfiguration(langFile);

        InputStream defLangStream = plugin.getResource("lang/" + lang + ".yml");
        if (defLangStream != null) {
            YamlConfiguration defLang = YamlConfiguration.loadConfiguration(
                new InputStreamReader(defLangStream, StandardCharsets.UTF_8));
            langConfig.setDefaults(defLang);
        }
    }

    public String getMessage(String path) {
        String message = langConfig.getString(path);
        if (message == null) {
            return "Message not found: " + path;
        }
        return formatMessage(message);
    }

    public String formatMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', 
               message.replace("\\n", "\n")
                     .replace("§", "&"));
    }

    public String getPrefix() {
        return getMessage("prefix");
    }

    public void reload() {
        loadConfig();
        loadLang();
    }

    public FileConfiguration getLangConfig() {
        return langConfig;
    }

    public List<String> getStringList(String path) {
        List<String> messages = langConfig.getStringList(path);
        return messages.stream()
                .map(this::formatMessage)
                .collect(Collectors.toList());
    }
} 