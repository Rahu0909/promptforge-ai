package com.rahulagarwal.promptforge.ai.tools.weather.dto.response;

public record WeatherResponse(

        String city,

        Integer temperature,

        String condition,

        Integer humidity,

        Integer windSpeed,

        String summary

) {
}