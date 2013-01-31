package net.avh4.listorganizer.features;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.avh4.listorganizer.ListSortingModel;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

@SuppressWarnings("UnusedDeclaration")
public class Steps {

    private final Agent agent;
    private final ListSortingModel listSortingModel;
    private final CsvItemsLoader csvItemsLoader;

    public Steps(Agent agent, ListSortingModel listSortingModel, CsvItemsLoader csvItemsLoader) {
        this.agent = agent;
        this.listSortingModel = listSortingModel;
        this.csvItemsLoader = csvItemsLoader;
    }

    @Given("^a list of items$")
    public void a_list_of_items() throws Throwable {
        listSortingModel.setItems("Horse", "Car", "Boat", "Pig", "Chicken");
    }

    @Given("^a set of groups")
    public void a_set_of_groups() throws Throwable {
        listSortingModel.setGroups("Animal", "Vehicle", "Other");
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
        assertThat(listSortingModel.itemsInGroup("Animal")).containsExactly("Horse", "Pig", "Chicken");
        assertThat(listSortingModel.itemsInGroup("Vehicle")).containsExactly("Car", "Boat");
        assertThat(listSortingModel.itemsInGroup("Other")).isEmpty();
    }

    @When("^I choose a GoodReads CSV file to sort$")
    public void I_choose_a_GoodReads_CSV_file_to_sort() throws Throwable {
        List<String> items = csvItemsLoader.createItemsFromCsv("goodreads_export.csv");
        listSortingModel.setItems(items);
    }

    @Then("^I see items with the author and title$")
    public void I_see_items_with_the_author_and_title() throws Throwable {
        assertThat(listSortingModel.getUpcomingItems()).containsExactly(
                "Harry Potter and the Order of the Phoenix (Harry Potter, #5) - J.K. Rowling",
                "It Chooses You - Miranda July",
                "Le Petit Prince - Antoine de Saint-Exupéry",
                "User Stories Applied: For Agile Software Development - Mike Cohn"
        );
    }

}
