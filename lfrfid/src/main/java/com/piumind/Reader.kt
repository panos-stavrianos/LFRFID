package com.piumind

import android.serialport.SerialPortSpd
import android.util.Log
import kotlin.concurrent.thread

class Reader {
    private var reader: Thread = thread { }
    private val nativeDev = SerialPortSpd()

    private var devCtrl: DeviceControl? = null
    private var interrupt = false

    fun setListener(rfidListener: RFIDListener) {
        try {
            close()
            devCtrl = DeviceControl(DEVICE_PATH)
            nativeDev.OpenSerial(SerialPortSpd.SERIAL_TTYMT2, 0)
            startReading(rfidListener)
        } catch (e: Exception) {
            close()
            e.printStackTrace()
        }
    }

    private fun startReading(rfidListener: RFIDListener) {
        reader = thread {
            try {
                interrupt = false
                devCtrl?.powerOnDevice()
                Thread.sleep(5)
                nativeDev.clearPortBuf(nativeDev.fd)
                while (!interrupt) {
                    val buf = nativeDev.ReadSerial(nativeDev.fd, BUF_SIZE)

                    if (buf != null && buf.size > 2) {
                        val hexMsg = String(buf.copyOfRange(1, buf.size - 2))
                        if (hexMsg.matches("-?[0-9a-fA-F]+".toRegex())) {
                            rfidListener.onNewRFID(hexMsg)
                        } else
                            Log.e(TAG, "Wrong Data")
                    }
                }
            } catch (e: Exception) {
                close()
                e.printStackTrace()
            }
        }
    }

    fun close() {
        Log.e(TAG, "Reader close")

        interrupt = true
        try {
            reader.interrupt()

            reader.join()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            devCtrl?.powerOffDevice()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            devCtrl?.deviceClose()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            nativeDev.CloseSerial(nativeDev.fd)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    interface RFIDListener {
        fun onNewRFID(rfid: String)
    }

    companion object {
        private const val DEVICE_PATH = "/sys/class/misc/mtgpio/pin"
        private const val BUF_SIZE = 64
        private const val TAG = "LFRFID Reader"
    }

}