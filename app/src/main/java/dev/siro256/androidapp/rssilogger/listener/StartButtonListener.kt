package dev.siro256.androidapp.rssilogger.listener

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.view.View
import android.widget.Button
import android.widget.EditText
import dev.siro256.androidapp.rssilogger.MainActivity
import dev.siro256.androidapp.rssilogger.R

object StartButtonListener: View.OnClickListener {
    @SuppressLint("MissingPermission")
    override fun onClick(view: View?) {
        //LocationManager
        val locationManager = MainActivity.instance.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        //変数に現在の時刻を代入
        MainActivity.startTime = System.currentTimeMillis()

        //変数にDelayを代入
        MainActivity.delay = MainActivity.instance.findViewById<EditText>(R.id.wait_time)
            .text.toString().toLong()

        MainActivity.instance.apply {
            //Viewを変更
            setContentView(R.layout.recoding_layout)

            //ボタンのリスナー登録
            findViewById<Button>(R.id.end_button).setOnClickListener(EndButtonListener)
        }

        //変数に空のlistを追加
        MainActivity.tempMeasuredData = arrayListOf()

        //GPSのリスナー登録
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
            MainActivity.delay!!.toLong(), 0F, LocationListener)
    }
}