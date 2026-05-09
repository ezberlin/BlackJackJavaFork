import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    
    Game() {
        startGame();
        while (players.size() > 0) {
            betTurn();
            dealCards();
            playerTurn();
            dealerTurn();
            determineWinner();
            resetRound();
        }
    }
    Scanner scanner = new Scanner(System.in);

    Deck cards = new Deck();
    Dealer dealer;
    ArrayList<Player> players;
    ArrayList<Player> stoppedPlaying;
   

    public void startGame(){
    this.dealer = new Dealer();
    players = new ArrayList<Player>();
    System.out.println("Would you like to view a rulebook? (true/false)");
    if (scanner.nextBoolean()){
        System.out.println("""
            \n======================================================================

GOAL:
Get as close to 21 as possible without going over, and beat the dealer.

CARD VALUES:
- Number cards (2-10): face value
- Jack(J), Queen(Q), King(K): 10
- Ace(A): 11 (or 1 if hand would be over 21 otherwise)

GAMEPLAY:
1. Each player places a bet
2. Each player and the dealer receive 2 cards
3. One dealer card is hidden, one is shown
4. Players choose: Hit (draw a card) or Stand (keep current hand)
5. The dealer draws until reaching 17 or higher

WINNING:
- Beat the dealer without going over 21 -> win your bet
- Blackjack (Ace + picture card) -> win 1.5x your bet
- Tie with dealer -> get your bet back
- Over 21 or lower than dealer -> lose your bet

BALANCE:
- Starting balance: $10,000
- Players with $0 are removed from the game

======================================================================
""");
    }
    System.out.println(" \n ");
    System.out.println("How many players are there?");
    int numbplayers = scanner.nextInt();
    scanner.nextLine();
    System.out.println();
    for (int i =0; i < numbplayers; i++){
        int j = i + 1;
        System.out.println("Hello player " + j + "! What is your name?");
        Player player = new Player(scanner.nextLine());
        players.add(player);
        System.out.println();
    }

    }
    public void betTurn(){
        for (int i = 0; i < players.size(); i++) {
            System.out.println(players.get(i).name + ", your balance is: " + players.get(i).playerBalance + "$ Place your bet.");
            players.get(i).bet(scanner.nextInt());
            System.out.println();
        }
    }
    public void dealCards(){
        cards.shuffledeck();
        for (int i = 0; i < players.size(); i++) {
            Card card1 = cards.deal();
            players.get(i).hit(card1);
            Card card2 = cards.deal();
            players.get(i).hit(card2);
        }
        Card hiddeCard = cards.deal();
        dealer.hit(hiddeCard);
        Card seconCard = cards.deal();
        dealer.hit(seconCard);
        System.out.println();
        System.out.println("The Dealers public card is:" + seconCard +"\n");
    }
    public void playerTurn(){
        for (int i = 0; i < players.size(); i++){
            System.out.println(players.get(i).name + ", your cards are: " + players.get(i).hand.handCards.get(0) + " and " +players.get(i).hand.handCards.get(1));
            System.out.println("Your Hand Value is: " + players.get(i).hand.getTotalHandValue()+"€");
            boolean hasStood = false;   

            while (!hasStood && !players.get(i).hand.isBust()){
                System.out.println("Do you want to hit? (true/false)");
                boolean hasHit = scanner.nextBoolean();

                if(hasHit){
                    Card card = cards.deal();
                    players.get(i).hit(card);
                    System.out.println();
                    System.out.print(players.get(i).name + ", your cards now are: ");

                    for (int j = 0; j < players.get(i).hand.handCards.size(); j++){
                        System.out.print(players.get(i).hand.handCards.get(j) +" ");
                    }
                    System.out.println();
                    System.out.println("Your Hand Value now is: " + players.get(i).hand.getTotalHandValue());
                    
                }   
                else{
                    hasStood = true;
                    System.out.println("Your turn has finished\n");

                }
                if (players.get(i).hand.isBust()) {
                    System.out.println("Your turn has finished\n");
                }
            }
        }
    }
    public void dealerTurn(){
        System.out.println("The dealers Cards are: " + dealer.hand.handCards.get(0) + " and " + dealer.hand.handCards.get(1) );
        System.out.println("The dealers handvalue is " + dealer.hand.getTotalHandValue());
        while (dealer.shouldHit()) {
            Card card = cards.deal();
            dealer.hit(card);
            System.out.println("The dealer pulled a " + card);
            System.out.println("The dealers handvalue now is: " + dealer.hand.getTotalHandValue());
        }
        System.out.println();
    }
    public void determineWinner(){
        stoppedPlaying = new ArrayList<Player>();
        for (int i =0; i < players.size(); i++){
            if (players.get(i).hand.getTotalHandValue() > 21){
                System.out.println(players.get(i).name + ", you lose, since your hand is worth over 21");
            }
            else if(dealer.hand.getTotalHandValue() > 21){
                System.out.println(players.get(i).name + ", you win " + players.get(i).bet +"$");
                players.get(i).playerBalance += players.get(i).bet*2;
            }
            else if(dealer.hand.getTotalHandValue() > players.get(i).hand.getTotalHandValue()){
                System.out.println(players.get(i).name + ", you lose, since the dealer is closer to 21 than you are");
            }
            else if(players.get(i).hand.getTotalHandValue() == 21 && players.get(i).hand.handCards.size() == 2){
                System.out.println("You got a BlackJack! You win "+ players.get(i).bet*1.5 + "$");
                players.get(i).playerBalance += (int) (players.get(i).bet * 2.5);
            }
            else if(dealer.hand.getTotalHandValue() < players.get(i).hand.getTotalHandValue()){
                System.out.println(players.get(i).name + ", you win " + players.get(i).bet+"$");
                players.get(i).playerBalance += players.get(i).bet * 2;
            }
            else{
                System.out.println(players.get(i).name+", you tied the dealer");
                players.get(i).playerBalance += players.get(i).bet;
            }
            System.out.println("Your balance is now "+ players.get(i).playerBalance+ "$");
            
            if (players.get(i).playerBalance <1){
                System.out.println("You've ran out of money. Thanks for playing!\n");
                stoppedPlaying.add(players.get(i));
                continue;
            }     
            System.out.println("Do you want to continue playing? (true/false)"); 
            boolean continuePlaying = scanner.nextBoolean();
            if (!continuePlaying){
                System.out.println("Thanks for playing!\n");
                stoppedPlaying.add(players.get(i));
            }
            else{
                System.out.println();
            }
        }
    }
    public void resetRound(){
        players.removeAll(stoppedPlaying);
        for (int i = 0; i < players.size(); i++){
            players.get(i).bet = 0;
            players.get(i).hand.handCards.clear();
        }
        dealer.hand.handCards.clear();
        cards = new Deck();
    }
}



