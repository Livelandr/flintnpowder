package org.ragingzombies.flintnpowder.item.guns.flintlocks;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.core.ammo.BaseAmmo;
import org.ragingzombies.flintnpowder.core.guns.FlintlockBase;
import org.ragingzombies.flintnpowder.core.guns.GunBase;
import org.ragingzombies.flintnpowder.handlers.ServerTickHandler;
import org.ragingzombies.flintnpowder.item.ModItemsAmmo;
import org.ragingzombies.flintnpowder.sound.ModSounds;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class GreekFire extends FlintlockBase {

    public GreekFire(Properties pProperties) {
        super(pProperties);

        shootCooldownTicks = 60;
        gunpowderCooldownTicks = 60;
        ramrodCooldownTicks = 80;

        noCock = true;

        addAllowedAmmo(ModItemsAmmo.FLAMINGGRAPESHOT.get());
        addAllowedAmmo(ModItemsAmmo.OILFLAMESHOT.get());
    }


    @Override
    public float accuracyModifier(UUID ply, ItemStack gun) {
        return 1 * super.accuracyModifier(ply, gun);
    }

    @Override
    public void onStuff(Level pLevel, LivingEntity shooter, ItemStack gun, InteractionHand pUsedHand) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.RAMROD.get(), SoundSource.NEUTRAL, 1.0F, 0.8F, 0);

        if (shooter instanceof Player ply) {
            ply.getCooldowns().addCooldown(this, 40);
        }
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pIsSelected) {
            if (pEntity instanceof LivingEntity livingEnt) {
                livingEnt.addEffect(
                        new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1, 0)
                );
            }
        }
    }

    @Override
    public boolean allowPressingTrigger(Level pLevel, LivingEntity pPlayer, ItemStack gun, InteractionHand pUsedHand) {
        ItemStack gunStack = pPlayer.getItemInHand(pUsedHand);

        ItemStack secondItemStack;
        if (pUsedHand == InteractionHand.MAIN_HAND)
            secondItemStack = pPlayer.getItemInHand(InteractionHand.OFF_HAND);
        else
            secondItemStack = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);

        return secondItemStack.is(Items.FLINT_AND_STEEL) && !pPlayer.isUnderWater();
    }

    @Override
    public void Shoot(Level pLevel, LivingEntity pPlayer, ItemStack gunStack) {
        gunStack.getTag().putInt("Gunpowder", 0);
        gunStack.getTag().putBoolean("HasAmmo", false);
        gunStack.getTag().putBoolean("IsCocked", false);
        gunStack.getTag().putBoolean("IsStuffed", false);

        pLevel.playSeededSound(null, pPlayer.getBlockX(), pPlayer.getBlockY(), pPlayer.getBlockZ(),
                SoundEvents.TNT_PRIMED, SoundSource.NEUTRAL, 1.0F, 0.75F, 0);

        ServerTickHandler.createTask(25, () -> {
            pLevel.playSeededSound(null, pPlayer.getBlockX(), pPlayer.getBlockY(), pPlayer.getBlockZ(),
                    SoundEvents.GENERIC_EXPLODE, SoundSource.NEUTRAL, 8.0F, 0.5F, 0);

            ItemStack ammoData = ItemStack.of((CompoundTag) gunStack.getTag().get("AmmoType"));

            BaseAmmo ammo = (BaseAmmo) ammoData.getItem();
            ammo.onAmmoShot(pPlayer, gunStack, pLevel);

            setReloadAnimation(gunStack);
        });
    }

    @Override
    public void onShoot(Level pLevel, LivingEntity shooter, ItemStack gunStack) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                SoundEvents.FLINTANDSTEEL_USE, SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        setReloadAnimation(gunStack);

        if (shooter instanceof Player) {
            ((Player) shooter).getCooldowns().addCooldown(this, shootCooldown(shooter, gunStack));
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
