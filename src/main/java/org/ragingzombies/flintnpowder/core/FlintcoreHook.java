package org.ragingzombies.flintnpowder.core;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

@FunctionalInterface
public interface FlintcoreHook {
    float process(LivingEntity shooter, ItemStack gun, float baseVal);
}
