package io.kenza.support.utils

fun Any.isRenderThread(): Boolean {
    return Thread.currentThread().name.equals("Render thread")
}