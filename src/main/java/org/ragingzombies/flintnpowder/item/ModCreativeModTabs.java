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
import org.ragingzombies.flintnpowder.ModItems;
import org.ragingzombies.flintnpowder.item.ammo.ModItemsAmmo;
import org.ragingzombies.flintnpowder.item.attachments.ModItemsAttachments;
import org.ragingzombies.flintnpowder.item.guns.ModItemsGuns;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Flintnpowder.MOD_ID);


    public static final RegistryObject<CreativeModeTab> FNP_AMMO_TA = CREATIVE_MODE_TABS.register("fnp_ammunition",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.GUNPOWDER))
                    .title(Component.translatable("fnp.creativetab.ammunition"))
                    .displayItems(((pParameters, pOutput) -> {
                        pOutput.accept(Items.GUNPOWDER);
                        pOutput.accept(ModItemsAmmo.CASTIRONROUNDSHOT.get());
                        pOutput.accept(ModItemsAmmo.STEELROUNDSHOT.get());
                        pOutput.accept(ModItemsAmmo.COPPERROUNDSHOT.get());
                        pOutput.accept(ModItemsAmmo.SHOTGUNSHELL.get());
                        pOutput.accept(ModItemsAmmo.SHOTGUNSHELLSLUG.get());
                        pOutput.accept(ModItemsAmmo.SHOTGUNSHELLDRAGON.get());
                        pOutput.accept(ModItemsAmmo.PISTOLROUND.get());
                        pOutput.accept(ModItemsAmmo.HANDGUNMAG.get());
                        // Empty
                    }))
                    .build());

    public static final RegistryObject<CreativeModeTab> FNP_FIREARMS_TA = CREATIVE_MODE_TABS.register("fnp_firearms",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.FNPTABICON.get()))
                    .title(Component.translatable("fnp.creativetab.firearms"))
                    .displayItems(((pParameters, pOutput) -> {
                        pOutput.accept(ModItemsGuns.MUSKET.get());
                        pOutput.accept(ModItemsGuns.PISTOL.get());
                        pOutput.accept(ModItemsGuns.FLINTER.get());
                        pOutput.accept(ModItemsGuns.SINGLEACTIONREVOLVER.get());
                        pOutput.accept(ModItemsGuns.PUMPACTIONSHOTGUN.get());
                        pOutput.accept(ModItemsGuns.SEMIAUTOPISTOL.get());
                        pOutput.accept(ModItems.RAMROD.get());
                        pOutput.accept(ModItemsAttachments.SILENCER.get());
                        pOutput.accept(ModItemsAttachments.BAYONET.get());
                        pOutput.accept(ModItemsAttachments.HIGHPROFILEOPTIC.get());
                        pOutput.accept(ModItemsAttachments.LOWPROFILEOPTIC.get());
                    }))
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
