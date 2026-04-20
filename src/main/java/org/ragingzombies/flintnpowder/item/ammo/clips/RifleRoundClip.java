package org.ragingzombies.flintnpowder.item.ammo.clips;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.ModItems;
import org.ragingzombies.flintnpowder.core.ammo.BaseAmmo;
import org.ragingzombies.flintnpowder.core.guns.GunBase;
import org.ragingzombies.flintnpowder.core.util.CameraWork;
import org.ragingzombies.flintnpowder.item.ModItemsAmmo;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.PistolRoundProjectile;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static org.ragingzombies.flintnpowder.core.util.CameraWork.OffsetEntityCamera;

public class RifleRoundClip extends BaseAmmo {
    ItemStack dummy = new ItemStack(Items.AIR);
    public RifleRoundClip(Properties pProperties) {
        super(pProperties);
        damage = 19;
    }

    @Override
    public int ammoCountInOne(ItemStack ammo) {
        return 4;
    }

    @Override
    public ItemStack getAmmoItemStack(ItemStack ammo) {
        return new ItemStack(ModItemsAmmo.RIFLEROUND.get());
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("flintnpowder.contains").
                append(String.valueOf(ammoCountInOne(dummy))).
                append(" ").
                append(getAmmoItemStack(dummy).getDisplayName())
        );

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
