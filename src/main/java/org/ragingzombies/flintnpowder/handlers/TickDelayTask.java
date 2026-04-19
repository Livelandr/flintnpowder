package org.ragingzombies.flintnpowder.handlers;

public class TickDelayTask {
    int ticksLeft = 0;
    boolean done = false;
    Runnable func;

    TickDelayTask(int ticks, Runnable action) {
        this.ticksLeft = ticks;
        func = action;
    }

    boolean tick() {
        if (!done && --ticksLeft <= 0) {
            func.run();
            done = true;
            return true;
        }

        return false;
    }
}
