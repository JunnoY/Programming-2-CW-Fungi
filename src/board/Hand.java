package board;
import cards.*;
import java.util.ArrayList;

public class Hand implements Displayable{
    private ArrayList<Card> handList = new ArrayList<>();

    public void add(Card card){
        handList.add(card);
    }

    public int size(){
        return handList.size();
    }

    public Card getElementAt(int index){
        //get method follows internal representation of indices
        return handList.get(index);
    }

    public Card removeElement(int number){
        //remove method follows visual representation of indices
        Card removecard = handList.get(number-1);
        handList.remove(number-1);
        return removecard;
    }
}
