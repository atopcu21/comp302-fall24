#comp302_project-fall24


Rokue-Like Game

Rokue-Like Game is a simple 2D adventure game where the player navigates a dungeon-like environment, avoids monsters, collects items, and progresses through challenges. The project demonstrates key software engineering concepts, including object-oriented programming, GUI design, and game development fundamentals.


Features

1. Main Menu

Start Game: Launch the game and transition to the gameplay screen.

Help: View instructions on how to play the game.

Exit: Close the application.

2. Gameplay

Player Movement: Use arrow keys to navigate the player character.

Monsters: Dynamic enemy characters with unique behaviors.

Items: Collectible objects to help the player progress.

Pause/Resume: Pause and resume the game using dedicated buttons.

3. Help Menu

Detailed instructions on game controls and objectives.

Navigation back to the Main Menu.

4. Game Over Screen

Displays when the player fails to complete the game within the allotted time or loses all lives.









Installation

Prerequisites

Java 17 or higher: Ensure you have Java Development Kit (JDK) installed.

IDE or Command-Line Tool: Recommended IDEs include IntelliJ IDEA, Eclipse, or VS Code.

Steps

Clone the repository or download the project zip file.

Open the project in your preferred IDE.

Build and run the project using the Main class as the entry point.



# Clone the repository
git clone https://github.com/your-repo/rokue-like-game.git
cd rokue-like-game

# Run the project (if using command line)
javac -d bin src/**/*.java
java -cp bin org.firstgame.Main










Project Structure


src/main/java/org/firstgame
├── assets            # Contains game assets such as images and fonts
├── entities          # Defines game objects like Player, Monsters, and Items
├── properties        # Utility classes for game constants and properties
├── ui                # GUI components including GameWindow, MainMenu, and HelpMenu
└── Main.java         # Entry point of the application











Controls

Arrow Keys: Move the player character.

Pause Button: Pause the game.

Resume Button: Resume the game.






Development

Technologies Used

Java: Primary programming language.

Swing: For GUI design.

AWT: For graphics rendering.

Maven/Gradle (optional): For project build and dependency management.

Key Concepts Implemented

Object-Oriented Programming (OOP)

Design Patterns (Singleton, Factory, etc.)

Model-View Separation Principle







Contribution Guidelines

Fork the repository and create a feature branch.

Make your changes and write clear commit messages.

Submit a pull request with a detailed description of your changes.




