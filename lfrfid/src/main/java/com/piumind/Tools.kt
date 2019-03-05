package com.piumind

import java.util.*
import kotlin.experimental.and


val ByteArray.toHex
    get() = this.joinToString(separator = "") {
        String.format("%02X", (it and (0xFF).toByte()))
    }

val String.toHex
    get() = this.toByteArray().toHex

val String.hexToByteArray
    get() = ByteArray(this.length / 2) {
        this.substring(it * 2, it * 2 + 2).toInt(16).toByte()
    }

val String.toSpaceHex: String
    get() {
        val parts = ArrayList<String>()

        val length = this.length
        var i = 0
        while (i < length) {
            parts.add(this.substring(i, Math.min(length, i + 2)))
            i += 2
        }
        return parts.joinToString(" ")
    }

fun String.removeSpaces(): String = replace(" ", "")
