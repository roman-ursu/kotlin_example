package com.techmagic.kotlinexample.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.techmagic.kotlinexample.R
import com.techmagic.kotlinexample.domain.pojo.WeatherDataDto
import org.jetbrains.anko.find
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), MainView {

    private var forecastList: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var presenter: MainPresenter = MainPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        forecastList = find(R.id.rv_forecast_list)
        progressBar = find(R.id.pb_progress)

        presenter.setView(this)

        forecastList!!.visibility = View.GONE
    }

    override fun onDestroy() {
        presenter.setView(null)
        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()

        presenter.loadData()
    }

    override fun showError(message: String?) {
        toast(message as CharSequence)
    }

    override fun showProgress() {
        forecastList!!.visibility = View.GONE
        progressBar!!.visibility = View.VISIBLE
    }

    override fun showData(weatherData: List<WeatherDataDto>?) {
        forecastList!!.visibility = View.VISIBLE
        progressBar!!.visibility = View.GONE

        forecastList!!.layoutManager = LinearLayoutManager(this)

        val items: List<String>? = weatherData?.map { (temperature, humidity, description) -> "$description $humidity $temperature" }
        if (items != null) {
            forecastList!!.adapter = ForecastListAdapter(items)
        }

        toast("data arrived")
    }

    class ForecastListAdapter(val items: List<String>) : RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            holder!!.textView.text = items[position]
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            return ViewHolder(TextView(parent!!.context))
        }

        override fun getItemCount(): Int {
            return items.size
        }


        class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
    }


}