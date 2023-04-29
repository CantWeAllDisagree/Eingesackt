package cantwe.alldisagree.mixins;

import cantwe.alldisagree.item.BackpackItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ItemStack checkStack) {
            ItemStack thisStack = (ItemStack) (Object) this;

            Item thisStackItem = thisStack.getItem();
            Item checkStackItem = checkStack.getItem();

            if(thisStackItem instanceof BackpackItem && checkStackItem instanceof BackpackItem) {
                return true;
            }
        }

        return super.equals(obj);
    }
}
