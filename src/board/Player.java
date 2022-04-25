package board;
import cards.*;
import java.util.ArrayList;

public class Player {
    private Hand h;
    private Display d;
    private int score, handlimit, sticks;

    public Player(){
        h = new Hand();
        d = new Display();
        d.add(new Pan());
        d.add(new Pan());
        score = 0;
        handlimit = 8;
        sticks = 0;
    }

    public int getScore() {
        return score;
    }

    public int getHandLimit() {
        return handlimit;
    }

    public int getStickNumber() {
        return sticks;
    }

    public void addSticks(int number_of_add_stick){
        for(int i=0;i<number_of_add_stick;i++){
            d.add(new Stick());
        }
        sticks = sticks + number_of_add_stick;
    }

    public void removeSticks(int number_of_remove_sticks){
         for(int i=0;i<number_of_remove_sticks;i++){
                    for(int j=0;j<d.size();j++){
                        if(d.getElementAt(j).getType()==CardType.STICK){
                            d.removeElement(j);
                            break;
                        }
                    }
                }
        sticks = sticks-number_of_remove_sticks;
    }

    public Hand getHand(){
        return h;
    }

    public Display getDisplay(){
        return d;
    }

    public void addCardtoHand(Card card){
        //handlimit is the greatest amount of cards in hand that i could have, it is not the space left in hand
        //we should not comapre handlimit with 0, instead we compare handlimit with hand.size()
        if(card.getType()!=CardType.BASKET){
            h.add(card);
        }
        else if(card.getType()==CardType.BASKET){
            d.add(card);
            handlimit = handlimit+2;
        }
        
    }
    public void addCardtoDisplay(Card card){
        d.add(card);  
    }
    
    public boolean takeCardFromTheForest(int visual_index){
        boolean success = false;
        if(h.size()<handlimit&&visual_index>=1&&visual_index<=8) {
            if (visual_index ==1|visual_index==2){
                Card card = Board.getForest().getElementAt(Board.getForest().size() - visual_index);
                if (card.getType() == CardType.BASKET) {
                    addCardtoDisplay(card);
                    handlimit = handlimit+2;
                    success = true;
                }
                else {
                    addCardtoHand(card);
                    success = true;
                }
            }
            else if (visual_index !=1&&visual_index!=2&&sticks >= (visual_index - 2)) {
                //check which card are we getting
                Card card = Board.getForest().getElementAt(Board.getForest().size() - visual_index);
                //remove a number of sticks cards in display to get a card in deep forest
                //visual_index-2 is the number of sticks we need to remove
                removeSticks(visual_index-2);
               
                //if we need the 3rd item start from the right, we need 1 stick, the first two don't need stick
                if (card.getType() == CardType.BASKET) {
                    addCardtoDisplay(card);
                    handlimit = handlimit+2; //having a basket card will increase handlimit by 2
                    success = true;
                }
                else {
                    addCardtoHand(card);
                    success = true;
                }
            }
        }
        return success;
    }

    public boolean takeFromDecay(){
        boolean success = false;
        int number_of_basket = 0;
        for(int i=0; i<Board.getDecayPile().size();i++){
            if(Board.getDecayPile().get(i).getType()==CardType.BASKET){
                number_of_basket +=1;
            }
        }
        //we add the handlimit first because we know there is basket in decaypile but it might not be the first one so we add the handlimit first which even 
        //the basket is not the first one we can still add the other card first
        handlimit = handlimit + 2*number_of_basket;
        //taking all the cards from decaypile
        if(Board.getDecayPile().size()+h.size()-number_of_basket<=handlimit){
            for(int i=0; i<Board.getDecayPile().size();i++){
                if(Board.getDecayPile().get(i).getType()==CardType.BASKET){
                    addCardtoDisplay(Board.getDecayPile().get(i));
                }
                else{
                    addCardtoHand(Board.getDecayPile().get(i));
                }
            }
            Board.getDecayPile().clear();
            success = true;
        }
        return success;
    }

