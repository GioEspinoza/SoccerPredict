# FutComp  
**JavaFX Match Prediction App using Real API Data**

## Overview
FutComp is a Java desktop application that predicts the stronger team between two selected football teams. It uses real data from TheSportsDB API, processes it, and applies a custom rating algorithm to generate predictions.

This project demonstrates full-stack desktop development, including GUI design, API integration, JSON parsing, and algorithmic decision-making.

---

## Features

- International Mode
  - Compare national teams (Argentina, France, Brazil, etc.)

- League Mode
  - Compare club teams across major leagues  
  - Premier League, La Liga, Serie A, Bundesliga, Ligue 1

- Live Data Integration
  - Fetches real team and match data using API

- Prediction System
  - Uses match results, goal difference, and points

- Modern UI
  - Built with JavaFX + SceneBuilder  
  - Clean card-based layout  
  - Dynamic UI updates  

---

## How It Works

### Data Flow

User, GUI, Controller, API, JSON, Objects, Calculation, Result, GUI

---

### Prediction Algorithm

Rating = Points + (Goal Difference × 0.5)

- Win - 3 points  
- Draw - 1 point  
- Loss - 0 points  

The team with the higher rating is predicted as stronger.

---

## Project Structure

futcomp/

App.java: Launches JavaFX app  
TitleController.java: Navigation between screens  

InternationalController.java  
LeagueController.java: UI + logic for both modes  

APIClass.java: Handles API requests  
JsonParse.java: Parses JSON responses  

Team.java: Team data model  
TeamStats.java: Match statistics model  
StatsCalc.java: Prediction logic  

---

## Notes

- Only the most recent match is used due to API limitations  
- Some team data (short name, stadium) is locally defined  
- API inconsistencies were handled manually for stability  

---

## Future Improvements

- Use last 5 matches instead of 1  
- Replace hardcoded team IDs with dynamic mapping  
- Add team logos dynamically  
- Improve prediction accuracy with better metrics  
- Refactor shared controller logic  

---

## Technologies Used

- Java  
- JavaFX  
- SceneBuilder  
- HTTP Client (Java 11+)  
- JSON (org.json)  
- TheSportsDB API  

---

## Author

Giovanni Espinoza  
Computer Science Student  
Farmingdale State College  

---

## Why This Project Matters

This project demonstrates:

- API integration  
- Object-oriented design  
- GUI development (JavaFX)  
- Multithreading (Platform.runLater)  
- Real-world data processing  

--

## 🔥 Preview

<img width="953" height="570" alt="image" src="https://github.com/user-attachments/assets/b0e4ce8f-7e73-4d1e-8d8c-6be31b846173" />


---

## 📬 Feedback

Feel free to fork, improve, or suggest features!
