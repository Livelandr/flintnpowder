package org.ragingzombies.flintnpowder.entity.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.ragingzombies.flintnpowder.Flintnpowder;
import org.ragingzombies.flintnpowder.entity.client.ModModelLayers;
import org.ragingzombies.flintnpowder.entity.client.models.MortarModel;
import org.ragingzombies.flintnpowder.entity.custom.MortarEntity;

public class MortarRenderer extends MobRenderer<MortarEntity, MortarModel<MortarEntity>> {
    public MortarRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new MortarModel<>(pContext.bakeLayer(ModModelLayers.MORTAR_LAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(MortarEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath(Flintnpowder.MOD_ID, "textures/entities/mortar.png");
    }

    @Override
    public void render(MortarEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
