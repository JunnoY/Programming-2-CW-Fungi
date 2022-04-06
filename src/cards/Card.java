package cards;
public class Card {
    protected CardType type;
    protected String cardName;

    public Card(CardType inputtype, String inputcardName) {
        type = inputtype;
        cardName = inputcardName;
    }

    public CardType getType(){
        return type;
    }

    public String getName(){
        return cardName;
    }
}
