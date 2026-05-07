
public class Game {
    
    Game() {
        startGame();
    }
    
    Deck cards = new Deck();
    public void startGame(){
        System.out.println("Before:");
        System.out.println(cards.cards); // test
        cards.shuffledeck();
        System.out.println("After");
        System.out.println(cards.cards); //test


    }
}
