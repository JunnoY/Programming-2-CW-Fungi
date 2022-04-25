package cards;

public class Mushroom extends EdibleItem{
    protected int sticksPerMushroom;

    public Mushroom(CardType cardType, String string){
        super(cardType,string);
        if(cardName.equals("honeyfungus")){
            sticksPerMushroom = 1;
        }
        else if(cardName.equals("treeear")){
            sticksPerMushroom = 2;
        }
        else if(cardName.equals("lawyerswig")){
            sticksPerMushroom = 1;
        }
        else if(cardName.equals("shiitake")){
            sticksPerMushroom = 2;
        }
        else if(cardName.equals("henofwoods")){
            sticksPerMushroom = 1;
        }
        else if(cardName.equals("birchbolete")){
            sticksPerMushroom = 2;
        }
        else if(cardName.equals("porcini")){
            sticksPerMushroom = 3;
        }
        else if(cardName.equals("chanterelle")){
            sticksPerMushroom = 2;
        }
        else if(cardName.equals("morel")){
            sticksPerMushroom = 4;
        }
    }

    public int getSticksPerMushroom(){
        return sticksPerMushroom;
    }
}
