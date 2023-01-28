package com.kenza.thaumcraft.reg

import net.minecraft.block.AbstractBlock
import net.minecraft.block.Material
import net.minecraft.block.SlabBlock
import java.util.function.Supplier

val STONE_SETTINGS = AbstractBlock.Settings
    .of(Material.STONE)
    .requiresTool()
    .strength(6f)