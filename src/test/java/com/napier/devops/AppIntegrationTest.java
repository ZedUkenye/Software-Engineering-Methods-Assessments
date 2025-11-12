package com.napier.devops;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest
{
    static App app;

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

}