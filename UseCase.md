# USE CASE: Generation of Population Reports From An SQL Table

---

## CHARACTERISTIC INFORMATION

### Goal in Context
The goal of this use case is to allow users (data analysts or general users) to generate and view population reports by country, region, or continent.  
The reports should display relevant population statistics, be ordered appropriately, and enable meaningful demographic analysis.

---

### Scope
**Population Report System (PRS)** — a software application designed to query and present population data from an SQL database.


---

### Preconditions
- The system is installed and connected to the SQL database.  
- The database contains up-to-date population data for countries, regions, and cities.  
- The user has launched the application 

---

### Success End Condition
- The selected report is successfully generated and displayed on-screen.  
- The data is sorted, filtered, and formatted according to user selection (e.g., by continent, country, or city).  
- The user can export or analyse the report as needed.

---

### Failed End Condition
- The report cannot be generated due to a database connection error, missing data, or invalid filter selection.  
- The system displays an error message indicating the issue.

---

### Primary Actor
**Data Analyst / User** — an individual seeking to retrieve, view, and analyse population statistics.

---

### Trigger
The user selects a population report option from the application’s main menu 

---

## MAIN SUCCESS SCENARIO

1. User opens the Population Report System.  
2. The system displays the main menu with report options.  
3. User selects a report type (e.g., *All Countries by Population*, *Cities by Country*, or *Continents by Total Population*).  
4. The system prompts the user for any filters (e.g., continent, region, or country).  
5. User enters or selects filter values.  
6. The system validates the input.  
7. The system queries the SQL database for matching data.  
8. The system formats and sorts the data according to the report criteria.  
9. The report is displayed to the user on-screen.  
10. The user reviews the data and optionally exports or saves the report.  
11. The system returns to the main menu after completion.

---

## EXTENSIONS

**1. Invalid Report Type Selected:**  
→ System displays an error message and prompts the user to select a valid report option.  

**2. Database Connection Failure:**  
→ System displays “Database connection error” and logs the issue.  

---

## SUB-VARIATIONS

- **Report Type:** Country, Region, Continent, City, or Capital City  
- **Sorting Options:** Ascending or Descending by population  
- **Output Format:** On-screen view 


---

## SCHEDULE

**DUE DATE:** Week 10 of coursework submission


