import javalib.worldimages.*;
import javalib.impworld.*;
import java.awt.Color;
import java.util.*;

// The main game world
class ConnectionsWorld extends World implements IConnections {
  ArrayList<Word> words;
  ArrayList<Word> selectedWords;
  ArrayList<Word> groupedWords;
  ArrayList<ArrayList<Word>> submittedGroup;
  ArrayList<ArrayList<Category>> allCategories;
  ArrayList<Category> chosenCategories;
  ArrayList<Category> guessedGroups;
  int triesLeft;
  String currentMessage;
  Random rand;
  Button deselectButton;
  Button shuffleButton;
  Button submitButton;


  // Constructor with passed in random
  ConnectionsWorld(Random rand) {
    super();
    this.rand = rand;
    this.allCategories = new ArrayList<ArrayList<Category>>();
    this.initializeCategories();
    this.chosenCategories = this.randomCategories();
    this.words = this.shuffledWords();
    this.selectedWords = new ArrayList<Word>();
    this.groupedWords = new ArrayList<Word>();
    this.submittedGroup = new ArrayList<ArrayList<Word>>();
    this.guessedGroups = new ArrayList<Category>();
    this.triesLeft = 4;
    this.deselectButton = new Button("Deselect All");
    this.deselectButton.updatePosition(new Posn(SCREEN_WIDTH - 450, SCREEN_HEIGHT - 35));
    this.shuffleButton = new Button("Shuffle");
    this.shuffleButton.updatePosition(new Posn(SCREEN_WIDTH - 350, SCREEN_HEIGHT - 35));
    this.submitButton = new Button("Submit");
    this.submitButton.updatePosition(new Posn(SCREEN_WIDTH - 266, SCREEN_HEIGHT - 35));
  }

  // Default constructor
  ConnectionsWorld() {
    this(new Random());
  }

  // Initialize all set of words
  public void initializeCategories() {
    // Initialize four array list of in the all categories array list
    for (int i = 0; i < 4; i++) {
      this.allCategories.add(new ArrayList<Category>());
    }

    // Initialize 5 yellow category
    this.addCategory(0, YELLOW, "QUICK PEEK",
            new ArrayList<String>(Arrays.asList("GANDER", "GLANCE", "GLIMPSE", "LOOK")));
    this.addCategory(0, YELLOW, "COLORS",
            new ArrayList<String>(Arrays.asList("RED", "BLUE", "GREEN", "YELLOW")));
    this.addCategory(0, YELLOW, "MONTHS",
            new ArrayList<String>(Arrays.asList("MARCH", "JUNE", "APRIL", "MAY")));
    this.addCategory(0, YELLOW, "CAPTIVATE",
            new ArrayList<String>(Arrays.asList("ABSORB", "ENTRANCE", "GRAB", "RIVET")));
    this.addCategory(0, YELLOW, "NUMBERS",
            new ArrayList<String>(Arrays.asList("ONE", "THREE", "SIX", "SEVEN")));

    // Initialize 5 green category
    this.addCategory(1, GREEN, "HOMOPHONES",
            new ArrayList<String>(Arrays.asList("PEAK", "PEEK", "PEKE", "PIQUE")));
    this.addCategory(1, GREEN, "ANIMALS",
            new ArrayList<String>(Arrays.asList("LION", "TIGER", "BEAR", "WOLF")));
    this.addCategory(1, GREEN, "SODAS",
            new ArrayList<String>(Arrays.asList("COKE", "FANTA", "SPRITE", "GINGER ALE")));
    this.addCategory(1, GREEN, "THINGS WITH WINGS",
            new ArrayList<String>(Arrays.asList("AIRPLANE", "FAIRY", "FLY", "HOSPITAL")));
    this.addCategory(1, GREEN, "SPORTS",
            new ArrayList<String>(Arrays.asList("SOCCER", "BASKET BALL", "RUN", "SKI")));

    // Initialize 5 blue category
    this.addCategory(2, BLUE, "DECEIT",
            new ArrayList<String>(Arrays.asList("ACT", "BLUFF", "CHARADE", "FRONT")));
    this.addCategory(2, BLUE, "MUSICAL INSTRUMENTS",
            new ArrayList<String>(Arrays.asList("PIANO", "VIOLIN", "FLUTE", "TRUMPET")));
    this.addCategory(2, BLUE, "COUNTRY",
            new ArrayList<String>(Arrays.asList("AUSTRALIA", "USA", "MEXICO", "CHINA")));
    this.addCategory(2, BLUE, "SUBJECTS",
            new ArrayList<String>(Arrays.asList("MATH", "ART", "PHYSICS", "ENGLISH")));
    this.addCategory(2, BLUE, "FRUITS",
            new ArrayList<String>(Arrays.asList("APPLE", "BANANA", "ORANGE", "FIG")));

    // Initialize 5 purple category
    this.addCategory(3, PURPLE, "PARTS OF A MOUNTAIN",
            new ArrayList<String>(Arrays.asList("CLIFF", "CRAG", "LEDGE", "RIDGE")));
    this.addCategory(3, PURPLE, "TYPES OF SHIPS",
            new ArrayList<String>(Arrays.asList("FERRY", "CRUISER", "YACHT", "SUBMARINE")));
    this.addCategory(3, PURPLE, "WRITING UTENSILS",
            new ArrayList<String>(Arrays.asList("PENCIL", "ERASOR", "PEN", "RULER")));
    this.addCategory(3, PURPLE, "TRANSPORTATIONS",
            new ArrayList<String>(Arrays.asList("BUS", "SUBWAY", "TRAIN", "CAR")));
    this.addCategory(3, PURPLE, "DESSERTS",
            new ArrayList<String>(Arrays.asList("CANNOLI", "COOKIES", "CAKE", "BREAD")));
  }


