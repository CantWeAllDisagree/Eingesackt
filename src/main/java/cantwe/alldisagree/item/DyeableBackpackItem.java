package cantwe.alldisagree.item;

import cantwe.alldisagree.config.BackpackInfo;
import net.minecraft.item.DyeableItem;

public class DyeableBackpackItem extends BackpackItem implements DyeableItem {

    public DyeableBackpackItem(BackpackInfo backpack, Settings settings) {
        super(backpack, settings);
    }
}
