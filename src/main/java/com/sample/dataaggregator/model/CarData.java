package com.sample.dataaggregator.model;
import java.time.LocalDateTime;

public record CarData(
    int id,
    LocalDateTime saleTime, 
    String make,
    String model,
    int mileage,
    String bodyStyle,
    double price
) // should probably add model year
{}