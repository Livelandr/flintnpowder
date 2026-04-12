package org.ragingzombies.flintnpowder.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.ragingzombies.flintnpowder.item.guns.ModItemsGuns;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.ModProjectiles;
import net.minecraftforge.client.event.ComputeFovModifierEvent;

import static org.ragingzombies.flintnpowder.Flintnpowder.MOD_ID;

// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModHandler {
    /*
    static AttachmentRenderer renderer;

    public static AttachmentRenderer getRenderer() {
        if (renderer == null) {
            renderer = new AttachmentRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(),
                    Minecraft.getInstance().getEntityModels());
        }
        return renderer;
    }
     */

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Shotgun
        ItemProperties.register(
                ModItemsGuns.PUMPACTIONSHOTGUN.get(),
                new ResourceLocation(MOD_ID, "shotgun_pumping"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("IsUncocked") ? 1.0F : 0.0F;
                }
        );
        // Musket
        ItemProperties.register(
                ModItemsGuns.MUSKET.get(),
                new ResourceLocation(MOD_ID, "musket_primed"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("IsCocked") ? 1.0F : 0.0F;
                }
        );
        ItemProperties.register(
                ModItemsGuns.MUSKET.get(),
                new ResourceLocation(MOD_ID, "musket_bayonet"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("HaveBayonet") ? 1.0F : 0.0F;
                }
        );
        // Pistol
        ItemProperties.register(
                ModItemsGuns.PISTOL.get(),
                new ResourceLocation(MOD_ID, "pistol_primed"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("IsCocked") ? 1.0F : 0.0F;
                }
        );
        // Handgun
        ItemProperties.register(
                ModItemsGuns.SEMIAUTOPISTOL.get(),
                new ResourceLocation(MOD_ID, "slider"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("SlideCocked") ? 1.0F : 0.0F;
                }
        );

        // Cast Iron Roundshot Projectile
        EntityRenderers.register(ModProjectiles.CASTIRONROUNDSHOTPROJECTILE.get(), ThrownItemRenderer::new);
        // Steel Roundshot Projectile
        EntityRenderers.register(ModProjectiles.STEELROUNDSHOTPROJECTILE.get(), ThrownItemRenderer::new);
        // Copper Roundshot Projectile
        EntityRenderers.register(ModProjectiles.COPPERROUNDSHOTPROJECTILE.get(), ThrownItemRenderer::new);
        // Buckshot Projectile
        EntityRenderers.register(ModProjectiles.BUCKSHOTPROJECTILE.get(), ThrownItemRenderer::new);
        // Slug Projectile
        EntityRenderers.register(ModProjectiles.SLUGPROJECTILE.get(), ThrownItemRenderer::new);
        // Dragon Breath Projectile
        EntityRenderers.register(ModProjectiles.DRAGONBREATHPROJECTILE.get(), ThrownItemRenderer::new);
        // Pistol Projectile
        EntityRenderers.register(ModProjectiles.PISTOLROUNDPROJECTILE.get(), ThrownItemRenderer::new);


    }
}