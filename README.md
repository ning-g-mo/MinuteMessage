# MinuteMessage

[English](README_EN.md) | 简体中文

一个支持多语言的 Minecraft 定时消息发送插件。

## 功能特点

- 🌏 多语言支持
- ⏰ 自定义消息间隔
- 🎯 多种消息类型（控制台、聊天、广播、世界）
- 🔄 版本更新检测
- 🎨 支持颜色代码和换行符
- 👋 玩家加入/退出消息
- 🌍 维度切换提示

## 状态

- 开发中
- 已知问题：PlaceholderAPI 变量支持正在修复中

## 使用方法

### 命令

- `/mm reload` - 重载配置
- `/mm list` - 查看所有消息组
- `/mm interval <组名> <分钟>` - 设置消息间隔
- `/mm toggle join` - 开关加入消息
- `/mm toggle quit` - 开关退出消息
- `/mm toggle dimension` - 开关维度切换消息

### 权限

- `minutemessage.admin` - 管理员权限

### 配置文件 

```yml
# MinuteMessage 配置文件
# 插件版本: 1.0.0
# 作者: 柠枺

# ========================
# 基础设置
# ========================
settings:
  # 是否检查更新
  check-update: true
  # 默认语言 (目前支持: zh_CN)
  language: 'zh_CN'
  # 玩家相关消息设置
  player:
    # 玩家加入消息
    join:
      enabled: false
      # 是否广播给其他玩家
      broadcast: true
      message: '&a欢迎 &f%player_name% &a加入服务器！'
    # 玩家退出消息
    quit:
      enabled: false
      # 是否广播给其他玩家
      broadcast: true
      message: '&e玩家 &f%player_name% &e离开了服务器'
    # 维度切换消息
    dimension:
      enabled: false
      # 是否广播给其他玩家
      broadcast: false
      messages:
        to_nether: '&c>> 你已进入下界'
        to_end: '&5>> 你已进入末地'
        to_overworld: '&2>> 你已返回主世界'

# ========================
# 消息组配置
# ========================
# 配置格式说明:
# messages:
#   <消息组名称>:
#     interval: <发送间隔(分钟)>
#     type: <消息类型>
#     worlds: [世界名称列表]  # 仅在 type: WORLD 时需要
#     content: [消息内容列表]
#
# 消息类型(type)说明:
# - CONSOLE: 仅在控制台显示
# - CHAT: 发送给所有玩家
# - BROADCAST: 全服广播
# - WORLD: 指定世界广播
#
# 支持的格式:
# - 颜色代码: &a, &b, &c 等
# - 换行符: \n
# - PlaceholderAPI 变量: %player_name% 等
# ========================

messages:
  # 欢迎消息示例
  welcome:
    interval: 15  # 每15分钟发送一次
    type: BROADCAST
    content:
      - '&a欢迎来到服务器！\n&b当前在线人数: %server_online%'
      - '&e感谢游玩我们的服务器！'
  
  # 规则提醒示例
  rules:
    interval: 30  # 每30分钟发送一次
    type: CHAT
    content:
      - '&6=== 服务器规则 ===\n&f1. 禁止使用作弊客户端\n&f2. 禁止恶意破坏\n&f3. 请与其他玩家和谐相处'
  
  # 世界公告示例
  world_notice:
    interval: 45  # 每45分钟发送一次
    type: WORLD
    worlds: 
      - 'world'
      - 'world_nether'
    content:
      - '&b这条消息只在主世界和地狱显示'

# ========================
# 命令说明
# ========================
# /mm reload - 重载配置
# /mm list - 查看所有消息组
# /mm interval <组名> <分钟> - 设置消息间隔
#
# 权限节点:
# minutemessage.admin - 管理员权限
# ========================

# ========================
# PlaceholderAPI 变量示例
# ========================
# %server_online% - 在线玩家数
# %server_max_players% - 最大玩家数
# %player_name% - 玩家名称
# %player_world% - 玩家所在世界
# 更多变量请访问: https://github.com/PlaceholderAPI/PlaceholderAPI/wiki/Placeholders
# ======================== 
```

## 安装

1. 下载最新版本的插件
2. 将插件放入服务器的 plugins 文件夹
3. 重启服务器或重载插件

## 依赖

- Spigot/Paper 1.20.1+
- PlaceholderAPI（可选）

## 开发计划

- [x] 基础消息系统
- [x] 多语言支持
- [x] 玩家事件消息
- [x] PlaceholderAPI 支持修复
- [ ] 更多消息类型
- [ ] 消息模板系统

## 贡献

欢迎提交 Issue 和 Pull Request！

## 许可证

本项目采用 MIT 许可证
