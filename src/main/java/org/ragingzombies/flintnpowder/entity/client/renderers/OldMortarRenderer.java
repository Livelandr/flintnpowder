package org.ragingzombies.flintnpowder.entity.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.ragingzombies.flintnpowder.Flintnpowder;
import org.ragingzombies.flintnpowder.entity.client.ModModelLayers;
import org.ragingzombies.flintnpowder.entity.client.models.MortarModel;
import org.ragingzombies.flintnpowder.entity.client.models.OldMortarModel;
import org.ragingzombies.flintnpowder.entity.custom.MortarEntity;
import org.ragingzombies.flintnpowder.entity.custom.OldMortarEntity;

public class OldMortarRenderer extends MobRenderer<OldMortarEntity, OldMortarModel<OldMortarEntity>> {
    public OldMortarRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new OldMortarModel<>(pContext.bakeLayer(ModModelLayers.OLDMORTAR_LAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(OldMortarEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath(Flintnpowder.MOD_ID, "textures/entities/oldmortar.png");
    }

    @Override
    public void render(OldMortarEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
