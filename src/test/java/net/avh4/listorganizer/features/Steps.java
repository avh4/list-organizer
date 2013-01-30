package net.avh4.listorganizer.features;

import com.google.common.collect.ImmutableList;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

@SuppressWarnings("UnusedDeclaration")
public class Steps {

    private ImmutableList<String> items;
    private Map<String, ArrayList<String>> itemsForGroup = new HashMap<>();

    @Given("^a list of items$")
    public void a_list_of_items() throws Throwable {
        items = ImmutableList.of("Horse", "Car", "Boat", "Pig", "Chicken");
    }

    @Given("^a set of groups")
    public void a_set_of_groups() throws Throwable {
        setGroups("Animal", "Vehicle", "Other");
    }

    @When("^I sort all the items using the keyboard$")
    public void I_sort_all_the_items_using_the_keyboard() throws Throwable {
        sort("Horse", "Animal");
        sort("Car", "Vehicle");
        sort("Boat", "Vehicle");
        sort("Pig", "Animal");
        sort("Chicken", "Animal");
    }

    @Then("^I can see the list of items for each groups$")
    public void I_can_see_the_list_of_items_for_each_groups() throws Throwable {
        assertThat(itemsInGroup("Animal")).containsExactly("Horse", "Pig", "Chicken");
        assertThat(itemsInGroup("Vehicle")).containsExactly("Car", "Boat");
        assertThat(itemsInGroup("Other")).isEmpty();
    }

    private void setGroups(String... groups) {
        for (String group : groups) {
            itemsForGroup.put(group, new ArrayList<String>());
        }
    }

    private void sort(String item, String group) {
        itemsForGroup.get(group).add(item);
    }

    private List<String> itemsInGroup(String group) {
        return itemsForGroup.get(group);
    }
}
