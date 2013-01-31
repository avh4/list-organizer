package net.avh4.listorganizer.features;

import au.com.bytecode.opencsv.CSVReader;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.avh4.listorganizer.ListOrganizer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

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
        listOrganizer.setGroups("Animal", "Vehicle", "Other");
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

    @When("^I choose a GoodReads CSV file to sort$")
    public void I_choose_a_GoodReads_CSV_file_to_sort() throws Throwable {
        List<String> items = createItemsFromCsv("goodreads_export.csv");
        listOrganizer.setItems(items);
    }

    private List<String> createItemsFromCsv(String filename) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(filename);
        Reader inputStream = new InputStreamReader(is);
        CSVReader csv = new CSVReader(inputStream);
        ArrayList<String> items = new ArrayList<>();
        try {
            csv.readNext();
            while (true) {
                String[] fields = csv.readNext();
                if (fields == null) break;
                items.add(fields[1] + " - " + fields[2]);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read file: " + filename, e);
        }
        return items;
    }

    @Then("^I see items with the author and title$")
    public void I_see_items_with_the_author_and_title() throws Throwable {
        assertThat(listOrganizer.getUpcomingItems()).containsExactly(
                "Harry Potter and the Order of the Phoenix (Harry Potter, #5) - J.K. Rowling",
                "It Chooses You - Miranda July",
                "Le Petit Prince - Antoine de Saint-Exupéry",
                "User Stories Applied: For Agile Software Development - Mike Cohn"
        );
    }
}
