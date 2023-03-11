package com.example.check_solve2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val StartButton: Button = findViewById(R.id.StartButton)
        val TrueButton: Button = findViewById(R.id.TrueButton)
        val FalseButton: Button = findViewById(R.id.FalseButton)
        val StopButton: Button = findViewById(R.id.StopButton)
        val CleanButton:Button = findViewById(R.id.CleanButton)

        var FirstLabel: TextView = findViewById(R.id.FirstLabel)
        var CanLabel: TextView = findViewById(R.id.CanLabel)
        var SecondLabel: TextView = findViewById(R.id.SecondLabel)
        val Result: TextView = findViewById(R.id.ResultLabel)

        var OkLabel: TextView = findViewById(R.id.OkLabel)
        var BadLabel: TextView = findViewById(R.id.BedLabel)
        var AllLabel: TextView = findViewById(R.id.AllLabel)

        var ProcentLabel: TextView = findViewById(R.id.ProcentLabel)

        var MinLabel: TextView = findViewById(R.id.MinLabel)
        var MaxLabel: TextView = findViewById(R.id.MaxLabel)
        var AvgSecondLabel: TextView = findViewById(R.id.AvgSecondLabel)

        var Time:Double
        var Time1:Long = 0
        var AllTime:Double = 0.0
        var Counter:Int=0

        var rnd = Random

        var OkInt = 0
        var BadInt = 0
        val Can:List<String> = listOf("+","-","/","*")

        fun ProcentCount():Double
        {
            return (((OkInt.toDouble() / (AllLabel.text.toString()
                .toDouble() / 100.0)) * 100).roundToInt().toDouble() / 100)
        }

        fun Proverka():Boolean
        {
            return when(CanLabel.text)
            {
                "+"->{
                    FirstLabel.text.toString().toInt()+SecondLabel.text.toString().toInt()==Result.text.toString().toInt()
                }

                "-"->{
                    FirstLabel.text.toString().toInt()-SecondLabel.text.toString().toInt()==Result.text.toString().toInt()
                }
                "/"->{
                    FirstLabel.text.toString().toInt()/SecondLabel.text.toString().toInt()==Result.text.toString().toInt()
                }
                "*"->{
                    FirstLabel.text.toString().toInt()*SecondLabel.text.toString().toInt()==Result.text.toString().toInt()
                }
                else->false
            }
        }

        fun RandAnswer(first: String,second:String,can:String)
        {
            val result =  when (can) {
                "+"->{
                    first.toInt()+second.toInt()
                }

                "-"->{
                    first.toInt()-second.toInt()
                }
                "/"->{
                    first.toInt()/second.toInt()
                }
                "*"->{
                    first.toInt()*second.toInt()
                }
                else->0
            }

            if(rnd.nextBoolean())
            {
                Result.text=result.toString()
            }
            else
            {
                Result.text=rnd.nextInt(20,200).toString()
            }

        }

        fun GetTime(Time:Double)
        {
            if(Counter==0)
            {
                MinLabel.text=Time.toString()
            }
            if(MinLabel.text.toString().toDouble()>Time)
            {
                MinLabel.text=Time.toString()
            }
            if(MaxLabel.text.toString().toDouble()<Time)
            {
                MaxLabel.text=Time.toString()
            }
            ++Counter;
            AllTime+=Time
            AvgSecondLabel.text=(((AllTime/Counter.toDouble())*100).roundToInt().toDouble()/100).toString()
        }

        fun RandValue(): Triple<String, String, String>
        {
            CanLabel.text = Can.elementAt(rnd.nextInt(0,4))
            var First=rnd.nextInt(10,100)
            var Second = rnd.nextInt(10,100)
            if(CanLabel.text=="/")
            {
                while (First.toDouble()%Second.toDouble()!=0.0)
                {
                    First = rnd.nextInt(10,100)
                    Second = rnd.nextInt(10,100)
                }
            }
            if(CanLabel.text=="*")
            {
                First=rnd.nextInt(1,10)
                Second=rnd.nextInt(1,10)
            }
            FirstLabel.text = First.toString()
            SecondLabel.text = Second.toString()
            return Triple(FirstLabel.text.toString(),SecondLabel.text.toString(),CanLabel.text.toString())
        }

        StartButton.setOnClickListener()
        {
            StartButton.isEnabled = false
            TrueButton.isEnabled = true
            FalseButton.isEnabled = true
            CleanButton.isEnabled=false
            StopButton.isEnabled=true
            Time1=System.currentTimeMillis()
            val value = RandValue()
            RandAnswer(value.first,value.second,value.third)
        }

        StopButton.setOnClickListener()
        {
            StopButton.isEnabled = false
            TrueButton.isEnabled = false
            FalseButton.isEnabled = false
            CleanButton.isEnabled=true
            StartButton.isEnabled = true
        }

        CleanButton.setOnClickListener()
        {
            OkLabel.text="0"
            BadLabel.text="0"
            AllLabel.text="0"
            ProcentLabel.text="0.00%"
            OkInt=0
            BadInt=0
            Counter=0
            MinLabel.text="0"
            MaxLabel.text="0"
            AvgSecondLabel.text="0.00"
            AllTime=0.0
            CleanButton.isEnabled=false
        }


        TrueButton.setOnClickListener()
        {
            if(Proverka())
            {
                OkLabel.text=(++OkInt).toString()
                AllLabel.text=(OkInt+BadInt).toString()
            }
            else
            {
                BadLabel.text=(++BadInt).toString()
                AllLabel.text=(OkInt+BadInt).toString()
            }
            ProcentLabel.text = ProcentCount().toString()+"%"
            val value = RandValue()
            RandAnswer(value.first,value.second,value.third)
            Time = ((((System.currentTimeMillis().toDouble()-Time1.toDouble())/1000.0)*100).roundToInt().toDouble()/100)
            GetTime(Time)
            Time=0.0;
            Time1=System.currentTimeMillis();
        }

        FalseButton.setOnClickListener()
        {
            if(!Proverka())
            {
                OkLabel.text=(++OkInt).toString()
                AllLabel.text=(OkInt+BadInt).toString()
            }
            else
            {
                BadLabel.text=(++BadInt).toString()
                AllLabel.text=(OkInt+BadInt).toString()
            }
            ProcentLabel.text = ProcentCount().toString()+"%"
            val value = RandValue()
            RandAnswer(value.first,value.second,value.third)
            Time = ((((System.currentTimeMillis().toDouble()-Time1.toDouble())/1000.0)*100).roundToInt().toDouble()/100)
            GetTime(Time)
            Time=0.0;
            Time1=System.currentTimeMillis();
        }
    }
}