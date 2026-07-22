package com.rahulagarwal.promptforge.ai.tools.weather.service;

import com.rahulagarwal.promptforge.ai.tools.weather.dto.response.WeatherResponse;

public interface WeatherService {

    WeatherResponse getWeather(String city);

}