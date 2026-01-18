import java.awt.Color;

//represents a world class to animate the Connections game
interface IConnections {
  // Constants for connections world
  int GRID_SIZE = 4;
  int CELL_WIDTH = 150;
  int CELL_HEIGHT = 75;
  int MESSAGE_HEIGHT = 40;
  int MESSAGE_POSN = 25;
  int SPACING = 10;
  int SCREEN_WIDTH = 650;
  int SCREEN_HEIGHT = 440;
  int BUTTON_HEIGHT = 30;
  Color YELLOW = new Color(255, 219, 88);
  Color GREEN = new Color(80, 200, 120);
  Color BLUE = new Color(70, 143, 234);
  Color PURPLE = new Color(178, 114, 224);
}