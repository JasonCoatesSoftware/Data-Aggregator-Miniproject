package com.sample.dataaggregator.parser;

import java.io.File;
import java.util.List;

public interface DataParser<T> {
    List<T> parse(File file) throws Exception;
}
