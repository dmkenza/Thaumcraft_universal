package com.kenza.thaumcraft

import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry
import io.kenza.support.utils.getRegBlockEntityType
import io.kenza.support.utils.reg.Ref
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.client.render.block.entity.BlockEntityRenderer

object ThaumcraftCommonClient {



    fun onInitialize() {

        Ref.CLIENT_ENTITY_REGISTERER_MAP.map { (id, render) ->

            val type = id.getRegBlockEntityType<BlockEntity>() //as BlockEntityType<*>

            BlockEntityRendererRegistry.register(type) {
                render.get() as BlockEntityRenderer<BlockEntity>
            }
        }

    }
}