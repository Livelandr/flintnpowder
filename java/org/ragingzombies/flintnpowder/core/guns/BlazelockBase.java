package org.ragingzombies.flintnpowder.core.guns;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.core.ammo.BaseAmmo;
import org.ragingzombies.flintnpowder.sound.ModSounds;

public class BlazelockBase extends GunBase {
    public BlazelockBase(Properties pProperties) {
        super(pProperties);
    }

    public int maxAmmo = 2;

    public void onChamberOpen(Level pLevel, LivingEntity shooter, InteractionHand pUsedHand) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.GUNSWING.get(), SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        if (shooter instanceof Player ply) {
            ply.getCooldowns().addCooldown(this, shootCooldownTicks);
        }
    }

    public void onChamberClose(Level pLevel, LivingEntity shooter, InteractionHand pUsedHand) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.GUNSWING.get(), SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        if (shooter instanceof Player ply) {
            ply.getCooldowns().addCooldown(this, shootCooldownTicks);
        }
    }

    public void onAmmoInsert(Level pLevel, LivingEntity shooter, InteractionHand pUsedHand) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        if (shooter instanceof Player ply) {
            ply.getCooldowns().addCooldown(this, shootCooldownTicks);
        }
    }

    public void Shoot(Level pLevel, LivingEntity pPlayer, ItemStack gunStack) {
        int curAmmo = gunStack.getTag().getInt("Ammo");
        ItemStack ammoData = ItemStack.of((CompoundTag) gunStack.getTag().get("AmmoType" + curAmmo));

        curAmmo--;
        gunStack.getTag().putInt("Ammo", curAmmo);

        if (curAmmo == 0) gunStack.getTag().putBoolean("ShootReady", false);

        BaseAmmo ammo = (BaseAmmo) ammoData.getItem();
        ammo.onAmmoShot(pPlayer, (GunBase) gunStack.getItem(), pLevel);

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
            if (gunStack.getTag().getBoolean("ShootReady")) {
                if (tryShoot(pLevel, pPlayer, pUsedHand)) {
                    Shoot(pLevel, pPlayer, gunStack);
                } else {
                    onTryFailure(pLevel, pPlayer, gunStack);
                }
            } else {
                if (gunStack.getTag().getBoolean("ChamberOpen")) {
                    if (gunStack.getTag().getInt("Ammo") < maxAmmo) {
                        if (checkAmmo(secondItemStack.getItem())) {
                            int curAmmo = gunStack.getTag().getInt("Ammo");
                            curAmmo++;
                            gunStack.getTag().putInt("Ammo", curAmmo);

                            secondItemStack.shrink(1);

                            CompoundTag ammoData = secondItemStack.serializeNBT();
                            gunStack.getTag().put("AmmoType" + curAmmo, ammoData);

                            onAmmoInsert(pLevel, pPlayer, pUsedHand);
                        } else {
                            gunStack.getTag().putBoolean("ChamberOpen", false);
                            onChamberClose(pLevel, pPlayer, pUsedHand);
                            if (gunStack.getTag().getInt("Ammo") > 0) {
                                gunStack.getTag().putBoolean("ShootReady", true);
                            }
                        }
                    } else {
                        gunStack.getTag().putBoolean("ChamberOpen", false);
                        onChamberClose(pLevel, pPlayer, pUsedHand);
                        gunStack.getTag().putBoolean("ShootReady", true);
                    }
                } else {
                    onChamberOpen(pLevel, pPlayer, pUsedHand);
                    gunStack.getTag().putBoolean("ChamberOpen", true);
                }



            }
        }
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }
}
