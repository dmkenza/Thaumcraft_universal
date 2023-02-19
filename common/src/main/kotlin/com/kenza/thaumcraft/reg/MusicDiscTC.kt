package com.kenza.thaumcraft.reg

import dev.architectury.registry.registries.RegistrySupplier
import io.kenza.support.utils.*
import io.kenza.support.utils.reg.DEFAULT_SINGLE_ITEM_SETTING
import io.kenza.support.utils.reg.Ref.MOD_TAB
import net.minecraft.item.Item
import net.minecraft.item.MusicDiscItem
import net.minecraft.sound.SoundEvent
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity

enum class MusicDiscTC {
    alpenglow, lunas_sorrow, the_moonlit_meadow;
//    music_disc_alpenglow("alpenglow"),
//    music_disc_lunas_sorrow("lunas_sorrow")
//    music_disc_the_moonlit_meadow("the_moonlit_meadow");


    var FOLDER: String = "disc"


    fun soundId(): Identifier {
        return identifier("${FOLDER}/${this.name}")
    }

    fun itemId(): Identifier {
        return identifier("music_disc_" + this.name)
    }


    val soundEvent: SoundEvent
        get() = soundId().run {
            getRegSoundEvent()
        }

    companion object {
        fun registerAll() {
            MusicDiscTC.values().map {
                it.soundId().apply {
                    soundEvent()
                }

                it.itemId().apply {
                    item {
                        createMusicDisc(
                            it.soundId().getRegSupSoundEvent()
                        )
                    }
                    itemDataGen()
                }


            }
        }


        private fun createMusicDisc(sound: RegistrySupplier<SoundEvent>) =
            MusicDiscItem(
                1, sound.get(), Item.Settings()
                    .maxCount(1)
                    .group(MOD_TAB)
                    .rarity(Rarity.RARE),
                0
            )
    }
}