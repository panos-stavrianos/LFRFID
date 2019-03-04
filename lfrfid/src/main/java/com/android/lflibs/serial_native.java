
package com.android.lflibs;

import android.util.Log;

public class serial_native {

    private int fd;
    private int delay = 100;
    private static final String TAG = "serial_native";

    public int OpenComPort(String device) {
        fd = openport(device);
        if (fd < 0) {
            Log.e(TAG, "native open returns null");
            return -1;
        }
        return 0;
    }

    public void CloseComPort() {
        closeport(fd);
    }

    public byte[] ReadPort(int count) {
        return readport(fd, count, delay);
    }

    public int WritePort(byte[] buf) {
        return writeport(fd, buf);
    }

    public void ClearBuffer() {
        clearportbuf(fd);
    }

    private native int openport(String port);                        //open serial port

    private native void closeport(int fd);                            //close serial port

    private native byte[] readport(int fd, int count, int delay);        //try to read number of count bytes, then return a byte array, which may be shorter than count

    private native int writeport(int fd, byte[] buf);                    //try to write byte array to serialport, may be failed

    private native void clearportbuf(int fd);                                //clear the serialport read/write buffer

    static {
        System.loadLibrary("package");
        System.loadLibrary("lfrfid");
    }
}