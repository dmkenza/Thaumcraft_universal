package com.kenza.thaumcraft.reg

import io.kenza.support.utils.KMath
import io.kenza.support.utils.getRegSoundEvent
import io.kenza.support.utils.identifier
import io.kenza.support.utils.pickRandomly
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import kotlin.math.absoluteValue

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

    companion object {
        val wands = listOf(SoundFX.wand1, SoundFX.wand2, SoundFX.wand3)

        fun playWandSound(world: World, pos: BlockPos) {

            val sound = wands.pickRandomly().soundEvent

            world
                .playSound(
                    null, pos,
                    sound, SoundCategory.AMBIENT, 1f, 1f
                )

        }
    }
}