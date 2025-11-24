package com.napier.devops;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the App class, ensuring database interactions work as expected.
 *
 * These tests are intended to verify the behaviour of the program against the *actual* database,
 * unlike the unit tests which operate using H2 in-memory test data.
 *
 * Because these tests rely on Docker / MySQL running, they act as the bridge between the
 * application logic and the real persistent data.
 */
public class AppIntegrationTest
{
    static App app;

    /**
     * This method initializes the `App` instance and establishes a connection to the database
     * before running the integration tests. It is executed once before any test methods are run
     * to ensure that the application has a valid connection to the database.
     *
     * The port 33060 assumes the world.sql database is running in a typical Docker setup.
     * If the DB is unavailable, all tests will fail — which is intentional for integration tests.
     */
    @BeforeAll
    static void init()
    {
        app = new App();

        // Establish a connection to the real MySQL instance.
        // No mock database is used here — these tests hit the live dataset.
        app.connect("localhost:33060");
    }

    @Test
    void getCountriesTest() {
        // Retrieve countries filtered by continent.
        ArrayList<Country> countries = app.getCountries("Europe", null, null);

        assertNotNull(countries); // List should not be null
        assertFalse(countries.isEmpty()); // List should not be empty

        // The first item should represent the largest or first-sorted country
        // depending on the query implemented inside App.getCountries().
        Country firstCountry = countries.get(0);

        // Basic sanity checks to ensure core fields were mapped correctly.
        assertNotNull(firstCountry.country_code); // Check not null
        assertNotNull(firstCountry.country_name); // Check not null
        assertTrue(firstCountry.country_population > 0); // Population should be greater than 0
    }

    @Test
    void getCitiesTest() {
        // Fetch cities located in Europe.
        ArrayList<City> cities = app.getCities("Europe", null, null, null, null);

        assertNotNull(cities); // List should not be null
        assertFalse(cities.isEmpty()); // List should not be empty

        City firstCity = cities.get(0);

        // Verify key data fields were correctly populated from the SQL result.
        assertNotNull(firstCity.city_name); // Check not null
        assertNotNull(firstCity.city_country_name); // Check not null
        assertTrue(firstCity.city_population > 0); // Population should be greater than 0
    }

    @Test
    void getCapitalTest() {
        // Query Asian capital cities.
        ArrayList<CapitalCity> capitals = app.getCapital("Asia", null, null);

        assertNotNull(capitals); // List should not be null
        assertFalse(capitals.isEmpty()); // List should not be empty

        CapitalCity firstCapital = capitals.get(0);

        // Check that the mapping correctly assigns essential fields.
        assertNotNull(firstCapital.capital_city_name); // Check not null
        assertNotNull(firstCapital.capital_city_country); // Check not null
        assertTrue(firstCapital.capital_city_population > 0); // Population should be greater than 0
    }

    @Test
    void getPopulationTest() {
        // Fetch population summary for Oceania.
        ArrayList<Population> population = app.getPopulation("Oceania", null, null);

        assertNotNull(population); // List should not be null
        assertFalse(population.isEmpty()); // List should not be empty

        Population firstPopulation = population.get(0);

        // Ensures all derived fields (city vs non-city population) are present.
        assertNotNull(firstPopulation.population_name); // Check not null
        assertTrue(firstPopulation.total_population > 0); // Should be positive
        assertTrue(firstPopulation.city_population > 0); // Should be positive
        assertTrue(firstPopulation.non_city_population > 0); // Should be positive
    }

    @Test
    void getInfoTest(){
        // Info reports combine multiple tables to summarise population data.
        ArrayList<Info> info = app.getInfo("Europe", null, null, null, null);

        assertNotNull(info); // List should not be null
        assertTrue(info.size() > 0); // At least one summary should be returned

        Info one = info.get(0);

        // Validates that the info report contains the expected fields.
        assertNotNull(one.info_name); // Check not null
        assertTrue(one.info_population > 0); // Population should be greater than 0
    }

    @Test
    void testGetLanguageTest() {
        // Retrieve the top languages spoken globally.
        ArrayList<Language> languages = app.getLanguages();

        assertNotNull(languages); // List should not be null
        assertTrue(languages.size() > 0); // Should return at least one language

        // Validate general language properties for each record in the list.
        // The loop ensures that every language entry is well-formed.
        for (Language language : languages) {
            assertNotNull(language.language); // Name should not be null
            assertTrue(language.speakers > 0); // Speaker count should be positive
            assertNotNull(language.speakers_percent); // Percent string should not be null
        }
    }
}
