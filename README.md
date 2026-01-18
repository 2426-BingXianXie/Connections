# Connections - Word Association Puzzle Game

A digital recreation of the popular word association puzzle game where players identify groups of four related words from a grid of 16 words. Features progressive difficulty levels, interactive UI, and comprehensive game state management for an engaging puzzle-solving experience.

---

## Overview

Connections is a word puzzle game inspired by The New York Times' daily game, challenging players to discover four groups of four words that share a common theme or category. Each game presents 16 words with varying difficulty levels, requiring both pattern recognition and strategic thinking.

### Key Features

- **Color-Coded Difficulty System** - Four difficulty levels (Yellow, Green, Blue, Purple)
- **Interactive Word Selection** - Click to select/deselect up to 4 words at a time
- **Dynamic Category Validation** - Real-time checking of submitted word groups
- **Shuffle Functionality** - Randomize word positions for fresh perspective
- **Progressive Feedback** - Context-aware messages based on game state
- **Multiple Category Sets** - 20+ pre-defined categories for replay value
- **Limited Attempts System** - 4 tries to find all groups, adding strategic depth

### Game Objectives

- **Primary Goal**: Identify all four groups of related words
- **Challenge**: Complete with maximum accuracy (minimal wrong attempts)
- **Strategy**: Balance between obvious and difficult connections
- **Win Condition**: Find all groups before running out of attempts

---

## Game Mechanics

### Core Components

#### Word
Individual word tiles that can be selected:
- **Properties**: Text, Category, Selection State
- **Visual States**:
    - Unselected: Light gray background, black text
    - Selected: Dark gray background, white text
- **Interactions**: Click to toggle selection (max 4 selected)

#### Category
Groupings of related words with difficulty-based colors:
- **Yellow (Easiest)**: Simple, straightforward connections
    - Examples: Colors (RED, BLUE, GREEN, YELLOW), Numbers (ONE, THREE, SIX, SEVEN)
- **Green (Easy)**: Slightly more challenging associations
    - Examples: Animals (LION, TIGER, BEAR, WOLF), Homophones (PEAK, PEEK, PEKE, PIQUE)
- **Blue (Medium)**: Requires deeper thinking
    - Examples: Deceit (ACT, BLUFF, CHARADE, FRONT), Musical Instruments
- **Purple (Hardest)**: Most obscure or nuanced connections
    - Examples: Parts of a Mountain (CLIFF, CRAG, LEDGE, RIDGE), Types of Ships

#### Button
Interactive controls for game actions:
- **Deselect All**: Clear current selection
- **Shuffle**: Randomize word positions
- **Submit**: Validate selected group (requires exactly 4 words)

### Game Flow

```
1. Game Initialization
   ├── Select 4 random categories (1 per difficulty)
   ├── Extract 16 words from selected categories
   ├── Shuffle words randomly on grid
   └── Set tries remaining = 4

2. Player Interaction Loop
   ├── Click words to select (up to 4)
   ├── Use Deselect All to clear selection
   ├── Use Shuffle to rearrange words
   └── Submit when 4 words selected
       ├── If correct → Add to guessed groups
       ├── If incorrect → Decrease tries
       └── If duplicate → Show warning message

3. Game End Conditions
   ├── Win: All 4 groups found
   │   └── Display congratulations with performance message
   └── Lose: 0 tries remaining
       └── Display game over with progress message

4. Restart
   └── Press 'R' to generate new puzzle
```

---

## Technology Stack

### Core Technologies
- **Java 11** - Primary programming language
- **JavaLib ImpWorld** - Interactive world-based game framework
- **JavaLib WorldImages** - Graphics and UI rendering
- **JUnit/Tester** - Testing framework

### Development Tools
- **Eclipse/IntelliJ IDEA** - IDE
- **Git** - Version control
- **Collections Framework** - ArrayList, Random, Collections utilities

### Key Libraries
```java
import javalib.impworld.*;      // Interactive world framework
import javalib.worldimages.*;   // Image and UI rendering
import tester.*;                // Testing framework
import java.awt.Color;          // Color management
import java.util.*;             // Collections and utilities
```

---

## System Architecture

### Class Hierarchy

