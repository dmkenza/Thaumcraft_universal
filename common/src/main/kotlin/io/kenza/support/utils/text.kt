import net.minecraft.text.MutableText
import net.minecraft.text.Text
import net.minecraft.util.Identifier

import net.minecraft.block.Block
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.math.ChunkPos

fun translatable(text: String, vararg args: Any): MutableText = Text.translatable(text, *args)
fun literal(text: String): MutableText = Text.literal(text)

val EMPTY = literal("")

