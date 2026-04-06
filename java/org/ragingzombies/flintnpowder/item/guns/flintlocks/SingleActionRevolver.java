package org.ragingzombies.flintnpowder.item.guns.flintlocks;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.core.guns.BlazelockBase;
import org.ragingzombies.flintnpowder.item.ammo.CastIronRoundshot;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

public class SingleActionRevolver extends BlazelockBase {
    public SingleActionRevolver(Properties pProperties) {
        super(pProperties);
        maxAmmo = 6;
        shootCooldownTicks = 10;
    }

    @Override
    public boolean tryShoot(Level pLevel, LivingEntity pPlayer, InteractionHand pUsedHand) {
        ItemStack secondItemStack;
        if (pUsedHand == InteractionHand.MAIN_HAND)
            secondItemStack = pPlayer.getItemInHand(InteractionHand.OFF_HAND);
        else
            secondItemStack = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);

        if (secondItemStack.is(Items.AIR)) return true;

        return false;
    }

    @Override
    public void onAmmoInsert(Level pLevel, LivingEntity shooter, InteractionHand pUsedHand) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        if (shooter instanceof Player ply) {
            ply.getCooldowns().addCooldown(this, 4);
        }
    }

    @Override
    public boolean checkAmmo(Item ammo){
        if (ammo instanceof CastIronRoundshot) {
            return true;
        }

        return false;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.flintnpowder.single_action_revolver.description_0"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.single_action_revolver.description_1"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.single_action_revolver.description_2"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.single_action_revolver.description_3"));
        pTooltipComponents.add(Component.translatable("item.flintnpowder.single_action_revolver.description_4"));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
