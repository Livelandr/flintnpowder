package org.ragingzombies.flintnpowder.core.guns;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import org.ragingzombies.flintnpowder.core.ammo.BaseAmmo;
import org.ragingzombies.flintnpowder.item.ModItems;
import org.ragingzombies.flintnpowder.sound.ModSounds;

import javax.annotation.Nullable;
import java.util.List;

public class FlintlockBase extends GunBase {
    public FlintlockBase(Properties pProperties) {
        super(pProperties);
    }

    public int GunpowderRequired = 1;

    public void onGunpowder(Level pLevel, LivingEntity shooter, ItemStack gun, InteractionHand pUsedHand) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                SoundEvents.SAND_BREAK, SoundSource.NEUTRAL, 5.0F, 1.0F, 0);

        if (shooter instanceof Player ply) {
            ply.getCooldowns().addCooldown(this, gunpowderCooldownTicks);
        }
    }

    public void onStuff(Level pLevel, LivingEntity shooter, ItemStack gun, InteractionHand pUsedHand) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.RAMROD.get(), SoundSource.NEUTRAL, 5.0F, 1.0F, 0);

        if (shooter instanceof Player ply) {
            ply.getCooldowns().addCooldown(this, ramrodCooldownTicks);
        }
    }

    public void onCock(Level pLevel, LivingEntity shooter, ItemStack gun, InteractionHand pUsedHand) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.FLINTPRIME.get(), SoundSource.NEUTRAL, 5.0F, 1.0F, 0);

        setAimAnimation(gun);

        if (shooter instanceof Player ply) {
            ply.getCooldowns().addCooldown(this, shootCooldownTicks);
        }
    }

    @Override
    public void Shoot(Level pLevel, LivingEntity pPlayer, ItemStack gunStack) {

        ItemStack ammoData = ItemStack.of((CompoundTag) gunStack.getTag().get("AmmoType"));

        BaseAmmo ammo = (BaseAmmo) ammoData.getItem();
        ammo.onAmmoShot(pPlayer, (GunBase) gunStack.getItem(), pLevel);

        gunStack.getTag().putInt("Gunpowder", 0);
        gunStack.getTag().putBoolean("HasAmmo", false);
        gunStack.getTag().putBoolean("IsCocked", false);
        gunStack.getTag().putBoolean("IsStuffed", false);

        setReloadAnimation(gunStack);
    }

    public boolean isRamrod(ItemStack item) {
        return item.is(ModItems.RAMROD.get());
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
                if (allowPressingTrigger(pLevel, pPlayer, gunStack, pUsedHand)) {
                    if (tryShoot(pLevel, pPlayer, gunStack, pUsedHand)) {
                        Shoot(pLevel, pPlayer, gunStack);
                        onShoot(pLevel, pPlayer, gunStack);
                    } else {
                        onTryFailure(pLevel, pPlayer, gunStack);
                    }
                }
            }

        // Try to add gunpowder if isn't added
        if (gunStack.getTag().getInt("Gunpowder") < GunpowderRequired) {
            // Add gunpowder
            if (secondItemStack.is(Tags.Items.GUNPOWDER)) {
                gunStack.getTag().putInt("Gunpowder", gunStack.getTag().getInt("Gunpowder")+1);
                secondItemStack.shrink(1);
                onGunpowder(pLevel, pPlayer, gunStack, pUsedHand);
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
                    onAmmo(pLevel, pPlayer, secondItemStack, gunStack, pUsedHand);
                }
            } else {
                if (!gunStack.getTag().getBoolean("IsStuffed")) {
                    if (isRamrod(secondItemStack)) {
                        gunStack.getTag().putBoolean("IsStuffed", true);
                        onStuff(pLevel, pPlayer, gunStack, pUsedHand);
                    }
                } else {
                    // Try to cock
                    if (!gunStack.getTag().getBoolean("IsCocked")) {
                        gunStack.getTag().putBoolean("IsCocked", true);

                        onCock(pLevel, pPlayer, gunStack, pUsedHand);
                    }
                }
            }

        }
        }

        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        // Statuses
        if (pLevel != null) {
            if (pStack.getOrCreateTag().getInt("Gunpowder") < GunpowderRequired) {
                pTooltipComponents.add(Component.translatable("flintnpowder.gunpowder").append(
                        String.valueOf(pStack.getTag().getInt("Gunpowder"))).append("/").append(String.valueOf(GunpowderRequired)).withStyle(ChatFormatting.RED));
            } else {
                pTooltipComponents.add(Component.translatable("flintnpowder.gunpowder").append(
                        String.valueOf(pStack.getTag().getInt("Gunpowder"))).append("/").append(String.valueOf(GunpowderRequired)).withStyle(ChatFormatting.DARK_GREEN));
                if (!pStack.getTag().getBoolean("HasAmmo")) {
                    pTooltipComponents.add(Component.translatable("flintnpowder.no_payload").withStyle(ChatFormatting.RED));
                } else {
                    ItemStack ammoData = ItemStack.of((CompoundTag) pStack.getTag().get("AmmoType"));

                    pTooltipComponents.add(Component.translatable("flintnpowder.payload").append(ammoData.getDisplayName()).withStyle(ChatFormatting.DARK_GREEN));
                    if (!pStack.getTag().getBoolean("IsStuffed")) {
                        pTooltipComponents.add(Component.translatable("flintnpowder.not_stuffed").withStyle(ChatFormatting.RED));
                    } else {
                        pTooltipComponents.add(Component.translatable("flintnpowder.ready_to_shoot").withStyle(ChatFormatting.DARK_GREEN));
                    }
                }
            }


        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
