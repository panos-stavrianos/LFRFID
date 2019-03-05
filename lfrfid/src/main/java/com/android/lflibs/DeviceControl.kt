package com.android.lflibs

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException

class DeviceControl
constructor(path: String) {
    private val ctrlFile: BufferedWriter

    init {
        val deviceName = File(path)
        ctrlFile = BufferedWriter(FileWriter(deviceName, false))    //open file
    }

    fun powerOnDevice() {
        try {
            ctrlFile.write("-wdout94 1")//64
            ctrlFile.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun powerOffDevice() {
        try {
            ctrlFile.write("-wdout94 0")//64
            ctrlFile.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun deviceClose() {
        try {
            ctrlFile.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}