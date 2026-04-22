package com.sample.dataaggregator.service;

import com.sample.dataaggregator.model.CarData;
import com.sample.dataaggregator.model.ProcessingReport;
import com.sample.dataaggregator.parser.DataParser;
import com.sample.dataaggregator.parser.DataParserFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DataProcessorService {

    public ProcessingReport processDirectory(String directoryPath) {
        File folder = new File(directoryPath);
        if (!folder.exists() || !folder.isDirectory()) {
            throw new IllegalArgumentException("invalid directory path: " + directoryPath);
        }

        File[] files = folder.listFiles();
        if (files == null || files.length == 0) return new ProcessingReport(0, 0, 0, 0);

        int coreCount = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(coreCount);
        
        List<Callable<List<CarData>>> tasks = new ArrayList<>();

        for (File file : files) {
            if (file.isDirectory()) continue;

            tasks.add(() -> {
                DataParser<CarData> parser = DataParserFactory.getParser(file.getName());
                return parser.parse(file);
            });
        }

        List<CarData> parsedRecords = new ArrayList<>();
        int successfulFiles = 0;
        int failedFiles = 0;

        try {
            List<Future<List<CarData>>> results = executor.invokeAll(tasks);

            for (Future<List<CarData>> futureResult : results) {
                try {
                    parsedRecords.addAll(futureResult.get());
                    successfulFiles++;
                } catch (Exception e) {
                    System.err.println("Execution failed.");
                    failedFiles++;
                }
            }
        } catch (InterruptedException e) {
            System.err.println("Process interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            executor.shutdown();
        }

        double totalValue = parsedRecords.stream()
                .mapToDouble(CarData::price)
                .sum();

        return new ProcessingReport(successfulFiles, parsedRecords.size(), failedFiles, totalValue);
    }
}