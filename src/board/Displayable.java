package board;
import cards.*;
import java.util.ArrayList;

public interface Displayable {
//An interface is a completely "abstract class" that is used to group related methods with empty bodies
    public void add(Card card);
    public int size();
    public Card getElementAt(int number);
    public Card removeElement(int number);
}
