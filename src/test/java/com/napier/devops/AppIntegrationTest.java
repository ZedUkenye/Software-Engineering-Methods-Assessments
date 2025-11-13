package com.napier.devops;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

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
        app.connect("localhost:33060");
    }

    @Test
    void getCountriesTest() {
        ArrayList<Country> countries = app.getCountries("Europe", null, null);

        assertNotNull(countries); // check the list is not null
        assertFalse(countries.isEmpty()); // check the list is not empty

        Country firstCountry = countries.get(0);

        assertEquals("RUS", firstCountry.country_code);
        assertEquals("Russian Federation", firstCountry.country_name);
        assertEquals("Europe", firstCountry.country_continent);
        assertEquals("Eastern Europe", firstCountry.country_region);
        assertEquals(146934000, firstCountry.country_population); // if population is int, remove quotes
        assertEquals("Moscow", firstCountry.country_capital);
    }

    @Test
    void getCitiesTest() {
        ArrayList<City> cities = app.getCities("Europe", null, null, null, null);

        assertNotNull(cities);
        assertFalse(cities.isEmpty());

        City firstCity = cities.get(0);

        assertEquals("Moscow", firstCity.city_name);
        assertEquals("Russian Federation", firstCity.city_country_name);
        assertEquals("Moscow (City)", firstCity.city_district);
        assertEquals(8389200, firstCity.city_population);
    }

    @Test
    void getCapitalTest() {
        ArrayList<CapitalCity> capitals = app.getCapital("Asia", null, null);

        assertNotNull(capitals);
        assertFalse(capitals.isEmpty());

        CapitalCity firstCapital = capitals.get(0);

        assertEquals("Seoul", firstCapital.capital_city_name);
        assertEquals("South Korea", firstCapital.capital_city_country);
        assertEquals(9981619, firstCapital.capital_city_population);
    }

    @Test
    void getPopulationTest() {
        ArrayList<Population> population = app.getPopulation("Oceania", null, null);

        assertNotNull(population);
        assertFalse(population.isEmpty());

        Population firstPopulation = population.get(0);
        assertEquals("Oceania", firstPopulation.population_name.trim());
        assertEquals(30401150, firstPopulation.total_population);
        assertEquals(13886149, firstPopulation.city_population);
        assertEquals(16515001, firstPopulation.non_city_population);
        assertEquals("45.68%", firstPopulation.city_population_percent);
        assertEquals("54.32%", firstPopulation.non_city_population_percent);
    }

    @Test
    void getInfoTest(){
        ArrayList<Info> info = app.getInfo("Europe", null, null, null, null);

        assertNotNull(info);
        assertEquals(1, info.size());

        Info one = info.get(0);
        assertEquals("Europe", one.info_name.trim());
        assertEquals(730074600, one.info_population);
    }


    @Test
    void testGetLanguageTest() {
        ArrayList<Language> language = app.getLanguages();

        assertNotNull(language);
        assertEquals(5, language.size());

        Language one = language.get(0);
        assertEquals("English", one.language.trim());
        assertEquals(347077867, one.speakers);
        assertEquals("5.71%", one.speakers_percent);

        Language two = language.get(1);
        assertEquals("Spanish", two.language.trim());
        assertEquals(355029462, two.speakers);
        assertEquals("5.84%", two.speakers_percent);

        Language three = language.get(2);
        assertEquals("Arabic", three.language.trim());
        assertEquals(233839239, three.speakers);
        assertEquals("3.85%", three.speakers_percent);

        Language four = language.get(3);
        assertEquals("Hindi", four.language.trim());
        assertEquals(405633070, four.speakers);
        assertEquals("6.67%", four.speakers_percent);

        Language five = language.get(4);
        assertEquals("Chinese", five.language.trim());
        assertEquals(1191843539, five.speakers);
        assertEquals("19.61%", five.speakers_percent);
    }

}