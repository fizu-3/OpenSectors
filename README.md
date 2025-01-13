# OpenSectors [1.8]

This is a Minecraft server plugin that divides the world into individual servers and synchronizes your data with them.

# Features

  - Going through sector border and teleporting between sectors
  - Player data synchronization between sectors
  - Synchronization of sector information

# Requirements

  - One velocity server
  - min. 2 spigot servers
  - One redis distribution

# Configuration 

The configuration of the sectors themselves is simple and clear. Just set the location of each sector and provide the redia distribution data in config

**Sectors configuration**

```JSON
{
  "sectorMap": {
    "s1": {
      "name": "s1",
      "sectorType": "NORMAL",
      "minX": -100,
      "maxX": 1000,
      "minZ": 100,
      "maxZ": 1000
    },
    "spawn_1": {
      "name": "spawn_1",
      "sectorType": "SPAWN",
      "minX": -100,
      "maxX": 100,
      "minZ": -100,
      "maxZ": 100
    }
  }
}
```

**Database Configuration**

```JSON
{
  "redisHost": "localhost",
  "redisPassword": "root",
  "redisPort": 6379
}

```

**Spigot Configuration**

```JSON
{
  "currentSector": "s1"
}

```

# Bug

if you found a bug in OpenSectors write to me on discord **fizuxd**

# TODO

  - Make an easy-to-use **API** for developers
  - Support for newer MC versions

# Thanks for Support <3
