package org.ragingzombies.flintnpowder.core.guns;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
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
import org.ragingzombies.flintnpowder.core.ammo.BaseAmmo;
import org.ragingzombies.flintnpowder.core.ammo.BaseMagazine;
import org.ragingzombies.flintnpowder.sound.ModSounds;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static org.ragingzombies.flintnpowder.core.attachments.AttachmentBase.attachmentTypes;

public class MagfedBase extends GunBase {
    public MagfedBase(Properties pProperties) {
        super(pProperties);
    }

    public List<Item> allowedMags = new ArrayList<>();

    public void addAllowedMagazine(Item mag) {
        allowedMags.add(mag);
    }

    public boolean checkMagazine(ItemStack mag) {
        for (Item a : allowedMags) {
            if (mag.getItem().getClass() == a.getClass()) {
                return true;
            }
        }

        return false;
    }

    public void onSlideStart(Level pLevel, LivingEntity shooter, ItemStack gun) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.GUNSWING.get(), SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        if (shooter instanceof Player ply) {
            ply.getCooldowns().addCooldown(this, 10);
        }
    }

    public void onSlideEnd(Level pLevel, LivingEntity shooter, ItemStack gun) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.GUNSWING.get(), SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        setAimAnimation(gun);

        if (shooter instanceof Player ply) {
            ply.getCooldowns().addCooldown(this, 15);
        }
    }

    public void onMagExtract(Level pLevel, LivingEntity shooter, ItemStack gun) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.PISTOLMAGIN.get(), SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        setReloadAnimation(gun);

        if (shooter instanceof Player ply) {
            ply.getCooldowns().addCooldown(this, 15);
        }
    }

    public void onMagInsert(Level pLevel, LivingEntity shooter, ItemStack gun) {
        pLevel.playSeededSound(null, shooter.getBlockX(), shooter.getBlockY(), shooter.getBlockZ(),
                ModSounds.PISTOLMAGIN.get(), SoundSource.NEUTRAL, 1.0F, 1.0F, 0);

        if (shooter instanceof Player ply) {
            ply.getCooldowns().addCooldown(this, 15);
        }
    }

    public void InsertMagazine(Player ply, ItemStack gun, ItemStack mag) {
        if (gun.getTag().getBoolean("HaveMag")) return;

        ((BaseMagazine) mag.getItem()).copyToGun(mag, gun);

        CompoundTag magData = mag.serializeNBT();
        gun.getTag().put("Magazine", magData);
        gun.getTag().putBoolean("HaveMag", true);

        mag.shrink(1);

        onMagInsert(ply.level(), ply, gun);
    }

    public void ExtractMagazine(Player ply, ItemStack gun) {
        if (!gun.getTag().getBoolean("HaveMag")) return;

        CompoundTag nbt = (CompoundTag) gun.getTag().get("Magazine");
        ItemStack magazineStack = ItemStack.of(nbt);

        gun.getTag().putBoolean("ShootReady", false);
        gun.getTag().putBoolean("HaveMag", false);

        BaseMagazine.SetFromGun(magazineStack, gun);

        ply.getInventory().add(magazineStack);

        onMagExtract(ply.level(), ply, gun);
    }

    public int GetAmmoAmount(ItemStack gun) {
        return gun.getOrCreateTag().getInt("AmmoCount");
    }

    public static ItemStack getNAmmo(ItemStack gun, int n) {
        CompoundTag nbt = (CompoundTag) gun.getOrCreateTag().get("A"+String.valueOf(n));
        ItemStack stack = ItemStack.of( nbt );

        return stack;
    }

    public BaseAmmo GetFirstAmmo(ItemStack gun) {
        int curAmmo = gun.getTag().getInt("AmmoCount");
        ItemStack ammoData = ItemStack.of((CompoundTag) gun.getTag().get("A" + (curAmmo-1)));

        BaseAmmo ammo = (BaseAmmo) ammoData.getItem();

        curAmmo--;
        gun.getTag().putInt("AmmoCount", curAmmo);

        return ammo;
    }

    public int GetMaxAmmoAmount(ItemStack pStack) {
        return pStack.getTag().getInt("MaxAmmoCount");
    }

    @Override
    public void Shoot(Level pLevel, LivingEntity pPlayer, ItemStack gunStack) {
        BaseAmmo ammo = GetFirstAmmo(gunStack);

        ammo.onAmmoShot(pPlayer, gunStack, pLevel);

        if (GetAmmoAmount(gunStack) <= 0) gunStack.getTag().putBoolean("ShootReady", false);
    }

    @Override
    public boolean tryShoot(Level pLevel, LivingEntity pPlayer, ItemStack gun, InteractionHand pUsedHand) {
        if (GetAmmoAmount(gun) == 0) return false;

        return true;
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

            // Attachment
            if (checkAttachmentComparability(pPlayer, gunStack, secondItemStack.getItem())) {
                this.setAttachment(pPlayer, gunStack, secondItemStack);
                return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
            }

            // I'm sleep-deprived hi
            if (gunStack.getTag().getBoolean("ShootReady")) {
                if (gunStack.getTag().getBoolean("HaveMag")) {
                    if (allowPressingTrigger(pLevel, pPlayer, gunStack, pUsedHand)) {
                        if (tryShoot(pLevel, pPlayer, gunStack, pUsedHand)) {
                            Shoot(pLevel, pPlayer, gunStack);
                            onShoot(pLevel, pPlayer, gunStack);
                        } else {
                            onTryFailure(pLevel, pPlayer, gunStack);
                            gunStack.getTag().putBoolean("ShootReady", false);
                        }
                    }
                } else {
                    onTryFailure(pLevel, pPlayer, gunStack);
                    gunStack.getTag().putBoolean("ShootReady", false);
                }
            } else {
                if (!gunStack.getTag().getBoolean("HaveMag")) {
                    if (checkMagazine(secondItemStack)) {
                        InsertMagazine(pPlayer, gunStack, secondItemStack);
                    }
                } else if (GetAmmoAmount(gunStack) <= 0) {
                    ExtractMagazine(pPlayer, gunStack);
                } else if (!gunStack.getTag().getBoolean("SlideCocked")) {
                    gunStack.getTag().putBoolean("SlideCocked", true);
                    onSlideStart(pLevel, pPlayer, gunStack);
                } else {
                    gunStack.getTag().putBoolean("SlideCocked", false);
                    gunStack.getTag().putBoolean("ShootReady", true);
                    onSlideEnd(pLevel, pPlayer, gunStack);
                }
            }
        }

        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }



    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        //super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);

        pTooltipComponents.add(Component.literal(""));

        int totalAttach = 0;
        for (String type : attachmentTypes) {
            if (isAttachmentValidAndEnabled(pStack, type)) {
                ItemStack item = getAttachmentStack(pStack, type);
                pTooltipComponents.add(Component.translatable("flintnpowder.attachment").append(item.getDisplayName()));
                item.getItem().appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);

                totalAttach++;
            }
        }
        if (totalAttach > 0) {
            pTooltipComponents.add(Component.literal(""));
        }


        if (!Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("flintnpowder.guninfoshift"));
            pTooltipComponents.add(Component.literal(""));
        } else {
            pTooltipComponents.add(Component.translatable("flintnpowder.guninfoammo"));
            for (Item ammo : allowedMags) {
                pTooltipComponents.add(Component.literal("   ").append((new ItemStack(ammo)).getDisplayName()));
            }

            pTooltipComponents.add(Component.literal(""));

            if (!allowedAttachments.isEmpty()) {
                pTooltipComponents.add(Component.translatable("flintnpowder.guninfoattachment"));
                for (Item ammo : allowedAttachments) {
                    pTooltipComponents.add(Component.literal("   ").append((new ItemStack(ammo)).getDisplayName()));
                }
            } else {
                pTooltipComponents.add(Component.translatable("flintnpowder.guninfonoattachment"));
            }

            pTooltipComponents.add(Component.literal(""));
        }

        // Ammo + Chamber open
        if (pLevel != null && pStack.hasTag() ) {
            long time = pLevel.getGameTime();

            if (!pStack.getTag().getBoolean("HaveMag")) {
                ChatFormatting format;
                if (time % 10 < 5) {
                    format = ChatFormatting.GRAY;
                } else {
                    format = ChatFormatting.DARK_RED;
                }
                pTooltipComponents.add(Component.translatable("flintnpowder.no_magazine").withStyle(format));
            } else {
                CompoundTag nbt = (CompoundTag) pStack.getTag().get("Magazine");
                ItemStack magazineStack = ItemStack.of(nbt);
                pTooltipComponents.add(Component.translatable("flintnpowder.magazine").withStyle(ChatFormatting.GRAY).
                        append(magazineStack.getDisplayName()));

                if (GetAmmoAmount(pStack) > 0) {
                    pTooltipComponents.add(Component.translatable("flintnpowder.ammo").withStyle(ChatFormatting.GRAY).append(
                            Component.literal(String.valueOf(GetAmmoAmount(pStack))).append(
                                    Component.literal("/").append(
                                            Component.literal(String.valueOf(GetMaxAmmoAmount(pStack)))))));

                    // Output all loaded ammo
                    for (int i = 0; i < GetAmmoAmount(pStack); i++) {
                        ItemStack ammoData = ItemStack.of((CompoundTag) pStack.getTag().get("A" + i));

                        pTooltipComponents.add(Component.literal(String.valueOf(i + 1)).append(Component.literal(": ")).append(ammoData.getDisplayName()));
                    }
                } else {
                    ChatFormatting format;
                    if (time % 10 < 5) {
                        format = ChatFormatting.GRAY;
                    } else {
                        format = ChatFormatting.DARK_RED;
                    }
                    pTooltipComponents.add(Component.translatable("flintnpowder.no_payload").withStyle(format));
                }
            }

        }
    }
}
