package com.example.csg;

import com.example.csg.indexer.FileResult;
import com.example.csg.indexer.Processor;
import com.example.csg.indexer.Rules;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class IndexerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(IndexerApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (args.length ==0 ){
			System.err.println("No files to process provided");
			System.exit(1);
		}

		List<Rules> rules = List.of(Rules.WORD_LONGER_FIVE,Rules.RULE_UPPER_CASE_WORD);

		Processor processor = new Processor();
		var results = processor.process(args, rules);

		for (var filename: results.keySet()){
			FileResult fileResult = results.get(filename);
			System.out.println("File: " + filename);
			if(fileResult.errorMessage() != null){
				System.out.println("Error: " + fileResult.errorMessage());
			} else {
				fileResult.result().forEach((rule, count)->System.out.println(rule.label +"  : " +count));
			}
		}

	}
}
