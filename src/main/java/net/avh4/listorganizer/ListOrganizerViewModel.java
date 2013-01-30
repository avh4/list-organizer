package net.avh4.listorganizer;

import com.google.common.collect.ImmutableList;

public interface ListOrganizerViewModel {
    ImmutableList<String> getGroups();

    ImmutableList<String> getUpcomingItems();
}
