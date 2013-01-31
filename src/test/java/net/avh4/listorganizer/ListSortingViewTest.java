package net.avh4.listorganizer;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.event.KeyEvent;

import static net.avh4.util.imagecomparison.ImageComparisonMatchers.isApproved;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class ListSortingViewTest {

    private ListSortingView subject;
    @Mock
    private ListSortingView.Model model;
    @Mock
    private ListSortingView.Actions actions;
    @Mock
    private Group animals;
    @Mock
    private Group vehicles;
    @Mock
    private Group other;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        subject = new ListSortingView(model, actions);
        stub(animals.getName()).toReturn("Animals");
        stub(vehicles.getName()).toReturn("Vehicles");
        stub(other.getName()).toReturn("Other");
        stub(model.getGroups()).toReturn(ImmutableList.of(animals, vehicles, other));
    }

    @Test
    public void basicScenario() throws Exception {
        stub(model.getUpcomingItems()).toReturn(ImmutableList.of("Horse", "Car", "Boat", "Pig", "Chicken"));
        assertThat(subject, isApproved());
    }

    @Test
    public void withOneItemLeft() throws Exception {
        stub(model.getUpcomingItems()).toReturn(ImmutableList.<String>of("Chicken"));
        assertThat(subject, isApproved());
    }

    @Test
    public void withNoMoreItems() throws Exception {
        stub(model.getUpcomingItems()).toReturn(ImmutableList.<String>of());
        stub(animals.getItems()).toReturn(ImmutableList.of("Horse", "Pig", "Chicken"));
        stub(vehicles.getItems()).toReturn(ImmutableList.of("Car", "Boat"));
        stub(other.getItems()).toReturn(ImmutableList.<String>of());
        assertThat(subject, isApproved());
    }

    @Test
    public void key_1_shouldSortTheCurrentItemToTheFirstGroup() {
        stub(model.getUpcomingItems()).toReturn(ImmutableList.of("Horse", "Car", "Boat", "Pig", "Chicken"));
        subject.key(KeyEvent.VK_1);
        verify(actions).sort("Horse", animals);
    }

    @Test
    public void key_2_shouldSortTheCurrentItemToTheSecondGroup() {
        stub(model.getUpcomingItems()).toReturn(ImmutableList.of("Car", "Boat", "Pig", "Chicken"));
        subject.key(KeyEvent.VK_2);
        verify(actions).sort("Car", vehicles);
    }

    @Test
    public void key_other_shouldDoNothing() {
        subject.key(KeyEvent.VK_A);
        subject.key(KeyEvent.VK_SLASH);
        verifyZeroInteractions(actions);
    }
}
