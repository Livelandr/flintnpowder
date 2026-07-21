package org.ragingzombies.flintnpowder.entity.client;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.ragingzombies.flintnpowder.Flintnpowder;

@Mod.EventBusSubscriber(modid = Flintnpowder.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModModelLayers {
    public static final ModelLayerLocation MORTAR_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(Flintnpowder.MOD_ID, "mortar_layer"), "main");

    public static final ModelLayerLocation OLDMORTAR_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(Flintnpowder.MOD_ID, "oldmortar_layer"), "main");

    public static final ModelLayerLocation CANNON_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(Flintnpowder.MOD_ID, "cannon_layer"), "main");

    public static final ModelLayerLocation HYPER_SKELETON_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(Flintnpowder.MOD_ID, "hyper_skelet_layer"), "main");
}
