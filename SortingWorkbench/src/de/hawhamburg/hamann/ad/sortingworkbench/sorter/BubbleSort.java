package de.hawhamburg.hamann.ad.sortingworkbench.sorter;

import de.hawhamburg.hamann.ad.sortingworkbench.SortingMetrics;

import java.util.List;

import static de.hawhamburg.hamann.ad.sortingworkbench.util.CompareElements.compareElements;

public class BubbleSort implements Sorter {

    @Override
    public String getName() {
        return "BubbleSort";
    }

    @Override
    public <T extends Comparable<T>> void sort(List<T> toSort, SortingMetrics metrics) {
        int size = toSort.size() - 1;

        for (int a = 1; a <= size; a++) {
            for (int b = 0; b <= (size - a); b++) {
                if (compareElements(toSort.get(b), toSort.get(b + 1), metrics) > 0) {
                    swap(toSort, b, b + 1, metrics);
                }
            }
        }
    }

    @Override
    public <T> void swap(List<T> source, int e1, int e2, SortingMetrics metrics) {
        Sorter.super.swap(source, e1, e2, metrics);
    }
}
