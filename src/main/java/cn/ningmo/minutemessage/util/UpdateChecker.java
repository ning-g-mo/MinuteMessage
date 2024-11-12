package cn.ningmo.minutemessage.util;

import cn.ningmo.minutemessage.MinuteMessage;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class UpdateChecker {
    private final MinuteMessage plugin;
    private final String MODRINTH_PROJECT_ID = "minutemessage"; // 替换为你的项目ID

    public UpdateChecker(MinuteMessage plugin) {
        this.plugin = plugin;
    }

    public void checkUpdate() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                URL url = new URL("https://api.modrinth.com/v2/project/" + MODRINTH_PROJECT_ID + "/version");
                InputStream inputStream = url.openStream();
                Scanner scanner = new Scanner(inputStream);
                StringBuilder builder = new StringBuilder();
                
                while (scanner.hasNext()) {
                    builder.append(scanner.next());
                }
                
                String latestVersion = parseVersion(builder.toString());
                String currentVersion = plugin.getDescription().getVersion();
                
                if (!currentVersion.equals(latestVersion)) {
                    plugin.getLogger().info(plugin.getConfigManager().getMessage("messages.update-available")
                            .replace("%version%", latestVersion)
                            .replace("%current_version%", currentVersion));
                }
                
                scanner.close();
                inputStream.close();
            } catch (IOException e) {
                plugin.getLogger().warning("检查更新失败: " + e.getMessage());
            }
        });
    }

    private String parseVersion(String json) {
        try {
            // 简单的 JSON 解析，你可能需要使用更好的 JSON 解析库
            return json.split("\"version_number\":\"")[1].split("\"")[0];
        } catch (Exception e) {
            return plugin.getDescription().getVersion();
        }
    }
} 