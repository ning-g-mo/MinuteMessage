# MinuteMessage

[简体中文](README.md) | English

A multilingual timed message plugin for Minecraft servers.

## Features

- 🌏 Multi-language support
- ⏰ Custom message intervals
- 🎯 Multiple message types (Console, Chat, Broadcast, World)
- 🔄 Version update checker
- 🎨 Color codes and line breaks support
- 👋 Player join/quit messages
- 🌍 Dimension change notifications

## Status

- Under development
- Known issues: PlaceholderAPI variables support is being fixed

## Usage

### Commands

- `/mm reload` - Reload configuration
- `/mm list` - View all message groups
- `/mm interval <group> <minutes>` - Set message interval
- `/mm toggle join` - Toggle join messages
- `/mm toggle quit` - Toggle quit messages
- `/mm toggle dimension` - Toggle dimension change messages

### Permissions

- `minutemessage.admin` - Admin permission

### Configuration 
```yml
# MinuteMessage Configuration File
# Plugin Version: 1.0.0
# Author: 柠枺

# ========================
# Basic Settings
# ========================
settings:
  # Whether to check for updates
  check-update: true
  # Default language (currently supports: zh_CN)
  language: 'zh_CN'
  # Player-related message settings
  player:
    # Player join message
    join:
      enabled: false
      # Whether to broadcast to other players
      broadcast: true
      message: '&aWelcome &f%player_name% &a to the server!'
    # Player quit message
    quit:
      enabled: false
      # Whether to broadcast to other players
      broadcast: true
      message: '&ePlayer &f%player_name% &e has left the server'
    # Dimension switch message
    dimension:
      enabled: false
      # Whether to broadcast to other players
      broadcast: false
      messages:
        to_nether: '&c>> You have entered the Nether'
        to_end: '&5>> You have entered the End'
        to_overworld: '&2>> You have returned to the Overworld'

# ========================
# Message Group Configuration
# ========================
# Configuration format description:
# messages:
#   <message group name>:
#     interval: <send interval (minutes)>
#     type: <message type>
#     worlds: [list of world names]  # Only needed when type: WORLD
#     content: [list of message content]
#
# Message type (type) description:
# - CONSOLE: Displayed only in the console
# - CHAT: Sent to all players
# - BROADCAST: Server-wide broadcast
# - WORLD: Specific world broadcast
#
# Supported formats:
# - Color codes: &a, &b, &c, etc.
# - New line character: \n
# - PlaceholderAPI variables: %player_name%, etc.
# ========================

messages:
  # Welcome message example
  welcome:
    interval: 15  # Sent every 15 minutes
    type: BROADCAST
    content:
      - '&aWelcome to the server!\n&bCurrent online players: %server_online%'
      - '&eThank you for playing on our server!'
  
  # Rules reminder example
  rules:
    interval: 30  # Sent every 30 minutes
    type: CHAT
    content:
      - '&6=== Server Rules ===\n&f1. No cheating clients\n&f2. No malicious destruction\n&f3. Please get along with other players'
  
  # World notice example
  world_notice:
    interval: 45  # Sent every 45 minutes
    type: WORLD
    worlds: 
      - 'world'
      - 'world_nether'
    content:
      - '&bThis message is only displayed in the Overworld and the Nether'

# ========================
# Command Description
# ========================
# /mm reload - Reload configuration
# /mm list - View all message groups
# /mm interval <group name> <minutes> - Set message interval
#
# Permission nodes:
# minutemessage.admin - Admin permissions
# ========================

# ========================
# PlaceholderAPI Variable Examples
# ========================
# %server_online% - Number of online players
# %server_max_players% - Maximum number of players
# %player_name% - Player name
# %player_world% - Player's current world
# For more variables, please visit: https://github.com/PlaceholderAPI/PlaceholderAPI/wiki/Placeholders
# ========================

```

## Installation

1. Download the latest version
2. Place the plugin in your server's plugins folder
3. Restart server or reload plugin

## Dependencies

- Spigot/Paper 1.20.1+
- PlaceholderAPI (optional)

## Roadmap

- [x] Basic message system
- [x] Multi-language support
- [x] Player event messages
- [ ] PlaceholderAPI support fix
- [ ] More message types
- [ ] Message template system

## Contributing

Issues and Pull Requests are welcome!

## License

This project is licensed under the MIT License