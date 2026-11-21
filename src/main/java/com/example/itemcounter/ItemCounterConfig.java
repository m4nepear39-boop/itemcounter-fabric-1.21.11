package com.example.itemcounter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/** Stores the settings (position + chosen item) in config/itemcounter.json */
public class ItemCounterConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("itemcounter.json");

    public int x = 10;
    public int y = 10;
    public String item = "minecraft:diamond";

    // The one and only config object used by the whole mod
    public static ItemCounterConfig INSTANCE = new ItemCounterConfig();

    public static void load() {
        try {
            if (Files.exists(FILE)) {
                ItemCounterConfig loaded = GSON.fromJson(Files.readString(FILE), ItemCounterConfig.class);
                if (loaded != null) INSTANCE = loaded;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            Files.writeString(FILE, GSON.toJson(INSTANCE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
