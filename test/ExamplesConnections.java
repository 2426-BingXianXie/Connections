import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import javalib.impworld.WorldScene;
import javalib.worldimages.FontStyle;
import javalib.worldimages.OverlayImage;
import javalib.worldimages.OverlayOffsetImage;
import javalib.worldimages.Posn;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldImage;
import tester.Tester;

// examples class of connections
class ExamplesConnections {
  ConnectionsWorld cworld = new ConnectionsWorld();

  //Instances for testing
  ConnectionsWorld world;
  Word testWord;
  Button testButton;
  Category testCategory;
  ArrayList<Word> testWords;

  // initData initializes the world and other test objects before each test
  void initData() {
    this.world = new ConnectionsWorld(new Random(42));
    this.testWord = new Word("Number", new Category
            (Color.YELLOW, "Number", new ArrayList<>()));
    this.testButton = new Button("Number");
    this.testCategory = new Category(Color.YELLOW, "Number",
            new ArrayList<>(Arrays.asList("ONE", "TWO", "THREE", "FOUR")));
    this.testWords = new ArrayList<>(Arrays.asList(
            new Word("ONE", this.testCategory),
            new Word("TWO", this.testCategory),
            new Word("THREE", this.testCategory),
            new Word("FOUR", this.testCategory)));
  }

  //Test updatePosition for game pieces
  void testUpdatePosition(Tester t) {
    initData();
    // check updatePosition correctly sets the position for Word
    this.testWord.updatePosition(new Posn(100, 200));
    t.checkExpect(this.testWord.position, new Posn(100, 200));
    // check updatePosition correctly sets the position for Button
    this.testButton.updatePosition(new Posn(50, 75));
    t.checkExpect(this.testButton.position, new Posn(50, 75));
    // check updatePosition correctly sets the position for Category
    this.testCategory.updatePosition(new Posn(0, 0));
    t.checkExpect(this.testCategory.position, new Posn(0, 0));
  }

