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
package org.ragingzombies.flintnpowder.item.guns.flintlocks;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.livelandr.flintcore.core.util.CameraWork;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.Lazy;
import com.livelandr.flintcore.core.ammo.BaseAmmo;
import org.ragingzombies.flintnpowder.core_modified.guns.FlintlockBaseEnchantable;
import org.ragingzombies.flintnpowder.core_modified.guns.MatchlockBaseEnchantable;
import org.ragingzombies.flintnpowder.handlers.ServerTickHandler;
import org.ragingzombies.flintnpowder.sound.ModSounds;

import javax.annotation.Nullable;
import java.util.List;

public class Handgonne extends MatchlockBaseEnchantable {

    protected final Lazy<Multimap<Attribute, AttributeModifier>> lazyAttributeMap;

    public Handgonne(Properties pProperties) {
        super(pProperties);

        GunpowderRequired = 2;
        shootCooldownTicks = 40;
        gunpowderCooldownTicks = 20;
        ramrodCooldownTicks = 40;

        noCock = true;

        addCompatibleCaliberTag("heavyroundshot");

        this.lazyAttributeMap = Lazy.of(() -> {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(Attributes.ATTACK_DAMAGE,
                    new AttributeModifier(
                            BASE_ATTACK_DAMAGE_UUID,
                            "Weapon modifier",
                            4,
                            AttributeModifier.Operation.ADDITION
                    ));
            builder.put(Attributes.ATTACK_SPEED,
                    new AttributeModifier(
                            BASE_ATTACK_SPEED_UUID,
                            "Weapon modifier",
                            -2.4,
                            AttributeModifier.Operation.ADDITION
                    ));

            return builder.build();
        });
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND) {
            return lazyAttributeMap.get();
        }
        return super.getAttributeModifiers(slot, stack);
    }

    @Override
    public float accuracyModifier(LivingEntity ply, ItemStack gun) {
        return 1.25F * super.accuracyModifier(ply, gun);
    }

    @Override
    public void onStuff(Level pLevel, LivingEntity shooter, ItemStack gun, InteractionHand pUsedHand) {
        setAimAnimation(gun);
        pLevel.playSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.RAMROD.get(), SoundSource.NEUTRAL, 1.0F, 0.8F);

        if (shooter instanceof Player ply) {
            ply.getCooldowns().addCooldown(this, 40);
        }
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pEntity instanceof LivingEntity livingEnt) {
            if (livingEnt.getItemInHand(InteractionHand.MAIN_HAND) == pStack || livingEnt.getItemInHand(InteractionHand.OFF_HAND) == pStack) {
                livingEnt.addEffect(
                        new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1, 0)
                );

            }
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public boolean allowPressingTrigger(Level pLevel, LivingEntity pPlayer, ItemStack gun, InteractionHand pUsedHand) {
        return !pPlayer.isUnderWater();
    }

    @Override
    public void onIgnition(Level pLevel, LivingEntity pPlayer, ItemStack gun) {
        pLevel.playSound(null, pPlayer.getBlockX(), pPlayer.getBlockY(), pPlayer.getBlockZ(),
                SoundEvents.TNT_PRIMED, SoundSource.NEUTRAL, 1.0F, 0.75F);
    }

    @Override
    public void shoot(Level pLevel, LivingEntity pPlayer, ItemStack gunStack) {
        gunStack.getTag().putInt("Gunpowder", 0);
        gunStack.getTag().putBoolean("HasAmmo", false);
        gunStack.getTag().putBoolean("IsCocked", false);
        gunStack.getTag().putBoolean("IsStuffed", false);

        super.shoot(pLevel, pPlayer, gunStack, CameraWork.getPlayerViewX(pPlayer), CameraWork.getPlayerViewY(pPlayer));
        pLevel.playSound(null, pPlayer.getBlockX(), pPlayer.getBlockY(), pPlayer.getBlockZ(),
                SoundEvents.GENERIC_EXPLODE, SoundSource.NEUTRAL, 8.0F, 0.5F);

        setReloadAnimation(gunStack);
    }

    @Override
    public void onShoot(float rotationX, float rotationY, Level pLevel, LivingEntity shooter, ItemStack gunStack) {
        pLevel.playSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.FLINTSTRIKE.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

        if (shooter instanceof Player) {
            setCooldown(shooter, gunStack, shootCooldown(shooter, gunStack));
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.flintnpowder.greek_fire.description_0"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.greek_fire.description_1"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.greek_fire.description_2"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.greek_fire.description_3"));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
