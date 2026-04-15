package org.ragingzombies.flintnpowder.handlers;

public class TickDelayTask {
    int ticksLeft = 0;
    Runnable func;

    TickDelayTask(int ticks, Runnable action) {
        this.ticksLeft = ticks;
        func = action;
    }

    boolean tick() {
        if (--ticksLeft <= 0) {
            func.run();
            return true;
        }

        return false;
    }
}
