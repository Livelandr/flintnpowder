package org.ragingzombies.flintnpowder.core.guns;

import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.ragingzombies.flintnpowder.core.ammo.BaseAmmo;
import org.ragingzombies.flintnpowder.enchantments.ModEnchantments;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class PumpActionBase extends GunBase {
    public PumpActionBase(Properties pProperties) {
        super(pProperties);
    }

    public boolean needCockToReload = true;
    public int maxAmmo = 6;

    public void OnCockStart(Level pLevel, LivingEntity shooter, ItemStack gun, InteractionHand pUsedHand) {
        if (shooter instanceof Player) {
            ((Player) shooter).getCooldowns().addCooldown(this, 5);
        }
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment == ModEnchantments.GHOST_LOADING.get() || super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return 22;
    }

    public void OnCockEnd(Level pLevel, LivingEntity shooter, ItemStack gun, InteractionHand pUsedHand) { }

    public int GetMaxAmmoAmount(ItemStack gun) {
        int amoLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.GHOST_LOADING.get(), gun);
        return ((PumpActionBase) gun.getItem()).maxAmmo + (int) (this.maxAmmo/4)*amoLevel;
    }

    public int GetAmmoAmount(ItemStack gun) {
        return gun.getTag().getInt("Ammo");
    }

    public void AddAmmo(ItemStack gun, ItemStack ammo) {
        int curAmmo = gun.getTag().getInt("Ammo");
        curAmmo++;
        gun.getTag().putInt("Ammo", curAmmo);

        CompoundTag ammoData = ammo.serializeNBT();
        gun.getTag().put("AmmoType" + curAmmo, ammoData);

        ammo.shrink(1);
    }

    public BaseAmmo GetFirstAmmo(ItemStack gun) {
        int curAmmo = gun.getTag().getInt("Ammo");
        ItemStack ammoData = ItemStack.of((CompoundTag) gun.getTag().get("AmmoType" + curAmmo));

        BaseAmmo ammo = (BaseAmmo) ammoData.getItem();

        curAmmo--;
        gun.getTag().putInt("Ammo", curAmmo);

        return ammo;
    }

    @Override
    public boolean tryShoot(Level pLevel, LivingEntity pPlayer, ItemStack gun, InteractionHand pUsedHand) {
        if (GetAmmoAmount(gun) == 0) return false;

        return true;
    }

    @Override
    public void Shoot(Level pLevel, LivingEntity pPlayer, ItemStack gunStack) {
        BaseAmmo ammo = GetFirstAmmo(gunStack);

        ammo.onAmmoShot(pPlayer, gunStack, pLevel);
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
                return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
            }

            if (!needCockToReload && checkAmmo(secondItemStack.getItem()) && GetAmmoAmount(gunStack) < GetMaxAmmoAmount(gunStack)) {
                AddAmmo(gunStack, secondItemStack);
                onAmmo(pLevel, pPlayer, gunStack, secondItemStack, pUsedHand);
            } else if (!gunStack.getTag().getBoolean("IsUncocked")) {
                if (gunStack.getTag().getBoolean("ReadyToShoot")) {
                    if (allowPressingTrigger(pLevel, pPlayer, gunStack, pUsedHand)) {
                        // Shoot
                        if (tryShoot(pLevel, pPlayer, gunStack, pUsedHand)) {
                            Shoot(pLevel, pPlayer, gunStack);
                            onShoot(pLevel, pPlayer, gunStack);
                        } else {
                            onTryFailure(pLevel, pPlayer, gunStack);
                        }
                        gunStack.getTag().putBoolean("ReadyToShoot", false);
                    }
                } else {
                    gunStack.getTag().putBoolean("IsUncocked", true);
                    OnCockStart(pLevel, pPlayer, gunStack, pUsedHand);
                }
            } else {
                if (needCockToReload && checkAmmo(secondItemStack.getItem()) && GetAmmoAmount(gunStack) < GetMaxAmmoAmount(gunStack)) {
                    AddAmmo(gunStack, secondItemStack);
                    onAmmo(pLevel, pPlayer, gunStack, secondItemStack, pUsedHand);
                } else {
                    gunStack.getTag().putBoolean("IsUncocked", false);
                    gunStack.getTag().putBoolean("ReadyToShoot", true);

                    OnCockEnd(pLevel, pPlayer, gunStack, pUsedHand);
                }
            }
        }

        return InteractionResultHolder.consume(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);

        // Ammo + Chamber open
        if (pLevel != null && pStack.hasTag() ) {
            long time = pLevel.getGameTime();

            if (GetAmmoAmount(pStack) > 0) {
                pTooltipComponents.add(Component.translatable("flintnpowder.ammo").withStyle(ChatFormatting.GRAY).append(
                        Component.literal( String.valueOf(GetAmmoAmount(pStack)) ).append(
                                Component.literal("/").append(
                                        Component.literal(String.valueOf(GetMaxAmmoAmount(pStack)))))));

                // Output all loaded ammo
                for (int i = 0; i < GetAmmoAmount(pStack); i++) {
                    ItemStack ammoData = ItemStack.of((CompoundTag) pStack.getTag().get("AmmoType" + (i+1)));

                    pTooltipComponents.add(Component.literal(String.valueOf(i+1)).append(Component.literal(": ")).append(ammoData.getDisplayName()));
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

            if (pStack.getTag().getBoolean("IsUncocked")) {
                ChatFormatting format;
                if (time % 10 < 5) {
                    format = ChatFormatting.GRAY;
                } else {
                    format = ChatFormatting.DARK_RED;
                }
                pTooltipComponents.add(Component.translatable("flintnpowder.uncocked").withStyle(format));
            }
        }
    }
}
