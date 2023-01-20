package io.kenza.support.utils

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d

fun BlockPos.toVec3d() = Vec3d(x.toDouble(), y.toDouble(), z.toDouble())