  // Test drawContent for Word, Button, and Category
  void testDrawContent(Tester t) {
    initData();

    // Test Word's drawContent for a selected word
    Word selectedWord = new Word("SELECTED", new Category
            (Color.YELLOW, "Test", new ArrayList<>()));
    selectedWord.selectWord();
    WorldImage selectedImg = selectedWord.drawContent();
    t.checkExpect(selectedImg, new OverlayImage(
            new TextImage("SELECTED", IWord.TEXT_SIZE, FontStyle.BOLD, Color.WHITE),
            new RectangleImage(IWord.CELL_WIDTH, IWord.CELL_HEIGHT, "solid", Color.DARK_GRAY)
    ));

    // Test Word's drawContent for an unselected word
    Word unselectedWord = new Word("UNSELECTED", new Category
            (Color.YELLOW, "Test", new ArrayList<>()));
    WorldImage unselectedImg = unselectedWord.drawContent();
    t.checkExpect(unselectedImg, new OverlayImage(
            new TextImage("UNSELECTED", IWord.TEXT_SIZE, FontStyle.BOLD, Color.BLACK),
            new RectangleImage(IWord.CELL_WIDTH, IWord.CELL_HEIGHT, "solid", Color.LIGHT_GRAY)
    ));

    // Test Button's drawContent for a default button style
    Button defaultBtn = new Button("DEFAULT");
    WorldImage defaultImg = defaultBtn.drawContent();
    int defaultWidth = (int) (new TextImage
            ("DEFAULT", IButton.TEXT_SIZE, Color.BLACK).getWidth()) + IButton.SPACING;
    t.checkExpect(defaultImg, new OverlayImage(
            new TextImage("DEFAULT", IButton.TEXT_SIZE, Color.BLACK),
            new RectangleImage(defaultWidth, IButton.BUTTON_HEIGHT, "outline", Color.BLACK)
    ));

    // Test Button's drawContent for a styled button
    Button styledBtn = new Button("STYLED");
    styledBtn.buttonStyle(Color.WHITE, Color.BLUE, "solid");
    WorldImage styledImg = styledBtn.drawContent();
    int styledWidth = (int) (new TextImage
            ("STYLED", IButton.TEXT_SIZE, Color.WHITE).getWidth()) + IButton.SPACING;
    t.checkExpect(styledImg, new OverlayImage(
            new TextImage("STYLED", IButton.TEXT_SIZE, Color.WHITE),
            new RectangleImage(styledWidth, IButton.BUTTON_HEIGHT, "solid", Color.BLUE)
    ));

    // Test Category's drawContent for a category with words
    Category testCategory = new Category
            (Color.GREEN, "COLOR", new ArrayList<>(Arrays.asList("A", "B", "C", "D")));
    WorldImage categoryImg = testCategory.drawContent();
    t.checkExpect(categoryImg, new OverlayOffsetImage(
            new TextImage("A, B, C, D", ICategory.FONT_SIZE, Color.BLACK),
            0, -10,
            new OverlayOffsetImage(
                    new TextImage("COLOR", ICategory.FONT_SIZE, FontStyle.BOLD, Color.BLACK),
                    0, 10,
                    new RectangleImage(ICategory.CELL_WIDTH, ICategory.CELL_HEIGHT, "solid", Color.GREEN)
            )));

    // Test Category's drawContent for an empty category
    Category emptyCat = new Category(Color.BLUE, "", new ArrayList<>());
    t.checkExpect(emptyCat.drawContent(),
            new OverlayOffsetImage(
                    new TextImage("", ICategory.FONT_SIZE, Color.BLACK),
                    0, -10,
                    new OverlayOffsetImage(
                            new TextImage("", ICategory.FONT_SIZE, FontStyle.BOLD, Color.BLACK),
                            0, 10,
                            new RectangleImage
                                    (ICategory.CELL_WIDTH, ICategory.CELL_HEIGHT, "solid", Color.BLUE)
                    )));

    // Test Category's drawContent for a pink category with words
    Category pinkCat = new Category
            (Color.PINK, "PINK", new ArrayList<>(Arrays.asList("X", "Y", "Z", "W")));
    t.checkExpect(pinkCat.drawContent(),
            new OverlayOffsetImage(
                    new TextImage("X, Y, Z, W", ICategory.FONT_SIZE, Color.BLACK),
                    0, -10,
                    new OverlayOffsetImage(
                            new TextImage("PINK", ICategory.FONT_SIZE, FontStyle.BOLD, Color.BLACK),
                            0, 10,
                            new RectangleImage
                                    (ICategory.CELL_WIDTH, ICategory.CELL_HEIGHT, "solid", Color.PINK)
                    )));
  }

  // Test isClicked to verify that clicks are detected correctly based on position
  void testIsClicked(Tester t) {
    initData();
    this.testWord.updatePosition(new Posn(100, 100));
    // in the field
    t.checkExpect(this.testWord.isClicked(new Posn(100, 100)), true);
    t.checkExpect(this.testWord.isClicked(new Posn(175, 137)), true);
    // out of fiield
    t.checkExpect(this.testWord.isClicked(new Posn(200, 200)), false);
  }

  // Test select and deselect operations for a Word
  void testSelectOrDeselect(Tester t) {
    initData();
    this.testWord.selectWord();
    t.checkExpect(this.testWord.isSelected, true);

    // Calling selectWord twice should still leave the word selected
    this.testWord.selectWord();
    t.checkExpect(this.testWord.isSelected, true);

    // After deselecting, the word should not be selected
    this.testWord.deselectWord();
    t.checkExpect(this.testWord.isSelected, false);
  }

  //Test buttonStyle to verify that button style changes are correctly applied
  void testButtonStyle(Tester t) {
    initData();
    this.testButton.buttonStyle(Color.RED, Color.BLUE, "solid");
    // text color red
    t.checkExpect(this.testButton.textColor, Color.RED);
    // base color blue
    t.checkExpect(this.testButton.baseColor, Color.BLUE);
    // mode solid
    t.checkExpect(this.testButton.mode, "solid");
  }

