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
package org.ragingzombies.flintnpowder.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.ragingzombies.flintnpowder.Flintnpowder;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Flintnpowder.MOD_ID);

    public static final RegistryObject<SoundEvent> RAMROD = registerSoundEvents("ramrod");
    public static final RegistryObject<SoundEvent> GUNSWING = registerSoundEvents("gunswing");
    public static final RegistryObject<SoundEvent> MUSKETFIRE = registerSoundEvents("musketfire");
    public static final RegistryObject<SoundEvent> BULLETHIT = registerSoundEvents("bullethit");

    public static final RegistryObject<SoundEvent> FLINTPRIME = registerSoundEvents("flintprime");
    public static final RegistryObject<SoundEvent> FLINTSTRIKE = registerSoundEvents("flintstrike");

    public static final RegistryObject<SoundEvent> SHOTGUNPUMPBACK = registerSoundEvents("shotgunpumpbackward");
    public static final RegistryObject<SoundEvent> SHOTGUNRELOAD = registerSoundEvents("shotgunshell");
    public static final RegistryObject<SoundEvent> SHOTGUNPUMPFORW = registerSoundEvents("shotgunpumpforward");
    public static final RegistryObject<SoundEvent> SHOTGUNSHOT = registerSoundEvents("autoshotgun");
    public static final RegistryObject<SoundEvent> SHOTGUNSHOTSILENCED = registerSoundEvents("silencedshotgun");

    public static final RegistryObject<SoundEvent> RIFLESHOOT = registerSoundEvents("rifle");
    public static final RegistryObject<SoundEvent> RIFLERELOAD = registerSoundEvents("rifleround");
    public static final RegistryObject<SoundEvent> RIFLEBOLTFORW = registerSoundEvents("rifleboltforward");
    public static final RegistryObject<SoundEvent> RIFLEBOLTBACK = registerSoundEvents("rifleboltbackward");

    public static final RegistryObject<SoundEvent> BRBOLTFORW = registerSoundEvents("riflecockforward");
    public static final RegistryObject<SoundEvent> BRBOLTBACK = registerSoundEvents("riflecockbackward");
    public static final RegistryObject<SoundEvent> BRMAGOUT = registerSoundEvents("riflemagout");
    public static final RegistryObject<SoundEvent> BRFIRE = registerSoundEvents("battlerifle");

    public static final RegistryObject<SoundEvent> PISTOLSHOOT = registerSoundEvents("pistol");
    public static final RegistryObject<SoundEvent> PISTOLDISTANTSHOOT = registerSoundEvents("distantpistolshot");
    public static final RegistryObject<SoundEvent> PISTOLCOCKBACKWARD = registerSoundEvents("pistolcockbackward");
    public static final RegistryObject<SoundEvent> PISTOLCOCKFORWARD = registerSoundEvents("pistolcockforward");
    public static final RegistryObject<SoundEvent> PISTOLMAGIN = registerSoundEvents("pistolmagin");

    public static final RegistryObject<SoundEvent> SNIPERSHOOT = registerSoundEvents("snipershoot");


    public static final RegistryObject<SoundEvent> GUNSHOTDISTANT = registerSoundEvents("distantgunshot");




    public static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Flintnpowder.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
