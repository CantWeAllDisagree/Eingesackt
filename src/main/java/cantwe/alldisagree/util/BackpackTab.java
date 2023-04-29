package cantwe.alldisagree.util;

import cantwe.alldisagree.network.ServerNetworking;
import cantwe.alldisagree.ui.BackpackHandledScreen;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import net.libz.api.InventoryTab;
import net.libz.registry.TabRegistry;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class BackpackTab extends InventoryTab {

    public BackpackTab(Text title, @Nullable Identifier texture, int preferredPos, Class<?>... screenClasses) {
        super(title, texture, preferredPos, screenClasses);
    }

    @Nullable
    @Override
    public ItemStack getItemStack(MinecraftClient client) {
        return BackpackUtil.getEquippedBackpack(client.player);
    }

    @Override
    public boolean shouldShow(MinecraftClient client) {
        return BackpackUtil.getEquippedBackpack(client.player) != null && !Objects.requireNonNull(BackpackUtil.getEquippedBackpack(client.player)).isEmpty();
    }

    @Override
    public void onClick(MinecraftClient client) {
        ClientPlayNetworking.send(ServerNetworking.OPEN_BACKPACK, new PacketByteBuf(Unpooled.buffer()));
    }

    // Had to outsource registry here
    public static void init() {
        TabRegistry.registerInventoryTab(new BackpackTab(Text.translatable("screen.bagged.backpack_screen"), null, -1, BackpackHandledScreen.class));
    }
}