  //Test isCorrectGroup method of Category, ensuring that a group of words is correctly recognized
  void testIsCorrectGroup(Tester t) {
    initData();
    // Fall in the same category
    t.checkExpect(this.testCategory.isCorrectGroup(testWords), true);

    // Test with an incomplete group.
    ArrayList<Word> wrongGroup = new ArrayList<>(testWords.subList(0, 3));
    t.checkExpect(this.testCategory.isCorrectGroup(wrongGroup), false);

    // Test with a wrong word included.
    wrongGroup.add(new Word("WRONG", this.testCategory));
    t.checkExpect(this.testCategory.isCorrectGroup(wrongGroup), false);
  }

  //Test listToString to verify that the category word list is converted to a string correctly
  void testListToString(Tester t) {
    initData();
    // A category to string
    t.checkExpect(this.testCategory.listToString(), "ONE, TWO, THREE, FOUR");

    // Remove one word and check the string output
    this.testCategory.words.remove(3);
    t.checkExpect(this.testCategory.listToString(), "ONE, TWO, THREE, ");

    // Clear words and verify the output is an empty string
    this.testCategory.words.clear();
    t.checkExpect(this.testCategory.listToString(), "");
  }


  //Test makeScene, winScene, and loseScene by comparing the scenes produced
  void testMakeScene(Tester t) {
    initData();
    // Initial state: No guessed groups and full tries
    WorldScene scene1 = this.world.makeScene();
    // Reset guessed groups and tries to initial values
    this.world.guessedGroups.clear();
    this.world.triesLeft = 4;
    t.checkExpect(scene1, this.world.makeScene());

    // Simulate a win state
    initData();
    for (int i = 0; i < 4; i++) {
      this.world.guessedGroups.add(this.world.chosenCategories.get(i));
    }
    this.world.triesLeft = 4;
    WorldScene scene2 = new WorldScene(650, 440);
    this.world.winScene(scene2);
    WorldScene expected2 = new WorldScene(650, 440);
    expected2.placeImageXY(new TextImage
                    ("Flawless victory! Pressed r to restart", 20, Color.black),
            650 / 2, 405);
    t.checkExpect(scene2, expected2);

    // Simulate a lose state
    initData();
    this.world.triesLeft = 0;
    WorldScene scene3 = new WorldScene(650, 440);
    this.world.loseScene(scene3);
    WorldScene expected3 = new WorldScene(650, 440);
    expected3.placeImageXY(new TextImage
                    ("No groups found. Better luck next time! Pressed r to restart", 20, Color.black),
            650 / 2, 405);
    t.checkExpect(scene3, expected3);
  }


  //Test groupScene to check that guessed groups are positioned correctly in the scene
  void testGroupScene(Tester t) {
    initData();
    WorldScene scene = new WorldScene(650, 440);

    // Initially, guessedGroups is empty
    t.checkExpect(this.world.guessedGroups.size(), 0);

    // Add one guessed group and verify its position
    Category testCat = this.world.chosenCategories.get(0);
    this.world.guessedGroups.add(testCat);
    this.world.groupScene(scene);
    t.checkExpect(testCat.position, new Posn(325, 87));

    // Add a second guessed group and verify its new position
    this.world.guessedGroups.add(this.world.chosenCategories.get(1));
    this.world.groupScene(scene);
    t.checkExpect(this.world.chosenCategories.get(1).position, new Posn(325, 172));
  }

  //Test wordScene to verify word block positions
  void testWordScene(Tester t) {
    initData();

    // Initially, create a scene
    this.world.makeScene();

    // Check the position of the first word
    Word firstWord = this.world.words.get(0);
    t.checkExpect(firstWord.position.x, 85);
    t.checkExpect(firstWord.position.y, 87);

    // Simulate a guessed group; this should shift the word positions
    this.world.guessedGroups.add(this.world.chosenCategories.get(0));
    this.world.makeScene();
    t.checkExpect(firstWord.position.y, 172);

    // Check the position of the last word in the grid
    Word lastWord = this.world.words.get(15);
    t.checkExpect(lastWord.position.x, 565);
  }


