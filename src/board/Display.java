package board;
import cards.*;
import java.util.ArrayList;

public class Display implements Displayable{
    private ArrayList<Card> displayList = new ArrayList<>();

    public void add(Card card){
        displayList.add(card);
    }

    public int size(){
        return displayList.size();
    }

    public Card getElementAt(int index){
        //get method follows internal representation of indices
        return displayList.get(index);
    }

    public Card removeElement(int index){
        //remove method follows visual representation of indices
        Card removecard = displayList.get(index);
        displayList.remove(index);
        return removecard;
    }
}
