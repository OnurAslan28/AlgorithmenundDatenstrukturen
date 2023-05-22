package de.hawhamburg.hamann.ad.sortingworkbench.sorter;

import de.hawhamburg.hamann.ad.sortingworkbench.SortingMetrics;

import java.util.List;

import static de.hawhamburg.hamann.ad.sortingworkbench.util.CompareElements.compareElements;

public class SelectionSort implements Sorter {

    @Override
    public String getName() {
        return "SelectionSort";
    }

    @Override
    public <T extends Comparable<T>> void sort(List<T> toSort, SortingMetrics metrics) {
        int size = toSort.size();
        int min;

        for (int i = 0; i <= size - 2; i++) {
            min = i;
            for (int j = i + 1; j <= size - 1; j++) {
                if (compareElements(toSort.get(min), toSort.get(j), metrics) > 0) {
                    min = j;
                }
            }
            if (min != i) {
                swap(toSort, i, min, metrics);
            }
        }
    }
}
