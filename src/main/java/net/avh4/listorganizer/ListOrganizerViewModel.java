package net.avh4.listorganizer;

import com.google.common.collect.ImmutableList;

public interface ListOrganizerViewModel {
    ImmutableList<Group> getGroups();

    ImmutableList<String> getUpcomingItems();
}
