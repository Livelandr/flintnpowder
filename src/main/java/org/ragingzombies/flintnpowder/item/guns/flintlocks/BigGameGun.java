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
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
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
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.Lazy;
import org.ragingzombies.flintnpowder.core_modified.guns.FlintlockBaseEnchantable;
import org.ragingzombies.flintnpowder.sound.ModSounds;

import javax.annotation.Nullable;
import java.util.List;

public class BigGameGun extends FlintlockBaseEnchantable {

    protected final Lazy<Multimap<Attribute, AttributeModifier>> lazyAttributeMap;

    public BigGameGun(Properties pProperties) {
        super(pProperties);

        gunpowderCooldownTicks = 20;
        ramrodCooldownTicks = 30;

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
    public Rarity getRarity(ItemStack pStack) {
        return Rarity.UNCOMMON;
    }


    public float damageModifier(LivingEntity shooter, ItemStack gun) {
        return 1.1F*super.damageModifier(shooter, gun);
    }

    @Override
    public float accuracyModifier(LivingEntity ply, ItemStack gun) {
        return 2F * super.accuracyModifier(ply, gun);
    }

    public void onStuff(Level pLevel, LivingEntity shooter, ItemStack gun, InteractionHand pUsedHand) {
        pLevel.playSound(null, shooter,
                ModSounds.RAMROD.get(), SoundSource.NEUTRAL, 1F, 1.0F);

        setAimAnimation(gun);

        if (shooter instanceof Player ply) {
            ply.getCooldowns().addCooldown(this, ramrodCooldown(ply, gun));
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
    }

    @Override
    public void onShoot(float rotationX, float rotationY, Level pLevel, LivingEntity shooter, ItemStack gunStack) {
        pLevel.playSound(null, shooter,
                ModSounds.FLINTSTRIKE.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
        pLevel.playSound(null, shooter,
                ModSounds.RIFLESHOOT.get(), SoundSource.NEUTRAL, 3.0F, 1.0F);
        pLevel.playSound(null, shooter,
                ModSounds.GUNSHOTDISTANT.get(), SoundSource.NEUTRAL, 9.0F, 1.0F);

        setReloadAnimation(gunStack);

        // Particles
        if (!pLevel.isClientSide()) {
            ServerLevel sLevel = (ServerLevel) pLevel;
            //Second index is your particle count. DO. NOT. TOUCH. pParticleCount.
            //I'm dead serious. I know it might be weird that the particle count is not the actual particle count, but just trust the process and don't touch it.
            //Thank you.
            for (int index0 = 0; index0 < 25; index0++) {
                double speed = 0.55;
                double spread = 0.12;

                sLevel.sendParticles(
                        ParticleTypes.POOF,
                        shooter.getX(), shooter.getY() + shooter.getEyeHeight() * 0.6, shooter.getZ(),
                        0,
                        shooter.getDeltaMovement().x + shooter.getLookAngle().x * speed + Mth.nextDouble(shooter.getRandom(), spread * (-1), spread),
                        shooter.getDeltaMovement().y + shooter.getLookAngle().y * speed + Mth.nextDouble(shooter.getRandom(), spread * (-1), spread),
                        shooter.getDeltaMovement().z + shooter.getLookAngle().z * speed + Mth.nextDouble(shooter.getRandom(), spread * (-1), spread),
                        1.0
                );
            }
            for (int index1 = 0; index1 < 15; index1++) {
                double speed = 0.22;
                double spread = 0.28;

                sLevel.sendParticles(
                        ParticleTypes.LARGE_SMOKE,
                        shooter.getX(), shooter.getY() + shooter.getEyeHeight() * 0.5, shooter.getZ(),
                        0,
                        shooter.getDeltaMovement().x + shooter.getLookAngle().x * speed + Mth.nextDouble(shooter.getRandom(), spread * (-1), spread),
                        shooter.getDeltaMovement().y + shooter.getLookAngle().y * speed + Mth.nextDouble(shooter.getRandom(), spread * (-1), spread),
                        shooter.getDeltaMovement().z + shooter.getLookAngle().z * speed + Mth.nextDouble(shooter.getRandom(), spread * (-1), spread),
                        1.0
                );
            }
        }

        if (shooter instanceof Player) {
            setCooldown(shooter, gunStack, shootCooldown(shooter, gunStack));
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.flintnpowder.rifle.description_0"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.rifle.description_1"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.greek_fire.description_2"));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
