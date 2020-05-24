package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private var time = 0
    private var isRunning = false
    private var timerTask: Timer? = null

    private var lap = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            isRunning = !isRunning //누르면 true값이 들어감

            if (isRunning){
                start()
            }else{
                pause()
            }

        }

        //랩타임 버튼과 이벤트 연결
        lapButton.setOnClickListener {
            recordLapTime()
        }

        resetFab.setOnClickListener {
            reset()
        }

    }

    private fun start() {

        //fab을 누르면 일시정지 버튼으로 변함
        fab.setImageResource(R.drawable.ic_pause_black_24dp)

        timerTask = timer(period = 10){
            // 0.01초마다 timer 변경
            time++
            val sec = time / 100
            val milli = time % 100
            runOnUiThread {
                secTextView.text = "$sec"
                milliTextView.text = "$milli"
            }
        }
    }

    private fun pause() {
        fab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        timerTask?.cancel() // 실행중인 timer가 있다면 취소

    }

    //랩타임 표시
    private fun recordLapTime(){
        val lapTime = this.time //현재 시간을 지역변수에 저장
        val textView = TextView(this)
        textView.text = "$lap Lap : ${lapTime / 100}.${lapTime % 100}"

        //맨 위에 랩타임 추가
        labLayout.addView(textView, 0)
        lap++

    }

    //타이머 초기화 메소드
    private fun reset(){
        timerTask?.cancel()

        //모든 변수 초기화
        time = 0
        isRunning = false
        fab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        secTextView.text = "0"
        milliTextView.text = "00"

    }


}
