package org.ragingzombies.flintnpowder.core.attachments;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AttachmentBase extends Item {
    public static final List<String> attachmentTypes = new ArrayList<>(Arrays.asList("Underbarrel","Silencer","Muzzle","Optic"));

    protected String type = "Misc";

    public AttachmentBase(Properties pProperties) {
        super(pProperties);
    }
    public static void RegisterAttachmentType(String type) {
        attachmentTypes.add(type);
    }

    public String getType() {
        return type;
    }

    public void onAttach(LivingEntity player, ItemStack attachment, ItemStack gun) {}
    public void onDetach(LivingEntity player, ItemStack attachment, ItemStack gun) {}
}
