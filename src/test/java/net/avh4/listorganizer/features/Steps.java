package net.avh4.listorganizer.features;

import com.google.common.collect.ImmutableList;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.avh4.listorganizer.ListOrganizer;

import static org.fest.assertions.Assertions.assertThat;

@SuppressWarnings("UnusedDeclaration")
public class Steps {
    private final ListOrganizer listOrganizer = new ListOrganizer();

    @Given("^a list of items$")
    public void a_list_of_items() throws Throwable {
        ImmutableList<String> items = ImmutableList.of("Horse", "Car", "Boat", "Pig", "Chicken");
    }

    @Given("^a set of groups")
    public void a_set_of_groups() throws Throwable {
        listOrganizer.setGroups("Animal", "Vehicle", "Other");
    }

    @When("^I sort all the items using the keyboard$")
    public void I_sort_all_the_items_using_the_keyboard() throws Throwable {
        listOrganizer.sort("Horse", "Animal");
        listOrganizer.sort("Car", "Vehicle");
        listOrganizer.sort("Boat", "Vehicle");
        listOrganizer.sort("Pig", "Animal");
        listOrganizer.sort("Chicken", "Animal");
    }

    @Then("^I can see the list of items for each groups$")
    public void I_can_see_the_list_of_items_for_each_groups() throws Throwable {
        assertThat(listOrganizer.itemsInGroup("Animal")).containsExactly("Horse", "Pig", "Chicken");
        assertThat(listOrganizer.itemsInGroup("Vehicle")).containsExactly("Car", "Boat");
        assertThat(listOrganizer.itemsInGroup("Other")).isEmpty();
    }
}
