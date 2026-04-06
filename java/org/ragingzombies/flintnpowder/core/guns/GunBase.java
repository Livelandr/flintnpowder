package org.ragingzombies.flintnpowder.core.guns;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.core.ammo.BaseAmmo;

public class GunBase extends Item {

    public int cooldownTicks = 20;
    public int shootCooldownTicks = 20;

    public GunBase(Properties pProperties) {
        super(pProperties);
    }

    public boolean checkAmmo(Item ammo) {
        return false;
    }

    public boolean tryShoot(Level pLevel, LivingEntity pPlayer, InteractionHand pUsedHand) {
        return true;
    }

    public void onTryFailure(Level pLevel, LivingEntity pPlayer, ItemStack gunStack) { }

    public float damageModifier() {
        return 1;
    }

    public void onShoot(Level pLevel, LivingEntity shooter, ItemStack gunStack) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                SoundEvents.FLINTANDSTEEL_USE, SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        if (shooter instanceof Player) {
            ((Player) shooter).getCooldowns().addCooldown(this, shootCooldownTicks);
        }
    }

    public void onAmmo(Level pLevel, LivingEntity shooter, InteractionHand pUsedHand) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        if (shooter instanceof Player) {
            ((Player) shooter).getCooldowns().addCooldown(this, shootCooldownTicks);
        }
    }
}
