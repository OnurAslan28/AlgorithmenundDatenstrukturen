package de.hawhamburg.hamann.ad.sortingworkbench.sorter;

import de.hawhamburg.hamann.ad.sortingworkbench.SortingMetrics;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static de.hawhamburg.hamann.ad.sortingworkbench.util.CompareElements.compareElements;

public class QuickSort implements Sorter {
    private final UsePivot pivotMode;

    public enum UsePivot {
        LEFT,
        MID,
        MEDIAN_OF_THREE,
        RANDOM
    }

    public QuickSort(UsePivot pivotMode) {
        this.pivotMode = pivotMode;
    }

    @Override
    public String getName() {
        return "QuickSort " + pivotMode;
    }

    @Override
    public <T extends Comparable<T>> void sort(List<T> toSort, SortingMetrics metrics) {
        quicksort(toSort, 0, toSort.size() - 1, metrics);
    }

    private <T extends Comparable<T>> void quicksort(List<T> toSort, int li, int re, SortingMetrics metrics) {
        assert li >= 0;

        if (li < re) {
            int pivot = partition(toSort, li, re, metrics);
            quicksort(toSort, li, pivot - 1, metrics);
            quicksort(toSort, pivot + 1, re, metrics);
        }
    }

    private <T extends Comparable<T>> int partition(List<T> toSort, int li, int re, SortingMetrics metrics) {
        int pivot = getPivotIndex(li, re);
        int last = re;

        swap(toSort, pivot, re, metrics);
        re--;

        while (li <= re) {
            while(compareElements(toSort.get(li), toSort.get(last), metrics) < 0) {
                li++;
            }
            while (compareElements(toSort.get(last), toSort.get(re), metrics) < 0) {
                if (re <= 0) break;
                re--;
            }

            if (re >= li) {
                swap(toSort, li, re, metrics);
                re--;
            }
        }

        swap(toSort, li, last, metrics);

        return li;
    }

    private int getPivotIndex(int li, int re) {
        switch (pivotMode) {
            case LEFT -> {
                return li;
            }
            case MID -> {
                return li + ((re - li + 1) / 2);
            }
            case MEDIAN_OF_THREE -> {
                return Math.max(Math.min(li, re), Math.min(Math.max(li, re), (li + re) / 2));
            }
            case RANDOM -> {
                return ThreadLocalRandom.current().nextInt(li, re);
            }
            default -> {
                return -1;
            }
        }
    }

    @Override
    public <T> void swap(List<T> source, int e1, int e2, SortingMetrics metrics) {
        Sorter.super.swap(source, e1, e2, metrics);
    }
}
