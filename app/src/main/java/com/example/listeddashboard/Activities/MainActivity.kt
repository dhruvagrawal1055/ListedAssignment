package com.example.listeddashboard.Activities

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.listeddashboard.Fragments.RecentsLinks
import com.example.listeddashboard.Fragments.TopLinks
import com.example.listeddashboard.R
import com.example.listeddashboard.ViewModel.Vm
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Locale

class MainActivity : AppCompatActivity() {
    lateinit var TodaysClicks:String
    lateinit var location:String
    lateinit var topSource:String
    lateinit var Totalclicks:TextView
    lateinit var loc:TextView
    lateinit var Sources:TextView
    lateinit var frag1:Button
    lateinit var frag2:Button
    lateinit var lineChart: LineChart
    lateinit var name:TextView
    lateinit var data:Map<String,Int>
    private lateinit var viewModel: Vm
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loc = findViewById(R.id.ahmedabad)
        Sources=findViewById(R.id.instagram)
        Totalclicks = findViewById(R.id.t123)
        frag1 = findViewById(R.id.toplinks)
        frag2 = findViewById(R.id.recentlinks)
        viewModel = ViewModelProvider(this)[Vm::class.java]
        lineChart = findViewById(R.id.lineChart)
        val fragmentManager = supportFragmentManager
        // To get localTime instance to greet
        val now = LocalDateTime.now()
        val greeting = when (now.hour) {
            in 0..11 -> "Good morning!"
            in 12..16 -> "Good afternoon!"
            in 17..23 -> "Good evening!"
            else -> "Welcome!"
        }
        findViewById<TextView>(R.id.greeting).text = greeting

        //Viewmodel result observer as we are using livedata
        viewModel.res.observe(this, Observer { res->
            println("inside main activity ")
            TodaysClicks = res.today_clicks.toString()
            location = res.top_location
            topSource = res.top_source
            Totalclicks.text = TodaysClicks
            loc.text=location
            Sources.text=topSource
            //data for line graph
            data = res.data.overall_url_chart
            graphPlot()
            //calling fragment as default then can be swtitched using tabs
            val fragment1 = TopLinks()
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, fragment1)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        })
        name = findViewById(R.id.name)
        name.text = "Dhruv Agrawal"//as name was not present in the api so hardcoded here

        //to switch fragment tabs between top links and recent links
        val buttonFragment1 = findViewById<Button>(R.id.toplinks)
        buttonFragment1.setOnClickListener {
            val fragment1 = TopLinks()
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, fragment1)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        val buttonFragment2 = findViewById<Button>(R.id.recentlinks)
        buttonFragment2.setOnClickListener {
            val fragment2 = RecentsLinks()
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, fragment2)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

    }
    private fun graphPlot(){
        //to plot the graph and to set its properties
        val xAxis = lineChart.xAxis
        xAxis.setDrawGridLines(true)
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        val yAxis = lineChart.axisLeft
        yAxis.setDrawGridLines(true)
        yAxis.setDrawAxisLine(true)
        yAxis.setDrawLabels(true)
        yAxis.textSize = 12f
        yAxis.textColor = Color.BLACK
        yAxis.axisLineWidth = 2f
        yAxis.setDrawZeroLine(true)
        yAxis.zeroLineColor = Color.BLACK

        lineChart.description.isEnabled = false
        lineChart.legend.isEnabled = false
        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(true)
        lineChart.setTouchEnabled(true)
        lineChart.isDragEnabled = true
        lineChart.setScaleEnabled(false)

        val entries = ArrayList<Entry>()
        val dateParser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        for ((dateStr, value) in data) {
            val date = dateParser.parse(dateStr)
            val millis = date?.time?.toFloat() ?: 0f
            entries.add(Entry(millis, value.toFloat()))
        }
        xAxis.textColor = Color.WHITE
        val dataSet = LineDataSet(entries, "My Data Set").apply {
            color = Color.BLUE
            fillColor = Color.BLUE
            fillAlpha = 50
            setDrawFilled(true)
            setDrawValues(false)
            setDrawCircles(false)
        }

        val lineData = LineData(dataSet)
        lineChart.data = lineData
        lineChart.invalidate()
    }
}