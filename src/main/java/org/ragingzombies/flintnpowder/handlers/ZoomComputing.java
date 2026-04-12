package org.ragingzombies.flintnpowder.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.ragingzombies.flintnpowder.Flintnpowder;
import org.ragingzombies.flintnpowder.core.guns.GunBase;

@Mod.EventBusSubscriber(modid = Flintnpowder.MOD_ID, value = Dist.CLIENT)
public class ZoomComputing {
    public static Double standardMouse = 0.5;
    public static boolean isZooming = false;

    private static boolean isAiming(Player ply) {
        ItemStack stack = ply.getMainHandItem();
        Item item = stack.getItem();

        if (item instanceof GunBase) {
            GunBase gun = (GunBase) item;
            if (gun.getAttachmentItem(stack, "Optic") != Items.AIR) {
                return true;
            }
        }
        return false;
    }

    private static float zoomModifier(Player ply) {
        ItemStack stack = ply.getMainHandItem();
        Item item = stack.getItem();

        if (item instanceof GunBase) {
            GunBase gun = (GunBase) item;
            if (gun.getAttachmentItem(stack, "Optic") != Items.AIR) {
                return stack.getOrCreateTag().getFloat("OpticZoom");
            }
        }
        return 1;
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        standardMouse = Minecraft.getInstance().options.sensitivity().get();
    }

    @SubscribeEvent
    public static void onComputeFov(ComputeFovModifierEvent event) {
        Player player = event.getPlayer();

        // KeyBindings check is required to be first to avoid extreme lags
        if (KeyBindings.ZOOM_KEY.isDown() && isAiming(player)) {
            float currentFov = event.getFovModifier();
            event.setNewFovModifier(currentFov * zoomModifier(player) );
            Minecraft.getInstance().options.sensitivity().set(standardMouse * 2 * zoomModifier(player));
            isZooming = true;
        } else {
            Minecraft.getInstance().options.sensitivity().set(standardMouse);
            isZooming = false;
        }
    }

    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent event) {
        event.setCanceled(isZooming);
    }
}