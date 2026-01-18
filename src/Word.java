import javalib.worldimages.*;
import java.awt.Color;

// represent a word
class Word extends AGamePiece implements IWord {
  String text;
  Category category;
  boolean isSelected;

  Word(String text, Category category) {
    super(CELL_WIDTH, CELL_HEIGHT);
    this.text = text;
    this.category = category;
    this.isSelected = false;
  }

  // Draw this word based on whether it is selected or not
  public WorldImage drawContent() {
    if (this.isSelected) {
      WorldImage base = new RectangleImage(CELL_WIDTH, CELL_HEIGHT, "solid", Color.DARK_GRAY);
      WorldImage text = new TextImage(this.text, TEXT_SIZE, FontStyle.BOLD, Color.WHITE);
      return new OverlayImage(text, base);
    }
    WorldImage base = new RectangleImage(CELL_WIDTH, CELL_HEIGHT, "solid", Color.LIGHT_GRAY);
    WorldImage text = new TextImage(this.text, TEXT_SIZE, FontStyle.BOLD, Color.BLACK);
    return new OverlayImage(text, base);
  }

  // Select this word
  // EFFECT: change the select state to true
  public void selectWord() {
    this.isSelected = true;
  }

  // Deselect this word
  // EFFECT: change the select state to false
  public void deselectWord() {
    this.isSelected = false;
  }
}