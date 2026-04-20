package org.ragingzombies.flintnpowder.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.ragingzombies.flintnpowder.Flintnpowder;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Flintnpowder.MOD_ID);

    public static final RegistryObject<Enchantment> GHOST_LOADING =
            ENCHANTMENTS.register("ghost_loading", () -> new Enchantment(
                    Enchantment.Rarity.RARE,
                    EnchantmentCategory.WEAPON,
                    new EquipmentSlot[]{EquipmentSlot.MAINHAND}
            ) {
                @Override
                public int getMaxLevel() {
                    return 3;
                }

                @Override
                public int getMinCost(int level) {
                    return 10 + (level - 1) * 8;
                }

                @Override
                public int getMaxCost(int level) {
                    return getMinCost(level) + 15;
                }
            });


    public static final RegistryObject<Enchantment> QUALITY_PROPELLANT =
            ENCHANTMENTS.register("quality_propellant", () -> new Enchantment(
                    Enchantment.Rarity.RARE,
                    EnchantmentCategory.WEAPON,
                    new EquipmentSlot[]{EquipmentSlot.MAINHAND}
            ) {
                @Override
                public int getMaxLevel() {
                    return 3;
                }

                @Override
                public int getMinCost(int level) {
                    return 10 + (level - 1) * 8;
                }

                @Override
                public int getMaxCost(int level) {
                    return getMinCost(level) + 15;
                }
            });



    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
