package cantwe.alldisagree.client;

import cantwe.alldisagree.Bagged;
import cantwe.alldisagree.item.DyeableBackpackItem;
import cantwe.alldisagree.item.DyeableTrinketBackpackItem;
import cantwe.alldisagree.item.TrinketBackpackItem;
import cantwe.alldisagree.model.BabyBackpackModel;
import cantwe.alldisagree.model.BackpackModel;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.registry.Registry;


public class TrinketBackpackRenderer implements TrinketRenderer {
    private final BackpackModel backpackModel = new BackpackModel(BackpackModel.getTexturedModelData().createModel());
    private final BabyBackpackModel babyBackpackModel = new BabyBackpackModel(BabyBackpackModel.getTexturedModelData().createModel());


    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity player, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if(!Bagged.CONFIG.trinketRendering) {
            return;
        }
            if (!player.getEquippedStack(EquipmentSlot.CHEST).isEmpty() && player.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof TrinketBackpackItem) {
                return;
            }

            matrices.push();
            if (contextModel instanceof BipedEntityModel) {
                ModelPart modelPart = ((BipedEntityModel<?>) contextModel).body;
                modelPart.rotate(matrices);
            }
            matrices.translate(0D, -0.9D, 0.2D);

            Model backpackModel = stack.isOf(Bagged.BACKPACKS.get(0)) ? this.babyBackpackModel : this.backpackModel;

            VertexConsumer vertexConsumer = ItemRenderer.getItemGlintConsumer(vertexConsumers,
                    backpackModel.getLayer(new Identifier("bagged", "textures/entity/" + Registry.ITEM.getId(stack.getItem()).getPath() + ".png")), false, stack.hasGlint());

            float f = 1.0F;
            float g = 1.0F;
            float h = 1.0F;
            if (stack.getItem() instanceof DyeableTrinketBackpackItem) {
                int i = ((DyeableTrinketBackpackItem) stack.getItem()).getColor(stack);
                f = (float) (i >> 16 & 0xFF) / 255.0f;
                g = (float) (i >> 8 & 0xFF) / 255.0f;
                h = (float) (i & 0xFF) / 255.0f;
            } else if (stack.getItem() instanceof DyeableBackpackItem) {
                int i = ((DyeableBackpackItem) stack.getItem()).getColor(stack);
                f = (float) (i >> 16 & 0xFF) / 255.0f;
                g = (float) (i >> 8 & 0xFF) / 255.0f;
                h = (float) (i & 0xFF) / 255.0f;
            }
            backpackModel.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, f, g, h, 1.0F);
            matrices.pop();
        }
}
