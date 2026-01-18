import javalib.worldimages.*;
import javalib.impworld.*;

// represents a game piece
abstract class AGamePiece {
  Posn position;
  int width;
  int height;

  AGamePiece(int width, int height) {
    this.width = width;
    this.height = height;
  }

  // Update this position to the given position
  public void updatePosition(Posn position) {
    this.position = position;
  }

  // Draw this game piece onto the given scene
  public void draw(WorldScene scene) {
    WorldImage content = this.drawContent();
    scene.placeImageXY(content, this.position.x, this.position.y);
  }

  // Check if this game piece is in the range of the given position
  public boolean isClicked(Posn pos) {
    return pos.x <= this.position.x + (this.width / 2)
            && pos.x >= this.position.x - (this.width / 2)
            && pos.y <= this.position.y + (this.height / 2)
            && pos.y >= this.position.y - (this.height / 2);
  }

  // Draw this game piece
  public abstract WorldImage drawContent();
}