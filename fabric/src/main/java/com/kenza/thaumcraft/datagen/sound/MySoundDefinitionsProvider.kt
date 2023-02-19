package com.kenza.thaumcraft.datagen.sound

import com.kenza.thaumcraft.datagen.sound.SoundDefinition.SoundType
import com.kenza.thaumcraft.reg.MusicDiscTC
import com.kenza.thaumcraft.reg.SoundTC
import io.kenza.support.utils.identifier
import io.kenza.support.utils.reg.Ref.MOD_ID
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import kotlin.io.path.Path

class MySoundDefinitionsProvider(dataGenerator: FabricDataGenerator) :
    SoundDefinitionsProvider(
        KPathResolver(
            Path(dataGenerator.output.parent.toString() + "\\resources\\assets"), "sounds"
        ),
//        dataGenerator.createPathResolver(DataGenerator.OutputType.RESOURCE_PACK, "sounds"),
        dataGenerator,
        MOD_ID
    ) {


    override fun registerSounds() {
        SoundTC.values().map {
            add(it.soundEvent, SoundDefinition.definition().apply {
                with(SoundDefinition.Sound.sound(identifier(it.name), SoundType.SOUND))
            })
        }

        MusicDiscTC.values().map {
            add(it.soundEvent, SoundDefinition.definition().apply {
//                val f = it.FOLDER
                with(SoundDefinition.Sound.sound(it.soundId(), SoundType.SOUND))
            })
        }
    }
}