  // Test buttonScene to verify that buttons are positioned and styled correctly
  void testButtonScene(Tester t) {
    initData();
    WorldScene scene = new WorldScene(650, 430);
    this.world.buttonScene(scene);
    Button deselect = this.world.deselectButton;
    Button shuffle = this.world.shuffleButton;
    Button submit = this.world.submitButton;
    // check deselect button posn
    t.checkExpect(deselect.position, new Posn(200, 405));
    // check shuffle button posn
    t.checkExpect(shuffle.position, new Posn(300, 405));
    // check submit button posn
    t.checkExpect(submit.position, new Posn(384, 405));
    // Simulate 4 selected words to check button style update
    this.world.selectedWords.addAll(this.world.words.subList(0, 4));
    this.world.buttonScene(scene);
    t.checkExpect(this.world.submitButton.textColor, Color.WHITE);
  }


  //Test messageScene and messageSceneCreate to verify the correct message is drawn
  void testMessageScene(Tester t) {
    // Test messageSceneCreate: directly draw a message and check the scene dimensions
    initData();
    WorldScene scene = new WorldScene(650, 440);
    this.world.messageSceneCreate(scene, "Game");
    t.checkExpect(scene.width, 650);

    // Test messageScene for initial state
    initData();
    WorldScene scene1 = new WorldScene(650, 440);
    this.world.guessedGroups.clear();
    this.world.triesLeft = 4;
    this.world.messageScene(scene1);
    WorldScene expected1 = new WorldScene(650, 440);
    expected1.placeImageXY(new TextImage("Create four groups of four!", 20, Color.BLACK),
            650 / 2, IConnections.MESSAGE_POSN);
    t.checkExpect(scene1, expected1);

    // Test messageScene when a custom currentMessage is set
    initData();
    WorldScene scene2 = new WorldScene(650, 440);
    this.world.triesLeft = 3;
    this.world.currentMessage = "Error";
    this.world.messageScene(scene2);
    WorldScene expected2 = new WorldScene(650, 440);
    expected2.placeImageXY(new TextImage("Error", 20, Color.black),
            650 / 2, IConnections.MESSAGE_POSN);
    t.checkExpect(scene2, expected2);

    // Test messageScene for win state
    initData();
    WorldScene scene3 = new WorldScene(650, 440);
    for (int i = 0; i < 4; i++) {
      this.world.guessedGroups.add(this.world.chosenCategories.get(i));
    }
    this.world.messageScene(scene3);
    WorldScene expected3 = new WorldScene(650, 440);
    expected3.placeImageXY(new TextImage("Congratulations! :)", 20, Color.black),
            650 / 2, IConnections.MESSAGE_POSN);
    t.checkExpect(scene3, expected3);
  }

  //Test winScene to verify that the correct winning message is drawn depending on tries left
  void testWinScene(Tester t) {
    // Test winScene for triesLeft = 4
    initData();
    for (int i = 0; i < 4; i++) {
      this.world.guessedGroups.add(this.world.chosenCategories.get(i));
    }
    this.world.triesLeft = 4;
    WorldScene scene1 = new WorldScene(650, 440);
    this.world.winScene(scene1);
    WorldScene expected1 = new WorldScene(650, 440);
    expected1.placeImageXY(new TextImage
                    ("Flawless victory! Pressed r to restart", 20, Color.black),
            650 / 2, 405);
    t.checkExpect(scene1, expected1);

    // Test winScene for triesLeft = 3
    initData();
    for (int i = 0; i < 4; i++) {
      this.world.guessedGroups.add(this.world.chosenCategories.get(i));
    }
    this.world.triesLeft = 3;
    WorldScene scene2 = new WorldScene(650, 440);
    this.world.winScene(scene2);
    WorldScene expected2 = new WorldScene(650, 440);
    expected2.placeImageXY(new TextImage
                    ("Wow, what a perfect game! Pressed r to restart", 20, Color.black),
            650 / 2, 405);
    t.checkExpect(scene2, expected2);

    // Test winScene for triesLeft = 2
    initData();
    for (int i = 0; i < 4; i++) {
      this.world.guessedGroups.add(this.world.chosenCategories.get(i));
    }
    this.world.triesLeft = 2;
    WorldScene scene3 = new WorldScene(650, 440);
    this.world.winScene(scene3);
    WorldScene expected3 = new WorldScene(650, 440);
    expected3.placeImageXY(new TextImage
                    ("Amazing puzzle solved! Pressed r to restart", 20, Color.black),
            650 / 2, 405);
    t.checkExpect(scene3, expected3);

    // Test winScene for triesLeft = 1
    initData();
    for (int i = 0; i < 4; i++) {
      this.world.guessedGroups.add(this.world.chosenCategories.get(i));
    }
    this.world.triesLeft = 1;
    WorldScene scene4 = new WorldScene(650, 440);
    this.world.winScene(scene4);
    WorldScene expected4 = new WorldScene(650, 440);
    expected4.placeImageXY(new TextImage
                    ("Great job completing the challenge! Pressed r to restart", 20, Color.black),
            650 / 2, 405);
    t.checkExpect(scene4, expected4);
  }

