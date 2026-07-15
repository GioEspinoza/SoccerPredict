# FutComp

[![Java 21](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white)](https://openjdk.org/)
[![JavaFX](https://img.shields.io/badge/UI-JavaFX-1f425f)](https://openjfx.io/)
[![TheSportsDB](https://img.shields.io/badge/Data-TheSportsDB-1d9bf0)](https://www.thesportsdb.com/)

FutComp is a JavaFX desktop application that compares football teams using
recent match and event statistics. It combines result points, goal difference,
shots, shots on target, and expected goals into a custom rating, then presents
the stronger side through an interactive international- and club-team UI.

The project demonstrates object-oriented Java design, REST API integration,
JSON parsing, local data caching, background work for responsive interfaces,
and multi-screen JavaFX navigation.

![FutComp application preview](https://github.com/user-attachments/assets/b0e4ce8f-7e73-4d1e-8d8c-6be31b846173)

## Features

- Compare national teams in International Mode
- Compare clubs from the Premier League, La Liga, Serie A, Bundesliga, and Ligue 1
- Display team names, stadiums, short names, and badges
- Retrieve football data through TheSportsDB
- Cache team and match data locally as JSON
- Keep network and prediction work off the JavaFX UI thread
- Return a clear no-data state when event statistics are unavailable

## Prediction model

FutComp evaluates the latest available match through three components:

```text
match rating   = result points + (goal difference × 0.5)
attack rating  = (goals × 2.0)
               + (shots on target × 0.3)
               + (total shots × 0.1)
               + (expected goals × 1.5)
defense rating = (goals conceded × -2.0)
               + (opponent shots on target × -0.3)
               + (opponent total shots × -0.1)
               + (opponent expected goals × -1.5)

final rating = match rating + attack rating + defense rating
```

The application compares both final ratings and identifies the stronger side.
This is a deterministic recent-form comparison, not a probabilistic betting
model or a guarantee of match results.

## Architecture

```text
JavaFX / FXML views
        ↓
Screen controllers
        ↓
Local JSON cache ← TheSportsDB HTTP client
        ↓
JSON parsers and domain models
        ↓
StatsCalc rating engine
        ↓
Prediction result
```

```text
src/futcomp/
├── App.java                         # JavaFX entry point
├── TitleController.java             # Mode selection and navigation
├── InternationalController.java     # National-team comparisons
├── LeagueController.java            # Club-team comparisons
├── APIClass.java                    # TheSportsDB HTTP requests
├── JsonStorage.java                 # Local cache refresh and retrieval
├── JsonParse.java                   # API response parsing
├── Team.java                        # Team model
├── TeamStats.java                   # Match result model
├── EventStats.java                  # Detailed event-stat model
└── StatsCalc.java                   # Rating and prediction logic
```

## Local setup

### Prerequisites

- JDK 21
- Apache NetBeans
- JavaFX SDK
- `org.json` library
- TheSportsDB API access

### Configure NetBeans

1. Clone the repository and open it as an existing NetBeans project.
2. Copy `.env.example` to `.env` and add your TheSportsDB API key:

   ```dotenv
   THESPORTSDB_API_KEY=replace_with_your_key
   ```

3. Open **Project Properties → Libraries**.
4. Point the JavaFX module path to your local JavaFX SDK.
5. Add the `org.json` JAR to the compile classpath.
6. Confirm the run options include:

   ```text
   --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml
   ```

7. Run `futcomp.App` from NetBeans.

The checked-in NetBeans project currently contains developer-local library
paths, so they must be updated for another workstation.

## Data behavior

- `data/team_data.json` stores team identity and display information.
- `data/teamStats_data.json` stores the latest retrieved match/event statistics.
- The cache is refreshed through TheSportsDB and used by the prediction screens.
- API coverage and event-stat availability vary by competition and subscription.
- API credentials are loaded from `THESPORTSDB_API_KEY` or a local ignored
  `.env` file and are never intended to be committed.

## Current limitations

- Ratings use the latest available match rather than a multi-match rolling window.
- The supported team list is currently defined in the application.
- Missing detailed event data prevents a prediction.
- The NetBeans dependency paths are machine-specific.

## Roadmap

- Compare form across the last five matches
- Replace fixed team mappings with dynamic API discovery
- Add confidence scores and explainable rating breakdowns
- Make dependency and API configuration portable
- Add automated tests for parsing and rating logic

## Author

Built by [Giovanni Espinoza](https://github.com/GioEspinoza).
