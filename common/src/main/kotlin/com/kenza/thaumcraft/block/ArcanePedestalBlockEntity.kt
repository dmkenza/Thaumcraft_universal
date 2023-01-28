package com.kenza.thaumcraft.block

import dev.architectury.hooks.block.BlockEntityHooks
import io.kenza.support.ImplementedInventory
import io.kenza.support.PlayerLookup
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.inventory.Inventories
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos

class ArcanePedestalBlockEntity(type: BlockEntityType<ArcanePedestalBlockEntity>?, pos: BlockPos?, state: BlockState?) :
    BlockEntity(type, pos, state), ImplementedInventory {

    private var inventoryItems: DefaultedList<ItemStack> = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY)
    private var inventoryTouched = true


    override fun markDirty() {
        super<BlockEntity>.markDirty()
    }

    override fun toInitialChunkDataNbt(): NbtCompound {
        val nbt = super.toInitialChunkDataNbt()
        writeNbt(nbt)
        return nbt
    }

    override fun setStack(slot: Int, stack: ItemStack?) {
        inventoryTouched = true
        super.setStack(slot, stack)
    }

    override fun getItems(): DefaultedList<ItemStack> {
        inventoryTouched = true
        return inventoryItems
    }

    override fun readNbt(nbt: NbtCompound) {
//        Inventories.readNbt(nbt, inventoryItems)
        super.readNbt(nbt)

        val tagList = nbt.get("Inventory") as NbtList? ?: NbtList()
        tagList.indices.forEach { i ->
            val stackTag = tagList.getCompound(i)
            val slot = stackTag.getInt("Slot")
            super.setStack(slot, ItemStack.fromNbt(stackTag))
        }
    }

    public override fun writeNbt(nbt: NbtCompound) {
//        Inventories.writeNbt(nbt, inventoryItems)
        val tagList = NbtList()
        for (i in 0 until items.size) {
            val stackTag = NbtCompound()
            stackTag.putInt("Slot", i)
            tagList.add(getStack(i).writeNbt(stackTag))
        }
        nbt.put("Inventory", tagList)


        super.writeNbt(nbt)
    }

    override fun toUpdatePacket(): BlockEntityUpdateS2CPacket {
        return BlockEntityUpdateS2CPacket.create(this)
    }


    fun tick() {
        if (!world!!.isClient && this.inventoryTouched) {

            val viewers = PlayerLookup.tracking(this)
//            val buf = PacketByteBuf(Unpooled.buffer())
//            buf.writeBlockPos(pos)
//
//            for (i in 0..6) {
//                buf.writeItemStack(items.get(i))
//            }
//            viewers.forEach(Consumer { player: ServerPlayerEntity? ->
////                NetworkManager.sendToPlayer(player, UPDATE_INV_PACKET_ID, buf)
//            })

            if(viewers.size > 0){
                BlockEntityHooks.syncData(this)
            }

            inventoryTouched = false
        }
    }

    companion object {
        val INVENTORY_SIZE = 1
    }

}