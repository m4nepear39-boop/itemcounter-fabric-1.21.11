package com.example.itemcounter;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

/** Main entry point: runs when the game starts. */
public class ItemCounterClient implements ClientModInitializer {
    public static final String MOD_ID = "itemcounter";

    private static KeyBinding editKey;

    @Override
    public void onInitializeClient() {
        ItemCounterConfig.load();

        // 1) Keybind (change it in Options > Controls > Key Binds > "Item Counter")
        KeyBinding.Category category = KeyBinding.Category.create(Identifier.of(MOD_ID, "main"));
        editKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.itemcounter.edit_hud",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT, // default key
                category
        ));

        // 2) Every tick, check if the key was pressed -> open the edit screen
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (editKey.wasPressed()) {
                if (client.currentScreen == null && client.player != null) {
                    client.setScreen(new HudEditScreen());
                }
            }
        });

        // 3) Draw the counter on the HUD every frame
        HudElementRegistry.attachElementBefore(
                VanillaHudElements.CHAT,
                Identifier.of(MOD_ID, "item_counter"),
                (context, tickCounter) -> ItemCounterHud.render(context)
        );
    }
}
