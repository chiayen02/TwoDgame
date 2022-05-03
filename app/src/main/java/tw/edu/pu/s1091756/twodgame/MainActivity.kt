package tw.edu.pu.s1091756.twodgame

import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.OnClickAction
import android.view.View
import kotlinx.coroutines.*
import tw.edu.pu.s1091756.twodgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var secondsLeft:Int = 1000  //倒數
    lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txv.text = secondsLeft.toString()
        binding.btnStart.isEnabled = true
        binding.btnStop.isEnabled = false

        binding.btnStart.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                job = GlobalScope.launch(Dispatchers.Main) {
                    while(secondsLeft > 0) {
                        secondsLeft--
                        binding.txv.text = secondsLeft.toString()
                        binding.btnStart.isEnabled = false
                        binding.btnStop.isEnabled = true
                        delay(25)
                    }
                }
                if(secondsLeft == 0){
                    secondsLeft = 1000
                    binding.txv.text = secondsLeft.toString()
                    binding.btnStart.isEnabled = true
                    binding.btnStop.isEnabled = false
                }
            }
        })
        binding.btnStop.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                job.cancel()
                binding.btnStart.isEnabled = true
                binding.btnStop.isEnabled = false}
        })
    }
    override fun onPause() {
        super.onPause()
        job.cancel()
    }
    override fun onResume() {
        super.onResume()
        if (binding.btnStart.isEnabled == false){
            job = GlobalScope.launch(Dispatchers.Main) {
                while(secondsLeft > 0) {
                    secondsLeft--
                    binding.txv.text = secondsLeft.toString()
                    delay(25)

                    val canvas: Canvas = binding.mysv.holder.lockCanvas()
                    binding.mysv.drawSomething(canvas)
                    binding.mysv.holder.unlockCanvasAndPost(canvas)
                }
            }
        }
    }
}