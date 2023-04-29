package cantwe.alldisagree.item;

import cantwe.alldisagree.Bagged;
import cantwe.alldisagree.config.BackpackInfo;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class TrinketBackpackItem extends BackpackItem implements Trinket {

    public TrinketBackpackItem(BackpackInfo backpack, Settings settings) {
        super(backpack, settings);
    }

    @Override
    public boolean canUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if(Bagged.CONFIG.requireEmptyForUnequip) {
            return Bagged.isBackpackEmpty(stack);
        }

        return true;
    }
}
