package cantwe.alldisagree.api;

import cantwe.alldisagree.Bagged;
import cantwe.alldisagree.client.TrinketBackpackRenderer;
import cantwe.alldisagree.config.BackpackInfo;
import cantwe.alldisagree.item.BackpackItem;
import cantwe.alldisagree.item.DyeableTrinketBackpackItem;
import cantwe.alldisagree.item.EnderBackpackItem;
import cantwe.alldisagree.item.TrinketBackpackItem;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;

import java.util.Optional;

public class TrinketCompat {

    public static void registerTrinketPredicate() {
        TrinketsApi.registerTrinketPredicate(Bagged.id("any_backpack"), (stack, slot, entity) -> {
            if(stack.getItem() instanceof BackpackItem || stack.getItem() instanceof EnderBackpackItem) {
                return TriState.TRUE;
            }

            return TriState.DEFAULT;
        });
    }

    public static void spillTrinketInventory(PlayerEntity player) {
        Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(player);
        if(component.isPresent()) {
            TrinketComponent trinketComponent = component.get();
            for (Pair<SlotReference, ItemStack> pair : trinketComponent.getAllEquipped()) {
                ItemStack stack = pair.getRight();
                if(stack.getItem() instanceof BackpackItem) {
                    Bagged.getBackpackContents(stack).forEach(backpackItem -> player.dropItem(backpackItem, true, false));
                    Bagged.wipeBackpack(stack);
                    player.dropItem(stack, true, false);
                    pair.getLeft().inventory().clear();
                }
            }
        }
    }

    public static void registerTrinketBackpack(Item item) {
        TrinketsApi.registerTrinket(item, (TrinketBackpackItem) item);
    }

    public static BackpackItem createTrinketBackpack(BackpackInfo backpack, FabricItemSettings settings) {
        return backpack.isDyeable() ? new DyeableTrinketBackpackItem(backpack, settings) : new TrinketBackpackItem(backpack, settings);
    }

    public static void registerTrinketRenderer(BackpackItem backpack) {
        TrinketRendererRegistry.registerRenderer(backpack, new TrinketBackpackRenderer());
    }
}
