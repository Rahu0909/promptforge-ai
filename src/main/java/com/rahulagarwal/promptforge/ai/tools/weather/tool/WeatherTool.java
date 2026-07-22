package com.rahulagarwal.promptforge.ai.tools.weather.tool;

import com.rahulagarwal.promptforge.ai.tools.weather.dto.response.WeatherResponse;
import com.rahulagarwal.promptforge.ai.tools.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherTool {

    private final WeatherService weatherService;

    @Tool(name = "weather", description = """
            Retrieves the current weather for a specified city.
            
            Use this tool whenever the user asks about weather,
            temperature, humidity, wind speed or weather conditions.
            """)
    public WeatherResponse getWeather(@ToolParam(description = "The city name") String city) {
        log.info("========================================");
        log.info("WEATHER TOOL INVOKED");
        log.info("City : {}", city);
        log.info("========================================");
        WeatherResponse response = weatherService.getWeather(city);
        log.info("Weather Tool Response : {}", response);
        return response;
    }
}