import java.util.ArrayList;

public class Hand {
 ArrayList<Card> handCards;
 Hand(){
    handCards = new ArrayList<Card>();
 }
 public void pullCard(Card card){
    handCards.add(card);
 }
 public int getTotalHandValue(){
    int totalValue = 0;
    for (int i = 0; i < handCards.size(); i++){
        Card cardInHand = handCards.get(i);
        totalValue += cardInHand.getValue();
        
    }
    return totalValue;
 }
 public boolean isBust(){
    return getTotalHandValue() > 21;
 }
}
