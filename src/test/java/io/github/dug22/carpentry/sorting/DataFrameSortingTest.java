package io.github.dug22.carpentry.sorting;

import io.github.dug22.carpentry.DataFrame;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataFrameSortingTest {

    private final DataFrame dataFrame = DataFrame.read().csv("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/fake_employee_data.csv");
    ;

    @Test
    public void sortTest() {
        DataFrame naturalResult = dataFrame.sort(new SortColumn[]{
                new SortColumn("Salary", SortColumn.Direction.ASCENDING)
        });

        assertTrue(isSorted(Arrays.stream(naturalResult.doubleColumn("Salary").getValues()).toList(), Comparator.naturalOrder()));

        DataFrame reverseResult = dataFrame.sort(new SortColumn[]{
                new SortColumn("Salary", SortColumn.Direction.DESCENDING)
        });

        assertTrue(isSorted(Arrays.stream(reverseResult.doubleColumn("Salary").getValues()).toList(), Comparator.reverseOrder()));

        List<Boolean> expectedMultiSortResult = List.of(true, false);

        DataFrame multiSortNaturalResult = dataFrame.sort(new SortColumn[]{
                new SortColumn("Age", SortColumn.Direction.ASCENDING),
                new SortColumn("Salary", SortColumn.Direction.ASCENDING)
        });


        List<Iterable<?>> iterables = List.of(Arrays.stream(multiSortNaturalResult.intColumn("Age").getValues()).toList(), Arrays.stream(multiSortNaturalResult.doubleColumn("Salary").getValues()).toList());
        List<Comparator<?>> comparators = List.of(Comparator.naturalOrder(), Comparator.naturalOrder());
        List<Boolean> multiNaturalSortResult = isSorted(iterables, comparators);
        assertEquals(expectedMultiSortResult, multiNaturalSortResult);
        multiSortNaturalResult.head(10);

        DataFrame multiSortReverseResult = dataFrame.sort(new SortColumn[]{
                new SortColumn("Age", SortColumn.Direction.DESCENDING),
                new SortColumn("Salary", SortColumn.Direction.DESCENDING)
        });
        iterables = List.of(Arrays.stream(multiSortReverseResult.intColumn("Age").getValues()).toList(), Arrays.stream(multiSortNaturalResult.doubleColumn("Salary").getValues()).toList());
        comparators = List.of(Comparator.reverseOrder(), Comparator.reverseOrder());
        List<Boolean> multiReverseSortResult = isSorted(iterables, comparators);
        assertEquals(expectedMultiSortResult, multiReverseSortResult);
        multiSortReverseResult.head(10);
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    private boolean isSorted(Iterable<?> iterable, Comparator comparator) {
        boolean first = true;
        Object prev = null;

        for (Object curr : iterable) {
            if (first) {
                first = false;
            } else if (comparator.compare(prev, curr) > 0) {
                return false;
            }
            prev = curr;
        }

        return true;
    }

    @SuppressWarnings({"rawtypes"})
    private List<Boolean> isSorted(List<Iterable<?>> iterables, List<Comparator<?>> comparators) {
        List<Boolean> isSortedList = new ArrayList<>();

        for (int i = 0; i < iterables.size(); i++) {
            Iterable<?> iterable = iterables.get(i);
            Comparator comparator = comparators.get(i);

            isSortedList.add(isSorted(iterable, comparator));
        }

        return isSortedList;
    }
}
