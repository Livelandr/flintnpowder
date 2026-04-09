package org.ragingzombies.flintnpowder.item.guns.pumpaction;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.core.guns.PumpActionBase;
import org.ragingzombies.flintnpowder.item.ammo.shotgun.ShotgunShell;
import org.ragingzombies.flintnpowder.item.ammo.shotgun.ShotgunShellDragon;
import org.ragingzombies.flintnpowder.item.ammo.shotgun.ShotgunShellSlug;
import org.ragingzombies.flintnpowder.sound.ModSounds;

import javax.annotation.Nullable;
import java.util.List;

public class PumpActionShotgun extends PumpActionBase {
    public PumpActionShotgun(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean checkAmmo(Item ammo) {
        if (ammo instanceof ShotgunShell) {
            return true;
        }
        if (ammo instanceof ShotgunShellSlug) {
            return true;
        }
        if (ammo instanceof ShotgunShellDragon) {
            return true;
        }

        return false;
    }

    @Override
    public boolean allowPressingTrigger(Level pLevel, LivingEntity pPlayer, ItemStack gun, InteractionHand pUsedHand) {
        ItemStack secondItemStack;
        if (pUsedHand == InteractionHand.MAIN_HAND)
            secondItemStack = pPlayer.getItemInHand(InteractionHand.OFF_HAND);
        else
            secondItemStack = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);

        if (secondItemStack.is(Items.AIR)) return true;

        return false;
    }

    @Override
    public void OnCockStart(Level pLevel, LivingEntity shooter, ItemStack gun, InteractionHand pUsedHand) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.SHOTGUNPUMPBACK.get(), SoundSource.NEUTRAL, 5.0F, 1.0F, 0);

        if (shooter instanceof Player) {
            ((Player) shooter).getCooldowns().addCooldown(this, 8);
        }
    }

    @Override
    public void OnCockEnd(Level pLevel, LivingEntity shooter, ItemStack gun, InteractionHand pUsedHand){
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.SHOTGUNPUMPFORW.get(), SoundSource.NEUTRAL, 5.0F, 1.0F, 0);

        setAimAnimation(gun);

        if (shooter instanceof Player) {
            ((Player) shooter).getCooldowns().addCooldown(this, 8);
        }
    }

    @Override
    public boolean tryShoot(Level pLevel, LivingEntity pPlayer, ItemStack gun, InteractionHand pUsedHand) {
        if (GetAmmoAmount(gun) == 0) return false;

        return true;
    }

    @Override
    public void onAmmo(Level pLevel, LivingEntity shooter, ItemStack gun, ItemStack ammo, InteractionHand pUsedHand) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.SHOTGUNRELOAD.get(), SoundSource.NEUTRAL, 5.0F, 1.0F, 0);

        setReloadAnimation(gun);

        if (shooter instanceof Player) {
            ((Player) shooter).getCooldowns().addCooldown(this, shootCooldownTicks);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal(""));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.shotgun.description_0"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.shotgun.description_1"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.shotgun.description_2"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.shotgun.description_3"));
        pTooltipComponents.add(Component.literal(""));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
