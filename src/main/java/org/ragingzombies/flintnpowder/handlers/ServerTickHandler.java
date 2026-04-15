package org.ragingzombies.flintnpowder.handlers;

import net.minecraft.util.TaskChainer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.ragingzombies.flintnpowder.Flintnpowder;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Flintnpowder.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerTickHandler {
    public static ServerTickHandler INSTANCE = new ServerTickHandler();
    static List<TickDelayTask> tasks = new ArrayList<>();

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        tasks.removeIf(TickDelayTask::tick);
    }

    public static void createTask(int ticks, Runnable action) {
        tasks.add(new TickDelayTask(ticks, action));
    }
}
