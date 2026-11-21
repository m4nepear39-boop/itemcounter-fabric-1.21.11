package com.example.itemcounter;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

/** Everything about drawing the counter box. */
public class ItemCounterHud {
    public static final int HEIGHT = 16;

    /** Turns the text from the config (like "minecraft:diamond" or just "diamond") into an Item. */
    public static Item getItem() {
        Identifier id = Identifier.tryParse(ItemCounterConfig.INSTANCE.item.trim().toLowerCase());
        if (id == null || !Registries.ITEM.containsId(id)) return Items.AIR;
        return Registries.ITEM.get(id);
    }

    /** Counts the item in the whole inventory (hotbar, main, armor, offhand). */
    public static int countItem(Item item) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || item == Items.AIR) return 0;
        PlayerInventory inv = client.player.getInventory();
        int total = 0;
        for (int i = 0; i < inv.size(); i++) {
            ItemStack stack = inv.getStack(i);
            if (stack.isOf(item)) total += stack.getCount();
        }
        return total;
    }

    /** Width of the clickable/draggable area. */
    public static int getWidth() {
        if (getItem() == Items.AIR) {
            return MinecraftClient.getInstance().textRenderer.getWidth("Invalid item");
        }
        return 16;
    }

    public static void render(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.options.hudHidden) return;
        // The edit screen draws the counter itself
        if (client.currentScreen instanceof HudEditScreen) return;
        draw(context);
    }

    /** Draws the item icon with the count in the corner, like a normal inventory slot. */
    public static void draw(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        int x = ItemCounterConfig.INSTANCE.x;
        int y = ItemCounterConfig.INSTANCE.y;

        Item item = getItem();
        if (item == Items.AIR) {
            context.drawTextWithShadow(client.textRenderer, "Invalid item", x, y + 4, 0xFFFF5555);
            return;
        }
        ItemStack stack = new ItemStack(item);
        context.drawItem(stack, x, y);
        // Draws the number at the bottom-right of the icon (same as vanilla stack counts)
        context.drawStackOverlay(client.textRenderer, stack, x, y, String.valueOf(countItem(item)));
    }
}
