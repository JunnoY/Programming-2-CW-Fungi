package cards;

public class EdibleItem extends Card{
    protected int flavourPoints;

    public EdibleItem(CardType cardType, String cardName){
        super(cardType, cardName);
        if(type==CardType.DAYMUSHROOM){
            if(cardName.equals("honeyfungus")){
                flavourPoints = 1;
            }
            else if(cardName.equals("treeear")){
                flavourPoints = 1;
            }
            else if(cardName.equals("lawyerswig")){
                flavourPoints = 2;
            }
            else if(cardName.equals("shiitake")){
                flavourPoints = 2;
            }
            else if(cardName.equals("henofwoods")){
                flavourPoints = 3;
            }
            else if(cardName.equals("birchbolete")){
                flavourPoints = 3;
            }
            else if(cardName.equals("porcini")){
                flavourPoints = 3;
            }
            else if(cardName.equals("chanterelle")){
                flavourPoints = 4;
            }
            else if(cardName.equals("morel")){
                flavourPoints = 6;
            }
            
        }
        else if(type==CardType.NIGHTMUSHROOM){
            if(cardName.equals("honeyfungus")){
                flavourPoints = 1;
            }
            else if(cardName.equals("treeear")){
                flavourPoints = 1;
            }
            else if(cardName.equals("lawyerswig")){
                flavourPoints = 2;
            }
            else if(cardName.equals("shiitake")){
                flavourPoints = 2;
            }
            else if(cardName.equals("henofwoods")){
                flavourPoints = 3;
            }
            else if(cardName.equals("birchbolete")){
                flavourPoints = 3;
            }
            else if(cardName.equals("porcini")){
                flavourPoints = 3;
            }
            else if(cardName.equals("chanterelle")){
                flavourPoints = 4;
            }
            else if(cardName.equals("morel")){
                flavourPoints = 6;
            }

        }
        else if(type==CardType.BUTTER){
            
            flavourPoints = 3;      
        }
        else if(type==CardType.CIDER){
            flavourPoints = 5;
        }
    }

    public int getFlavourPoints() {
        return flavourPoints;
    }
}