  // Add category to this all categories
  public void addCategory(int difficulty, Color color, String label, ArrayList<String> words) {
    this.allCategories.get(difficulty).add(new Category(color, label, words));
  }

  // Generate a random category list with one category of each difficulty level
  ArrayList<Category> randomCategories() {
    ArrayList<Category> randomCat = new ArrayList<Category>();

    for (int i = 0; i < 4; i++) {
      ArrayList<Category> shuffled = new ArrayList<Category>(this.allCategories.get(i));
      Collections.shuffle(shuffled, this.rand);
      randomCat.add(shuffled.get(0));
    }

    return randomCat;
  }

  // Shuffle this list of words
  public ArrayList<Word> shuffledWords() {
    ArrayList<Word> allWords = new ArrayList<Word>();

    for (Category cat : this.chosenCategories) {
      for (String word : cat.words) {
        allWords.add(new Word(word, cat));
      }
    }

    Collections.shuffle(allWords, this.rand);
    return allWords;
  }

  // Make scene for the game
  public WorldScene makeScene() {
    WorldScene scene = new WorldScene(SCREEN_WIDTH, SCREEN_HEIGHT);

    // Make scene for the grouped
    this.groupScene(scene);

    // Make scene for the word blocks
    this.wordScene(scene);

    // All groups are guessed display the winning scene
    if (this.guessedGroups.size() == 4) {
      this.winScene(scene);
    }
    // All tries are  used display the losing scene
    else if (this.triesLeft == 0) {
      this.loseScene(scene);
    }
    // Game not ended make the button scene
    else {
      // Make scene for all the buttons
      this.buttonScene(scene);
      // Display how many tries is left 
      WorldImage heartPart = new TextImage("♡: " + this.triesLeft, 20, Color.black);
      scene.placeImageXY(heartPart, SCREEN_WIDTH - 200, SCREEN_HEIGHT - 35);
    }

    // Make scene for the message part
    this.messageScene(scene);

    return scene;
  }

