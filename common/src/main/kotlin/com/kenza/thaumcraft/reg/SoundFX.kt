package com.kenza.thaumcraft.reg

import io.kenza.support.utils.*
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import kotlin.math.absoluteValue

enum class SoundFX {
    cameraticks, wandfail, hhoff, hhon, neutralization, scan, spill, upgrade, urnbreak, whispers,

    bubble1, bubble2, bubble3, bubble4,
    cameraclack1, cameraclack2, cameraclack3,
    page1, page2, poof1, poof2, pump1, pump2, pump3,
    shock1, shock2, wand1, wand2, wand3,
    wind1, wind2, write1, write2, zap1, zap2;


    val soundEvent: SoundEvent
        get() = identifier(name).run {
            getRegSoundEvent()
        }

    fun playSound(world: World, pos: BlockPos, player: PlayerEntity, soundVolume: Float = 1f, pitch: Float = 1f) {
        if (!world.isClient) {
            world
                .playSound(
                    null, pos,
                    soundEvent, SoundCategory.AMBIENT, soundVolume, pitch
                )
        } else {
            world
                .playSound(
                    player, pos,
                    soundEvent, SoundCategory.AMBIENT, soundVolume, pitch
                )
        }

    }


    companion object {
        private val wands = listOf(wand1, wand2, wand3)
        private val cameraclacks = listOf(cameraclack1, cameraclack2, cameraclack3)
        private val bubbles = listOf(bubble1, bubble2, bubble3)
        private val pages = listOf(page1, page2)
        private val poofs = listOf(poof1, poof2)
        private val winds = listOf(wind1, wind2)
        private val pumps = listOf(pump1, pump2, pump3)
        private val shocks = listOf(shock1, shock2)
        private val writes = listOf(write1, write2)
        private val zaps = listOf(zap1, zap2)


        private fun playSound(world: World, pos: BlockPos, list: List<SoundFX>, player: PlayerEntity) =
            list.pickRandomly().playSound(world, pos, player)


        fun playWandSound(world: World, pos: BlockPos, player: PlayerEntity) = playSound(world, pos, wands, player)
        fun playCameraClack(world: World, pos: BlockPos, player: PlayerEntity) =
            playSound(world, pos, cameraclacks, player)


    }
}