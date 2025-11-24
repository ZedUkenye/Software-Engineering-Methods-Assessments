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
        assertNotNull(firstCountry.country_code); // Check country code is not null
        assertNotNull(firstCountry.country_name); // Check country name is not null
        assertTrue(firstCountry.country_population > 0); // Population should be positive
    }

    @Test
    void getCitiesTest() {
        ArrayList<City> cities = app.getCities("Europe", null, null, null, null);

        assertNotNull(cities); // List should not be null
        assertFalse(cities.isEmpty()); // List should not be empty

        City firstCity = cities.get(0);
        assertNotNull(firstCity.city_name); // City name should not be null
        assertNotNull(firstCity.city_country_name); // City country name should not be null
        assertTrue(firstCity.city_population > 0); // City population should be positive
    }

    @Test
    void getCapitalTest() {
        ArrayList<CapitalCity> capitals = app.getCapital("Asia", null, null);

        assertNotNull(capitals); // List should not be null
        assertFalse(capitals.isEmpty()); // List should not be empty

        CapitalCity firstCapital = capitals.get(0);
        assertNotNull(firstCapital.capital_city_name); // Capital city name should not be null
        assertNotNull(firstCapital.capital_city_country); // Capital city country should not be null
        assertTrue(firstCapital.capital_city_population > 0); // Capital city population should be positive
    }

    @Test
    void getPopulationTest() {
        ArrayList<Population> population = app.getPopulation("Oceania", null, null);

        assertNotNull(population); // List should not be null
        assertFalse(population.isEmpty()); // List should not be empty

        Population firstPopulation = population.get(0);
        assertNotNull(firstPopulation.population_name); // Population name should not be null
        assertTrue(firstPopulation.total_population > 0); // Total population should be positive
        assertTrue(firstPopulation.city_population >= 0); // City population should be non-negative
        assertTrue(firstPopulation.non_city_population >= 0); // Non-city population should be non-negative
    }

    @Test
    void getInfoTest(){
        ArrayList<Info> info = app.getInfo("Europe", null, null, null, null);

        assertNotNull(info); // List should not be null
        assertTrue(info.size() > 0); // List should have at least one item

        Info one = info.get(0);
        assertNotNull(one.info_name); // Info name should not be null
        assertTrue(one.info_population > 0); // Population should be positive
    }

    @Test
    void testGetLanguageTest() {
        ArrayList<Language> languages = app.getLanguages();

        assertNotNull(languages); // List should not be null
        assertTrue(languages.size() > 0); // List should have at least one item

        // Validate general language properties
        for (Language language : languages) {
            assertNotNull(language.language); // Language name should not be null
            assertTrue(language.speakers > 0); // Number of speakers should be positive
            assertNotNull(language.speakers_percent); // Speakers percent should not be null
        }
    }
}
