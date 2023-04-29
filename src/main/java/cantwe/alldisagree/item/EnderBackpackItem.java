package cantwe.alldisagree.item;

import cantwe.alldisagree.Bagged;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class EnderBackpackItem extends Item {

    public static final Text CONTAINER_NAME = Text.translatable("container.enderchest");

    public EnderBackpackItem() {
        super(new Item.Settings().group(Bagged.GROUP).maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        EnderChestInventory enderChestInventory = player.getEnderChestInventory();

        if (enderChestInventory != null) {
            if (!world.isClient) {
                player.openHandledScreen(new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) ->
                        GenericContainerScreenHandler.createGeneric9x3(i, playerInventory, enderChestInventory), CONTAINER_NAME));

                player.incrementStat(Stats.OPEN_ENDERCHEST);
            }
        }

        return TypedActionResult.success(player.getStackInHand(hand));
    }
}
