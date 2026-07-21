package org.ragingzombies.flintnpowder.entity.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.ragingzombies.flintnpowder.Flintnpowder;
import org.ragingzombies.flintnpowder.entity.client.ModModelLayers;
import org.ragingzombies.flintnpowder.entity.client.models.CannonModel;
import org.ragingzombies.flintnpowder.entity.custom.CannonEntity;

public class CannonRenderer extends MobRenderer<CannonEntity, CannonModel<CannonEntity>> {
    public CannonRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new CannonModel<>(pContext.bakeLayer(ModModelLayers.CANNON_LAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(CannonEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath(Flintnpowder.MOD_ID, "textures/entities/cannon.png");
    }

    @Override
    public void render(CannonEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {

        pMatrixStack.pushPose();
        float smoothedY = Mth.lerp(pPackedLight, pEntity.yRotO, pEntity.getYRot());

        pMatrixStack.mulPose(Axis.YP.rotationDegrees(-smoothedY));
        pMatrixStack.popPose();

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
