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
                        pOutput.accept(ModItemsAmmo.FOOLSGOLDROUNDSHOT.get());
                        pOutput.accept(ModItemsAmmo.COPPERVOLLEYSHOT.get());
                        pOutput.accept(ModItemsAmmo.HEAVYCASTIRONROUNDSHOT.get());
                        pOutput.accept(ModItemsAmmo.HEAVYSTEELROUNDSHOT.get());
                        pOutput.accept(ModItemsAmmo.CASTIRONBOMB.get());
                        pOutput.accept(ModItemsAmmo.CASTIRONBUCKSHOT.get());
                        pOutput.accept(ModItemsAmmo.STEELBUCKSHOT.get());
                        pOutput.accept(ModItemsAmmo.FLAMINGBUCKSHOT.get());
                        pOutput.accept(ModItemsAmmo.FLAMINGGRAPESHOT.get());
                        pOutput.accept(ModItemsAmmo.OILFLAMESHOT.get());
                        pOutput.accept(ModItemsAmmo.SHOTGUNSHELL.get());
                        pOutput.accept(ModItemsAmmo.SHOTGUNSHELLSLUG.get());
                        pOutput.accept(ModItemsAmmo.SHOTGUNSHELLDRAGON.get());
                        pOutput.accept(ModItemsAmmo.PISTOLROUND.get());
                        pOutput.accept(ModItemsAmmo.RIFLEROUND.get());
                        pOutput.accept(ModItemsAmmo.HANDGUNMAG.get());
                        pOutput.accept(ModItemsAmmo.EXTENDEDHANDGUNMAG.get());
                        // Empty
                    }))
                    .build());

    public static final RegistryObject<CreativeModeTab> FNP_FIREARMS_TA = CREATIVE_MODE_TABS.register("fnp_firearms",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.FNPTABICON.get()))
                    .title(Component.translatable("fnp.creativetab.firearms"))
                    .displayItems(((pParameters, pOutput) -> {
                        pOutput.accept(ModItemsGuns.LOGCANNON.get());
                        pOutput.accept(ModItemsGuns.VOLLEYGUN.get());
                        pOutput.accept(ModItemsGuns.FLINTER.get());
                        pOutput.accept(ModItemsGuns.GREEKFIRE.get());
                        pOutput.accept(ModItemsGuns.FLAMINGHALBERD.get());
                        pOutput.accept(ModItemsGuns.HANDGONNE.get());
                        pOutput.accept(ModItemsGuns.PISTOL.get());
                        pOutput.accept(ModItemsGuns.MUSKET.get());
                        pOutput.accept(ModItemsGuns.BLUNDERBUSS.get());
                        pOutput.accept(ModItemsGuns.ARQUEBUS.get());
                        pOutput.accept(ModItemsGuns.RIFLE.get());
                        pOutput.accept(ModItemsGuns.BIGGAMEGUN.get());
                        pOutput.accept(ModItemsGuns.BRUTTBUSS.get());
                        pOutput.accept(ModItemsGuns.SINGLEACTIONREVOLVER.get());
                        pOutput.accept(ModItemsGuns.TRAPDOORRIFLE.get());
                        pOutput.accept(ModItemsGuns.BREAKACTIONCOACHGUN.get());
                        pOutput.accept(ModItemsGuns.PUMPACTIONSHOTGUN.get());
                        pOutput.accept(ModItemsGuns.BOLTACTIONRIFLE.get());
                        pOutput.accept(ModItemsGuns.SEMIAUTOPISTOL.get());
                        pOutput.accept(ModItemsGuns.OPENBOLTSUBMACHINEGUN.get());
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
