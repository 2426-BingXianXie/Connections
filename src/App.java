import tester.*;

// Main application class to run the Connections game
public class App {
  public static void main(String[] args) {
    ConnectionsWorld world = new ConnectionsWorld();
    world.bigBang(650, 430, 0.2);
  }
}