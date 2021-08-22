## v5.2.0 Changelog
```
* New Gui System (`xyz.thepogramsrc.superocreapi.spigot.gui.Gui`)
* Added new util to retrieve time seconds from string & word
* Code cleanup & Fixes
```

## v5.1.0 Changelog
```
* New Zip Library
* Some Cleanup
```

## v5.0.4 Changelog
```
* Fixed NullPointerException Spam
```

## v5.0.3 Changelog:
```
* Added missing search term argument
```

## v5.0.2 Changelog:
```
* Added custom filter for Browser GUI
```

## v5.0.1-SNAPSHOT Changelog:
```
* Code Cleanup
* 1.8 Support
```

## v5.0.0-SNAPSHOT Changelog:
```
* Updated .gitignore file
* Added Java 16 Support
* Added Caching system for some utilities
* SLF4J-api and SLF4J-nop is now compiled into SuperCoreAPI
* Moved libraries to package xyz.theprogramsrc.supercoreapi.libs
* Added util to retrieve Message Digest Instance
* Added util to generate checksum from file
* Removed placeholder injectors so checksum validation will work
* Dependency Manager will only load if the class LibraryLoader exists
* Added Utils#hasClass to check if a class exists
* Fixed Utils#downloadFile (basically added User Agent)
* Fixed some tests
```

> Sorry for the little gap here, forgot to update this. You can check the changelogs [here](https://github.com/TheProgramSrc/SuperCoreAPI/releases)

## v4.2.13 Changelog:
```
* Moved Log Filter register method to the LogsFilter class
* Bungee Plugin Fixes
* Removed executors for local jar functions
```

## v4.2.12 Changelog:
```
* Migrated Update Checker to Songoda
* Bug Fixes
```

## v4.2.11 Changelog:
```
- Removed tests in SpigotPlugin
```

## v4.2.10 Changelog:
```
+ Added Scroll Browser (Vertical Browser)
+ Added GUIUpdateEvent
* Fixed issues with Browser Search Feature not working
```

## v4.2.9 Changelog:
```
* Now SimpleItem#getDisplayName() and SimpleItem#getLore() doesn't return null to avoid errors 
```

## v4.2.8 Changelog:
```
* Fixed SimpleItem not applying placeholders 
```

## v4.2.7 Changelog:
```
+ Added more checks to the SkinTexture to avoid errors 
```

## v4.2.6 Changelog:
```
* Fixed version in SuperCoreAPI Update Checker
```

## v4.2.5 Changelog:
```
+ Added method to add an error to the last errors list
```

## v4.2.4 Changelog:
```
+ Added a list to save the last errors
```

## v4.2.3 Changelog:
```
+ Added method to build an Exception into a String
+ Added method to upload data to https://paste.theprogramsrc.xyz/
+ Added method to validate json string
```

## v4.2.2 Changelog:
```
+ Added API Update Checker
+ Now the API can be added as jar file to the plugins folder
* Fixes with Songoda Update Checker
```

## v4.2.1 Changelog:
```
+ Now the API check if the MojangAPI (https://sessionserver.mojang.com) is down.
```

## v4.2.0 Changelog:
```
* Code Improvement
* SkinTexture Error Fixes
```

## v4.1.3 Changelog:
```
* Fixed a error that throws an Exception every time that you try to check the SkinTexture and the server doesn't give a response.
```

## v4.1.1 Changelog:
```
+ Added HexColor Parser in SuperUtils
* Bug Fixes with GUI not being closed if GUI#canCloseGUI is disabled
* Changed GUI Update from 5 Ticks to 2 Ticks
```

## v4.1.0 Changelog:
```
+ Re-Added TimerEvent due to fixes with GUI Creators
* Changed RecurringTask package
* MaterialBrowser Fixes
* SimpleItem Improvements
* SkinTexture Improvements
* Added GitHub Heads DataBase
* Added SpigotPlugin Field to SpigotModule
* Code Improvements
* Bug Fixes
```
