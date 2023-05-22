package de.hawhamburg.hamann.ad.sortingworkbench.util;

import de.hawhamburg.hamann.ad.sortingworkbench.SortingMetrics;

public class CompareElements {
    public static <T extends Comparable<T>> int compareElements(T e1, T e2, SortingMetrics metrics) {
        metrics.incrementCompares();
        return e1.compareTo(e2);
    }
}

