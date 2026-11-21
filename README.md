# Item Counter (Fabric 1.21.11)

## Setup (once)
1. Install JDK 21 and IntelliJ IDEA (Community is fine).
2. Go to https://fabricmc.net/develop/template and generate a template
   (Minecraft 1.21.11, mod id `itemcounter`, package `com.example.itemcounter`, Yarn mappings).
3. Copy `gradlew`, `gradlew.bat` and the `gradle/` folder from that template into this folder
   (they are the Gradle wrapper, which can't be included here).
4. Open this folder in IntelliJ -> let Gradle import.

## Run / build
- Test: run the `Minecraft Client` run config (or `./gradlew runClient`).
- Build jar: `./gradlew build` -> `build/libs/itemcounter-1.0.0.jar`
  (put it in your mods folder with Fabric API).

## Use in game
- Press **Right Shift** to open the edit screen (change it in Controls).
- Drag the box with the mouse, type an item id in the text field.
- Press **ESC** to save and close.
