package com.getir.readingisgood.logging;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService{

    private final LogRepository logRepository;

    @Override
    public void logToDatabase(LogEntity logEntity) {
        logEntity.setTimeStamp(new Date());
        logRepository.save(logEntity);
    }
}
