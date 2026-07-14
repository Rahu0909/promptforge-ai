package com.rahulagarwal.promptforge.export.factory;

import com.rahulagarwal.promptforge.export.enums.ExportType;
import com.rahulagarwal.promptforge.export.strategy.ExportStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ExportStrategyFactory {

    private final List<ExportStrategy> strategies;

    public ExportStrategy getStrategy(ExportType exportType) {

        return strategies.stream()
                .filter(strategy -> strategy.getType() == exportType)
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Unsupported export type : " + exportType
                        ));
    }
}