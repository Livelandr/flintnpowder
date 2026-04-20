package org.ragingzombies.flintnpowder;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.ragingzombies.flintnpowder.core.util.PlayerSpecificModifiers;
import org.ragingzombies.flintnpowder.handlers.ServerTickHandler;
import org.ragingzombies.flintnpowder.item.ModCreativeModTabs;
import org.ragingzombies.flintnpowder.item.ModItemsAmmo;
import org.ragingzombies.flintnpowder.item.ModItemsAttachments;
import org.ragingzombies.flintnpowder.item.ModItemsGuns;
import org.ragingzombies.flintnpowder.item.ammo.projectiles.ModProjectiles;
import org.ragingzombies.flintnpowder.core.network.PacketHandler;
import org.ragingzombies.flintnpowder.sound.ModSounds;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Flintnpowder.MOD_ID)
public class Flintnpowder {

    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "flintnpowder";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public Flintnpowder() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModItemsAmmo.register(modEventBus);
        ModItemsAttachments.register(modEventBus);
        ModItemsGuns.register(modEventBus);

        ModSounds.register(modEventBus);
        ModProjectiles.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(PlayerSpecificModifiers.INSTANCE);
        MinecraftForge.EVENT_BUS.register(ServerTickHandler.INSTANCE);
        modEventBus.addListener(this::addCreative);

        PacketHandler.register();
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Starting Flint'n'Powder...");
    }

}
