package com.piumind

import android.util.Log
import com.android.lflibs.DeviceControl
import com.android.lflibs.serial_native
import kotlin.concurrent.thread

class Reader {
    private var reader: Thread = thread { }
    private val nativeDev: serial_native = serial_native()
    private lateinit var devCtrl: DeviceControl
    fun read(rfidListener: RFIDListener) {
        try {
            devCtrl = DeviceControl(DEVICE_PATH)

            if (nativeDev.OpenComPort(SERIAL_PORT_PATH) < 0) {
                Log.e(TAG, "Cannot open port")
                return
            }
            startReading(rfidListener)
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
    }

    private fun startReading(rfidListener: RFIDListener) {
        reader = thread {
            try {
                devCtrl.powerOnDevice()
                Thread.sleep(5)
                nativeDev.ClearBuffer()
                while (true) {
                    val buf = nativeDev.ReadPort(BUF_SIZE)
                    if (buf != null && buf.size > 2)
                        rfidListener.onNewRFID(String(buf.copyOfRange(1, buf.size - 2)))
                }
            } catch (e: Exception) {
                Log.e(TAG, e.message)
            }
        }
    }

    fun close() {
        try {
            reader.interrupt()
            devCtrl.powerOffDevice()
            devCtrl.deviceClose()
            nativeDev.CloseComPort()
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
    }

    interface RFIDListener {
        fun onNewRFID(rfid: String)
    }

    companion object {
        private const val SERIAL_PORT_PATH = "/dev/ttyMT2"
        private const val DEVICE_PATH = "/sys/class/misc/mtgpio/pin"
        private const val BUF_SIZE = 64
        private const val TAG = "LFRFID Reader"
    }

}