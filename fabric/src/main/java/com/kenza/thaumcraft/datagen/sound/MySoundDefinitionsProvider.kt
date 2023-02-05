package com.kenza.thaumcraft.datagen.sound

import com.kenza.thaumcraft.datagen.sound.SoundDefinition.SoundType
import com.kenza.thaumcraft.reg.SoundFX
import io.kenza.support.utils.identifier
import io.kenza.support.utils.reg.Ref.MOD_ID
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import kotlin.io.path.Path

class MySoundDefinitionsProvider(dataGenerator: FabricDataGenerator) :
    SoundDefinitionsProvider(
        KPathResolver(
            Path("C:\\projects_mc\\work\\Thaumcraft_universal\\common\\src\\main\\resources\\assets"), "sounds"
        ),
//        dataGenerator.createPathResolver(DataGenerator.OutputType.RESOURCE_PACK, "sounds"),
        dataGenerator,
        MOD_ID
    ) {



    override fun registerSounds() {
        SoundFX.values().map {
            add(it.soundEvent, SoundDefinition.definition().apply {
                with(SoundDefinition.Sound.sound(identifier(it.name), SoundType.SOUND))
            })
        }
    }
}