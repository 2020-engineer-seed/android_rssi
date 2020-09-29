package dev.siro256.androidapp.rssilogger

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.ActivityCompat
import dev.siro256.androidapp.rssilogger.listener.StartButtonListener

class MainActivity : AppCompatActivity() {
    init {
        instance = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Viewを設定
        setContentView(R.layout.first_layout)

        //権限の確認・要求
        isPermitted()

        //ボタンのリスナー登録
        findViewById<Button>(R.id.start_button).setOnClickListener(StartButtonListener)
    }

    private fun isPermitted() {
        //ACCESS_FINE_LOCATIONが付与されているか確認
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
        }

        //WRITE_EXTERNAL_STORAGEが付与されているか確認
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        }
    }

    companion object {
        //このクラスのインスタンス
        lateinit var instance: MainActivity
        //処理を遅延させる時間
        var delay: Long? = 500
        //計測を開始した時刻
        var startTime = 0L
        //計測データの一時保管用変数
        var tempMeasuredData: ArrayList<MeasuredData>? = null
    }

    //計測データの保存用データクラス
    data class MeasuredData(val sn: Int, val time: Long, val latitude: Double, val longitude: Double, val apName: String, val rssi: Int, val freq: Int)
}