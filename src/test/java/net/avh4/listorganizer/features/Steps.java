package net.avh4.listorganizer.features;

import com.google.common.collect.ImmutableList;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.avh4.framework.data.test.ClasspathResourcesExternalStorage;
import net.avh4.listorganizer.Group;
import net.avh4.listorganizer.ListSortingModel;
import net.avh4.listorganizer.SortingFinishedListener;
import net.avh4.listorganizer.TextFileWriter;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

@SuppressWarnings("UnusedDeclaration")
public class Steps {

    private final Agent agent;
    private final ListSortingModel listSortingModel;
    private final TestExternalStorage externalStorage;
    private final CsvItemsLoader goodReadsCsvItemsLoader;
    private List<String> items;
    private ImmutableList<String> groups = ImmutableList.of("Animal", "Vehicle", "Other");

    public Steps(Agent agent, ListSortingModel listSortingModel, TestExternalStorage externalStorage) {
        this.agent = agent;
        this.listSortingModel = listSortingModel;
        this.externalStorage = externalStorage;
        this.goodReadsCsvItemsLoader = new GoodreadsCsvItemsLoader(externalStorage);
    }

    @Given("^a list of items$")
    public void a_list_of_items() throws Throwable {
        items = ImmutableList.of("Horse", "Car", "Boat", "Pig", "Chicken");
    }

    @Given("^a set of groups")
    public void a_set_of_groups() throws Throwable {
        assertThat(groups).isNotEmpty();
    }

    @When("^I sort all the items using the keyboard$")
    public void I_sort_all_the_items_using_the_keyboard() throws Throwable {
        listSortingModel.startSorting(groups, items, new SortingFinishedListener() {
            @Override
            public void onSortingFinished(ImmutableList<Group> groups) {
            }
        });
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
        items = goodReadsCsvItemsLoader.getItems();
        listSortingModel.startSorting(groups, items, null);
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

    @When("^a list is sorted$")
    public void a_list_is_sorted() throws Throwable {
        items = ImmutableList.of("Horse", "Cow", "Boat");
        groups = ImmutableList.of("Animals", "Vehicles", "Other");
        SortingFinishedListener listener = new TextFileWriter(externalStorage);
        listSortingModel.startSorting(groups, items, listener);
        agent.sortNextItem("Horse", "Animals");
        agent.sortNextItem("Cow", "Animals");
        agent.sortNextItem("Boat", "Vehicles");
    }

    @When("^the sorted groups should be written to text files$")
    public void the_sorted_groups_should_be_written_to_text_files() throws Throwable {
        assertThat(externalStorage.getString("Animals.txt")).isEqualTo("Horse\nCow\n");
        assertThat(externalStorage.getString("Vehicles.txt")).isEqualTo("Boat\n");
        assertThat(externalStorage.getString("Other.txt")).isEqualTo("");
    }

    public static class GoodreadsCsvItemsLoader extends CsvItemsLoader {
        public static final String FILE = "goodreads_export.csv";

        public GoodreadsCsvItemsLoader(ClasspathResourcesExternalStorage externalStorage) {
            super(FILE, externalStorage);
            externalStorage.allowFile(FILE);
        }
    }

    public static class TestExternalStorage extends ClasspathResourcesExternalStorage {
        public TestExternalStorage() {
            super(".");
        }
    }
}
