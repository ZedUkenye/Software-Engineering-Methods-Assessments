package com.napier.devops;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    static Connection conn;
    static App app;

    @BeforeAll
    static void setup() throws Exception {
        // Create in-memory H2 database
        conn = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        Statement stmt = conn.createStatement();

        // Create tables
        stmt.execute(
                "CREATE TABLE city (" +
                        "  ID int NOT NULL AUTO_INCREMENT," +
                        "  Name char(35) NOT NULL DEFAULT ''," +
                        "  CountryCode char(3) NOT NULL DEFAULT ''," +
                        "  District char(20) NOT NULL DEFAULT ''," +
                        "  Population int NOT NULL DEFAULT 0," +
                        "  PRIMARY KEY (ID)" +
                        ");"
        );

        // Insert into city
        stmt.execute("INSERT INTO city VALUES (1, 'City 1', 'ONE', 'District 1', 10);");
        stmt.execute("INSERT INTO city VALUES (2, 'City 2', 'TWO', 'District 2', 20);");

        // Create country table
        stmt.execute(
                "CREATE TABLE country (" +
                        "  Code char(3) NOT NULL DEFAULT ''," +
                        "  Name char(52) NOT NULL DEFAULT ''," +
                        "  Continent enum('Asia','Europe','North America','Africa','Oceania','Antarctica','South America') NOT NULL DEFAULT 'Asia'," +
                        "  Region char(26) NOT NULL DEFAULT ''," +
                        "  Population int NOT NULL DEFAULT 0," +
                        "  Capital int DEFAULT NULL," +
                        "  PRIMARY KEY (Code)" +
                        ");"
        );

        // Insert into country (using valid enum values for Continent)
        stmt.execute("INSERT INTO country VALUES ('ONE', 'Country 1', 'Europe', 'Region', 1000, 1);");
        stmt.execute("INSERT INTO country VALUES ('TWO', 'Country 2', 'Europe', 'Region', 100, 2);");

        stmt.execute(
                "CREATE TABLE countrylanguage (" +
                        "  CountryCode CHAR(3) NOT NULL DEFAULT ''," +
                        "  Language CHAR(30) NOT NULL DEFAULT ''," +
                        "  Percentage DECIMAL(4,1) NOT NULL DEFAULT 0.0," +
                        "  PRIMARY KEY (`CountryCode`,`Language`)" +
                        ");"
        );

        stmt.execute("INSERT INTO countrylanguage (CountryCode, Language, Percentage) VALUES ('ONE', 'English', 50.0);");
        stmt.execute("INSERT INTO countrylanguage (CountryCode, Language, Percentage) VALUES ('TWO', 'Chinese', 50.0);");

        // Inject H2 connection into App's private static 'con' field
        app = new App();
        java.lang.reflect.Field conField = App.class.getDeclaredField("con");
        conField.setAccessible(true);
        conField.set(null, conn);
    }


    //GetCountrys Method Testing

    @Test
    void testGetCountriesReturnsAll() {
        ArrayList<Country> countries = app.getCountries(null, null, null);

        assertNotNull(countries);
        assertEquals(2, countries.size());

        Country one = countries.get(0);
        assertEquals("ONE", one.country_code);
        assertEquals("Country 1", one.country_name.trim());
        assertEquals(1000, one.country_population);
        assertEquals("City 1", one.country_capital.trim());

        Country two = countries.get(1);
        assertEquals("TWO", two.country_code);
        assertEquals("Country 2", two.country_name.trim());
        assertEquals(100, two.country_population);
        assertEquals("City 2", two.country_capital.trim());
    }

    @Test
    void testGetCountriesWithContinentFilter() {
        ArrayList<Country> countries = app.getCountries("Europe", null, null);
        assertEquals(2, countries.size());
    }

    @Test
    void testGetCountriesWithRegionFilter() {
        ArrayList<Country> countries = app.getCountries(null, "Region", null);
        assertEquals(2, countries.size());
    }

    @Test
    void testGetCountriesWithLimitFilter() {
        ArrayList<Country> countries = app.getCountries(null, null, "1");
        assertEquals(1, countries.size());
    }

    @Test
    void testGetCountriesWithNegativeLimitFilter() {
        ArrayList<Country> countries = app.getCountries(null, null, "-1");
        assertNull(countries);
    }

    @Test
    void testGetCountriesNoMatch() {
        ArrayList<Country> countries = app.getCountries(null, "Unknown", null);
        assertEquals(0, countries.size());
    }

    @Test
    void testGetCountriesWithEmptyStringFilter() {
        ArrayList<Country> countries = app.getCountries("", "", "");
        assertEquals(2, countries.size());
    }

    @Test
    void testGetCountriesWithMultipleFilters() {
        ArrayList<Country> countries = app.getCountries("Continent", "Region", "");
        assertNull(countries);
    }


    //GetCities Method Testing

    @Test
    void testGetCitiesReturnsAll() {
        ArrayList<City> cities = app.getCities(null, null, null, null, null);

        assertNotNull(cities);
        assertEquals(2, cities.size());

        City one = cities.get(0);
        assertEquals("City 2", one.city_name.trim());
        assertEquals("Country 2", one.city_country_name.trim());
        assertEquals("District 2", one.city_district.trim());
        assertEquals(20, one.city_population);

        City two = cities.get(1);
        assertEquals("City 1", two.city_name.trim());
        assertEquals("Country 1", two.city_country_name.trim());
        assertEquals("District 1", two.city_district.trim());
        assertEquals(10, two.city_population);
    }

    @Test
    void testGetCitiesWithContinentFilter() {
        ArrayList<City> cities = app.getCities("Europe", null, null, null, null);
        assertEquals(2, cities.size());
    }

    @Test
    void testGetCitiesWithRegionFilter() {
        ArrayList<City> cities = app.getCities(null, "Region", null, null, null);
        assertEquals(2, cities.size());
    }

    @Test
    void testGetCitiesWithCountryFilter() {
        ArrayList<City> cities = app.getCities(null, null, "Country 1", null, null);
        assertEquals(1, cities.size());
        assertEquals("City 1", cities.get(0).city_name.trim());
    }

    @Test
    void testGetCitiesWithDistrictFilter() {
        ArrayList<City> cities = app.getCities(null, null, null, "District 1", null);
        assertEquals(1, cities.size());
        assertEquals("City 1", cities.get(0).city_name.trim());
    }

    @Test
    void testGetCitiesWithLimitFilter() {
        ArrayList<City> cities = app.getCities(null, null, null, null, "1");
        assertEquals(1, cities.size());
    }

    @Test
    void testGetCitiesWithNegativeLimitFilter() {
        ArrayList<City> cities = app.getCities(null, null, null, null, "-1");
        assertNull(cities);
    }

    @Test
    void testGetCitiesNoMatch() {
        ArrayList<City> cities = app.getCities(null, null, "Unknown", null, null);
        assertEquals(0, cities.size());
    }

    @Test
    void testGetCitiesWithEmptyStringFilter() {
        ArrayList<City> cities = app.getCities("", "", "", "", "");
        assertEquals(2, cities.size());
    }

    @Test
    void testGetCitiesWithMultipleFilters() {
        ArrayList<City> cities = app.getCities("Europe", "Region", "Country 1", "District 1", null);
        assertNull(cities);
    }


    //GetCapital Method Testing

    @Test
    void testGetCapitalReturnsAll() {
        ArrayList<CapitalCity> capital = app.getCapital(null, null, null);

        assertNotNull(capital);
        assertEquals(2, capital.size());

        CapitalCity one = capital.get(0);
        assertEquals("City 2", one.capital_city_name.trim());
        assertEquals("Country 2", one.capital_city_country.trim());
        assertEquals(20, one.capital_city_population);

        CapitalCity two = capital.get(1);
        assertEquals("City 1", two.capital_city_name.trim());
        assertEquals("Country 1", two.capital_city_country.trim());
        assertEquals(10, two.capital_city_population);
    }

    @Test
    void testGetCapitalWithContinentFilter() {
        ArrayList<CapitalCity> capital = app.getCapital("Europe", null, null);
        assertEquals(2, capital.size());
    }

    @Test
    void testGetCapitalWithRegionFilter() {
        ArrayList<CapitalCity> capital = app.getCapital(null, "Region", null);
        assertEquals(2, capital.size());
    }

    @Test
    void testGetCapitalWithLimitFilter() {
        ArrayList<CapitalCity> capital = app.getCapital(null, null, "1");
        assertEquals(1, capital.size());
    }

    @Test
    void testGetCapitalWithNegativeLimitFilter() {
        ArrayList<CapitalCity> capital = app.getCapital(null, null, "-1");
        assertNull(capital);
    }

    @Test
    void testGetCapitalNoMatch() {
        ArrayList<CapitalCity> capital = app.getCapital(null, "NULL", null);
        assertEquals(0, capital.size());
    }

    @Test
    void testGetCapitalWithEmptyStringFilter() {
        ArrayList<CapitalCity> capital = app.getCapital("", "", "");
        assertEquals(2, capital.size());
    }

    @Test
    void testGetCapitalWithMultipleFilters() {
        ArrayList<CapitalCity> capital = app.getCapital("Continent", "Region", "");
        assertNull(capital);
    }


    //GetPopulation Method Testing


    @Test
    void testGetPopulationReturns() {
        ArrayList<Population> population = app.getPopulation("Europe", null, null);

        assertNotNull(population);
        assertEquals(1, population.size());

        Population one = population.get(0);
        assertEquals("Europe", one.population_name.trim());
        assertEquals(1100, one.total_population);
        assertEquals(30, one.city_population);
        assertEquals(1070, one.non_city_population);
        assertEquals("2.73%", one.city_population_percent);
        assertEquals("97.27%", one.non_city_population_percent);
    }

    @Test
    void testGetPopulationWithRegionFilter() {
        ArrayList<Population> population = app.getPopulation(null, "Region", null);
        assertEquals(1, population.size());
    }

    @Test
    void testGetPopulationWithCountryFilter() {
        ArrayList<Population> population = app.getPopulation(null, null, "Country 1");
        assertEquals(1, population.size());

        Population one = population.get(0);
        assertEquals("Country 1", one.population_name.trim());
    }

    @Test
    void testGetPopulationWithEmptyFilters() {
        ArrayList<Population> population = app.getPopulation("", "", "");
        assertNull(population);
    }

    @Test
    void testGetPopulationNoMatch() {
        ArrayList<Population> population = app.getPopulation(null, "Unknown", null);
        assertEquals(0, population.size());
    }

    @Test
    void testGetPopulationWithMultipleFilters() {
        ArrayList<Population> population = app.getPopulation("Europe", "Region", "Country 1");
        assertNull(population);
    }

    @Test
    void testGetPopulationWithNullFilters() {
        ArrayList<Population> population = app.getPopulation(null, null, null);
        assertNull(population);
    }



    //GetInfo Method Testing


    @Test
    void testGetInfoReturns() {
        ArrayList<Info> info = app.getInfo("Europe", null, null, null, null);

        assertNotNull(info);
        assertEquals(1, info.size());

        Info one = info.get(0);
        assertEquals("Europe", one.info_name.trim());
        assertEquals(1100, one.info_population);
    }

    @Test
    void testGetInfoWithRegionFilter() {
        ArrayList<Info> info = app.getInfo(null, "Region", null, null, null);
        assertNotNull(info);
        assertEquals(1, info.size());

        Info one = info.get(0);
        assertEquals("Region", one.info_name.trim());
    }

    @Test
    void testGetInfoWithCountryFilter() {
        ArrayList<Info> info = app.getInfo(null, null, "Country 1", null, null);
        assertNotNull(info);
        assertEquals(1, info.size());

        Info one = info.get(0);
        assertEquals("Country 1", one.info_name.trim());
    }

    @Test
    void testGetInfoWithDistrictFilter() {
        ArrayList<Info> info = app.getInfo(null, null, null, "District 1", null);
        assertNotNull(info);
        assertEquals(1, info.size());

        Info one = info.get(0);
        assertEquals("District 1", one.info_name.trim());
    }

    @Test
    void testGetInfoWithCityFilter() {
        ArrayList<Info> info = app.getInfo(null, null, null, null, "City 1");
        assertNotNull(info);
        assertEquals(1, info.size());

        Info one = info.get(0);
        assertEquals("City 1", one.info_name.trim());
    }

    @Test
    void testGetInfoWithEmptyFilters() {
        ArrayList<Info> info = app.getInfo("", "", "", "", "");
        assertNull(info);
    }

    @Test
    void testGetInfoWithNullFilters() {
        ArrayList<Info> info = app.getInfo(null, null, null, null, null);
        assertNull(info);
    }

    @Test
    void testGetInfoNoMatch() {
        ArrayList<Info> info = app.getInfo("Unknown", null, null, null, null);
        assertNull(info);
    }

    @Test
    void testGetInfoWithMultipleFilters() {
        ArrayList<Info> info = app.getInfo("Europe", "Region", "Country 1", null, null);
        assertNull(info);
    }


    //GetLanguage Method Testing

    @Test
    void testGetLanguageReturnsAll() {
        ArrayList<Language> language = app.getLanguages();

        assertNotNull(language);
        assertEquals(2, language.size());

        Language one = language.get(0);
        assertEquals("Chinese", one.language.trim());
        assertEquals(50, one.speakers);
        assertEquals("4.55%", one.speakers_percent);

        Language two = language.get(1);
        assertEquals("English", two.language.trim());
        assertEquals(500, two.speakers);
        assertEquals("45.45%", two.speakers_percent);
    }



}


