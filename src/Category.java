import javalib.worldimages.*;
import java.awt.Color;
import java.util.ArrayList;

// represent a category
class Category extends AGamePiece implements ICategory {
  String label;
  Color color;
  ArrayList<String> words;

  Category(Color color, String label,  ArrayList<String> words) {
    super(CELL_WIDTH, CELL_HEIGHT);
    this.label = label;
    this.color = color;
    this.words = new ArrayList<String>(words);
  }

  // Determine if the given list of selected words has the same text as this words list
  public boolean isCorrectGroup(ArrayList<Word> selectedWords) {
    int containNum = 0;
    for (Word selected : selectedWords) {
      if (this.words.contains(selected.text)) {
        containNum++;
      }
    }
    return containNum == 4;
  }

  // Turn this list of words to one string
  public String listToString() {
    String answer = "";
    for (int i = 0; i < this.words.size(); i++) {
      if (i == 3) {
        answer = answer + this.words.get(i);
      }
      else {
        answer = answer + this.words.get(i) + ", ";
      }
    }
    return answer;
  }

  // Draw this category 
  public WorldImage drawContent() {
    WorldImage base = new RectangleImage(CELL_WIDTH, CELL_HEIGHT, "solid", this.color);
    WorldImage labelText = new TextImage(this.label, FONT_SIZE, FontStyle.BOLD, Color.BLACK);
    WorldImage wordText = new TextImage(this.listToString(), FONT_SIZE, Color.BLACK);
    return new OverlayOffsetImage(wordText, 0, -10,
            new OverlayOffsetImage(labelText, 0, 10, base));
  }
}