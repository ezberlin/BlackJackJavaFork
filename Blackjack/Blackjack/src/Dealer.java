public class Dealer extends Player {

Dealer() {
    super ("Dealer");
}
public boolean shouldHit(){
    return hand.getTotalHandValue() < 17;
    }
}

