package com.kenza.thaumcraft.reg

import io.kenza.support.utils.getRegSoundEvent
import io.kenza.support.utils.identifier
import net.minecraft.sound.SoundEvent

enum class SoundFX {
    cameraticks, bubble1, bubble2, bubble3, bubble4,
    cameraclack1, cameraclack2, cameraclack3, hhoff, hhon,
    neutralization, page1, page2, poof1, poof2, pump1, pump2, pump3,
    scan, shock1, shock2, spill, upgrade, urnbreak, wand1, wand2, wand3, wandfail,
    whispers, wind1, wind2, write1, write2, zap1, zap2;


    val soundEvent: SoundEvent
        get() = identifier(name).run {
            getRegSoundEvent()
        }
}