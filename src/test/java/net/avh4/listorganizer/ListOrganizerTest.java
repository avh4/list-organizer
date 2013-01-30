package net.avh4.listorganizer;

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
        assertThat(subject.getGroups()).containsExactly("Camels", "Birds", "Monkeys");
    }

    @Test
    public void sort_shouldAdvanceToNextItem() {
        subject.setGroups("Animals");
        subject.setItems("Horse", "Monkey", "Dog");
        subject.sort("Horse", "Animals");
        assertThat(subject.getUpcomingItems()).containsExactly("Monkey", "Dog");
    }
}
