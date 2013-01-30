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

public class ListOrganizerViewTest {

    private ListOrganizerView subject;
    @Mock
    private ListOrganizerViewModel model;
    @Mock
    private ListOrganizerActions actions;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        subject = new ListOrganizerView(model, actions);
        stub(model.getGroups()).toReturn(ImmutableList.of("Animals", "Vehicles", "Other"));
    }

    @Test
    public void basicScenario() throws Exception {
        stub(model.getUpcomingItems()).toReturn(ImmutableList.of("Horse", "Car", "Boat", "Pig", "Chicken"));
        assertThat(subject, isApproved());
    }

    @Test
    public void key_1_shouldSortTheCurrentItemToTheFirstGroup() {
        stub(model.getUpcomingItems()).toReturn(ImmutableList.of("Horse", "Car", "Boat", "Pig", "Chicken"));
        subject.key(KeyEvent.VK_1);
        verify(actions).sort("Horse", "Animals");
    }

    @Test
    public void key_2_shouldSortTheCurrentItemToTheSecondGroup() {
        stub(model.getUpcomingItems()).toReturn(ImmutableList.of("Car", "Boat", "Pig", "Chicken"));
        subject.key(KeyEvent.VK_2);
        verify(actions).sort("Car", "Vehicles");
    }

    @Test
    public void key_other_shouldDoNothing() {
        subject.key(KeyEvent.VK_A);
        subject.key(KeyEvent.VK_SLASH);
        verifyZeroInteractions(actions);
    }
}
