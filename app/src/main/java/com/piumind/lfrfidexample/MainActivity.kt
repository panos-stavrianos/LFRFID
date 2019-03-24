package com.piumind.lfrfidexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.piumind.Reader

class MainActivity : AppCompatActivity(), Reader.RFIDListener {

    private var reader = Reader()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        reader.setListener(this)
    }

    override fun onNewRFID(rfid: String) {
        println(rfid)
    }

    public override fun onDestroy() {
        reader.close()
        super.onDestroy()
    }
}