```
┌──────────────────────┐
│   AGamePiece         │
│   (abstract)         │
├──────────────────────┤
│ - position: Posn     │
│ - width: int         │
│ - height: int        │
│ + updatePosition()   │
│ + draw()             │
│ + isClicked()        │
│ + drawContent()      │◄────abstract
└──────────────────────┘
           ▲
           │ Extends
    ┌──────┴──────┬──────────────┬──────────────┐
    │             │              │              │
┌───────────┐ ┌─────────┐  ┌─────────────┐ ┌──────────────┐
│   Word    │ │ Button  │  │  Category   │ │ConnectionsW..│
├───────────┤ ├─────────┤  ├─────────────┤ ├──────────────┤
│- text     │ │- label  │  │- label      │ │- words       │
│- category │ │- mode   │  │- color      │ │- selected... │
│- isSelect │ │- colors │  │- words[]    │ │- guessed...  │
└───────────┘ └─────────┘  └─────────────┘ │- categories  │
                                            │- triesLeft   │
                                            └──────────────┘
```

### Data Flow

```
User Click → isClicked() → State Update → Scene Rendering
                              │
                              ├── Word Selection/Deselection
                              ├── Button Actions
                              │   ├── Deselect All
                              │   ├── Shuffle
                              │   └── Submit
                              └── Game State Validation
                                  ├── Check Correct Group
                                  ├── Update Tries
                                  └── Check Win/Loss
```

### Category Pool Structure

```
allCategories
├── [0] Yellow Categories (5 sets)
│   ├── QUICK PEEK
│   ├── COLORS
│   ├── MONTHS
│   ├── CAPTIVATE
│   └── NUMBERS
├── [1] Green Categories (5 sets)
│   ├── HOMOPHONES
│   ├── ANIMALS
│   ├── SODAS
│   ├── THINGS WITH WINGS
│   └── SPORTS
├── [2] Blue Categories (5 sets)
│   ├── DECEIT
│   ├── MUSICAL INSTRUMENTS
│   ├── COUNTRY
│   ├── SUBJECTS
│   └── FRUITS
└── [3] Purple Categories (5 sets)
    ├── PARTS OF A MOUNTAIN
    ├── TYPES OF SHIPS
    ├── WRITING UTENSILS
    ├── TRANSPORTATIONS
    └── DESSERTS
```

---

## Installation & Setup

### Prerequisites

1. **Java Development Kit (JDK) 11+**
   ```bash
   java -version  # Verify installation
   ```

2. **JavaLib ImpWorld Library**
    - Download from course resources
    - Add to project classpath

3. **IDE** (Eclipse or IntelliJ IDEA)
    - Configure Java project
    - Import JavaLib library

### Setup Instructions

1. **Clone or Download Project**
   ```bash
   # If using Git
   git clone <repository-url>
   cd connections-game
   ```

2. **Import to IDE**
    - **Eclipse**: File → Import → Existing Projects
    - **IntelliJ**: File → Open → Select project directory

3. **Add JavaLib to Build Path**
    - Right-click project → Build Path → Add External JARs
    - Select `javalib.jar` file

4. **Compile and Run**
   ```java
   // Run the testBigBang method in ExamplesConnections class
   void testBigBang(Tester t) {
       ConnectionsWorld world = new ConnectionsWorld();
       world.bigBang(650, 430, 0.2);
   }
   ```

---

## How to Play

### Starting the Game

```java
ConnectionsWorld game = new ConnectionsWorld();
game.bigBang(650, 430, 0.2);  // width, height, tick rate
```

### Controls

| Action | Control | Description |
|--------|---------|-------------|
| **Select Word** | Mouse Click | Click word to select (up to 4) |
| **Deselect Word** | Mouse Click | Click selected word again to deselect |
| **Deselect All** | Button Click | Clear all selections at once |
| **Shuffle Words** | Button Click | Randomize word positions |
| **Submit Group** | Button Click | Check if 4 selected words form a group |
| **Restart Game** | R Key | Generate new puzzle with different categories |

### Game Interface

```
┌────────────────────────────────────────────────┐
│         Create four groups of four!            │  ← Message Area
├────────────────────────────────────────────────┤
│  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌──────┐ │
│  │ GANDER  │ │  BLUE   │ │  ACT    │ │ CLIFF│ │  ← Word Grid
│  └─────────┘ └─────────┘ └─────────┘ └──────┘ │     (4x4)
│  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌──────┐ │
│  │ GLANCE  │ │ BLUFF   │ │  RED    │ │ CRAG │ │
│  └─────────┘ └─────────┘ └─────────┘ └──────┘ │
│  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌──────┐ │
│  │ GLIMPSE │ │CHARADE  │ │ GREEN   │ │LEDGE │ │
│  └─────────┘ └─────────┘ └─────────┘ └──────┘ │
│  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌──────┐ │
│  │  LOOK   │ │ FRONT   │ │ YELLOW  │ │RIDGE │ │
│  └─────────┘ └─────────┘ └─────────┘ └──────┘ │
├────────────────────────────────────────────────┤
│  [Deselect All] [Shuffle] [Submit]  ♡: 4      │  ← Control Panel
└────────────────────────────────────────────────┘
```

