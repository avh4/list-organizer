package net.avh4.listorganizer.features;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.avh4.listorganizer.ListOrganizer;

import static org.fest.assertions.Assertions.assertThat;

@SuppressWarnings("UnusedDeclaration")
public class Steps {

    private final Agent agent;
    private final ListOrganizer listOrganizer;

    public Steps(Agent agent, ListOrganizer listOrganizer) {
        this.agent = agent;
        this.listOrganizer = listOrganizer;
    }

    @Given("^a list of items$")
    public void a_list_of_items() throws Throwable {
        listOrganizer.setItems("Horse", "Car", "Boat", "Pig", "Chicken");
    }

    @Given("^a set of groups")
    public void a_set_of_groups() throws Throwable {
        agent.listOrganizer.setGroups("Animal", "Vehicle", "Other");
    }

    @When("^I sort all the items using the keyboard$")
    public void I_sort_all_the_items_using_the_keyboard() throws Throwable {
        agent.sortNextItem("Horse", "Animal");
        agent.sortNextItem("Car", "Vehicle");
        agent.sortNextItem("Boat", "Vehicle");
        agent.sortNextItem("Pig", "Animal");
        agent.sortNextItem("Chicken", "Animal");
    }

    @Then("^I can see the list of items for each groups$")
    public void I_can_see_the_list_of_items_for_each_groups() throws Throwable {
        assertThat(listOrganizer.itemsInGroup("Animal")).containsExactly("Horse", "Pig", "Chicken");
        assertThat(listOrganizer.itemsInGroup("Vehicle")).containsExactly("Car", "Boat");
        assertThat(listOrganizer.itemsInGroup("Other")).isEmpty();
    }
}
