package io.kenza.support.utils

import dev.architectury.registry.registries.RegistrySupplier
import io.kenza.support.utils.Ref.BLOCKS
import io.kenza.support.utils.Ref.BLOCK_ENTITY_TYPES
import io.kenza.support.utils.Ref.ITEMS
import io.kenza.support.utils.Ref.POINT_OF_INTEREST_TYPES
import io.kenza.support.utils.Ref.SCREEN_HANDLERS
import io.kenza.support.utils.Ref.SOUNDS_EVENTS
import io.kenza.support.utils.Ref.VILLAGER_PROFESSIONS
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.nbt.NbtCompound
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.sound.SoundEvent
import net.minecraft.util.Identifier
import net.minecraft.util.math.ChunkPos
import net.minecraft.village.VillagerProfession
import net.minecraft.world.poi.PointOfInterestType
import java.util.function.Supplier


fun Identifier.block(supplier: Supplier<Block> ): RegistrySupplier<Block> {
    return BLOCKS.register(this, supplier)
}

fun Identifier.createBlock(func: () -> AbstractBlock.Settings): Supplier<Block> {
    return Supplier {
        Block(func.invoke())
    }
}

//fun Identifier.fluid(fluid: Fluid): Identifier {
//    Registry.register(Registry.FLUID, this, fluid)
//    return this
//}

//fun Identifier.blockItem(): RegistrySupplier<Item> {
//    return item {
//        MOD_BLOCKS_MAP[this]?.get()!!.defaultBlockItem()
//    }
//}

fun Identifier.item(supplier: Supplier<Item>): RegistrySupplier<Item> {
    return ITEMS.register(this, supplier)
}

fun Identifier.soundEvent(supplier: Supplier<SoundEvent> ): RegistrySupplier<SoundEvent> {
    return SOUNDS_EVENTS.register(this, supplier)
}
//fun Identifier.soundEvent(): RegistrySupplier<SoundEvent> = soundEvent{SoundEvent(this)}
fun Identifier.soundEvent(): RegistrySupplier<SoundEvent> = soundEvent{SoundEvent.of(this)}

fun Identifier.screenHandler(supplier: Supplier<ScreenHandlerType<*>>): RegistrySupplier<ScreenHandlerType<*>> {
    return SCREEN_HANDLERS.register(this, supplier)
}

fun Identifier.poiType(supplier: Supplier<PointOfInterestType>): RegistrySupplier<PointOfInterestType> {
    return POINT_OF_INTEREST_TYPES.register(this, supplier)
}

fun Identifier.profession(supplier: Supplier<VillagerProfession>): RegistrySupplier<VillagerProfession> {
    return VILLAGER_PROFESSIONS.register(this, supplier)
}


fun Identifier.blockEntityType(supplier: Supplier<BlockEntityType<*>>): RegistrySupplier<BlockEntityType<*>> {
    return BLOCK_ENTITY_TYPES.register(this, supplier)

}

val EMPTY_ITEM = { Item(Item.Settings()) }

//private fun Block.defaultBlockItem(): BlockItem {
//    return BlockItem(this, Item.Settings().group(Ref.MOD_TAB))
//}


fun ChunkPos.toNbt() = NbtCompound().also {
    it.putInt("x", x)
    it.putInt("z", z)
}

fun getChunkPos(nbt: NbtCompound) = ChunkPos(nbt.getInt("x"), nbt.getInt("z"))



