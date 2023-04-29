package cantwe.alldisagree.api;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class InventoryTab {

    private final Class<?>[] screenClasses;
    private final Text title;
    @Nullable
    private final Identifier texture;
    private final int preferredPos;

    public InventoryTab(Text title, @Nullable Identifier texture, int preferredPos, Class<?>... screenClasses) {
        this.screenClasses = screenClasses;
        this.title = title;
        this.texture = texture;
        this.preferredPos = preferredPos;
    }

    public Text getTitle() {
        return this.title;
    }

    @Nullable
    public Identifier getTexture() {
        return this.texture;
    }

    @Nullable
    public ItemStack getItemStack(MinecraftClient client) {
        return null;
    }

    public int getPreferredPos() {
        return this.preferredPos;
    }

    public boolean shouldShow(MinecraftClient client) {
        return true;
    }

    public void onClick(MinecraftClient client) {
    }

    public boolean canClick(Class<?> screenClass, MinecraftClient client) {
        return !isSelectedScreen(screenClass);
    }

    public boolean isSelectedScreen(Class<?> screenClass) {
        for (Class<?> aClass : screenClasses) {
            if (aClass.equals(screenClass)) {
                return true;
            }
        }
        return false;
    }

}
