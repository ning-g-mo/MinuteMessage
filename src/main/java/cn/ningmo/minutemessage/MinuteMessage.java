package cn.ningmo.minutemessage;

import org.bukkit.plugin.java.JavaPlugin;
import cn.ningmo.minutemessage.manager.MessageManager;
import cn.ningmo.minutemessage.manager.ConfigManager;
import cn.ningmo.minutemessage.command.MainCommand;
import cn.ningmo.minutemessage.listener.PlayerListener;
import cn.ningmo.minutemessage.util.UpdateChecker;

public class MinuteMessage extends JavaPlugin {
    private static MinuteMessage instance;
    private ConfigManager configManager;
    private MessageManager messageManager;

    @Override
    public void onEnable() {
        instance = this;
        
        // 显示启动信息
        getLogger().info("&b ___  ___  ___  ___  ___  ___  ___  ___  ___");
        getLogger().info("&b|   \\/   ||   \\/   ||   \\/   ||   \\/   ||   \\");
        getLogger().info("&b| |\\  /| || |\\  /| || |\\  /| || |\\  /| || |\\ \\");
        getLogger().info("&b| | \\/ | || | \\/ | || | \\/ | || | \\/ | || | \\ \\");
        getLogger().info("&b|_|    |_||_|    |_||_|    |_||_|    |_||_|  \\_\\");
        getLogger().info("&b");
        getLogger().info("&b MinuteMessage &fv" + getDescription().getVersion());
        getLogger().info("&b 作者: &f柠枺");
        getLogger().info("&b");
        
        // 初始化配置
        this.configManager = new ConfigManager(this);
        getLogger().info("&a[✓] 配置文件加载完成");
        
        this.messageManager = new MessageManager(this);
        getLogger().info("&a[✓] 消息管理器初始化完成");
        
        // 注册命令
        getCommand("minutemessage").setExecutor(new MainCommand(this));
        getLogger().info("&a[✓] 命令注册完成");
        
        // 注册监听器
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getLogger().info("&a[✓] 事件监听器注册完成");
        
        // 检查 PlaceholderAPI
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getLogger().info("&a[✓] 已找到 PlaceholderAPI");
        } else {
            getLogger().warning("&e[!] 未找到 PlaceholderAPI，变量功能将不可用");
        }
        
        // 检查更新
        if (getConfig().getBoolean("settings.check-update")) {
            getLogger().info("&b[*] 正在检查更新...");
            new UpdateChecker(this).checkUpdate();
        }
        
        // 启动消息任务
        messageManager.startTasks();
        getLogger().info("&a[✓] 定时消息任务已启动");
        
        getLogger().info("&a插件启动完成！");
    }

    @Override
    public void onDisable() {
        getLogger().info("&b正在关闭 MinuteMessage...");
        
        if (messageManager != null) {
            messageManager.stopTasks();
            getLogger().info("&a[✓] 已停止所有定时消息任务");
        }
        
        getLogger().info("&a插件已安全关闭！");
    }

    public static MinuteMessage getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }
} 