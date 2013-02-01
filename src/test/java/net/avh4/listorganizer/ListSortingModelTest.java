package net.avh4.listorganizer;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class ListSortingModelTest {

    private ListSortingModel subject;
    @Mock
    private SortingFinishedListener listener;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        subject = new ListSortingModel();
        List<String> groups = ImmutableList.of("Beasts", "Birds", "Vehicles");
        List<String> items = ImmutableList.of("Horse", "Carriage");
        subject.startSorting(groups, items, listener);
    }

    @Test
    public void setItems_shouldSetUpcomingItems() {
        assertThat(subject.getUpcomingItems()).containsExactly("Horse", "Carriage");
    }

    @Test
    public void getGroups() {
        assertThat(Iterables.transform(subject.getGroups(), new Function<Group, String>() {
            @Override
            public String apply(Group group) {
                return group.getName();
            }
        })).contains("Beasts", "Birds", "Vehicles");
    }

    @Test
    public void sort_shouldAdvanceToNextItem() {
        subject.sort("Horse", subject.getGroups().get(0));
        assertThat(subject.getUpcomingItems()).containsExactly("Carriage");
    }

    @Test
    public void sort_onTheLastItem_shouldCallTheSortingFinishedListener() {
        subject.sort("Horse", subject.getGroups().get(0));
        subject.sort("Carriage", subject.getGroups().get(2));
        verify(listener).onSortingFinished(subject.getGroups());
    }
}
