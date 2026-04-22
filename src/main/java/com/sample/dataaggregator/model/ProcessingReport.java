package com.sample.dataaggregator.model;

// Summary report generated after processing a batch of data files.

public record ProcessingReport(
        int totalFilesProcessed,
        int totalRecordsParsed,
        int failedFiles,
        double totalValue
) {
}