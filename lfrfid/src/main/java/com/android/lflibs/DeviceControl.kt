package com.android.lflibs

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

class DeviceControl
constructor(path: String, private val ctrlFile: BufferedWriter = BufferedWriter(FileWriter(File(path), false))) {

    @Throws(Exception::class)
    fun powerOnDevice() {
        ctrlFile.write("-wdout94 1")//64
        ctrlFile.flush()
    }

    @Throws(Exception::class)
    fun powerOffDevice() {
        ctrlFile.write("-wdout94 0")//64
        ctrlFile.flush()
    }

    @Throws(Exception::class)
    fun deviceClose() {
        ctrlFile.close()
    }
}