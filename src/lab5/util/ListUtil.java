package lab5.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {
    public static <T> List<List<T>> splitList(List<T> originalList, int numberOfSublists) {
        List<List<T>> sublists = new ArrayList<>();
        final int itemsPerList = originalList.size() / numberOfSublists;
        int additionalItems = originalList.size() % numberOfSublists;

        int i = 0;
        for (int j = 0; j < numberOfSublists; j++) {
            int listSize = (additionalItems > 0) ? itemsPerList + 1 : itemsPerList;
            sublists.add(originalList.subList(i, i + listSize));

            i += listSize;
            additionalItems = Math.max(additionalItems - 1, 0);
        }
        return sublists;
    }
}
