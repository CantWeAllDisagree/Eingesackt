package cantwe.alldisagree.util;

import cantwe.alldisagree.Bagged;
import cantwe.alldisagree.item.BackpackItem;
import cantwe.alldisagree.item.DyeableBackpackItem;
import cantwe.alldisagree.item.DyeableTrinketBackpackItem;
import cantwe.alldisagree.model.BabyBackpackModel;
import cantwe.alldisagree.model.BackpackModel;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("rawtypes")
@Environment(EnvType.CLIENT)
public class BackpackUtil {

    public static boolean isTrinketsLoaded = FabricLoader.getInstance().isModLoaded("trinkets");

    private static final BackpackModel backpackModel = new BackpackModel(BackpackModel.getTexturedModelData().createModel());
    private static final BabyBackpackModel babyBackpackModel = new BabyBackpackModel(BabyBackpackModel.getTexturedModelData().createModel());

    public static void renderBackpack(PlayerEntityModel playerEntityModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemStack itemStack) {
        if (itemStack.getItem() instanceof BackpackItem) {
            matrices.push();
            ModelPart modelPart = playerEntityModel.body;
            modelPart.rotate(matrices);

            matrices.translate(0D, -0.9D, 0.2D);

            Model backpackModel = itemStack.isOf(Bagged.BACKPACKS.get(0)) ? BackpackUtil.babyBackpackModel : BackpackUtil.backpackModel;

            VertexConsumer vertexConsumer = ItemRenderer.getItemGlintConsumer(vertexConsumers,
                    backpackModel.getLayer(new Identifier("bagged", "textures/entity/" + Registry.ITEM.getId(itemStack.getItem()).getPath() + ".png")), false, itemStack.hasGlint());

            float f = 1.0F;
            float g = 1.0F;
            float h = 1.0F;
            if (itemStack.getItem() instanceof DyeableBackpackItem) {
                int i = ((DyeableBackpackItem) itemStack.getItem()).getColor(itemStack);
                f = (float) (i >> 16 & 0xFF) / 255.0f;
                g = (float) (i >> 8 & 0xFF) / 255.0f;
                h = (float) (i & 0xFF) / 255.0f;
            } else if (isTrinketsLoaded && itemStack.getItem() instanceof DyeableTrinketBackpackItem) {
                int i = ((DyeableTrinketBackpackItem) itemStack.getItem()).getColor(itemStack);
                f = (float) (i >> 16 & 0xFF) / 255.0f;
                g = (float) (i >> 8 & 0xFF) / 255.0f;
                h = (float) (i & 0xFF) / 255.0f;
            }
            backpackModel.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, f, g, h, 1.0F);
            matrices.pop();
        }
    }
    @Nullable
    public static ItemStack getEquippedBackpack(PlayerEntity playerEntity) {
        if (isTrinketsLoaded && TrinketsApi.getTrinketComponent(playerEntity).get().isEquipped(stack -> stack.getItem() instanceof BackpackItem))
            return TrinketsApi.getTrinketComponent(playerEntity).get().getEquipped(stack -> stack.getItem() instanceof BackpackItem).get(0).getRight();
        else if (playerEntity.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof BackpackItem)
            return playerEntity.getEquippedStack(EquipmentSlot.CHEST);
        return null;
    }

}
