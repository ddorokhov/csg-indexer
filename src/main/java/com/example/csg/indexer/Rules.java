package com.example.csg.indexer;

import java.util.function.Predicate;

public enum Rules {

    RULE_UPPER_CASE_WORD("UpperCaseWord",w->Character.isUpperCase(w.charAt(0))),
    WORD_LONGER_FIVE("WordsLongerThanFive",w->w.length()>5);

    public final String label;

    public final Predicate<String> predicate;

    Rules(String label, Predicate<String> predicate) {
        this.label = label;
        this.predicate = predicate;
    }
}
