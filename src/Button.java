import javalib.worldimages.*;
import java.awt.Color;

// represent a button
class Button extends AGamePiece implements IButton {
  String label;
  Color textColor;
  Color baseColor;
  String mode;

  Button(String label) {
    super(0, BUTTON_HEIGHT);
    this.label = label;
    this.textColor = Color.BLACK;
    this.baseColor = Color.BLACK;
    this.mode = "outline";
  }

  // Change the button style
  public void buttonStyle(Color textColor, Color baseColor, String mode) {
    this.textColor = textColor;
    this.baseColor = baseColor;
    this.mode = mode;
  }

  // Draw this button onto the given scene
  public WorldImage drawContent() {
    WorldImage text = new TextImage(this.label, TEXT_SIZE, textColor);
    WorldImage base = new RectangleImage(Math.toIntExact(Math.round(text.getWidth()) + SPACING),
            BUTTON_HEIGHT, mode, baseColor);
    this.width = Math.toIntExact(Math.round(base.getWidth()));
    return new OverlayImage(text, base);
  }
}