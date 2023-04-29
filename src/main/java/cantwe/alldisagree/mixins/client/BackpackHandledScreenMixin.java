package cantwe.alldisagree.mixins.client;

import cantwe.alldisagree.mixins.BaggedKeybindsAccessor;
import org.spongepowered.asm.mixin.Mixin;

import cantwe.alldisagree.ui.BackpackHandledScreen;
import cantwe.alldisagree.ui.BackpackScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
@Mixin(BackpackHandledScreen.class)
public abstract class BackpackHandledScreenMixin extends HandledScreen<BackpackScreenHandler> {

    public BackpackHandledScreenMixin(BackpackScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (BaggedKeybindsAccessor.getOpenBackpackKeyBinding().matchesKey(keyCode, scanCode)) {
            this.close();
            return true;
        } else
            return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