    public boolean cookMushrooms(ArrayList<Card> cardsList) {
        EdibleItem Butter = new EdibleItem(CardType.BUTTER, "butter");
        EdibleItem Cider = new EdibleItem(CardType.CIDER, "cider");
        boolean success = false;
        int number_pan_in_list = 0;
        int number_pan_in_display = 0;
        boolean no_non_edible_card = true;
        boolean all_mushroom_are_same_type = true;

        //check if there is non_edible_card:
        for (int i = 0; i < cardsList.size(); i++) {
            if (cardsList.get(i).getType() == CardType.STICK|cardsList.get(i).getType() == CardType.BASKET) {
                no_non_edible_card = false;
            }
        }
        for (int i = 0; i < cardsList.size(); i++) {
            if (cardsList.get(i).getType() == CardType.PAN) {
                number_pan_in_list += 1;
            }
        }
        
        for (int i = 0; i < d.size(); i++) {
            if (d.getElementAt(i).getType() == CardType.PAN) {
                number_pan_in_display += 1;
            }
        }
        //the player has indicated specific cards from his hand, which the cards that he indicates should be identical
        //if you choose from hand, you should only choose ONE PAN
        //the process can only go on if there is no non edible card
    
        if ((number_pan_in_list == 1 | number_pan_in_display > 1)&&no_non_edible_card) {
            int identical_mushrooms = 0;
            int butter = 0;
            int cider = 0;
            int first_mushroom_index = -999;
            

            //checking if ALL conditions are met
            outer:for (int i = 0; i < cardsList.size(); i++) {
                // although I add the first item to identical mushroom before comparing it to other items
                //as if it doesnt match with any other item, identical mushroom will be less than 3 so still wont work
                if (cardsList.get(i).getType() == CardType.NIGHTMUSHROOM) {
                    identical_mushrooms = identical_mushrooms + 2;
                }
                else if (cardsList.get(i).getType() == CardType.DAYMUSHROOM) {
                    identical_mushrooms = identical_mushrooms + 1;
                }
                else if (cardsList.get(i).getType() == CardType.BUTTER) {
                    butter += 1;
                    continue;
                } else if (cardsList.get(i).getType() == CardType.CIDER) {
                    cider += 1;
                    continue;
                } else if (cardsList.get(i).getType() == CardType.PAN) {
                    continue;//jump to the outer for loop
                }

                for (int j = i+1; j < cardsList.size(); j++) {
                    if (cardsList.get(j).getType() == CardType.NIGHTMUSHROOM) {
                        if (cardsList.get(i).getName().equals(cardsList.get(j).getName())) {
                            identical_mushrooms = identical_mushrooms + 2;
                            first_mushroom_index = i;
                        }
                        else{
                            all_mushroom_are_same_type = false;
                            break outer;
                        }
                    } else if (cardsList.get(j).getType() == CardType.DAYMUSHROOM) {
                        if (cardsList.get(i).getName().equals(cardsList.get(j).getName())) {
                            identical_mushrooms = identical_mushrooms + 1;
                            first_mushroom_index = i;
                        }
                        else{
                            all_mushroom_are_same_type = false;
                            break outer;
                        }
                    } else if (cardsList.get(j).getType() == CardType.BUTTER) {
                        butter += 1;
                    } else if (cardsList.get(j).getType() == CardType.CIDER) {
                        cider += 1;
                    }
                    
                }
                break;//we only got here when we finish comparing the first mushroom with the rest of the card
                // //comparison only happens one round, so the number of identical mushrooms wont have repetition
            }
        

            //CALCULATE SCORE AND REMOVE CARDS

            //IF USE BUTTER AND CIDER
            //if there is pan in list, remove ONE pan in hand(ONLY ONE!!!)
            if (number_pan_in_list == 1&&all_mushroom_are_same_type) {
                for (int i = 0; i < h.size(); i++) {
                    if (h.getElementAt(i).getType() == CardType.PAN) {
                        h.removeElement(i);
                        break;
                    }
                }
                if (cider == 0 && butter > 0 && identical_mushrooms >= 4) {
                    // can have two butter
                    if (identical_mushrooms == 8&&identical_mushrooms<12 && butter == 2) {
                        EdibleItem mushroom = new EdibleItem(cardsList.get(first_mushroom_index).getType(), cardsList.get(first_mushroom_index).getName());
                        score = score + identical_mushrooms * mushroom.getFlavourPoints() + 2 * Butter.getFlavourPoints();
                        success = true;

                        //remove butter and rest of the mushrooms
                        for (int i = 0; i < butter; i++) {
                            for (int j = 0; j < h.size(); j++) {
                                if (h.getElementAt(j).getType() == CardType.BUTTER) {
                                    h.removeElement(j);
                                    break;
                                }
                            }
                        }
                        for (int i = 0; i < identical_mushrooms; i++) {
                            for (int j = 0; j < h.size(); j++) {
                                if (h.getElementAt(j).getName() == mushroom.getName()) {
                                    h.removeElement(j);
                                    break;
                                }
                            }
                        }
                    }
                    //else only one butter flavourpoint
                    else if (butter == 1 && identical_mushrooms >= 4&&identical_mushrooms<8) {
                        EdibleItem mushroom = new EdibleItem(cardsList.get(first_mushroom_index).getType(), cardsList.get(first_mushroom_index).getName());
                        score = score + identical_mushrooms * mushroom.getFlavourPoints() + Butter.getFlavourPoints();
                        success = true;

                        //remove butter and rest of the mushrooms
                        for (int i = 0; i < butter; i++) {
                            for (int j = 0; j < h.size(); j++) {
                                if (h.getElementAt(j).getType() == CardType.BUTTER) {
                                    h.removeElement(j);
                                    break;
                                }
                            }
                        }
                        for (int i = 0; i < identical_mushrooms; i++) {
                            for (int j = 0; j < h.size(); j++) {
                                if (h.getElementAt(j).getName() == mushroom.getName()) {
                                    h.removeElement(j);
                                    break;
                                }
                            }
                        }

                    }
                } else if (cider > 0 && identical_mushrooms >= 5) {
                    //every 5 cards can have one cider(not both cider and butter)
                    if (identical_mushrooms >=5&&identical_mushrooms<10 && cider == 1 && butter == 0) {
                        EdibleItem mushroom = new EdibleItem(cardsList.get(first_mushroom_index).getType(), cardsList.get(first_mushroom_index).getName());
                        score = score + identical_mushrooms * mushroom.getFlavourPoints() + Cider.getFlavourPoints();
                        success = true;

                        //remove cider and rest of the mushrooms
                        for (int i = 0; i < cider; i++) {
                            for (int j = 0; j < h.size(); j++) {
                                if (h.getElementAt(j).getType() == CardType.CIDER) {
                                    h.removeElement(j);
                                    break;
                                }
                            }
                        }
                        for (int i = 0; i < identical_mushrooms; i++) {
                            for (int j = 0; j < h.size(); j++) {
                                if (h.getElementAt(j).getName() == mushroom.getName()) {
                                    h.removeElement(j);
                                    break;
                                }
                            }
                        }
                    }

                    //in this case can have one cider and one butter
                    else if (identical_mushrooms >= 9&&identical_mushrooms<13 && butter == 1 && cider == 1) {
                        EdibleItem mushroom = new EdibleItem(cardsList.get(first_mushroom_index).getType(), cardsList.get(first_mushroom_index).getName());
                        score = score + identical_mushrooms * mushroom.getFlavourPoints() + Butter.getFlavourPoints() + Cider.getFlavourPoints();
                        success = true;

                        //remove butter, cider and rest of the mushrooms
                        for (int i = 0; i < butter; i++) {
                            for (int j = 0; j < h.size(); j++) {
                                if (h.getElementAt(j).getType() == CardType.BUTTER) {
                                    h.removeElement(j);
                                    break;
                                }
                            }
                        }
                        for (int i = 0; i < cider; i++) {
                            for (int j = 0; j < h.size(); j++) {
                                if (h.getElementAt(j).getType() == CardType.CIDER) {
                                    h.removeElement(j);
                                    break;
                                }
                            }
                        }
                        for (int i = 0; i < identical_mushrooms; i++) {
                            for (int j = 0; j < h.size(); j++) {
                                if (h.getElementAt(j).getName() == mushroom.getName()) {
                                    h.removeElement(j);
                                    break;
                                }
                            }
                        }
                    }
                    //in this case can have two cider
                    else if (identical_mushrooms >= 10&&identical_mushrooms<15 && cider == 2 && butter == 0) {
                        EdibleItem mushroom = new EdibleItem(cardsList.get(first_mushroom_index).getType(), cardsList.get(first_mushroom_index).getName());
                        score = score + identical_mushrooms * mushroom.getFlavourPoints() + 2 * Cider.getFlavourPoints();
                        success = true;

                        //remove cider and rest of the mushrooms
                        for (int i = 0; i < cider; i++) {
                            for (int j = 0; j < h.size(); j++) {
                                if (h.getElementAt(j).getType() == CardType.CIDER) {
                                    h.removeElement(j);
                                    break;
                                }
                            }
                        }
                        for (int i = 0; i < identical_mushrooms; i++) {
                            for (int j = 0; j < h.size(); j++) {
                                if (h.getElementAt(j).getName() == mushroom.getName()) {
                                    h.removeElement(j);
                                    break;
                                }
                            }
                        }
                    }

                }
                //IF DONT USE BUTTER AND CIDER
                else if (identical_mushrooms >= 3&&cider==0&&butter==0) {
                    EdibleItem mushroom = new EdibleItem(cardsList.get(first_mushroom_index).getType(), cardsList.get(first_mushroom_index).getName());
                    score = score + identical_mushrooms * mushroom.getFlavourPoints();
                    success = true;

                    //remove the mushrooms
                    for (int i = 0; i < identical_mushrooms; i++) {
                        for (int j = 0; j < h.size(); j++) {
                            if (h.getElementAt(j).getName() == mushroom.getName()) {
                                h.removeElement(j);
                                break;
                            }
                        }
                    }
                }
            }

            //when there is no pan in list which there is no pan in hand or it is not used, then we remove one pan in display
            //pan in display needs to >1 because the first one is the default one
            if (number_pan_in_list == 0 && number_pan_in_display > 1&&all_mushroom_are_same_type) {
                for (int i = 0; i < d.size(); i++) {
                    if (d.getElementAt(i).getType() == CardType.PAN) {
                        d.removeElement(i);
                        break;
                    }
                }
                if (cider == 0 && butter > 0 && identical_mushrooms >= 4) {
                    // can have two butter
                    if (identical_mushrooms == 8&&identical_mushrooms<12 && butter == 2) {
                        EdibleItem mushroom = new EdibleItem(cardsList.get(first_mushroom_index).getType(), cardsList.get(first_mushroom_index).getName());
                        score = identical_mushrooms * mushroom.getFlavourPoints() + 2 * Butter.getFlavourPoints();
                        success = true;

                        //remove butter and rest of the mushrooms
                        for (int i = 0; i < butter; i++) {
                            for (int j = 0; j < h.size(); j++) {
                                if (h.getElementAt(j).getType() == CardType.BUTTER) {
                                    h.removeElement(j);
                                    break;
                                }
                            }
                        }
                        for (int i = 0; i < identical_mushrooms; i++) {
                            for (int j = 0; j < h.size(); j++) {
                                if (h.getElementAt(j).getName() == mushroom.getName()) {
                                    h.removeElement(j);
                                    break;
                                }
                            }
                        }
                    }
                    //else only one butter flavourpoint
                    else if (butter == 1 && identical_mushrooms >= 4&&identical_mushrooms<8) {
                        EdibleItem mushroom = new EdibleItem(cardsList.get(first_mushroom_index).getType(), cardsList.get(first_mushroom_index).getName());
                        score = identical_mushrooms * mushroom.getFlavourPoints() + Butter.getFlavourPoints();
                        success = true;

                        //remove butter and rest of the mushrooms
                        for (int i = 0; i < butter; i++) {
                            for (int j = 0; j < h.size(); j++) {
                                if (h.getElementAt(j).getType() == CardType.BUTTER) {
                                    h.removeElement(j);
                                    break;
                                }
                            }
                        }
                        for (int i = 0; i < identical_mushrooms; i++) {
                            for (int j = 0; j < h.size(); j++) {
                                if (h.getElementAt(j).getName() == mushroom.getName()) {
                                    h.removeElement(j);
                                    break;
                                }
                            }
                        }

                    }
                } else if (cider > 0 && identical_mushrooms >= 5) {
                    //every 5 cards can have one cider(not both cider and butter)
                    if (identical_mushrooms >=5&&identical_mushrooms<10 && cider == 1 && butter == 0) {
                        EdibleItem mushroom = new EdibleItem(cardsList.get(first_mushroom_index).getType(), cardsList.get(first_mushroom_index).getName());
                        score = score + identical_mushrooms * mushroom.getFlavourPoints() + Cider.getFlavourPoints();
                        success = true;

                        //remove cider and rest of the mushrooms
                        for (int i = 0; i < cider; i++) {
                            for (int j = 0; j < h.size(); j++) {
                                if (h.getElementAt(j).getType() == CardType.CIDER) {
                                    h.removeElement(j);
                                    break;
                                }
                            }
                        }
                        for (int i = 0; i < identical_mushrooms; i++) {
                            for (int j = 0; j < h.size(); j++) {
                                if (h.getElementAt(j).getName() == mushroom.getName()) {
                                    h.removeElement(j);
                                    break;
                                }
                            }
                        }
                    }


                    //in this case can have one cider and one butter
                    else if (identical_mushrooms >= 9&&identical_mushrooms<13 && butter == 1 && cider == 1) {
                        EdibleItem mushroom = new EdibleItem(cardsList.get(first_mushroom_index).getType(), cardsList.get(first_mushroom_index).getName());
                        score = score + identical_mushrooms * mushroom.getFlavourPoints() + Butter.getFlavourPoints() + Cider.getFlavourPoints();
                        success = true;
                        //remove butter, cider and rest of the mushrooms
                        for (int i = 0; i < butter; i++) {
                            for (int j = 0; j < h.size(); j++) {
                                if (h.getElementAt(j).getType() == CardType.BUTTER) {
                                    h.removeElement(j);
                                    break;
                                }
                            }
                        }
                        for (int i = 0; i < cider; i++) {
                            for (int j = 0; j < h.size(); j++) {
                                if (h.getElementAt(j).getType() == CardType.CIDER) {
                                    h.removeElement(j);
                                    break;
                                }
                            }
                        }
                        for (int i = 0; i < identical_mushrooms; i++) {
                            for (int j = 0; j < h.size(); j++) {
                                if (h.getElementAt(j).getName() == mushroom.getName()) {
                                    h.removeElement(j);
                                    break;
                                }
                            }
                        }
                    }
                    //in this case can have two cider
                    else if (identical_mushrooms >= 10&&identical_mushrooms<15 && cider == 2 && butter == 0) {
                        EdibleItem mushroom = new EdibleItem(cardsList.get(first_mushroom_index).getType(), cardsList.get(first_mushroom_index).getName());
                        score = score + identical_mushrooms * mushroom.getFlavourPoints() + 2 * Cider.getFlavourPoints();
                        success = true;

                        //remove cider and rest of the mushrooms
                        for (int i = 0; i < cider; i++) {
                            for (int j = 0; j < h.size(); j++) {
                                if (h.getElementAt(j).getType() == CardType.CIDER) {
                                    h.removeElement(j);
                                    break;
                                }
                            }
                        }
                        for (int i = 0; i < identical_mushrooms; i++) {
                            for (int j = 0; j < h.size(); j++) {
                                if (h.getElementAt(j).getName() == mushroom.getName()) {
                                    h.removeElement(j);
                                    break;
                                }
                            }
                        }
                    }

                }
                //IF DONT USE BUTTER AND CIDER
                else if (identical_mushrooms >= 3&&cider==0&butter==0) {
                    EdibleItem mushroom = new EdibleItem(cardsList.get(first_mushroom_index).getType(), cardsList.get(first_mushroom_index).getName());
                    score = score + identical_mushrooms * mushroom.getFlavourPoints();
                    success = true;

                    //remove the mushrooms
                    for (int i = 0; i < identical_mushrooms; i++) {
                        for (int j = 0; j < h.size(); j++) {
                            if (h.getElementAt(j).getName() == mushroom.getName()) {
                                h.removeElement(j);
                                break;
                            }
                        }
                    }
                }
            }

        }
        return success;
    }

