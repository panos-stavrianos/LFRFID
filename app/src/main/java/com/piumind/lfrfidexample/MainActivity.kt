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

    override fun onPause() {
        reader.close()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        reader.setListener(this)
    }

    public override fun onDestroy() {
        reader.close()
        super.onDestroy()
    }
}
