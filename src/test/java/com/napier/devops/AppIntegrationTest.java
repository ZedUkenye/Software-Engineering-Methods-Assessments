package com.napier.devops;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the App class, ensuring database interactions work as expected.
 */
public class AppIntegrationTest
{
    static App app;

    /**
     * This method initializes the `App` instance and establishes a connection to the database
     * before running the integration tests. It is executed once before any test methods are run
     * to ensure that the application has a valid connection to the database.
     */
    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("localhost:33060"); // Connect to the database
    }

    @Test
    void getCountriesTest() {
        ArrayList<Country> countries = app.getCountries("Europe", null, null);

        assertNotNull(countries); // List should not be null
        assertFalse(countries.isEmpty()); // List should not be empty

        Country firstCountry = countries.get(0);
        assertNotNull(firstCountry.country_code); // Check not null
        assertNotNull(firstCountry.country_name); // Check not null
        assertTrue(firstCountry.country_population > 0); // Population should be greater than 0
    }

    @Test
    void getCitiesTest() {
        ArrayList<City> cities = app.getCities("Europe", null, null, null, null);

        assertNotNull(cities); // List should not be null
        assertFalse(cities.isEmpty()); // List should not be empty

        City firstCity = cities.get(0);
        assertNotNull(firstCity.city_name); // Check not null
        assertNotNull(firstCity.city_country_name); // Check not null
        assertTrue(firstCity.city_population > 0); // Population should be greater than 0
    }

    @Test
    void getCapitalTest() {
        ArrayList<CapitalCity> capitals = app.getCapital("Asia", null, null);

        assertNotNull(capitals); // List should not be null
        assertFalse(capitals.isEmpty()); // List should not be empty

        CapitalCity firstCapital = capitals.get(0);
        assertNotNull(firstCapital.capital_city_name); // Check not null
        assertNotNull(firstCapital.capital_city_country); // Check not null
        assertTrue(firstCapital.capital_city_population > 0); // Population should be greater than 0
    }

    @Test
    void getPopulationTest() {
        ArrayList<Population> population = app.getPopulation("Oceania", null, null);

        assertNotNull(population); // List should not be null
        assertFalse(population.isEmpty()); // List should not be empty

        Population firstPopulation = population.get(0);
        assertNotNull(firstPopulation.population_name); // Check not null
        assertTrue(firstPopulation.total_population > 0); // Population should be greater than 0
        assertTrue(firstPopulation.city_population > 0); // Population should be greater than 0
        assertTrue(firstPopulation.non_city_population > 0); // Population should be greater than 0
    }

    @Test
    void getInfoTest(){
        ArrayList<Info> info = app.getInfo("Europe", null, null, null, null);

        assertNotNull(info); // List should not be null
        assertTrue(info.size() > 0); // List should have at least one item

        Info one = info.get(0);
        assertNotNull(one.info_name); // Check not null
        assertTrue(one.info_population > 0); // Population should be greater than 0
    }

    @Test
    void testGetLanguageTest() {
        ArrayList<Language> languages = app.getLanguages();

        assertNotNull(languages); // List should not be null
        assertTrue(languages.size() > 0); // List should have at least one item

        // Validate general language properties
        for (Language language : languages) {
            assertNotNull(language.language); // Check not null
            assertTrue(language.speakers > 0); // count should be greater than 0
            assertNotNull(language.speakers_percent); // Check not null
        }
    }
}
