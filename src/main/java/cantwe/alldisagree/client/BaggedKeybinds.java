package cantwe.alldisagree.client;

import cantwe.alldisagree.network.ServerNetworking;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;
import org.lwjgl.glfw.GLFW;

public class BaggedKeybinds {

    private static final KeyBinding OPEN_BACKPACK = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.bagged.open_backpack",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_B,
            "category.bagged.keybindings"));

    public static void initialize() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (OPEN_BACKPACK.wasPressed()) {
                ClientPlayNetworking.send(ServerNetworking.OPEN_BACKPACK, new PacketByteBuf(Unpooled.buffer()));
            }
        });
    }
}
