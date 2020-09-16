package dev.siro256.androidapp.rssilogger.listener

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.net.wifi.WifiManager
import android.widget.TextView
import dev.siro256.androidapp.rssilogger.MainActivity
import dev.siro256.androidapp.rssilogger.R

object LocationListener: LocationListener {
    //WifiManager
    private val wifiManager = MainActivity.instance.getSystemService(Context.WIFI_SERVICE) as WifiManager
    //計測した回数
    var count = 1

    //位置情報更新時のリスナー
    override fun onLocationChanged(location: Location) {
        //WiFiの情報を更新
        wifiManager.startScan()
        //処理を遅延
        MainActivity.delay?.let { Thread.sleep(it) }
        //データを保存する処理の実行
        task(location)
    }

    private fun task(location: Location) {
        //結果が空ならreturn
        if (wifiManager.scanResults.size == 0) return
        //現在時刻の取得
        val time = System.currentTimeMillis()
        //スキャンデータを保存
        wifiManager.scanResults.forEach {
            MainActivity.tempMeasuredData!!.add(
                MainActivity.MeasuredData(count, time,
                    location.latitude, location.longitude, it.SSID, it.level)
            )
            //画面のカウンターの更新
            MainActivity.instance.findViewById<TextView>(R.id.count).text = count.toString()
        }
        //カウンターの回数を増やす
        count ++
    }
}