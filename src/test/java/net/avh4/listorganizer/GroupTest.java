package net.avh4.listorganizer;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class GroupTest {

    private Group subject;

    @Before
    public void setUp() {
        subject = new Group("Mineral");
    }

    @Test
    public void shouldHaveName() {
        assertThat(subject.getName()).isEqualTo("Mineral");
    }

    @Test
    public void addItem_shouldAddItems() {
        subject.addItem("Gold");
        assertThat(subject.getItems()).containsExactly("Gold");
    }
}
