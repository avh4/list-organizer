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
    private Group animals;
    private Group vehicles;
    private Group other;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        subject = new ListSortingView(model, actions);
        animals = new Group("Animals");
        vehicles = new Group("Vehicles");
        other = new Group("Other");
        stub(model.getGroups()).toReturn(ImmutableList.of(animals, vehicles, other));
        stub(model.getUpcomingItems()).toReturn(ImmutableList.of("Horse", "Car", "Boat", "Pig", "Chicken"));
    }

    @Test
    public void basicScenario() throws Exception {
        assertThat(subject, isApproved());
    }

    @Test
    public void withFourGroups() throws Exception {
        Group minerals = new Group("Minerals");
        stub(model.getGroups()).toReturn(ImmutableList.of(animals, minerals, vehicles, other));
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
        animals.addItem("Horse");
        animals.addItem("Pony");
        animals.addItem("Cow");
        animals.addItem("Pig");
        animals.addItem("Chicken");
        animals.addItem("Porpoise");
        vehicles.addItem("Car");
        vehicles.addItem("Boat");
        assertThat(subject, isApproved());
    }

    @Test
    public void whileSorting_shouldShowFiveMostRecentItemsInEachGroup() throws Exception {
        animals.addItem("NOT SEEN");
        animals.addItem("Horse");
        animals.addItem("Pony");
        animals.addItem("Cow");
        animals.addItem("Pig");
        animals.addItem("Chicken");
        assertThat(subject, isApproved());
    }

    @Test
    public void key_1_shouldSortTheCurrentItemToTheFirstGroup() {
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