  /// Test loseScene to verify that the correct losing message is drawn based on guessed groups
  void testLoseScene(Tester t) {
    // Test loseScene for 0 guessed groups
    initData();
    this.world.guessedGroups.clear();
    this.world.triesLeft = 0;
    WorldScene scene1 = new WorldScene(650, 440);
    this.world.loseScene(scene1);
    WorldScene expected1 = new WorldScene(650, 440);
    expected1.placeImageXY(new TextImage
                    ("No groups found. Better luck next time! Pressed r to restart", 20, Color.black),
            650 / 2, 405);
    t.checkExpect(scene1, expected1);

    // Test loseScene for 1 guessed group
    initData();
    this.world.guessedGroups.clear();
    this.world.guessedGroups.add(this.world.chosenCategories.get(0));
    this.world.triesLeft = 0;
    WorldScene scene2 = new WorldScene(650, 440);
    this.world.loseScene(scene2);
    WorldScene expected2 = new WorldScene(650, 440);
    expected2.placeImageXY(new TextImage
                    ("Close attempt! Pressed r to restart", 20, Color.black),
            650 / 2, 405);
    t.checkExpect(scene2, expected2);

    // Test loseScene for 2 guessed group
    initData();
    this.world.guessedGroups.clear();
    for (int i = 0; i < 2; i++) {
      this.world.guessedGroups.add(this.world.chosenCategories.get(i));
    }
    this.world.triesLeft = 0;
    WorldScene scene3 = new WorldScene(650, 440);
    this.world.loseScene(scene3);
    WorldScene expected3 = new WorldScene(650, 440);
    expected3.placeImageXY(new TextImage
                    ("Good progress made! Pressed r to restart", 20, Color.black),
            650 / 2, 405);
    t.checkExpect(scene3, expected3);

    // Test loseScene for 3 guessed group
    initData();
    this.world.guessedGroups.clear();
    for (int i = 0; i < 3; i++) {
      this.world.guessedGroups.add(this.world.chosenCategories.get(i));
    }
    this.world.triesLeft = 0;
    WorldScene scene4 = new WorldScene(650, 440);
    this.world.loseScene(scene4);
    WorldScene expected4 = new WorldScene(650, 440);
    expected4.placeImageXY(new TextImage
                    ("So close to victory! Pressed r to restart", 20, Color.black),
            650 / 2, 405);
    t.checkExpect(scene4, expected4);
  }

  //Test endScene to verify that a custom ending message is drawn correctly
  void testEndScene(Tester t) {
    // check message place on the correct posn
    initData();
    WorldScene scene1 = new WorldScene(650, 440);
    this.world.endScene(scene1, "Good progress made! Pressed r to restart");
    WorldScene expected1 = new WorldScene(650, 440);
    expected1.placeImageXY(new TextImage("Good progress made! Pressed r to restart", 20, Color.BLACK),
            650 / 2, 405);
    t.checkExpect(scene1, expected1);
    // check message place on the correct posn
    initData();
    WorldScene scene2 = new WorldScene(650, 440);
    this.world.endScene(scene2, "Game Over");
    WorldScene expected2 = new WorldScene(650, 440);
    expected2.placeImageXY(new TextImage("Game Over", 20, Color.black),
            650 / 2, 405);
    t.checkExpect(scene2, expected2);
    // check message place on the correct posn
    initData();
    WorldScene scene3 = new WorldScene(650, 440);
    this.world.endScene(scene3, "Better luck next time! Pressed r to restart");
    WorldScene expected3 = new WorldScene(650, 440);
    expected3.placeImageXY(new TextImage
                    ("Better luck next time! Pressed r to restart", 20, Color.black),
            650 / 2, 405);
    t.checkExpect(scene3, expected3);
  }

