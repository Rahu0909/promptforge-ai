package com.rahulagarwal.promptforge.ai.tools.weather.service;

import com.rahulagarwal.promptforge.ai.tools.weather.dto.response.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WeatherServiceImpl implements WeatherService {

    @Override
    public WeatherResponse getWeather(String city) {
        log.info("----------------------------------------");
        log.info("Fetching weather for {}", city);
        WeatherResponse response = new WeatherResponse(city, 31, "Sunny", 48, 12,
                String.format("The weather in %s is Sunny with a temperature of 31°C.", city));
        log.info("Weather response prepared successfully.");

        return response;
    }
}