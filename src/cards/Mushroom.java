package cards;

public class Mushroom extends EdibleItem{
    protected int sticksPerMushroom;

    public Mushroom(CardType cardType, String string){
        super(cardType,string);
    }

    public int getSticksPerMushroom(){
        return sticksPerMushroom;
    }
}
