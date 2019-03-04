package com.piumind

import android.os.Message
import android.util.Log
import com.android.lflibs.DeviceControl
import com.android.lflibs.serial_native
import java.io.BufferedWriter
import java.io.File
import java.io.IOException
import kotlin.concurrent.thread
import kotlin.experimental.xor

class Reader {
    private var DevCtrl: DeviceControl? = null
    private val SERIALPORT_PATH = "/dev/ttyMT2"
    private val BUFSIZE = 64
    var reader: Thread = thread { }
    private var size = 0
    private var xor_result: Byte = 0
    private var count0 = 0
    private var count1 = 0
    private var count2 = 0
    private var count3 = 0
    private var count4 = 0
    private var count5 = 0

    private val device_path: File? = null
    private val proc: BufferedWriter? = null
    private var NativeDev: serial_native? = null
    fun read(rfidListener: RFIDListener) {
        NativeDev = serial_native()
        if (NativeDev!!.OpenComPort(SERIALPORT_PATH) < 0) {
            //todo failed
            return
        }
        try {
            DevCtrl = DeviceControl("/sys/class/misc/mtgpio/pin")
            startReading(rfidListener)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    private fun startReading(rfidListener: RFIDListener) {
        reader = thread {
            Log.d("lfrfid", "thread start")
            while (true) {
                val buf = NativeDev?.ReadPort(BUFSIZE)
                if (buf != null) {
                    val msg = Message()
                    if (buf.size >= 2) {
                        size = 0
                        msg.what = 1
                        msg.obj = buf
                        rfidListener.onNewRFID(decode(msg))
                    }
                }
            }
        }
    }

    fun close() {
        try {
            reader.interrupt()
            DevCtrl?.PowerOffDevice()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        NativeDev?.CloseComPort()
    }

    fun decode(msg: Message): String {
        return if (msg.what == 1) {
            val buf = msg.obj as ByteArray
            if (buf.size == 30) {
                for (a in 1..26) {
                    xor_result = xor_result xor buf[a]
                }
                val cnt = String(buf)
                val serialNumber = arrayOfNulls<String>(30)
                serialNumber[9] = cnt.substring(1, 2)
                serialNumber[8] = cnt.substring(2, 3)
                serialNumber[7] = cnt.substring(3, 4)
                serialNumber[6] = cnt.substring(4, 5)
                serialNumber[5] = cnt.substring(5, 6)
                serialNumber[4] = cnt.substring(6, 7)
                serialNumber[3] = cnt.substring(7, 8)
                serialNumber[2] = cnt.substring(8, 9)
                serialNumber[1] = cnt.substring(9, 10)
                serialNumber[0] = cnt.substring(10, 11)
                val reverse =
                    serialNumber[0] + serialNumber[1] + serialNumber[2] + serialNumber[3] + serialNumber[4] + serialNumber[5] + serialNumber[6] + serialNumber[7] + serialNumber[8] + serialNumber[9]
                val decFirst = java.lang.Long.parseLong(reverse, 16)
                val string = java.lang.Long.toString(decFirst)
                size = string.length
                when (size) {
                    1// if (xor_result==buf[27])
                    -> {
                        serialNumber[10] = cnt.substring(14, 15)
                        serialNumber[11] = cnt.substring(13, 14)
                        serialNumber[12] = cnt.substring(12, 13)
                        serialNumber[13] = cnt.substring(11, 12)
                        val countryCode = serialNumber[11] + serialNumber[12] + serialNumber[13]
                        val decResult = java.lang.Long.parseLong(countryCode, 16)
                        val secondDec = java.lang.Long.toString(decResult)
                        val combine =
                            secondDec + "0" + "0" + "0" + "0" + "0" + "0" + "0" + "0" + "0" + "0" + "0" + string
                        combine
                        //   break;
                    }
                    2 // if (xor_result==buf[27])
                    -> {
                        serialNumber[10] = cnt.substring(14, 15)
                        serialNumber[11] = cnt.substring(13, 14)
                        serialNumber[12] = cnt.substring(12, 13)
                        serialNumber[13] = cnt.substring(11, 12)
                        val countryCode = serialNumber[11] + serialNumber[12] + serialNumber[13]
                        val decResult = java.lang.Long.parseLong(countryCode, 16)
                        val secondDec = java.lang.Long.toString(decResult)
                        val combine = secondDec + "0" + "0" + "0" + "0" + "0" + "0" + "0" + "0" + "0" + "0" + string
                        combine
                    }
                    3 // if (xor_result==buf[27])
                    -> {
                        serialNumber[10] = cnt.substring(14, 15)
                        serialNumber[11] = cnt.substring(13, 14)
                        serialNumber[12] = cnt.substring(12, 13)
                        serialNumber[13] = cnt.substring(11, 12)
                        val countryCode = serialNumber[11] + serialNumber[12] + serialNumber[13]
                        val decResult = java.lang.Long.parseLong(countryCode, 16)
                        val secondDec = java.lang.Long.toString(decResult)
                        val combine = secondDec + "0" + "0" + "0" + "0" + "0" + "0" + "0" + "0" + "0" + string
                        combine
                    }
                    4   //if (xor_result==buf[27])
                    -> {
                        serialNumber[10] = cnt.substring(14, 15)
                        serialNumber[11] = cnt.substring(13, 14)
                        serialNumber[12] = cnt.substring(12, 13)
                        serialNumber[13] = cnt.substring(11, 12)
                        val countryCode = serialNumber[11] + serialNumber[12] + serialNumber[13]
                        val decResult = java.lang.Long.parseLong(countryCode, 16)
                        val secondDec = java.lang.Long.toString(decResult)
                        val combine = secondDec + "0" + "0" + "0" + "0" + "0" + "0" + "0" + "0" + string
                        combine
                    }
                    5  // if (xor_result==buf[27])
                    -> {
                        serialNumber[10] = cnt.substring(14, 15)
                        serialNumber[11] = cnt.substring(13, 14)
                        serialNumber[12] = cnt.substring(12, 13)
                        serialNumber[13] = cnt.substring(11, 12)
                        val countryCode = serialNumber[11] + serialNumber[12] + serialNumber[13]
                        val decResult = java.lang.Long.parseLong(countryCode, 16)
                        val secondDec = java.lang.Long.toString(decResult)
                        val combine = secondDec + "0" + "0" + "0" + "0" + "0" + "0" + "0" + string
                        combine
                    }
                    6  // if (xor_result==buf[27])
                    -> {
                        serialNumber[10] = cnt.substring(14, 15)
                        serialNumber[11] = cnt.substring(13, 14)
                        serialNumber[12] = cnt.substring(12, 13)
                        serialNumber[13] = cnt.substring(11, 12)
                        val countryCode = serialNumber[11] + serialNumber[12] + serialNumber[13]
                        val decResult = java.lang.Long.parseLong(countryCode, 16)
                        val secondDec = java.lang.Long.toString(decResult)
                        val combine = secondDec + "0" + "0" + "0" + "0" + "0" + "0" + string
                        combine
                    }
                    7   // if (xor_result==buf[27])
                    -> {
                        serialNumber[10] = cnt.substring(14, 15)
                        serialNumber[11] = cnt.substring(13, 14)
                        serialNumber[12] = cnt.substring(12, 13)
                        serialNumber[13] = cnt.substring(11, 12)
                        val countryCode = serialNumber[11] + serialNumber[12] + serialNumber[13]
                        val decResult = java.lang.Long.parseLong(countryCode, 16)
                        val secondDec = java.lang.Long.toString(decResult)
                        val combine = secondDec + "0" + "0" + "0" + "0" + "0" + string
                        return combine
                    }
                    8   // if (xor_result==buf[27])
                    -> {
                        serialNumber[10] = cnt.substring(14, 15)
                        serialNumber[11] = cnt.substring(13, 14)
                        serialNumber[12] = cnt.substring(12, 13)
                        serialNumber[13] = cnt.substring(11, 12)
                        val countryCode = serialNumber[11] + serialNumber[12] + serialNumber[13]
                        val decResult = java.lang.Long.parseLong(countryCode, 16)
                        val secondDec = java.lang.Long.toString(decResult)
                        val combine = secondDec + "0" + "0" + "0" + "0" + string
                        combine
                    }
                    9   // if (xor_result==buf[27])
                    -> {
                        serialNumber[10] = cnt.substring(14, 15)
                        serialNumber[11] = cnt.substring(13, 14)
                        serialNumber[12] = cnt.substring(12, 13)
                        serialNumber[13] = cnt.substring(11, 12)
                        val countryCode = serialNumber[11] + serialNumber[12] + serialNumber[13]
                        val decResult = java.lang.Long.parseLong(countryCode, 16)
                        val secondDec = java.lang.Long.toString(decResult)
                        val combine = secondDec + "0" + "0" + "0" + string
                        combine
                    }
                    10  // if (xor_result==buf[27])
                    -> {
                        serialNumber[10] = cnt.substring(14, 15)
                        serialNumber[11] = cnt.substring(13, 14)
                        serialNumber[12] = cnt.substring(12, 13)
                        serialNumber[13] = cnt.substring(11, 12)
                        val countryCode = serialNumber[11] + serialNumber[12] + serialNumber[13]
                        val decResult = java.lang.Long.parseLong(countryCode, 16)
                        val secondDec = java.lang.Long.toString(decResult)
                        val combine = secondDec + "0" + "0" + string
                        combine
                    }
                    11   //if (xor_result==buf[27])
                    -> {
                        serialNumber[10] = cnt.substring(14, 15)
                        serialNumber[11] = cnt.substring(13, 14)
                        serialNumber[12] = cnt.substring(12, 13)
                        serialNumber[13] = cnt.substring(11, 12)
                        val countryCode = serialNumber[11] + serialNumber[12] + serialNumber[13]
                        val decResult = java.lang.Long.parseLong(countryCode, 16)
                        val secondDec = java.lang.Long.toString(decResult)
                        val combine = secondDec + "0" + string
                        combine
                    }
                    12   // if (xor_result==buf[27])
                    -> {
                        serialNumber[10] = cnt.substring(14, 15)
                        serialNumber[11] = cnt.substring(13, 14)
                        serialNumber[12] = cnt.substring(12, 13)
                        serialNumber[13] = cnt.substring(11, 12)
                        val countryCode = serialNumber[11] + serialNumber[12] + serialNumber[13]
                        val decResult = java.lang.Long.parseLong(countryCode, 16)
                        val secondDec = java.lang.Long.toString(decResult)
                        val combine = secondDec + string
                        combine
                    }
                    else -> ""
                }
            } else {
                val cnt = String(buf)
                count0 = Integer.parseInt(cnt.substring(1, 3), 16)
                count1 = Integer.parseInt(cnt.substring(3, 5), 16)
                count2 = Integer.parseInt(cnt.substring(5, 7), 16)
                count3 = Integer.parseInt(cnt.substring(7, 9), 16)
                count4 = Integer.parseInt(cnt.substring(9, 11), 16)
                count5 = count0 xor count1 xor count2 xor count3 xor count4
                val b = ByteArray(4)
                b[0] = (count5 and 0xff).toByte()
                if (b[0] == buf[11]) cnt.substring(1, cnt.length - 2)
                else ""
            }
        } else ""
    }

    interface RFIDListener {
        fun onNewRFID(rfid: String)
    }
}