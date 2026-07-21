package org.ragingzombies.flintnpowder.entity.client.renderers;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.ragingzombies.flintnpowder.Flintnpowder;
import org.ragingzombies.flintnpowder.entity.client.ModModelLayers;
import org.ragingzombies.flintnpowder.entity.client.models.HyperSkeletonModel;
import org.ragingzombies.flintnpowder.entity.custom.HyperSkeletonEntity;

@OnlyIn(Dist.CLIENT)
public class HyperSkeletonRenderer extends HumanoidMobRenderer<HyperSkeletonEntity, HyperSkeletonModel<HyperSkeletonEntity>> {
    private static final ResourceLocation SKELETON_LOCATION = ResourceLocation.fromNamespaceAndPath(Flintnpowder.MOD_ID, "textures/entities/cultist.png");

    public HyperSkeletonRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new HyperSkeletonModel<>(pContext.bakeLayer(ModModelLayers.HYPER_SKELETON_LAYER)), 0.5F);

        this.addLayer(new HumanoidArmorLayer<>(
                this,
                new HumanoidModel<>(pContext.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel<>(pContext.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                pContext.getModelManager()
        ));
    }

    public ResourceLocation getTextureLocation(HyperSkeletonEntity pEntity) {
        return SKELETON_LOCATION;
    }
}