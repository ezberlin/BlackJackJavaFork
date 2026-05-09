import java.util.Scanner;

public class Player {
    int bet;
    int playerBalance = 10000;
    Hand hand;
    String name;
    Scanner scanner = new Scanner(System.in);
    Player(String name) {
        this.hand = new Hand();
        this.name = name;
    }

    public void hit(Card card) {
        hand.pullCard(card);
    }
    public void bet(int bet) {
        while (bet > playerBalance){
            System.out.println("You cannot afford that! Try again");
            bet = scanner.nextInt();
        }
        playerBalance -= bet;
        this.bet = bet;
    

}
}
