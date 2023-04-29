package cantwe.alldisagree;

import cantwe.alldisagree.item.BackpackItem;
import cantwe.alldisagree.util.BackpackTab;
import cantwe.alldisagree.api.TrinketCompat;
import cantwe.alldisagree.client.BaggedKeybinds;
import cantwe.alldisagree.ui.BackpackHandledScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.item.DyeableItem;

@Environment(EnvType.CLIENT)
public class BaggedClient implements ClientModInitializer {
    private static final boolean isLibZLoaded = FabricLoader.getInstance().isModLoaded("libz");

    @Override
    public void onInitializeClient()
    {
        if (isLibZLoaded) {
            BackpackTab.init();
        }
        HandledScreens.register(Bagged.CONTAINER_TYPE, BackpackHandledScreen::new);
        BaggedKeybinds.initialize();

        for (BackpackItem backpack : Bagged.BACKPACKS) {
            if (Bagged.TRINKETS_LOADED) {
                TrinketCompat.registerTrinketRenderer(backpack);
            }

            if (backpack instanceof DyeableItem dyeableItem) {
                ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
                    if (tintIndex > 0) {
                        return -1;
                    } else {
                        return dyeableItem.getColor(stack);
                    }
                }, backpack);
            }}
    }

}