  // Create scene for the message on the top
  public void messageScene(WorldScene scene) {
    // Starter message
    if (this.guessedGroups.size() == 0 && this.triesLeft == 4) {
      this.messageSceneCreate(scene, "Create four groups of four!");
    }
    // Losing message
    else if (this.triesLeft == 0) {
      this.messageSceneCreate(scene, "Uh oh :(");
    }
    // Winning message
    else if (this.guessedGroups.size() == 4) {
      this.messageSceneCreate(scene, "Congratulations! :)");
    }
    // Display current message according to the state of the world
    else {
      this.messageSceneCreate(scene, this.currentMessage);

    }

  }

  // Helper to create the message scene
  public void messageSceneCreate(WorldScene scene, String msg) {
    WorldImage winMessage = new TextImage(msg, 20, Color.black);
    scene.placeImageXY(winMessage, SCREEN_WIDTH / 2, MESSAGE_POSN);
  }

  // Create scene for the grouped categories
  public void groupScene(WorldScene scene) {
    if (this.guessedGroups.size() > 0) {
      for (int i = 0; i < this.guessedGroups.size(); i++) {
        int xPosition = SCREEN_WIDTH / 2;
        int yPosition = (CELL_HEIGHT / 2 + SPACING) + (i * CELL_HEIGHT)
                + (i * SPACING) + MESSAGE_HEIGHT;
        // Update the group position
        this.guessedGroups.get(i).updatePosition(new Posn(xPosition, yPosition));
        // draw the group
        this.guessedGroups.get(i).draw(scene);
      }
    }
  }

  // Create scene for the words list
  public void wordScene(WorldScene scene) {
    for (int i = 0; i < GRID_SIZE - this.guessedGroups.size(); i++) {
      for (int j = 0; j < GRID_SIZE; j++) {
        // Compute index and corresponding x and y positions
        int currentIdx = j + (i * GRID_SIZE);
        int xPosn = (CELL_WIDTH / 2 + SPACING) + (j * CELL_WIDTH) + (j * SPACING);
        int yPosn = (CELL_HEIGHT / 2 + SPACING) + (i * CELL_HEIGHT) + (i * SPACING)
                + MESSAGE_HEIGHT + (CELL_HEIGHT * this.guessedGroups.size())
                + (SPACING * this.guessedGroups.size());
        // Update the word position
        this.words.get(currentIdx).updatePosition(new Posn(xPosn, yPosn));
        // draw the word get from the words list
        this.words.get(currentIdx).draw(scene);
      }
    }
  }

  // Create scene for the winning scene
  public void winScene(WorldScene scene) {
    // Win with 4 tries left
    if (this.triesLeft > 3) {
      this.endScene(scene, "Flawless victory! Pressed r to restart");
    }
    // Win with 3 tries left
    else if (this.triesLeft > 2) {
      this.endScene(scene, "Wow, what a perfect game! Pressed r to restart");
    }
    // Win with 2 tries left
    else if (this.triesLeft > 1) {
      this.endScene(scene, "Amazing puzzle solved! Pressed r to restart");
    }
    // Win with 1 tries left
    else {
      this.endScene(scene, "Great job completing the challenge! Pressed r to restart");
    }
  }

  // Create scene for the losing scene
  public void loseScene(WorldScene scene) {
    // Lose with 3 groups guessed
    if (this.guessedGroups.size() > 2) {
      this.endScene(scene, "So close to victory! Pressed r to restart");
    }
    // Lose with 2 groups guessed
    else if (this.guessedGroups.size() > 1) {
      this.endScene(scene, "Good progress made! Pressed r to restart");
    }
    // Lose with 1 group guessed
    else if (this.guessedGroups.size() > 0) {
      this.endScene(scene, "Close attempt! Pressed r to restart");
    }
    // Lose with 0 group guessed
    else {
      this.endScene(scene, "No groups found. Better luck next time! Pressed r to restart");
    }
  }

  // Create scene for the button section
  public void buttonScene(WorldScene scene) {
    // Draw the deselect all button onto the scene
    this.deselectButton.draw(scene);

    // Draw the shuffle button onto the scene
    this.shuffleButton.draw(scene);

    // Update the button's style if there are 4 words selected to show the 
    // change of the state of the button
    if (this.selectedWords.size() > 3) {
      this.submitButton.buttonStyle(Color.WHITE, Color.BLACK, "solid");
    }
    else {
      this.submitButton.buttonStyle(Color.LIGHT_GRAY, Color.LIGHT_GRAY, "outline");
    }

    // Draw the submit button onto the scene
    this.submitButton.draw(scene);
  }

