package cantwe.alldisagree.client;

import cantwe.alldisagree.item.BackpackItem;
import cantwe.alldisagree.util.BackpackUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;


public class BackpackFeature extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    public BackpackFeature(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {


        ItemStack chestSlot = player.getEquippedStack(EquipmentSlot.CHEST);

        BackpackUtil.renderBackpack(this.getContextModel(), matrices, vertexConsumers, light, player.getEquippedStack(EquipmentSlot.CHEST));

        if(chestSlot.getItem() instanceof BackpackItem) {
            matrices.push();

            // Initial transformation
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));
            matrices.translate(0, -0.2, -0.25);

            // Shifting
            if(player.isSneaking()) {
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(25));
                matrices.translate(0, -0.2, 0);
            }

            MinecraftClient.getInstance().getItemRenderer().renderItem(chestSlot, ModelTransformation.Mode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);
            matrices.pop();
        }
    }
}