### Gameplay Strategy

1. **Start with Obvious Groups**: Look for clear connections (colors, numbers)
2. **Use Process of Elimination**: Narrow down possibilities as you find groups
3. **Watch for Tricky Connections**: Purple categories often have subtle links
4. **Don't Rush**: Think carefully before submitting to preserve attempts
5. **Use Shuffle**: Fresh layout can reveal patterns you missed

### Winning Conditions

**Flawless Victory** (4 tries left)
- All groups found without any mistakes
- Highest achievement

**Perfect Game** (3 tries left)
- All groups found with one mistake
- Excellent performance

**Amazing Solve** (2 tries left)
- Completed with two mistakes
- Good performance

**Challenge Complete** (1 try left)
- Won on last attempt
- Victory snatched from defeat

### Losing Conditions

**So Close** (3 groups found)
- One group away from victory

**Good Progress** (2 groups found)
- Halfway to completion

**Close Attempt** (1 group found)
- Made some progress

**No Groups** (0 groups found)
- Better luck next time

---

## Implementation Details

### Random Category Selection

**Algorithm**: Stratified Random Sampling

```java
ArrayList<Category> randomCategories() {
    ArrayList<Category> randomCat = new ArrayList<Category>();
    
    // Select one category from each difficulty level
    for (int i = 0; i < 4; i++) {
        ArrayList<Category> shuffled = new ArrayList<Category>(
            this.allCategories.get(i)
        );
        Collections.shuffle(shuffled, this.rand);
        randomCat.add(shuffled.get(0));  // First after shuffle
    }
    
    return randomCat;
}
```

**Why Stratified Sampling?**
- Ensures balanced difficulty distribution
- One category from each color level
- Creates fair, solvable puzzles
- Maintains game progression curve

### Word Grid Management

**Coordinate Calculation**:

```java
public void wordScene(WorldScene scene) {
    for (int i = 0; i < GRID_SIZE - this.guessedGroups.size(); i++) {
        for (int j = 0; j < GRID_SIZE; j++) {
            int currentIdx = j + (i * GRID_SIZE);
            
            // X position: spacing + column offset
            int xPosn = (CELL_WIDTH / 2 + SPACING) + 
                       (j * CELL_WIDTH) + 
                       (j * SPACING);
            
            // Y position: spacing + row offset + guessed groups offset
            int yPosn = (CELL_HEIGHT / 2 + SPACING) + 
                       (i * CELL_HEIGHT) + 
                       (i * SPACING) + 
                       MESSAGE_HEIGHT + 
                       (CELL_HEIGHT * this.guessedGroups.size()) + 
                       (SPACING * this.guessedGroups.size());
            
            this.words.get(currentIdx).updatePosition(new Posn(xPosn, yPosn));
            this.words.get(currentIdx).draw(scene);
        }
    }
}
```

**Dynamic Grid Adjustment**:
- Grid shrinks as groups are guessed
- Remaining words shift up to fill space
- Maintains consistent spacing
- Responsive to game state changes

### Group Validation System

**Correctness Checking**:

```java
public boolean isCorrectGroup(ArrayList<Word> selectedWords) {
    int containNum = 0;
    
    // Count how many selected words belong to this category
    for (Word selected : selectedWords) {
        if (this.words.contains(selected.text)) {
            containNum++;
        }
    }
    
    // All 4 words must match
    return containNum == 4;
}
```

**Duplicate Submission Prevention**:

```java
public boolean submittedBefore() {
    for (ArrayList<Word> submittedWords : this.submittedGroup) {
        // Check if current selection matches any previous submission
        if (submittedWords.containsAll(this.selectedWords) && 
            this.selectedWords.containsAll(submittedWords)) {
            return true;
        }
    }
    return false;
}
```

**Benefits**:
- Prevents wasting attempts on same incorrect group
- Provides helpful feedback to player
- Tracks submission history
- Encourages strategic thinking

### Click Detection Algorithm

```java
public boolean isClicked(Posn pos) {
    return pos.x <= this.position.x + (this.width / 2) &&   // Right bound
           pos.x >= this.position.x - (this.width / 2) &&   // Left bound
           pos.y <= this.position.y + (this.height / 2) &&  // Bottom bound
           pos.y >= this.position.y - (this.height / 2);    // Top bound
}
```

**Hit Box Calculation**:
- Center-based positioning
- Width/height-based boundaries
- Pixel-perfect detection
- Works for all game pieces

### Submit Button State Management

