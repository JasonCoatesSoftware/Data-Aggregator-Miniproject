package com.sample.dataaggregator;
import com.sample.dataaggregator.model.ProcessingReport;
import com.sample.dataaggregator.service.DataProcessorService;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Data Aggregator");

        DataProcessorService service = new DataProcessorService();
        
        try {
            ProcessingReport report = service.processDirectory("data");
            System.out.println("\nReport:");
            System.out.println("Files Processed Successfully: " + report.totalFilesProcessed());
            System.out.println("Files Failed/Skipped: " + report.failedFiles());
            System.out.println("Total Records Parsed: " + report.totalRecordsParsed());
            System.out.println("Total Value: $" + Math.round(report.totalValue() *100.0) / 100.0);

        } catch (Exception e) {
            System.err.println("Whoops: " + e.getMessage());
        }
    }
}