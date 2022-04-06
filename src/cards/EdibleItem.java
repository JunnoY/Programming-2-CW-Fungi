package cards;

public class EdibleItem extends Card{
    protected int flavourPoints;

    public EdibleItem(CardType cardType, String string){
        super(cardType, string);
    }

    public int getFlavourPoints() {
        return flavourPoints;
    }
}
