package com.example.mediaplayer1

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.e
import android.widget.SeekBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.Exception
import java.util.logging.Handler

class MainActivity : AppCompatActivity() {

    private var mp: MediaPlayer? = null
    private var currentSong: MutableList<Int> = mutableListOf(R.raw.terehawale)
    lateinit var start: FloatingActionButton
    lateinit var pause: FloatingActionButton
    lateinit var stop: FloatingActionButton
    lateinit var seekbar: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start = findViewById(R.id.start)
        pause = findViewById(R.id.pause)
        stop = findViewById(R.id.stop)
        seekbar = findViewById(R.id.seekbar)

        controlSound(currentSong[0])

    }

    private fun controlSound(id: Int) {
        start.setOnClickListener {
            if (mp == null) {
                mp = MediaPlayer.create(this, id)

                initialiseSeekbar()

            }
            mp?.start()

        }

        pause.setOnClickListener {
            if (mp != null) mp?.pause()
        }

        stop.setOnClickListener {
            if (mp != null) {
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp = null
            }
        }


        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mp?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })



    }

    private fun initialiseSeekbar() {
        seekbar.max = mp!!.duration
        val handler = android.os.Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    seekbar.progress = mp!!.currentPosition
                    handler.postDelayed(this, 1000)
                } catch (e: Exception) {
                    seekbar.progress = 0
                }
            }
        }, 1000) // Add closing parenthesis and specify delay in milliseconds
    }
}