package de.hawhamburg.hamann.ad.sortingworkbench.sorter;

import java.util.List;

import de.hawhamburg.hamann.ad.sortingworkbench.SortingMetrics;
import de.hawhamburg.hamann.ad.sortingworkbench.sorter.Sorter;

public class InsertionSort implements Sorter {

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public <T extends Comparable<T>> void sort(List<T> toSort, SortingMetrics metrics) {
        T temp;

        for (int i = 1; i < toSort.size(); i++) {
            int j = i;
            temp = toSort.get(i);

            while (j >= 1 && toSort.get(j - 1).compareTo(temp) > 0) {
                metrics.incrementCompares();
                metrics.incrementMoves();
                toSort.set(j, toSort.get(j - 1));

                j--;
            }
            toSort.set(j, temp);
        }
        return;
    }
}
