package net.avh4.listorganizer;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static net.avh4.util.imagecomparison.ImageComparisonMatchers.isApproved;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.stub;

public class ListOrganizerViewTest {

    private ListOrganizerView subject;
    @Mock
    private ListOrganizerViewModel model;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        subject = new ListOrganizerView(model);
    }

    @Test
    public void basicScenario() throws Exception {
        stub(model.getGroups()).toReturn(ImmutableList.of("Animals", "Vehicles", "Other"));
        stub(model.getUpcomingItems()).toReturn(ImmutableList.of("Horse", "Car", "Boat", "Pig", "Chicken"));
        assertThat(subject, isApproved());
    }
}
