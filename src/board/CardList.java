package board;
import cards.*;
import java.util.ArrayList;

public class CardList {
    private ArrayList<Card> cList;

    public CardList(){
        cList = new ArrayList<Card>();
    }

    public void add(Card card){
        //adding cards to the arraylist in the direction of left to right
        //always add the newest card at the most left position and all the other cards will shift size
        cList.add(0,card);
    }

    public int size(){
        return cList.size();
    }

    public Card getElementAt(int index){
        return cList.get(index);
    }

    public Card removeCardAt(int visual_index){
        //'number' is the visual representation of indices in forest, which starts from 1, from right to left
        //so we use c.list.size()-number to get the real index of the item in the array
        Card removecard = cList.get(cList.size()-visual_index);
        cList.remove(cList.size()-visual_index);
        //so we know the card that's been removed
        return removecard;
    }
}
