package dev.siro256.androidapp.rssilogger.listener

import android.content.Context
import android.location.LocationManager
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.EditText
import dev.siro256.androidapp.rssilogger.MainActivity
import dev.siro256.androidapp.rssilogger.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object EndButtonListener: View.OnClickListener {
    override fun onClick(view: View?) {
        MainActivity.instance.apply {
            //GPSのリスナーの解除
            (MainActivity.instance.getSystemService(Context.LOCATION_SERVICE) as LocationManager).removeUpdates(LocationListener)

            //取得したデータの書き出し
            exportFile()

            //Viewを変更
            setContentView(R.layout.first_layout)

            //text変更
            findViewById<EditText>(R.id.wait_time).setText(MainActivity.delay.toString())

            //ボタンのリスナー登録
            findViewById<Button>(R.id.start_button).setOnClickListener(StartButtonListener)
        }
    }

    private fun exportFile() {
        //ファイルを書き出すディレクトリ
        val documentDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        //Epochから変換した時刻
        val date = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").apply {
            timeZone = TimeZone.getTimeZone("Asia/Tokyo")
        }.format(Date(MainActivity.startTime))
        //データをファイルに書き出すためのStringBuilder
        val builder = StringBuilder()
        //書き出す先のCSVを生成
        val csvFile = File(documentDir, "DataSet_${date}.csv")
        //ヘッダ
        builder.append("SerialNumber,EpochTime,Latitude,Longitude,APName,RSSI,Frequency")
        //計測データをStringBuilderに追加
        MainActivity.tempMeasuredData?.forEach {
            builder.append(",\n${it.sn},${it.time},${it.latitude},${it.longitude},${it.apName},${it.rssi}, ${it.freq}")
        }
        //CSVに書き出す
        csvFile.writeText(builder.toString())
    }
}