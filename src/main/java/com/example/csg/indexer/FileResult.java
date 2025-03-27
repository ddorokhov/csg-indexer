package com.example.csg.indexer;

import java.util.Map;

public record FileResult(String filename,  Map<Rules, Integer> result,   String errorMessage) {}
