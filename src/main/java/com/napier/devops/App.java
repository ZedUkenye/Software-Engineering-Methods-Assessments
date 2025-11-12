package com.napier.devops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;

@SpringBootApplication
@RestController
public class App {

    /**
     * Connection to MySQL database.
     */
    private static Connection con = null;

    /**
     * Connect to the MySQL database.
     * @param location Database host:port
     */
    public static void connect(String location) {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for DB to start
                Thread.sleep(5000);

                // Connect to database
                con = DriverManager.getConnection(
                        "jdbc:mysql://" + location + "/world?allowPublicKeyRetrieval=true&useSSL=false",
                        "root",
                        "example"
                );

                System.out.println("Successfully connected");
                break;

            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(sqle.getMessage());

            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public static void disconnect() {
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

    @RequestMapping("country")
    public ArrayList<Country> getCountries(
            @RequestParam(value = "continent", required = false) String continent,
            @RequestParam(value = "region", required = false) String region,
            @RequestParam(value = "limit", required = false) String limit) {
        try {
            //used to send queries to the database
            Statement stmt = con.createStatement();

            //sql select statement
            String sqlSelect = "SELECT country.Code AS code, country.Name AS name, country.Continent AS continent, country.Region AS region, country.Population as population, city.name AS capital " +
                    "FROM country " +
                    "JOIN city ON city.Id = country.Capital ";

            int filterCount = 0;

            if (continent != null && !continent.isEmpty()){
                sqlSelect += " WHERE country.Continent = '" + continent + "'";
                filterCount++;
            }
            if (region != null && !region.isEmpty()){
                sqlSelect += " WHERE country.Region = '" + region + "'";
                filterCount++;
            }

            if (filterCount > 1){
                throw new Exception("Filter count exceeded");
            }

            sqlSelect += " ORDER BY country.Population DESC ";

            if (limit != null && !limit.isEmpty()){
                sqlSelect += " LIMIT " + limit;
            }

            //used to send queries to the database
            ResultSet sqlResults = stmt.executeQuery(sqlSelect);

            ArrayList<Country> country = new ArrayList<>();

            while (sqlResults.next()) {
                Country ctry = new Country();
                ctry.country_code = sqlResults.getString("code");
                ctry.country_name = sqlResults.getString("name");
                ctry.country_continent = sqlResults.getString("continent");
                ctry.country_region = sqlResults.getString("region");
                ctry.country_population = sqlResults.getInt("population");
                ctry.country_capital = sqlResults.getString("capital");

                country.add(ctry);
            }
            return country;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    @RequestMapping("city")
    public ArrayList<City> getCities(
            @RequestParam(value = "continent", required = false) String continent,
            @RequestParam(value = "region", required = false) String region,
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "district", required = false) String district,
            @RequestParam(value = "limit", required = false) String limit) {
        try {
            //used to send queries to the database
            Statement stmt = con.createStatement();

            //sql select statement
            String sqlSelect = "SELECT city.Name AS name, country.Name AS country, city.District AS district, city.Population AS population " +
                    "FROM city " +
                    "JOIN country ON city.CountryCode = country.Code ";

            int filterCount = 0;

            if (continent != null && !continent.isEmpty()){
                sqlSelect += " WHERE country.Continent = '" + continent + "'";
                filterCount++;
            }
            if (region != null && !region.isEmpty()){
                sqlSelect += " WHERE country.Region = '" + region + "'";
                filterCount++;
            }
            if (country != null && !country.isEmpty()){
                sqlSelect += " WHERE country.Name = '" + country + "'";
                filterCount++;
            }
            if (district != null && !district.isEmpty()){
                sqlSelect += " WHERE city.District = '" + district + "'";
                filterCount++;
            }

            if (filterCount > 1){
                throw new Exception("Filter count exceeded");
            }



            sqlSelect += " ORDER BY city.Population DESC ";

            if (limit != null && !limit.isEmpty()){
                sqlSelect += " LIMIT " + limit;
            }

            //used to send queries to the database
            ResultSet sqlResults = stmt.executeQuery(sqlSelect);

            ArrayList<City> city = new ArrayList<>();

            while (sqlResults.next()) {
                City cty = new City();
                cty.city_name = sqlResults.getString("name");
                cty.city_country_name = sqlResults.getString("country");
                cty.city_district = sqlResults.getString("district");
                cty.city_population = sqlResults.getInt("population");

                city.add(cty);
            }
            return city;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    @RequestMapping("capital")
    public ArrayList<CapitalCity> getCapital(
            @RequestParam(value = "continent", required = false) String continent,
            @RequestParam(value = "region", required = false) String region,
            @RequestParam(value = "limit", required = false) String limit) {
        try {
            //used to send queries to the database
            Statement stmt = con.createStatement();

            //sql select statement
            String sqlSelect = "SELECT city.Name AS name, country.Name AS country, city.Population AS population " +
                    "FROM city " +
                    "JOIN country ON country.Capital = city.ID ";

            if (continent != null && !continent.isEmpty()){
                sqlSelect += " WHERE country.Continent = '" + continent + "'";
            }
            if (region != null && !region.isEmpty()){
                sqlSelect += " WHERE country.Region = '" + region + "'";
            }

            sqlSelect += " ORDER BY city.Population DESC ";

            if (limit != null && !limit.isEmpty()){
                sqlSelect += " LIMIT " + limit;
            }

            //used to send queries to the database
            ResultSet sqlResults = stmt.executeQuery(sqlSelect);

            ArrayList<CapitalCity> capital = new ArrayList<>();

            while (sqlResults.next()) {
                CapitalCity cty = new CapitalCity();
                cty.capital_city_name = sqlResults.getString("name");
                cty.capital_city_country = sqlResults.getString("country");
                cty.capital_city_population = sqlResults.getInt("population");

                capital.add(cty);
            }
            return capital;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get capital city details");
            return null;
        }
    }

    @RequestMapping("population")
    public ArrayList<Population> getPopulation(
            @RequestParam(value = "continent", required = false) String continent,
            @RequestParam(value = "region", required = false) String region,
            @RequestParam(value = "country", required = false) String country){
        try {
            //used to send queries to the database
            Statement stmt = con.createStatement();

            //sql select statement
            String sqlSelect = "";

            int filterCount = 0;
            if (continent != null && !continent.isEmpty()){
                sqlSelect = "SELECT c.Continent AS name, " +
                        "SUM(c.Population) AS total, " +
                        "CONCAT(ROUND(SUM(ct.city_pop) / SUM(c.Population) * 100, 2), '%') AS inCities, " +
                        "SUM(ct.city_pop) AS cities, " +
                        "SUM(c.Population) - SUM(ct.city_pop) AS nonCities, " +
                        "CONCAT(ROUND((SUM(c.Population) - SUM(ct.city_pop)) / SUM(c.Population) * 100, 2), '%') AS outCities " +
                        "FROM country c " +
                        "LEFT JOIN ( " +
                        "    SELECT CountryCode, SUM(Population) AS city_pop " +
                        "    FROM city " +
                        "    GROUP BY CountryCode " +
                        ") ct ON c.Code = ct.CountryCode " +
                        "WHERE c.Continent = '" + continent + "' " +
                        "GROUP BY c.Continent;";
                filterCount++;
            }

            if (region != null && !region.isEmpty()){
                sqlSelect = "SELECT c.Region AS name, " +
                        "SUM(c.Population) AS total, " +
                        "CONCAT(ROUND(SUM(ct.city_pop) / SUM(c.Population) * 100, 2), '%') AS inCities, " +
                        "SUM(ct.city_pop) AS cities, " +
                        "SUM(c.Population) - SUM(ct.city_pop) AS nonCities, " +
                        "CONCAT(ROUND((SUM(c.Population) - SUM(ct.city_pop)) / SUM(c.Population) * 100, 2), '%') AS outCities " +
                        "FROM country c " +
                        "LEFT JOIN ( " +
                        "    SELECT CountryCode, SUM(Population) AS city_pop " +
                        "    FROM city " +
                        "    GROUP BY CountryCode " +
                        ") ct ON c.Code = ct.CountryCode " +
                        "WHERE c.Region = '" + region + "' " +
                        "GROUP BY c.Region;";
                filterCount++;

            }

            if (country != null && !country.isEmpty()){
                sqlSelect = "SELECT c.Name AS name, " +
                        "SUM(c.Population) AS total, " +
                        "CONCAT(ROUND(SUM(ct.city_pop) / SUM(c.Population) * 100, 2), '%') AS inCities, " +
                        "SUM(ct.city_pop) AS cities, " +
                        "SUM(c.Population) - SUM(ct.city_pop) AS nonCities, " +
                        "CONCAT(ROUND((SUM(c.Population) - SUM(ct.city_pop)) / SUM(c.Population) * 100, 2), '%') AS outCities " +
                        "FROM country c " +
                        "LEFT JOIN ( " +
                        "    SELECT CountryCode, SUM(Population) AS city_pop " +
                        "    FROM city " +
                        "    GROUP BY CountryCode " +
                        ") ct ON c.Code = ct.CountryCode " +
                        "WHERE c.Name = '" + country + "' " +
                        "GROUP BY c.Name;";
                filterCount++;
            }

            if (filterCount > 1){
                throw new Exception("Filter count exceeded");
            }

            //used to send queries to the database
            ResultSet sqlResults = stmt.executeQuery(sqlSelect);

            ArrayList<Population> population = new ArrayList<>();

            while (sqlResults.next()) {
                Population pop = new Population();
                pop.population_name = sqlResults.getString("name");
                pop.total_population = sqlResults.getLong("total");
                pop.city_population_percent = sqlResults.getString("inCities");
                pop.non_city_population_percent = sqlResults.getString("outCities");
                pop.city_population = sqlResults.getLong("cities");
                pop.non_city_population = sqlResults.getLong("nonCities");

                population.add(pop);
            }
            return population;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population details");
            return null;
        }

    }

    @RequestMapping("info")
    public ArrayList<Info> getInfo(
            @RequestParam(value = "continent", required = false) String continent,
            @RequestParam(value = "region", required = false) String region,
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "district", required = false) String district,
            @RequestParam(value = "city", required = false) String city) {
        try {

            Statement stmt = con.createStatement();

            //sql select statement
            String sqlSelect = "";

            int filterCount = 0;
            if (continent != null && !continent.isEmpty()){
                sqlSelect = "SELECT country.Continent AS name, SUM(country.Population) AS population " +
                        "FROM country " +
                        "WHERE country.Continent = '" + continent + "' " +
                        "GROUP BY country.Continent";
                filterCount++;
            }

            if (region != null && !region.isEmpty()){
                sqlSelect = "SELECT country.Region AS name, SUM(country.Population) AS population " +
                        "FROM country " +
                        "WHERE country.Region = '" + region + "' " +
                        "GROUP BY country.Region";
                filterCount++;
            }

            if (country != null && !country.isEmpty()){
                sqlSelect = "SELECT country.Name AS name, country.Population AS population " +
                        "FROM country " +
                        "WHERE country.Name = '" + country + "' ";
                filterCount++;
            }

            if (district != null && !district.isEmpty()){
                sqlSelect = "SELECT city.District AS name, SUM(city.Population) AS population " +
                        "FROM city " +
                        "WHERE city.District = '" + district + "' " +
                        "GROUP BY city.District";
            }

            if (city != null && !city.isEmpty()){
                sqlSelect = "SELECT city.Name AS name, city.Population AS population " +
                        "FROM city " +
                        "WHERE city.Name = '" + city + "' ";
                filterCount++;
            }

            if  (filterCount > 1){
                throw new Exception("Filter count exceeded");
            }


            //used to send queries to the database
            ResultSet sqlResults = stmt.executeQuery(sqlSelect);

            ArrayList<Info> info = new ArrayList<>();

            while (sqlResults.next()) {
                Info i = new Info();
                i.info_name = sqlResults.getString("name");
                i.info_population = sqlResults.getLong("population");

                info.add(i);
            }
            return info;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get info details");
            return null;
        }
    }

    @RequestMapping("language")
    public ArrayList<Language> getLanguages(){
        try {

            Statement stmt = con.createStatement();

            //sql select statement
            String sqlSelect = "SELECT countrylanguage.Language AS language, " +
                    "ROUND(SUM(country.Population * (countrylanguage.Percentage / 100)), 0) AS speakers, " +
                    "CONCAT(ROUND(SUM(country.Population * (countrylanguage.Percentage / 100)) / " +
                    "(SELECT SUM(Population) FROM country) * 100, 2), '%') AS percent " +
                    "FROM country " +
                    "JOIN countrylanguage ON countrylanguage.CountryCode = country.Code " +
                    "WHERE TRIM(countrylanguage.Language) IN ('Chinese', 'English', 'Hindi', 'Spanish', 'Arabic') " +
                    "GROUP BY countrylanguage.Language";


            ResultSet sqlResults = stmt.executeQuery(sqlSelect);

            ArrayList<Language> language = new ArrayList<>();


            while (sqlResults.next()) {
                Language lang = new Language();
                lang.language= sqlResults.getString("language");
                lang.speakers = sqlResults.getLong("speakers");
                lang.speakers_percent = sqlResults.getString("percent");

                language.add(lang);
            }
            return language;

        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Failed to get language details");
            return null;
        }
    }


    /**
     * Main method to start the application and connect to database.
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            connect("localhost:33060");
        } else {
            connect(args[0]);
        }

        SpringApplication.run(App.class, args);
    }
}