  //Test randomCategories and shuffledWords methods to verify that they produce proper randomized output.
  void testRandomCategoriesAndShuffle(Tester t) {
    // Test randomCategories
    initData();

    ArrayList<Category> RC1 = this.world.randomCategories();
    ArrayList<Category> RC2 = this.world.randomCategories();

    // randomCategories should return a list of size 4.
    t.checkExpect(RC1.size(), 4);

    // Each category in the random list must be from the corresponding allCategories list.
    for (int i = 0; i < 4; i++) {
      t.checkExpect(this.world.allCategories.get(i).contains(RC1.get(i)), true);
    }
    // Two successive calls should yield different orders (due to shuffling).
    for (int i = 0; i < 4; i++) {
      t.checkExpect(RC1.get(i) != RC2.get(i), true);
    }
    t.checkExpect(!RC1.equals(RC2), true);

    // Test shuffledWords
    initData();
    ArrayList<Word> ws = this.world.shuffledWords();

    // Build a list of all expected words from chosenCategories without using '+='.
    ArrayList<String> allExpectedWords = new ArrayList<String>();
    for (Category cat : this.world.chosenCategories) {
      allExpectedWords.addAll(cat.words);
    }
    int expectedCount = allExpectedWords.size();
    t.checkExpect(ws.size(), expectedCount);

    // Collect the actual word texts from shuffledWords.
    ArrayList<String> actualTexts = new ArrayList<String>();
    for (Word w : ws) {
      actualTexts.add(w.text);
    }

    // Build expectedTexts from the combined words of chosenCategories.
    ArrayList<String> expectedTexts = new ArrayList<String>(allExpectedWords);

    // Sort both lists so that order does not matter.
    Collections.sort(actualTexts);
    Collections.sort(expectedTexts);
    t.checkExpect(actualTexts, expectedTexts);
  }


  //Test initializeCategories to verify that categories are initialized correctly.
  void testInitializeCategories(Tester t) {
    initData();
    this.world.initializeCategories();

    // Test that there are 10 yellow categories.
    t.checkExpect(this.world.allCategories.get(0).size(), 10);

    // Test that the first green category is labeled "HOMOPHONES".
    t.checkExpect(this.world.allCategories.get(1).get(0).label, "HOMOPHONES");

    // Test that the first purple category has the expected color.
    t.checkExpect(this.world.allCategories.get(3).get(0).color, new Color(178, 114, 224));
  }


  //Test addCategory to verify that new categories are added properly.
  void testAddCategory(Tester t) {
    initData();

    // Add a new yellow category.
    int initialYellow = this.world.allCategories.get(0).size();
    this.world.addCategory(0, Color.YELLOW, "NEW YELLOW", new ArrayList<>());
    t.checkExpect(this.world.allCategories.get(0).size(), initialYellow + 1);

    // Add a new purple category.
    int initialPurple = this.world.allCategories.get(3).size();
    this.world.addCategory(3, new Color(178, 114, 224), "NEW PURPLE", new ArrayList<>());
    t.checkExpect(this.world.allCategories.get(3).size(), initialPurple + 1);

    // Verify that the label of the newly added yellow category is "NEW YELLOW".
    t.checkExpect(this.world.allCategories.get(0).get(initialYellow).label, "NEW YELLOW");
  }

  void testOnKeyEvent(Tester t) {

  }

  void testOnMousePressed(Tester t) {

  }

  void testMouseWord(Tester t) {

  }

  void testMouseDeselect(Tester t) {

  }

  void testMouseShuffle(Tester t) {

  }

  void testMouseSubmit(Tester t) {

  }

  void testSubmittedBefore(Tester t) {

  }



  void testBigBang(Tester t) {
    int worldWidth = 650;
    int worldHeight = 430;
    double tickRate = 0.2;
    this.cworld.bigBang(worldWidth, worldHeight, tickRate);
  }
}
