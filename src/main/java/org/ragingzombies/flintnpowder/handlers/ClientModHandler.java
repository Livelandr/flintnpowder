/*
 * Copyright (C) 2026 Livelandr
 *
 * This file is part of Flint'N'Powder.
 *
 * Flint'N'Powder is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Flint'N'Powder is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package org.ragingzombies.flintnpowder.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import com.livelandr.flintcore.core.guns.GunBase;
import org.ragingzombies.flintnpowder.entity.ModEntities;
import org.ragingzombies.flintnpowder.entity.client.renderers.*;
import org.ragingzombies.flintnpowder.entity.custom.OldMortarEntity;
import org.ragingzombies.flintnpowder.item.ModItemsAttachments;
import org.ragingzombies.flintnpowder.item.ModItemsGuns;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.ModProjectiles;

import static net.minecraft.resources.ResourceLocation.fromNamespaceAndPath;
import static org.ragingzombies.flintnpowder.Flintnpowder.MOD_ID;

// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModHandler {

    // Maybe, some day...
    static AttachmentRenderer renderer;

    public static AttachmentRenderer getRenderer() {
        if (renderer == null) {
            renderer = new AttachmentRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(),
                    Minecraft.getInstance().getEntityModels());
        }
        return renderer;
    }

    @SubscribeEvent
    public static void registerOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAbove(
                VanillaGuiOverlay.VIGNETTE.id(),
                "hpopticoverlay",
                new AimingOverlay()
        );
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // MOBS
        EntityRenderers.register(ModEntities.OLDMORTAR.get(), OldMortarRenderer::new);
        EntityRenderers.register(ModEntities.MORTAR.get(), MortarRenderer::new);
        EntityRenderers.register(ModEntities.CANNON.get(), CannonRenderer::new);
        EntityRenderers.register(ModEntities.HYPER_SKELETON.get(), HyperSkeletonRenderer::new);

        // GUNS
        // Musket
        ItemProperties.register(
                ModItemsGuns.MUSKET.get(),
                fromNamespaceAndPath(MOD_ID, "musket_bayonet"),
                (stack, level, entity, seed) -> {
                    return GunBase.getGunBase(stack).isAttachmentSpecific(stack, "underbarrel", ModItemsAttachments.BAYONET.get()) ? 1.0F : 0.0F;
                }
        );
        ItemProperties.register(
                ModItemsGuns.BOLTACTIONRIFLE.get(),
                fromNamespaceAndPath(MOD_ID, "bolt_action_bayonet"),
                (stack, level, entity, seed) -> {
                    return GunBase.getGunBase(stack).isAttachmentSpecific(stack, "underbarrel", ModItemsAttachments.BAYONET.get()) ? 1.0F : 0.0F;
                }
        );

        // Arquebus
        ItemProperties.register(
                ModItemsGuns.FLINTER.get(),
                fromNamespaceAndPath(MOD_ID, "arquebus_ready"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("IsStuffed") ? 1.0F : 0.0F;
                }
        );
        // Donderbuss
        ItemProperties.register(
                ModItemsGuns.DONDERBUSS.get(),
                fromNamespaceAndPath(MOD_ID, "donderbuss_ready"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("IsStuffed") ? 1.0F : 0.0F;
                }
        );
        // Bolt Action Rifle
        ItemProperties.register(
                ModItemsGuns.BOLTACTIONRIFLE.get(),
                fromNamespaceAndPath(MOD_ID, "bolt_action_rifle_bolting"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("IsUncocked") ? 1.0F : 0.0F;
                }
        );
        // Blunderbuss
        ItemProperties.register(
                ModItemsGuns.BLUNDERBUSS.get(),
                fromNamespaceAndPath(MOD_ID, "blunderbuss_primed"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("IsCocked") ? 1.0F : 0.0F;
                }
        );
        // Coachgun
        ItemProperties.register(
                ModItemsGuns.BREAKACTIONCOACHGUN.get(),
                fromNamespaceAndPath(MOD_ID, "break_action_coachgun_opened"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("ChamberOpen") ? 1.0F : 0.0F;
                }
        );
        // Rifle
        ItemProperties.register(
                ModItemsGuns.RIFLE.get(),
                fromNamespaceAndPath(MOD_ID, "rifle_primed"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("IsCocked") ? 1.0F : 0.0F;
                }
        );
        // Trapdoor
        ItemProperties.register(
                ModItemsGuns.TRAPDOORRIFLE.get(),
                fromNamespaceAndPath(MOD_ID, "trapdoor_rifle_opened"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("ChamberOpen") ? 1.0F : 0.0F;
                }
        );
        ItemProperties.register(
                ModItemsGuns.TRAPDOORRIFLE.get(),
                fromNamespaceAndPath(MOD_ID, "trapdoor_rifle_primed"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("ShootReady") ? 1.0F : 0.0F;
                }
        );
        // Single action revolver
        ItemProperties.register(
                ModItemsGuns.SINGLEACTIONREVOLVER.get(),
                fromNamespaceAndPath(MOD_ID, "singleactionrevolver_opened"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("ChamberOpen") ? 1.0F : 0.0F;
                }
        );
        ItemProperties.register(
                ModItemsGuns.SINGLEACTIONREVOLVER.get(),
                fromNamespaceAndPath(MOD_ID, "singleactionrevolver_primed"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("ShootReady") ? 1.0F : 0.0F;
                }
        );
        // Pistol
        ItemProperties.register(
                ModItemsGuns.PISTOL.get(),
                fromNamespaceAndPath(MOD_ID, "pistol_primed"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("IsCocked") ? 1.0F : 0.0F;
                }
        );
        // Handgun
        ItemProperties.register(
                ModItemsGuns.SEMIAUTOPISTOL.get(),
                fromNamespaceAndPath(MOD_ID, "slider"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("SlideCocked") ? 1.0F : 0.0F;
                }
        );
        // "Broom handle" pistol
        ItemProperties.register(
                ModItemsGuns.BROOMHANDLE.get(),
                fromNamespaceAndPath(MOD_ID, "broomhandlepistol_reloading"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("ChamberOpen") ? 1.0F : 0.0F;
                }
        );
        // Open Bolt Submachine gun
        ItemProperties.register(
                ModItemsGuns.OPENBOLTSUBMACHINEGUN.get(),
                fromNamespaceAndPath(MOD_ID, "open_bolt_smg_magless"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("HaveMag") ? 0.0F : 1.0F;
                }
        );
        ItemProperties.register(
                ModItemsGuns.OPENBOLTSUBMACHINEGUN.get(),
                fromNamespaceAndPath(MOD_ID, "open_bolt_smg_primed"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("SlideCocked") ? 1.0F : 0.0F;
                }
        );
        // Big Game
        ItemProperties.register(
                ModItemsGuns.BIGGAMEGUN.get(),
                fromNamespaceAndPath(MOD_ID, "big_game_primed"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("IsCocked") ? 1.0F : 0.0F;
                }
        );
        // Flaming Halberd
        ItemProperties.register(
                ModItemsGuns.FLAMINGHALBERD.get(),
                fromNamespaceAndPath(MOD_ID, "flaming_halberd_ready"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("IsStuffed") ? 1.0F : 0.0F;
                }
        );
        // Bruttbuss
        ItemProperties.register(
                ModItemsGuns.BRUTTBUSS.get(),
                fromNamespaceAndPath(MOD_ID, "bruttbuss_primed"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("IsStuffed") ? 1.0F : 0.0F;
                }
        );
        // CLOSED BOLT BATTLE RIFLE
        ItemProperties.register(
                ModItemsGuns.CLOSEDBOLTBATTLERIFLE.get(),
                fromNamespaceAndPath(MOD_ID, "closed_bolt_mag_rifle_unloaded"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("HaveMag") ? 0.0F : 1.0F;
                }
        );
        ItemProperties.register(
                ModItemsGuns.CLOSEDBOLTBATTLERIFLE.get(),
                fromNamespaceAndPath(MOD_ID, "closed_bolt_mag_rifle_priming"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("SlideCocked") ? 1.0F : 0.0F;
                }
        );
        // GAS OPERATED ASSAULT RIFLE
        ItemProperties.register(
                ModItemsGuns.GASOPERATEDASSAULTRIFLE.get(),
                fromNamespaceAndPath(MOD_ID, "gas_operated_assault_rifle_unloaded"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("HaveMag") ? 0.0F : 1.0F;
                }
        );
        ItemProperties.register(
                ModItemsGuns.GASOPERATEDASSAULTRIFLE.get(),
                fromNamespaceAndPath(MOD_ID, "gas_operated_assault_rifle_priming"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("SlideCocked") ? 1.0F : 0.0F;
                }
        );
        // GAS OPERATED SHOTGUN
        ItemProperties.register(
                ModItemsGuns.GASOPERATEDSHOTGUN.get(),
                fromNamespaceAndPath(MOD_ID, "gas_operated_shotgun_unloaded"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("HaveMag") ? 0.0F : 1.0F;
                }
        );
        ItemProperties.register(
                ModItemsGuns.GASOPERATEDSHOTGUN.get(),
                fromNamespaceAndPath(MOD_ID, "gas_operated_shotgun_priming"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("SlideCocked") ? 1.0F : 0.0F;
                }
        );
        // Anti materiel rifle
        ItemProperties.register(
                ModItemsGuns.SNIPERRIFLE.get(),
                fromNamespaceAndPath(MOD_ID, "unloaded"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("HaveMag") ? 0.0F : 1.0F;
                }
        );
        ItemProperties.register(
                ModItemsGuns.SNIPERRIFLE.get(),
                fromNamespaceAndPath(MOD_ID, "reloading"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("SlideCocked") ? 1.0F : 0.0F;
                }
        );
        // Machine Gun
        ItemProperties.register(
                ModItemsGuns.MACHINEGUN.get(),
                fromNamespaceAndPath(MOD_ID, "have_mag"),
                (stack, level, entity, seed) -> {
                    return stack.getOrCreateTag().getBoolean("HaveMag") ? 1.0F : 0.0F;
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
        //
        EntityRenderers.register(ModProjectiles.THEROCKPROJECTILE.get(), ThrownItemRenderer::new);
        //
        EntityRenderers.register(ModProjectiles.HEAVYCASTIRONPROJECTILE.get(), ThrownItemRenderer::new);

        EntityRenderers.register(ModProjectiles.CASTIRONBOMB.get(), ThrownItemRenderer::new);

        EntityRenderers.register(ModProjectiles.INVISIBLEPROJECTILE.get(), ThrownItemRenderer::new);

        EntityRenderers.register(ModProjectiles.FOOLSGOLD.get(), ThrownItemRenderer::new);

        EntityRenderers.register(ModProjectiles.HESHELL.get(), ThrownItemRenderer::new);

        EntityRenderers.register(ModProjectiles.FLAMINGMORTARSHELL.get(), ThrownItemRenderer::new);

        EntityRenderers.register(ModProjectiles.CLUSTERPELLET.get(), ThrownItemRenderer::new);

        EntityRenderers.register(ModProjectiles.CLUSTERMORTARSHELL.get(), ThrownItemRenderer::new);

        EntityRenderers.register(ModProjectiles.CASTIRONCANNONBALL.get(), ThrownItemRenderer::new);

        EntityRenderers.register(ModProjectiles.STEELCANNONBALL.get(), ThrownItemRenderer::new);

        EntityRenderers.register(ModProjectiles.POISONGASPROJECTILE.get(), ThrownItemRenderer::new);

        EntityRenderers.register(ModProjectiles.HEALINGGASPROJECTILE.get(), ThrownItemRenderer::new);

        EntityRenderers.register(ModEntities.SMOKE_GAS.get(), SpriteRenderer::new);
    }
}