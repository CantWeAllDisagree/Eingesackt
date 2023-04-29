package cantwe.alldisagree.compat;

import cantwe.alldisagree.Bagged;
import com.misterpemodder.shulkerboxtooltip.api.ShulkerBoxTooltipApi;
import com.misterpemodder.shulkerboxtooltip.api.provider.PreviewProviderRegistry;
import com.misterpemodder.shulkerboxtooltip.impl.provider.EnderChestPreviewProvider;
import net.minecraft.item.Item;

import java.util.stream.Collectors;

public class BaggedShulkerBoxTooltipCompat implements ShulkerBoxTooltipApi {

    @Override
    public void registerProviders(PreviewProviderRegistry registry) {
        registry.register(Bagged.id("backpack"), new BaggedPreviewProvider(), Bagged.BACKPACKS.stream().map(backpack -> (Item) backpack).collect(Collectors.toList()));
        registry.register(Bagged.id("ender_pouch"), new EnderChestPreviewProvider(), Bagged.ENDER_POUCH);
    }
}
