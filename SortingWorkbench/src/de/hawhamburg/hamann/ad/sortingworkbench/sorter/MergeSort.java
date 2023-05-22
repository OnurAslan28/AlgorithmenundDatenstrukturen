package de.hawhamburg.hamann.ad.sortingworkbench.sorter;

import de.hawhamburg.hamann.ad.sortingworkbench.SortingMetrics;

import java.util.List;

import static de.hawhamburg.hamann.ad.sortingworkbench.util.CompareElements.compareElements;

public class MergeSort implements Sorter {

    @Override
    public String getName() {
        return "MergeSort";
    }

    @Override
    public <T extends Comparable<T>> void sort(List<T> toSort, SortingMetrics metrics) {
        mergeSort(toSort, 0, toSort.size() - 1, metrics);
    }

    private <T extends Comparable<T>> void mergeSort(List<T> toSort, int left, int right, SortingMetrics metrics) {
        if (left < right) {
            int middle = left + (right - left) / 2;

            // Sort first and second halves
            mergeSort(toSort, left, middle, metrics);
            mergeSort(toSort, middle + 1, right, metrics);

            merge(toSort, left, middle, right, metrics);
        }
    }

    private <T extends Comparable<T>> void merge(List<T> toSort, int start, int middle, int end, SortingMetrics metrics) {
        int start2 = middle + 1;

        // If the direct merge is already sorted
        // arr[mid] <= arr[start2]
        if (compareElements(toSort.get(middle), toSort.get(start2), metrics) <= 0) {
            return;
        }

        // Two pointers to maintain start
        // of both arrays to merge
        while (start <= middle && start2 <= end) {

            // If element 1 is in right place
            // arr[start] <= arr[start2]
            if (compareElements(toSort.get(start), toSort.get(start2), metrics) <= 0) {
                start++;
            }
            else {
                int index = start2;

                // Shift all the elements between element 1
                // element 2, right by 1.
                while (index != start) {
                    swap(toSort, index, index - 1, metrics);
                    index--;
                }

                // Update all the pointers
                start++;
                middle++;
                start2++;
            }
        }

    }

    @Override
    public <T> void swap(List<T> source, int e1, int e2, SortingMetrics metrics) {
        Sorter.super.swap(source, e1, e2, metrics);
    }
}
