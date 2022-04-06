package board;
import cards.*;

import java.util.Collections;
import java.util.Stack;

public class CardPile {
    private Stack<Card> cPile;

    public CardPile(){
        cPile = new Stack<Card>();
    }

    public void addCard(Card card){
        cPile.push(card);
    }

    public Card drawCard(){
        Card card = cPile.pop();
        return card;
    }

    public void shufflePile(){
        Collections.shuffle(cPile);
    }

    public int pileSize(){
        return cPile.size();
    }

    public boolean isEmpty(){
        if(cPile.size()==0){
            return true;
        }
        else {
            return false;
        }
    }
}
