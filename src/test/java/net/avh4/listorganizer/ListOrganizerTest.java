package net.avh4.listorganizer;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ListOrganizerTest {

    private ListOrganizer subject;

    @Before
    public void setUp() {
        subject = new ListOrganizer();
    }

    @Test
    public void setItems_shouldSetUpcomingItems() {
        subject.setItems("Horse", "Carriage");
        assertThat(subject.getUpcomingItems()).containsExactly("Horse", "Carriage");
    }

    @Test
    public void setGroups_shouldSetGroups() {
        subject.setGroups("Camels", "Birds", "Monkeys");
        assertThat(Iterables.transform(subject.getGroups(), new Function<Group, String>() {
            @Override
            public String apply(Group group) {
                return group.getName();
            }
        })).contains("Camels", "Birds", "Monkeys");
    }

    @Test
    public void sort_shouldAdvanceToNextItem() {
        subject.setGroups("Animals");
        subject.setItems("Horse", "Monkey", "Dog");
        subject.sort("Horse", subject.getGroups().get(0));
        assertThat(subject.getUpcomingItems()).containsExactly("Monkey", "Dog");
    }
}
