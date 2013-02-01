package net.avh4.listorganizer;

import com.google.common.collect.ImmutableList;

public interface SortingFinishedListener {
    void onSortingFinished(ImmutableList<Group> groups);
}
