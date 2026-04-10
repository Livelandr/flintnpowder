package org.ragingzombies.flintnpowder.item.attachments;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.core.attachments.AttachmentBase;

import javax.annotation.Nullable;
import java.util.List;

public class Bayonet extends AttachmentBase {
    public Bayonet(Properties pProperties) {
        super(pProperties);
        type = "Underbarrel";
    }

    @Override
    public void onAttach(LivingEntity player, ItemStack attachment, ItemStack gun) {
        gun.getOrCreateTag().putBoolean("HaveBayonet", true);
        attachment.shrink(1);
    }

    @Override
    public void onDetach(LivingEntity player, ItemStack attachment, ItemStack gun) {
        gun.getOrCreateTag().putBoolean("HaveBayonet", false);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.flintnpowder.bayonet.description"));
    }
}
