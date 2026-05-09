package org.ragingzombies.flintnpowder.item.ammo.clips;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.core.ammo.BaseAmmo;
import org.ragingzombies.flintnpowder.item.ModItemsAmmo;

import javax.annotation.Nullable;
import java.util.List;

public class PistolRoundClip extends BaseAmmo {
    ItemStack dummy = new ItemStack(Items.AIR);
    public PistolRoundClip(Properties pProperties) {
        super(pProperties);
        damage = 6;
    }

    @Override
    public int ammoCountInOne(ItemStack ammo) {
        return 10;
    }

    @Override
    public ItemStack getAmmoItemStack(ItemStack ammo) {
        return new ItemStack(ModItemsAmmo.PISTOLROUND.get());
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
