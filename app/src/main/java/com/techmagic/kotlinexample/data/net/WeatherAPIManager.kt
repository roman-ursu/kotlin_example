package com.techmagic.kotlinexample.data.net

import com.techmagic.kotlinexample.data.DataMapper
import com.techmagic.kotlinexample.data.Mapper
import com.techmagic.kotlinexample.data.manager.Manager
import com.techmagic.kotlinexample.domain.Units
import com.techmagic.kotlinexample.domain.pojo.WeatherDataDto
import rx.Observable

/**
 * Created by Roman Ursu on 5/22/17
 */

class WeatherAPIManager(private val weatherAPI: WeatherAPI, private val dataMapper: DataMapper) : Manager {

    fun loadWeather(units: Units, count: Int): Observable<List<WeatherDataDto>> {
        val unitsString: String = Mapper.map(units)

        return weatherAPI.loadWeather(count, unitsString).map { forecastListEntity -> dataMapper.map(forecastListEntity) }
    }
}
