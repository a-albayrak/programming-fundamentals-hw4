package presentationlayer;

public class App {

    public static void main(String[] args) {
        TBGame tbGame = new TBGame();
        TBGame.Menu menu = tbGame.new Menu();
        menu.mainMenu();
    }
}