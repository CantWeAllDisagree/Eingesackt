package cantwe.alldisagree.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import cantwe.alldisagree.client.BaggedKeybinds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.KeyBinding;

@Environment(EnvType.CLIENT)
@Mixin(BaggedKeybinds.class)
public interface BaggedKeybindsAccessor {

    @Accessor("OPEN_BACKPACK")
    static KeyBinding getOpenBackpackKeyBinding() {
        throw new AssertionError("This should not occur!");
    }

}
