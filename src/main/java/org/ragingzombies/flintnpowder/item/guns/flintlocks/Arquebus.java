package org.ragingzombies.flintnpowder.item.guns.flintlocks;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
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
import org.ragingzombies.flintnpowder.item.ModItemsAttachments;
import org.ragingzombies.flintnpowder.sound.ModSounds;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class Arquebus extends FlintlockBase {

    public Arquebus(Properties pProperties) {
        super(pProperties);

        shootCooldownTicks = 30;
        gunpowderCooldownTicks = 20;
        ramrodCooldownTicks = 60;

        noCock = true;


        addAllowedAmmo(ModItemsAmmo.CASTIRONROUNDSHOT.get());
        addAllowedAmmo(ModItemsAmmo.STEELROUNDSHOT.get());

        addAllowedAttachment(ModItemsAttachments.LOWPROFILEOPTIC.get());
    }


    @Override
    public float accuracyModifier(UUID ply) {
        return 1 * super.accuracyModifier(ply);
    }

    @Override
    public void onStuff(Level pLevel, LivingEntity shooter, ItemStack gun, InteractionHand pUsedHand) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.RAMROD.get(), SoundSource.NEUTRAL, 1.0F, 0.8F, 0);

        if (shooter instanceof Player ply) {
            ply.getCooldowns().addCooldown(this, 35);
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
                SoundEvents.TNT_PRIMED, SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        ServerTickHandler.createTask(25, () -> {
            pLevel.playSeededSound(null, pPlayer.getBlockX(), pPlayer.getBlockY(), pPlayer.getBlockZ(),
                    ModSounds.MUSKETFIRE.get(), SoundSource.NEUTRAL, 3.0F, 1.0F, 0);
            pLevel.playSeededSound(null, pPlayer.getBlockX(), pPlayer.getBlockY(), pPlayer.getBlockZ(),
                    ModSounds.GUNSHOTDISTANT.get(), SoundSource.NEUTRAL, 9.0F, 1.0F, 0);

            ItemStack ammoData = ItemStack.of((CompoundTag) gunStack.getTag().get("AmmoType"));

            BaseAmmo ammo = (BaseAmmo) ammoData.getItem();
            ammo.onAmmoShot(pPlayer, (GunBase) gunStack.getItem(), pLevel);

            setReloadAnimation(gunStack);
        });
    }

    @Override
    public void onShoot(Level pLevel, LivingEntity shooter, ItemStack gunStack) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.FLINTSTRIKE.get(), SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        setReloadAnimation(gunStack);

        if (shooter instanceof Player) {
            ((Player) shooter).getCooldowns().addCooldown(this, shootCooldown(shooter, gunStack));
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.flintnpowder.arquebus.description_0"));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
