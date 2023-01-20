package io.kenza.support.utils


object Mathmc{
    fun Any.between(from: Int, to: Int) = (Math.random() * (to - from) + from).toInt()
}