```java
public void buttonScene(WorldScene scene) {
    // Update button appearance based on selection count
    if (this.selectedWords.size() > 3) {
        // Active state: 4 words selected
        this.submitButton.buttonStyle(Color.WHITE, Color.BLACK, "solid");
    } else {
        // Inactive state: < 4 words selected
        this.submitButton.buttonStyle(
            Color.LIGHT_GRAY, Color.LIGHT_GRAY, "outline"
        );
    }
    
    this.submitButton.draw(scene);
}
```

**Visual Feedback**:
- Clear indication of button availability
- Prevents invalid submissions
- Intuitive UI state communication

---

## Game Design

### Difficulty Progression

#### Yellow (Difficulty: 1/4)
**Characteristics**:
- Straightforward, obvious connections
- Common knowledge required
- Direct word associations

**Example Categories**:
- COLORS: RED, BLUE, GREEN, YELLOW
- MONTHS: MARCH, JUNE, APRIL, MAY
- NUMBERS: ONE, THREE, SIX, SEVEN

#### Green (Difficulty: 2/4)
**Characteristics**:
- Slightly more nuanced connections
- May require specific knowledge
- Thematic groupings

**Example Categories**:
- HOMOPHONES: PEAK, PEEK, PEKE, PIQUE
- ANIMALS: LION, TIGER, BEAR, WOLF
- SODAS: COKE, FANTA, SPRITE, GINGER ALE

#### Blue (Difficulty: 3/4)
**Characteristics**:
- Abstract or conceptual connections
- Requires lateral thinking
- Less obvious associations

**Example Categories**:
- DECEIT: ACT, BLUFF, CHARADE, FRONT
- MUSICAL INSTRUMENTS: PIANO, VIOLIN, FLUTE, TRUMPET
- SUBJECTS: MATH, ART, PHYSICS, ENGLISH

#### Purple (Difficulty: 4/4)
**Characteristics**:
- Most obscure connections
- Specialized knowledge helpful
- Multiple interpretation potential

**Example Categories**:
- PARTS OF A MOUNTAIN: CLIFF, CRAG, LEDGE, RIDGE
- TYPES OF SHIPS: FERRY, CRUISER, YACHT, SUBMARINE
- WRITING UTENSILS: PENCIL, ERASER, PEN, RULER

### Design Principles

#### 1. Progressive Challenge
- Easier groups typically found first
- Difficulty increases with remaining groups
- Satisfying skill curve

#### 2. Visual Clarity
- Color-coded difficulty system
- Clear selection states
- Responsive button styling
- Organized layout

#### 3. Fair Gameplay
- 4 attempts allows for mistakes
- Duplicate prevention helps players
- Clear feedback on every action
- Shuffle helps when stuck

#### 4. Replay Value
- 20 total categories (5 per difficulty)
- Random selection each game
- Different word arrangements
- Multiple solving strategies

---

## Testing

### Test Coverage

Comprehensive test suite with **25+ test methods**:

#### Component Tests
- `testUpdatePosition()` - Position updates for all game pieces
- `testDrawContent()` - Rendering for Word, Button, Category
- `testIsClicked()` - Click detection accuracy
- `testSelectOrDeselect()` - Word selection state management
- `testButtonStyle()` - Button appearance changes

#### Game Logic Tests
- `testIsCorrectGroup()` - Group validation accuracy
- `testListToString()` - Category word list formatting
- `testRandomCategoriesAndShuffle()` - Random generation
- `testInitializeCategories()` - Category pool setup
- `testAddCategory()` - Category addition

#### Scene Rendering Tests
- `testMakeScene()` - Complete scene construction
- `testGroupScene()` - Guessed group positioning
- `testWordScene()` - Word grid layout
- `testButtonScene()` - Button panel rendering
- `testMessageScene()` - Message display
- `testWinScene()` - Victory messages
- `testLoseScene()` - Game over messages
- `testEndScene()` - Ending screen formatting

### Running Tests

```java
// In ExamplesConnections class
public static void main(String[] args) {
    Tester.run(new ExamplesConnections());
}
```

**Test Example**:

```java
// Test correct group validation
void testIsCorrectGroup(Tester t) {
    initData();
    
    // Correct group of 4 words from same category
    t.checkExpect(this.testCategory.isCorrectGroup(testWords), true);
    
    // Incomplete group (only 3 words)
    ArrayList<Word> wrongGroup = new ArrayList<>(testWords.subList(0, 3));
    t.checkExpect(this.testCategory.isCorrectGroup(wrongGroup), false);
    
    // Wrong word included in group
    wrongGroup.add(new Word("WRONG", this.testCategory));
    t.checkExpect(this.testCategory.isCorrectGroup(wrongGroup), false);
}
```

