# Population Report System

## Overview
The **Population Report System** is a data reporting application designed to generate, display, and analyse global population data.  
This project was developed as part of a coursework assignment, following **Scrum** methodology and utilising a provided SQL database.

The system enables users to access structured population reports, filter by region or continent, and gain insights into global demographic trends.

---

## EPICS & USER STORIES

### EPIC 1: Country Population Reports

#### **User Story 1.1**
**As a** data analyst  
**I want to** view all countries in the world ordered by population (largest to smallest)  
**So that I can** identify the most and least populated countries globally.

**Acceptance Criteria:**
- Displays all countries sorted in descending order by population.
- Includes columns: **Code**, **Name**, **Continent**, **Region**, **Population**.

---

#### **User Story 1.2**
**As a** data analyst  
**I want to** view countries within a specific continent  
**So that I can** analyse the population distribution of that continent.

**Acceptance Criteria:**
- User can filter countries by **continent**.
- Results are ordered by population (largest to smallest).
- Displays columns: **Code**, **Name**, **Region**, **Population**.

---

#### **User Story 1.3**
**As a** data analyst  
**I want to** view countries within a specific region  
**So that I can** compare population differences within regions.

**Acceptance Criteria:**
- User can filter countries by **region**.
- Results ordered by population in descending order.
- Displays: **Name**, **Continent**, **Population**.

---

### EPIC 2: City Population Reports

#### **User Story 2.1**
**As a** data analyst  
**I want to** view all cities ordered by population  
**So that I can** identify the largest and smallest cities worldwide.

**Acceptance Criteria:**
- Displays all cities with their **Name**, **Country**, **District**, and **Population**.
- Results sorted by population (largest to smallest).

---

#### **User Story 2.2**
**As a** user  
**I want to** view all cities within a specific country  
**So that I can** analyse national urban population distribution.

**Acceptance Criteria:**
- Filter by **country name or code**.
- Ordered by city population descending.
- Displays relevant city information.

---

### EPIC 3: Capital City Reports

#### **User Story 3.1**
**As a** user  
**I want to** view all capital cities ordered by population  
**So that I can** understand how capital cities compare globally.

**Acceptance Criteria:**
- Lists all capital cities in descending order of population.
- Displays: **City Name**, **Country**, **Population**.

---

### EPIC 4: Population Statistics

#### **User Story 4.1**
**As a** data analyst  
**I want to** view the total population of each continent  
**So that I can** compare overall population size by continent.

**Acceptance Criteria:**
- Displays each continent with its **total population**.
- Data pulled dynamically from the SQL database.

---

#### **User Story 4.2**
**As a** data analyst  
**I want to** view population breakdowns (urban vs rural)  
**So that I can** assess how people are distributed geographically.

**Acceptance Criteria:**
- Displays **total population**, **urban population**, and **rural population** by continent, region, or country.

---

## Team
Developed by **Group 5**

---