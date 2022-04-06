package board;
import java.util.ArrayList;
import cards.*;

public class Board {
    private static CardPile forestCardsPile;
    private static CardList forest;
    private static ArrayList<Card> decayPile;

    public static void initialisePiles(){
        forestCardsPile = new CardPile();
        forest = new CardList();
        decayPile = new ArrayList<Card>();
    }

    public static void setUpCards(){
        for(int i=0; i<5;i++){
            forestCardsPile.addCard(new Basket());
        }
        //there are 11 pan cards in the forest card pile, 2 pans go automatically to the display
        for(int i=0; i<11;i++){
            forestCardsPile.addCard(new Pan());
        }

        for(int i=0; i<3;i++){
            forestCardsPile.addCard(new Butter());
            forestCardsPile.addCard(new Cider());
        }
        for(int i=0; i<10;i++){
            forestCardsPile.addCard(new HoneyFungus(CardType.DAYMUSHROOM));
        }
        forestCardsPile.addCard(new HoneyFungus(CardType.NIGHTMUSHROOM));
        for(int i=0; i<8;i++){
            forestCardsPile.addCard(new TreeEar(CardType.DAYMUSHROOM));
        }
        forestCardsPile.addCard(new TreeEar(CardType.NIGHTMUSHROOM));

        for(int i=0; i<6;i++){
            forestCardsPile.addCard(new LawyersWig(CardType.DAYMUSHROOM));
        }
        forestCardsPile.addCard(new LawyersWig(CardType.NIGHTMUSHROOM));

        for(int i=0; i<5;i++){
            forestCardsPile.addCard(new Shiitake(CardType.DAYMUSHROOM));
            forestCardsPile.addCard(new HenOfWoods(CardType.DAYMUSHROOM));
        }
        forestCardsPile.addCard(new Shiitake(CardType.NIGHTMUSHROOM));
        forestCardsPile.addCard(new HenOfWoods(CardType.NIGHTMUSHROOM));

        for(int i=0; i<4;i++){
            forestCardsPile.addCard(new BirchBolete(CardType.DAYMUSHROOM));
            forestCardsPile.addCard(new Porcini(CardType.DAYMUSHROOM));
            forestCardsPile.addCard(new Chanterelle(CardType.DAYMUSHROOM));
        }
        forestCardsPile.addCard(new BirchBolete(CardType.NIGHTMUSHROOM));
        forestCardsPile.addCard(new Porcini(CardType.NIGHTMUSHROOM));
        forestCardsPile.addCard(new Chanterelle(CardType.NIGHTMUSHROOM));

        for(int i=0; i<3;i++) {
            forestCardsPile.addCard(new Morel(CardType.DAYMUSHROOM));
        }
    }

    public static CardPile getForestCardsPile() {
        return forestCardsPile;
    }

    public static CardList getForest() {
        return forest;
    }

    public static ArrayList<Card> getDecayPile() {
        return decayPile;
    }

    public static void updateDecayPile(){
        int lastindex = forest.size()-1;
        //if the decayPile >= 4, we clear the decay pile
        if(decayPile.size()>=4){
            decayPile.clear();
            //'getElementAt()' follows internal indices
            decayPile.add(forest.getElementAt(lastindex));
            //because the visualisation of indices in forest is from 1 to..., from right to left
            //which visually, the most right item in forest is item 1(always),
            //but in reality, in the arraylist, the most right item is the last item in the arraylist
            //which in the method of 'removeCardAt', we define the real index we actually look for
            //is "arrayList.size()-input number"
            forest.removeCardAt(1);
        }
        else {
            decayPile.add(forest.getElementAt(lastindex));
            forest.removeCardAt(1);
        }
    }
}
