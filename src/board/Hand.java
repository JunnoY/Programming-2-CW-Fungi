package board;
import cards.*;
import java.util.ArrayList;

public class Hand implements Displayable{
    private ArrayList<Card> handList = new ArrayList<Card>();

    public void add(Card card){
        this.handList.add(card);
    }

    public int size(){
        return handList.size();
    }

    public Card getElementAt(int index){
        //get method follows internal representation of indices
        return handList.get(index);
    }

    public Card removeElement(int index){
        Card removecard = handList.get(index);
        handList.remove(index);
        return removecard;
    }
}