  // Create scene for the ending
  public void endScene(WorldScene scene, String msg) {
    WorldImage endingMessage = new TextImage(msg, 20, Color.black);
    scene.placeImageXY(endingMessage, SCREEN_WIDTH / 2, SCREEN_HEIGHT - 35);
  }

  // Handler to handle key input
  public void onKeyEvent(String key) {

    // Restart a new game if the key r is pressed
    if (key.equals("r")) {
      this.chosenCategories = this.randomCategories();
      this.words = this.shuffledWords();
      this.guessedGroups.clear();
      this.selectedWords.clear();
      this.triesLeft = 4;
    }
  }

  // Handler to handle mouse input
  public void onMousePressed(Posn pos) {

    // Deactivate all buttons if the game is wined or lose 
    if ((this.groupedWords.size() < 4) && (this.triesLeft > 0)) {
      // Handle mouse click for word's block
      this.mouseWord(pos);

      // Handle mouse click for deselect all button
      this.mouseDeselect(pos);

      // Handle mouse click for shuffle button
      this.mouseShuffle(pos);

      // Handle mouse click for submit button if there are 4 words selected
      if (this.selectedWords.size() == 4) {
        this.mouseSubmit(pos);
      }
    }

  }

  // Select or deselect the word that is clicked 
  public void mouseWord(Posn pos) {
    for (Word current : this.words) {
      if (current.isClicked(pos)) {
        if (current.isSelected) {
          // Deselect the word if the word is selected and remove it from the selectedWord list
          current.deselectWord();
          this.selectedWords.remove(current);
        }
        else {
          // if there are less than 4 selected word then select the word that is clicked and add it
          // to the selected word list
          if (this.selectedWords.size() < 4) {
            current.selectWord();
            this.selectedWords.add(current);
          }
        }
      }
    }
  }

  // Deslect all the words in this word list
  public void mouseDeselect(Posn pos) {
    if (this.deselectButton.isClicked(pos)) {
      for (Word current : this.words) {
        current.deselectWord();
        this.selectedWords.remove(current);
      }
    }
  }

  // Shuffle all the words in this word list
  public void mouseShuffle(Posn pos) {
    if (this.shuffleButton.isClicked(pos)) {
      Collections.shuffle(this.words, this.rand);
    }
  }

  // Helper to check if this current selected words are already submit before
  public boolean submittedBefore() {
    for (ArrayList<Word> submittedWords : this.submittedGroup) {
      if (submittedWords.containsAll(this.selectedWords)
              && this.selectedWords.containsAll(submittedWords)) {
        return true;
      }

    }
    return false;
  }

  // Checked if the submitted selected words are correctly grouped 
  public void mouseSubmit(Posn pos) {
    if (this.submitButton.isClicked(pos)) {
      // Check if the selected word is in a group
      if (this.submittedBefore()) {
        // If so then display message to tell user that the words is submitted before
        this.currentMessage = "This group of words is submitted before! Try another group.";
      }
      else {
        // Add the submitted word list to the submitted group
        this.submittedGroup.add(new ArrayList<Word>(this.selectedWords));
        // Check if the submitted word list is a correct group
        for (Category cat : this.chosenCategories) {
          if (cat.isCorrectGroup(this.selectedWords)) {
            // If in one group then add them to the guessed group and remove them from
            // this word list and clear the selected word list
            this.guessedGroups.add(cat);
            this.words.removeAll(this.selectedWords);
            this.selectedWords.clear();
            this.currentMessage = "Yeah! Keep going!";
          }
        }
        // If not in one group then minus one try 
        if (this.selectedWords.size() > 0) {
          this.triesLeft--;
          this.currentMessage = "Uh oh, wrong one!";
        }
      }
    }
  }
}
