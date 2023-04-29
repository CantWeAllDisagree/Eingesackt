package cantwe.alldisagree.item;

import cantwe.alldisagree.config.BackpackInfo;
import net.minecraft.item.DyeableItem;

public class DyeableTrinketBackpackItem extends TrinketBackpackItem implements DyeableItem {

    public DyeableTrinketBackpackItem(BackpackInfo backpack, Settings settings) {
        super(backpack, settings);
    }
}
