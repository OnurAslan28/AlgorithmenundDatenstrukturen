package de.hawhamburg.hamann.ad.sortingworkbench;

import de.hawhamburg.hamann.ad.sortingworkbench.sorter.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortTest {
    ArrayList<Integer> expectedList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
    ArrayList<Integer> testList;
    SortingMetrics metrics;


    public enum SortingMode {
        RANDOM,
        SORTED,
        REVERSED
    }

    public void initTestSetup(SortingMode sortingMode) {
        switch (sortingMode) {
            case RANDOM -> {
                testList = new ArrayList<>(Arrays.asList(3, 5, 4, 8, 6, 1, 2, 7, 9));
                metrics = new SortingMetrics(SortingMetrics.ListType.RANDOM);
            }
            case REVERSED -> {
                testList = new ArrayList<>(Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1));
                metrics = new SortingMetrics(SortingMetrics.ListType.REVERSE_ORDERED);
            }
            default -> {
                testList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
                metrics = new SortingMetrics(SortingMetrics.ListType.ORDERED);
            }
        }
    }

    @Test
    public void SelectionSortTest() {
        SelectionSort selectionSort = new SelectionSort();

        SortingMode mode = SortingMode.RANDOM;
        initTestSetup(mode);
        selectionSort.sort(testList, metrics);
        assertEquals(expectedList, testList);

        mode = SortingMode.SORTED;
        initTestSetup(mode);
        selectionSort.sort(testList, metrics);
        assertEquals(expectedList, testList);

        mode = SortingMode.REVERSED;
        initTestSetup(mode);
        selectionSort.sort(testList, metrics);
        assertEquals(expectedList, testList);
    }

    @Test
    public void InsertionSortTest() {
        InsertionSort insertionSort = new InsertionSort();

        SortingMode mode = SortingMode.RANDOM;
        initTestSetup(mode);
        insertionSort.sort(testList, metrics);
        assertEquals(expectedList, testList);

        mode = SortingMode.SORTED;
        initTestSetup(mode);
        insertionSort.sort(testList, metrics);
        assertEquals(expectedList, testList);

        mode = SortingMode.REVERSED;
        initTestSetup(mode);
        insertionSort.sort(testList, metrics);
        assertEquals(expectedList, testList);
    }

    @Test
    public void BubbleSortTest() {
        BubbleSort bubbleSort = new BubbleSort();

        SortingMode mode = SortingMode.RANDOM;
        initTestSetup(mode);
        bubbleSort.sort(testList, metrics);
        assertEquals(expectedList, testList);

        mode = SortingMode.SORTED;
        initTestSetup(mode);
        bubbleSort.sort(testList, metrics);
        assertEquals(expectedList, testList);

        mode = SortingMode.REVERSED;
        initTestSetup(mode);
        bubbleSort.sort(testList, metrics);
        assertEquals(expectedList, testList);
    }

    @Test
    public void QuickSortLeftTest() {
        QuickSort quickSort = new QuickSort(QuickSort.UsePivot.LEFT);

        SortingMode mode = SortingMode.RANDOM;
        initTestSetup(mode);
        quickSort.sort(testList, metrics);
        assertEquals(expectedList, testList);

        mode = SortingMode.SORTED;
        initTestSetup(mode);
        quickSort.sort(testList, metrics);
        assertEquals(expectedList, testList);

        mode = SortingMode.REVERSED;
        initTestSetup(mode);
        quickSort.sort(testList, metrics);
        assertEquals(expectedList, testList);
    }

    @Test
    public void QuickSortMidTest() {
        QuickSort quickSort = new QuickSort(QuickSort.UsePivot.MID);

        SortingMode mode = SortingMode.RANDOM;
        initTestSetup(mode);
        quickSort.sort(testList, metrics);
        assertEquals(expectedList, testList);

        mode = SortingMode.SORTED;
        initTestSetup(mode);
        quickSort.sort(testList, metrics);
        assertEquals(expectedList, testList);

        mode = SortingMode.REVERSED;
        initTestSetup(mode);
        quickSort.sort(testList, metrics);
        assertEquals(expectedList, testList);
    }

    @Test
    public void QuickSortMedianTest() {
        QuickSort quickSort = new QuickSort(QuickSort.UsePivot.MEDIAN_OF_THREE);

        SortingMode mode = SortingMode.RANDOM;
        initTestSetup(mode);
        quickSort.sort(testList, metrics);
        assertEquals(expectedList, testList);

        mode = SortingMode.SORTED;
        initTestSetup(mode);
        quickSort.sort(testList, metrics);
        assertEquals(expectedList, testList);

        mode = SortingMode.REVERSED;
        initTestSetup(mode);
        quickSort.sort(testList, metrics);
        assertEquals(expectedList, testList);
    }

    @Test
    public void QuickSortRandomTest() {
        QuickSort quickSort = new QuickSort(QuickSort.UsePivot.RANDOM);

        SortingMode mode = SortingMode.RANDOM;
        initTestSetup(mode);
        quickSort.sort(testList, metrics);
        assertEquals(expectedList, testList);

        mode = SortingMode.SORTED;
        initTestSetup(mode);
        quickSort.sort(testList, metrics);
        assertEquals(expectedList, testList);

        mode = SortingMode.REVERSED;
        initTestSetup(mode);
        quickSort.sort(testList, metrics);
        assertEquals(expectedList, testList);
    }

    @Test
    public void MergeSortTest() {
        MergeSort mergeSort = new MergeSort();

        SortingMode mode = SortingMode.RANDOM;
        initTestSetup(mode);
        mergeSort.sort(testList, metrics);
        assertEquals(expectedList, testList);

        mode = SortingMode.SORTED;
        initTestSetup(mode);
        mergeSort.sort(testList, metrics);
        assertEquals(expectedList, testList);

        mode = SortingMode.REVERSED;
        initTestSetup(mode);
        mergeSort.sort(testList, metrics);
        assertEquals(expectedList, testList);
    }
}
