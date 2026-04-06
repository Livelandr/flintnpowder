package org.ragingzombies.flintnpowder.core.guns;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import org.ragingzombies.flintnpowder.core.ammo.BaseAmmo;
import org.ragingzombies.flintnpowder.item.ModItems;
import org.ragingzombies.flintnpowder.sound.ModSounds;

public class FlintlockBase extends GunBase {
    public FlintlockBase(Properties pProperties) {
        super(pProperties);
    }

    public void onGunpowder(Level pLevel, LivingEntity shooter, InteractionHand pUsedHand) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                SoundEvents.SAND_BREAK, SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        if (shooter instanceof Player ply) {
            ply.getCooldowns().addCooldown(this, shootCooldownTicks);
        }
    }

    public void onStuff(Level pLevel, LivingEntity shooter, InteractionHand pUsedHand) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.RAMROD.get(), SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        if (shooter instanceof Player ply) {
            ply.getCooldowns().addCooldown(this, shootCooldownTicks);
        }
    }

    public void onCock(Level pLevel, LivingEntity shooter, InteractionHand pUsedHand) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.GUNSWING.get(), SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        if (shooter instanceof Player ply) {
            ply.getCooldowns().addCooldown(this, shootCooldownTicks);
        }
    }

    public void Shoot(Level pLevel, LivingEntity pPlayer, ItemStack gunStack) {

        ItemStack ammoData = ItemStack.of((CompoundTag) gunStack.getTag().get("AmmoType"));

        BaseAmmo ammo = (BaseAmmo) ammoData.getItem();
        ammo.onAmmoShot(pPlayer, (GunBase) gunStack.getItem(), pLevel);

        gunStack.getTag().putBoolean("HasGunpowder", false);
        gunStack.getTag().putBoolean("HasAmmo", false);
        gunStack.getTag().putBoolean("IsCocked", false);
        gunStack.getTag().putBoolean("IsStuffed", false);


        onShoot(pLevel, pPlayer, gunStack);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        // Getting hand and offhand item
        ItemStack gunStack = pPlayer.getItemInHand(pUsedHand);

        ItemStack secondItemStack;
        if (pUsedHand == InteractionHand.MAIN_HAND)
            secondItemStack = pPlayer.getItemInHand(InteractionHand.OFF_HAND);
        else
            secondItemStack = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);


        if (!pLevel.isClientSide()) {

            if (!gunStack.hasTag()) gunStack.setTag(new CompoundTag());

            // If everything is done - shoot
            if (gunStack.getTag().getBoolean("IsCocked")) {
                if (tryShoot(pLevel, pPlayer, pUsedHand)) {
                    Shoot(pLevel, pPlayer, gunStack);
                } else {
                    onTryFailure(pLevel, pPlayer, gunStack);
                }
            }

            // Try to add gunpowder if isn't added
            if (!gunStack.getTag().getBoolean("HasGunpowder")) {
                // Add gunpowder
                if (secondItemStack.is(Tags.Items.GUNPOWDER)) {
                    gunStack.getTag().putBoolean("HasGunpowder", true);
                    secondItemStack.shrink(1);
                    onGunpowder(pLevel, pPlayer, pUsedHand);
                }
            } else {
                // Try to add ammo
                if (!gunStack.getTag().getBoolean("HasAmmo")) {
                    if (checkAmmo(secondItemStack.getItem())) {
                        // Putting Ammo
                        CompoundTag ammoData = secondItemStack.serializeNBT();
                        gunStack.getTag().put("AmmoType", ammoData);
                        gunStack.getTag().putBoolean("HasAmmo", true);

                        secondItemStack.shrink(1);
                        onAmmo(pLevel, pPlayer, pUsedHand);
                    }
                } else {
                    if (!gunStack.getTag().getBoolean("IsStuffed")) {
                        if (secondItemStack.is(ModItems.RAMROD.get())) {
                            gunStack.getTag().putBoolean("IsStuffed", true);
                            onStuff(pLevel, pPlayer, pUsedHand);
                        }
                    } else {
                        // Try to cock
                        if (!gunStack.getTag().getBoolean("IsCocked")) {
                            pPlayer.getCooldowns().addCooldown(this, cooldownTicks);
                            gunStack.getTag().putBoolean("IsCocked", true);

                            onCock(pLevel, pPlayer, pUsedHand);
                        }
                    }
                }

            }
            }
            return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
        }

}
