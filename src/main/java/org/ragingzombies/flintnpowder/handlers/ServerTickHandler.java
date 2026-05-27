/*
 * Copyright (C) 2026 Livelandr
 *
 * This file is part of Flint'N'Powder.
 *
 * Flint'N'Powder is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Flint'N'Powder is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
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