    public boolean sellMushrooms(String inputstring, int sellingnumber){
        boolean success = false;

        //convert input string to the right format
        String str_after_conversion = inputstring.replaceAll(" ", "");
        str_after_conversion = str_after_conversion.replaceAll("[^A-Za-z0-9]", "").toLowerCase();


        
        int number_of_input_type_mushroom_in_hand = 0;
        int number_of_input_type_day_mushroom =0;
        int number_of_input_type_night_mushroom =0;
        //check how many mushrooms cards that are the type that we want in hand
        for(int i=0; i<h.size();i++){
            if(h.getElementAt(i).getName().equals(str_after_conversion)&&h.getElementAt(i).getType()==CardType.DAYMUSHROOM){
                number_of_input_type_day_mushroom +=1;
                number_of_input_type_mushroom_in_hand+=1;
            }
            else if(h.getElementAt(i).getName().equals(str_after_conversion)&&h.getElementAt(i).getType()==CardType.NIGHTMUSHROOM){
                number_of_input_type_night_mushroom+=1;
                number_of_input_type_mushroom_in_hand+=2;
            }
        }
        // selling process begin
        if(number_of_input_type_mushroom_in_hand>=2&&number_of_input_type_mushroom_in_hand>=sellingnumber){

        if(number_of_input_type_day_mushroom>0&&number_of_input_type_night_mushroom>0&&sellingnumber%2==0) {

            int selling_mushroom_found = 0;
            while (selling_mushroom_found < sellingnumber) {

                if (selling_mushroom_found < sellingnumber && number_of_input_type_night_mushroom > 0) {
                    for (int j = 0; j < h.size(); j++) {
                        if (h.getElementAt(j).getName().equals(str_after_conversion) && h.getElementAt(j).getType() == CardType.NIGHTMUSHROOM) {
                            Mushroom sellmushroom = new Mushroom(h.getElementAt(j).getType(), h.getElementAt(j).getName());
                            sticks = sticks + 2 * sellmushroom.getSticksPerMushroom(); //a nightmushroom = 2 mushroom
                            for (int k = 0; k < 2 * sellmushroom.getSticksPerMushroom(); k++) {
                                d.add(new Stick());
                            }
                            h.removeElement(j);
                            number_of_input_type_mushroom_in_hand = number_of_input_type_mushroom_in_hand - 1;
                            //if it is a nightcard, it represents two mushrooms so sellingnumber will need to increase 2, as each for loop sellingnumber auto increases 1, we increase an addtional 1
                            selling_mushroom_found += 2;
                            if (selling_mushroom_found == sellingnumber) {
                                success = true;
                                break;
                            }

                        }
                    }
                } else if (selling_mushroom_found < sellingnumber && number_of_input_type_night_mushroom == 0) {
                    for (int j = 0; j < h.size(); j++) {
                        if (h.getElementAt(j).getName().equals(str_after_conversion) && h.getElementAt(j).getType() == CardType.DAYMUSHROOM) {
                            Mushroom sellmushroom = new Mushroom(h.getElementAt(j).getType(), h.getElementAt(j).getName());
                            sticks = sticks + sellmushroom.getSticksPerMushroom(); //a daymushroom = 1 mushroom
                            for (int k = 0; k < sellmushroom.getSticksPerMushroom(); k++) {
                                d.add(new Stick());
                            }
                            h.removeElement(j);
                            selling_mushroom_found += 1;
                            if (selling_mushroom_found == sellingnumber) {
                                success = true;
                                break;
                            }
                        }
                    }

                }
            }
        }

        else{
                int selling_mushroom_found = 0;
                while(selling_mushroom_found<sellingnumber){
                    for (int j = 0; j < h.size(); j++) {
                        if(h.getElementAt(j).getName().equals(str_after_conversion)&&h.getElementAt(j).getType()==CardType.DAYMUSHROOM){
                            Mushroom sellmushroom = new Mushroom(h.getElementAt(j).getType(),h.getElementAt(j).getName());
                            sticks = sticks + sellmushroom.getSticksPerMushroom(); //a daymushroom = 1 mushroom
                            for (int k = 0; k <sellmushroom.getSticksPerMushroom(); k++){
                                d.add(new Stick());}
                            h.removeElement(j);
                            selling_mushroom_found+=1;
                            if(selling_mushroom_found==sellingnumber){
                                success = true;
                                break;                   }
                        }
                        else if(h.getElementAt(j).getName().equals(str_after_conversion)&&h.getElementAt(j).getType()==CardType.NIGHTMUSHROOM){
                            Mushroom sellmushroom = new Mushroom(h.getElementAt(j).getType(),h.getElementAt(j).getName());
                            sticks = sticks + 2*sellmushroom.getSticksPerMushroom(); //a nightmushroom = 2 mushroom
                            for (int k = 0; k < 2*sellmushroom.getSticksPerMushroom(); k++){
                                d.add(new Stick());}
                            h.removeElement(j);
                            //if it is a nightcard, it represents two mushrooms so sellingnumber will need to increase 2, as each for loop sellingnumber auto increases 1, we increase an addtional 1
                            selling_mushroom_found+=2;
                            if(selling_mushroom_found==sellingnumber){
                                success = true;
                                break;                   }
                        }

                    }
                }

            }
        }

        return success;
    }

    public boolean putPanDown(){
        boolean success = false;
        int number_pan_in_hand = 0;
        for (int i = 0; i < h.size(); i++) {
            if (h.getElementAt(i).getType() == CardType.PAN) {
                number_pan_in_hand += 1;
            }
        }

        if(number_pan_in_hand>=1){
            for (int i = 0; i < h.size(); i++) {
                if (h.getElementAt(i).getType() == CardType.PAN) {
                    h.removeElement(i);
                    d.add(new Pan());
                    success = true;
                    break; // after one pan is removed from hand, we end this process
                }
            }

        }
        return success;
    }
}
