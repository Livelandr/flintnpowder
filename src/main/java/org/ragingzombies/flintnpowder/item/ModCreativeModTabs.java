package org.ragingzombies.flintnpowder.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.ragingzombies.flintnpowder.Flintnpowder;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Flintnpowder.MOD_ID);


    public static final RegistryObject<CreativeModeTab> FNP_AMMO_TA = CREATIVE_MODE_TABS.register("fnp_ammunition",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.GUNPOWDER))
                    .title(Component.translatable("fnp.creativetab.ammunition"))
                    .displayItems(((pParameters, pOutput) -> {
                        pOutput.accept(Items.GUNPOWDER);
                        pOutput.accept(ModItems.CASTIRONROUNDSHOT.get());
                        pOutput.accept(ModItems.COPPERROUNDSHOT.get());
                        pOutput.accept(ModItems.SHOTGUNSHELL.get());
                        pOutput.accept(ModItems.SHOTGUNSHELLSLUG.get());
                        pOutput.accept(ModItems.SHOTGUNSHELLDRAGON.get());
                        pOutput.accept(ModItems.PISTOLROUND.get());
                        // Empty
                    }))
                    .build());

    public static final RegistryObject<CreativeModeTab> FNP_FIREARMS_TA = CREATIVE_MODE_TABS.register("fnp_firearms",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.FNPTABICON.get()))
                    .title(Component.translatable("fnp.creativetab.firearms"))
                    .displayItems(((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.MUSKET.get());
                        pOutput.accept(ModItems.FLINTER.get());
                        pOutput.accept(ModItems.SINGLEACTIONREVOLVER.get());
                        pOutput.accept(ModItems.PUMPACTIONSHOTGUN.get());
                        pOutput.accept(ModItems.RAMROD.get());
                    }))
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
