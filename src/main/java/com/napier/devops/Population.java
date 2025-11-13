package com.napier.devops;
/**
 * The Population class represents population data for a particular area (continent, region, or country),
 * including the total population, city population, non-city population, and the percentage breakdowns.
 * This class is used to hold data on population distribution from the database.
 */
public class Population {

    public String population_name;

    public long total_population;

    public long city_population;

    public long non_city_population;

    public String city_population_percent;

    public String non_city_population_percent;


}
