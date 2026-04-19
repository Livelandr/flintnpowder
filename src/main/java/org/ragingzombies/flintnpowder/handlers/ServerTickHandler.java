package org.ragingzombies.flintnpowder.handlers;

import net.minecraft.util.TaskChainer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.ragingzombies.flintnpowder.Flintnpowder;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Mod.EventBusSubscriber(modid = Flintnpowder.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerTickHandler {
    public static ServerTickHandler INSTANCE = new ServerTickHandler();
    static List<TickDelayTask> tasks = new ArrayList<>();
    private static final Lock lock = new ReentrantLock(true);

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        lock.lock();
        try {
            tasks.removeIf(TickDelayTask::tick);
        } catch (Exception ignored) {
        } finally {
            lock.unlock();
        }
    }

    public static void createTask(int ticks, Runnable action) {
        lock.lock();
        try {
            tasks.add(new TickDelayTask(ticks+1, action));
        } catch (Exception ignored) {
        } finally {
            lock.unlock();
        }
    }
}
