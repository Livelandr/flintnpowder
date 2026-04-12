package org.ragingzombies.flintnpowder.item.attachments;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.ragingzombies.flintnpowder.core.attachments.AttachmentBase;

import javax.annotation.Nullable;
import java.util.List;

public class HighProfileOptic extends AttachmentBase {
    public HighProfileOptic(Properties pProperties) {
        super(pProperties);
        type = "Optic";
    }

    @Override
    public void onAttach(LivingEntity player, ItemStack attachment, ItemStack gun) {
        gun.getOrCreateTag().putFloat("OpticZoom", 0.05f);
        attachment.shrink(1);
    }

    @Override
    public void onDetach(LivingEntity player, ItemStack attachment, ItemStack gun) {}

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.flintnpowder.hpoptic.description"));
    }
}
