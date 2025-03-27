package com.example.csg.indexer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;


public class Processor {
    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static final Logger logger = Logger.getLogger(Processor.class.getName());

    public Map<String, FileResult> process(String[] inputs, List<Rules> rules){
        Map<String, FileResult> results = new LinkedHashMap<>();

        List<CompletableFuture<FileResult>> futures = new ArrayList<>();
        for (String filename : inputs) {
            CompletableFuture<FileResult> future = CompletableFuture.supplyAsync(() -> processFile(filename, rules), executorService);
            futures.add(future);
        }

        for (CompletableFuture<FileResult> future : futures) {
            FileResult result = future.join(); // дождались результат
            results.put(result.filename(), result);
        }

        executorService.shutdown();

        return results;
    }

    private FileResult processFile(String filename, List<Rules> rules){
        File file = new File(filename);

        Map<Rules, Integer> result = new HashMap<>();

        try(Scanner scanner = new Scanner(file)){
            logger.info("Processing file: " + file.getAbsolutePath());
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] words = line.split("\\W+");

                for (String word : words) {
                    if (word.isBlank()) continue;

                    for (var rule: rules){
                        int current = result.getOrDefault(rule, 0);
                        if (rule.predicate.test(word)) {
                            result.put(rule, current + 1);
                        }
                    }

                }
            }

        } catch (Exception e) {
            logger.severe("Error. Cannot read file: " +filename);
            return new FileResult(filename, new HashMap<>(), "Error while reading file, "+e.getMessage());

        }

        return new FileResult(filename, result, null);
    }
}
