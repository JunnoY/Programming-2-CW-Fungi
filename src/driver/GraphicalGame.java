package driver;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
// import javafx.scene.layout.BackgroundImage;
// import javafx.scene.layout.BackgroundPosition;
// import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Modality;
import javafx.stage.*;
import board.*;
import cards.*;
import java.util.ArrayList;
import java.io.Console;



public class GraphicalGame extends Application{
    private static Player p1, p2;
    public static Stage MainWindow = new Stage();
    // Boxes in GamePane
    public static VBox decaybox = new VBox();
    public static VBox Playbox = new VBox();
    public static VBox Control_Panel = new VBox();
    public static BorderPane Control_Panel_Border = new BorderPane();
    public static HBox player1box = new HBox();
    public static BorderPane player1border = new BorderPane(); //the border inside the p1 hbox
    public static BorderPane player1statusborder = new BorderPane(); //the border inside the p1 hbox
    public static VBox P1Hand = new VBox(); //vbox for player hand and display
    public static VBox P1Display = new VBox();
    public static HBox player2box = new HBox();
    public static BorderPane player2border = new BorderPane(); //the border inside the p2 hbox
    public static BorderPane player2statusborder = new BorderPane(); //the border inside the p1 hbox
    public static VBox P2Hand = new VBox();
    public static VBox P2Display = new VBox();
    public static HBox forestbox = new HBox();
    public static BorderPane forestborder = new BorderPane(); //the border inside the p2 hbox
    public static BorderPane foreststatusborder = new BorderPane(); //the border inside the p1 hbox

    public static ArrayList<Integer> indexlist=new ArrayList<>();
    private static String card_identity = "";
    private static int action_num = 0;
    static boolean p1plays = true;
    Player currentPlayer;
    Button btn1 = createButtonsinControlPanel("Take one card from the forest");
    Button btn2 = createButtonsinControlPanel("Take all the cards out of the decay pile");
    Button btn3 = createButtonsinControlPanel("Cook three or more identical types of mushrooms");
    Button btn4 = createButtonsinControlPanel("Sell two or more identical types of mushrooms");
    Button btn5 = createButtonsinControlPanel("Put down one pan");
    Button confirmbtn = createButtonsinControlPanel("Confirm");

    public void start(Stage stage){
        FlowPane startPane = createNewFlowPane();
        MainWindow.setWidth(1920);
        MainWindow.setHeight(1080);
        MainWindow.setTitle("Fungi Game");

        Text welcometext = new Text("Welcome to Fungi Game!");
        //can't set font in linux
        welcometext.setFont(Font.font("", FontWeight.BOLD,FontPosture.ITALIC,52));
        welcometext.setFill(Color.valueOf("#2E4053"));
        Button playbtn = createButtons("Play");
        playbtn.setOnAction(event -> {play();});
        Button exitbtn = createButtons("Exit");
        exitbtn.setOnAction(event -> {MainWindow.close();});
        startPane.setVgap(200);
        startPane.setHgap(1920);
        startPane.setAlignment(Pos.CENTER);
        startPane.getChildren().addAll(welcometext,playbtn,exitbtn);
        MainWindow.show();
    }
    public static FlowPane createNewFlowPane(){
        FlowPane Pane = new FlowPane();
        Scene Scene = new Scene(Pane,1920,1080);
        MainWindow.setScene(Scene);
        return Pane;
    }
    public static BorderPane createNewBorderPane(){
        BorderPane Pane = new BorderPane();
        Scene Scene = new Scene(Pane,1920,1080);
        MainWindow.setScene(Scene);
        return Pane;
    }

    public Button createButtons(String button_name){
        Button button = new Button(button_name);
        button.setStyle("-fx-background-color:#2E4053;-fx-font-weight: bold;-fx-text-fill:#FFFFFF;");
        button.setMinWidth(200.0);
        button.setMinHeight(100.0);
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                button.setStyle("-fx-background-color:#1d3652;-fx-font-weight: bold;-fx-text-fill:#FFFFFF;");
            }
        });
        button.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                button.setStyle("-fx-background-color:#2E4053;-fx-font-weight: bold;-fx-text-fill:#FFFFFF;");
            }
        });
        return button;
    }

    public static Button createButtonsinControlPanel(String button_name){
        Button button = new Button(button_name);
        button.setMinHeight(50);
        button.setMinWidth(400);
        button.setStyle("-fx-background-color:#2E4053;-fx-font-weight: bold;-fx-text-fill:#FFFFFF;");
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                button.setStyle("-fx-background-color:#1d3652;-fx-font-weight: bold;-fx-text-fill:#FFFFFF;");
            }
        });
        button.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                button.setStyle("-fx-background-color:#2E4053;-fx-font-weight: bold;-fx-text-fill:#FFFFFF;");
            }
        });
        return button;
    }
    public Text createText(AnchorPane pane, Double topd, Double leftd){
        Text text = new Text();
        AnchorPane.setTopAnchor(text,topd);
        AnchorPane.setLeftAnchor(text,leftd);
        pane.getChildren().add(text);
        return text;
    }

    public static Text createGameText(String textname){
        Text text = new Text(textname);
        text.setFont(Font.font("", FontWeight.BOLD,20));
        text.setTextAlignment(TextAlignment.CENTER);
        return text;
    }

    public void play() {
        BorderPane gameBorderPane = createNewBorderPane();
        //Three big boxes
        //Play box
        Playbox.setPrefSize(1280, 1080);
        Playbox.setStyle("-fx-background-color:#0877c7;");
        BorderPane PlayboxPane = new BorderPane();
        //Control Panel
        Control_Panel.setPrefSize(420, 1080);
        Control_Panel_Border.setPrefSize(240, 1080);
        Control_Panel_Border.setStyle("-fx-background-color:#0877c7;");
        //Decay Box
        decaybox.setPrefSize(400, 1080);
        decaybox.setStyle("-fx-background-color:#FFFFFF;");
        //Inside Play Box
        //Player1 Box
        player1box.setPrefSize(1280, 440);
        player1box.setStyle("-fx-background-color:#EB8634;"); //orange
        player1border.setPrefSize(1280, 400);
        player1statusborder.setPrefSize(1280, 15);
        P1Hand.setPrefSize(780, 400);
        P1Display.setPrefSize(500, 400);
        //Player2 Box
        player2box.setPrefSize(1280, 440);
        player2box.setStyle("-fx-background-color:#48CF0E;"); //green
        player2border.setPrefSize(1280, 400);
        player2statusborder.setPrefSize(1280, 15);
        P2Hand.setPrefSize(780, 400);
        P2Display.setPrefSize(500, 400);
        //forest
        forestbox.setPrefSize(1280, 200);
        forestborder.setPrefSize(1280, 230);
        foreststatusborder.setPrefSize(1280, 20);
        forestbox.setStyle("-fx-background-color:#0877c7;"); //blue
        //Text in boxes
        Text Control_Text = createGameText("Control Panel");
        Text Player1Text = createGameText("Player1");
        Text Player2Text = createGameText("Player2");
        Text ForestText = createGameText("Forest");
        player1statusborder.setLeft(Player1Text);
        player1border.setTop(player1statusborder);
        player1border.setRight(P1Display);
        player1border.setCenter(P1Hand);
        player1box.getChildren().addAll(player1border);
        player2statusborder.setLeft(Player2Text);
        player2border.setTop(player2statusborder);
        player2border.setRight(P2Display);
        player2border.setCenter(P2Hand);
        player2box.getChildren().addAll(player2border);
        foreststatusborder.setLeft(ForestText);
        forestborder.setTop(foreststatusborder);
        forestbox.getChildren().addAll(forestborder);
        //create buttons in control panel
        FlowPane ControlPanelPane = new FlowPane();
        ControlPanelPane.setPrefSize(420, 1060);
        ControlPanelPane.setVgap(30);
        ControlPanelPane.setAlignment(Pos.CENTER);
        ControlPanelPane.getChildren().addAll(btn1, btn2, btn3, btn4, btn5, confirmbtn);
        //add things in the 3 big boxes
        Control_Panel_Border.setTop(Control_Text);
        Control_Panel_Border.setBottom(ControlPanelPane);
        Control_Panel.getChildren().addAll(Control_Panel_Border);
        PlayboxPane.setPrefSize(1280, 1080);
        PlayboxPane.setTop(player1box);
        PlayboxPane.setCenter(forestbox);
        PlayboxPane.setBottom(player2box);
        Playbox.getChildren().addAll(PlayboxPane);
        gameBorderPane.setRight(Control_Panel);
        gameBorderPane.setCenter(Playbox);
        gameBorderPane.setLeft(decaybox);
        Board.initialisePiles();
        Board.setUpCards();
        Board.getForestCardsPile().shufflePile();

        //Populate forest
        p1 = new Player();
        p2 = new Player();
        for (int i = 0; i < 8; i++) {
            Board.getForest().add(Board.getForestCardsPile().drawCard());
        }
        //Initialise players and populate player hands
        p1.addCardtoHand(Board.getForestCardsPile().drawCard());
        p1.addCardtoHand(Board.getForestCardsPile().drawCard());
        p1.addCardtoHand(Board.getForestCardsPile().drawCard());
        p2.addCardtoHand(Board.getForestCardsPile().drawCard());
        p2.addCardtoHand(Board.getForestCardsPile().drawCard());
        p2.addCardtoHand(Board.getForestCardsPile().drawCard());
        displayBoard();
        btn1.setUserData(new Object[]{1});
        btn1.addEventFilter(MouseEvent.MOUSE_CLICKED,actionclickHandler);
        btn2.setUserData(new Object[]{2});
        btn2.addEventFilter(MouseEvent.MOUSE_CLICKED,actionclickHandler);
        btn3.setUserData(new Object[]{3});
        btn3.addEventFilter(MouseEvent.MOUSE_CLICKED,actionclickHandler);
        btn4.setUserData(new Object[]{4});
        btn4.addEventFilter(MouseEvent.MOUSE_CLICKED,actionclickHandler);
        btn5.setUserData(new Object[]{5});
        btn5.addEventFilter(MouseEvent.MOUSE_CLICKED,actionclickHandler);
        confirmbtn.setUserData(new Object[]{"confirm"});
        confirmbtn.addEventFilter(MouseEvent.MOUSE_CLICKED,confirmclickHandler);
        if (p1plays) {
                Text p1turntext = createGameText("Player 1's turn");
                p1turntext.setFont(Font.font("", FontWeight.BOLD, 24));
                Control_Panel_Border.setCenter(p1turntext);
        } else {
            Text p2turntext = createGameText("Player 2's turn");
            p2turntext.setFont(Font.font("", FontWeight.BOLD, 24));
            Control_Panel_Border.setCenter(p2turntext);
        }
        currentPlayer = p1;
    }
    private static ArrayList storeindexofimg(int index){
        indexlist.add(index);
        return indexlist;
    }
    private static String storecard_identity(String input){
        card_identity =input;
        return card_identity;
    }
    private EventHandler<MouseEvent> imageclickHandler = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent e){
            Object obj = e.getSource();
            Object[] param = (Object[]) ((Node)obj).getUserData();
            int index = (int)param[1];
            String card_ident = (String)param[0];
            if(card_ident.equals("p1")&&currentPlayer.equals(p1)){
                storeindexofimg(index);
                storecard_identity(card_ident);
                System.out.println("Card "+index+" clicked");
            }
            else if(card_ident.equals("p2")&&currentPlayer.equals(p2)){
                storeindexofimg(index);
                storecard_identity(card_ident);
                System.out.println("Card "+index+" clicked");
            }
            else if(card_ident.equals("forest")){
                storeindexofimg(index);
                storecard_identity(card_ident);
                System.out.println("Card "+index+" clicked");
            }
            else{
                System.out.println("Wrong selection");
            }

        }
    };
    private EventHandler<MouseEvent> actionclickHandler = e -> {
        indexlist.clear();
        Object obj = e.getSource();
        Object[] param = (Object[]) ((Node)obj).getUserData();
        int index = (int)param[0];
        action_num = index;
        System.out.println("Action "+index+" clicked");
    };
    private EventHandler<MouseEvent> confirmclickHandler = new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent e){
            System.out.println("confirm clicked");
            if (Board.getForest().size() > 0) {
                    Boolean succesfullMove = false;
                    if (action_num==1&&indexlist.size()==1){
                        if(card_identity.equals("forest")) {
                            int position = indexlist.get(0);
                            System.out.println("Position " + position);
                            if (currentPlayer.takeCardFromTheForest(position)) {
                                if (Board.getForestCardsPile().pileSize() > 0) {
                                    Board.getForest().add(Board.getForestCardsPile().drawCard());
                                }
                                succesfullMove = true;
                            }
                        }
                        else{
                            Text errortext = createGameText("Wrong card chosen, try again!");
                            errortext.setFont(Font.font("", FontWeight.BOLD, 18));
                            Control_Panel_Border.setCenter(errortext);
                        }
                    }

                    else if(action_num==2){
                        if (currentPlayer.takeFromDecay()) {
                            succesfullMove = true;
                        }
                        else{
                            Text errortext = createGameText("Action failed, try another action!");
                            errortext.setFont(Font.font("", FontWeight.BOLD, 18));
                            Control_Panel_Border.setCenter(errortext);
                        }
                    }
                    else if(action_num==3){
                        ArrayList<Card> cookingmushrooms = new ArrayList<Card>();
                        if(currentPlayer.equals(p1)&&card_identity.equals("p1")){
                            for (int k = 0; k < indexlist.size(); k++) {
                                cookingmushrooms.add(currentPlayer.getHand().getElementAt(indexlist.get(k)));
                            }
                            if (currentPlayer.cookMushrooms(cookingmushrooms)) {
                                succesfullMove = true;
                            }
                        }
                        else if(currentPlayer.equals(p2)&&card_identity.equals("p2")){
                            for (int k = 0; k < indexlist.size(); k++) {
                                cookingmushrooms.add(currentPlayer.getHand().getElementAt(indexlist.get(k)));
                            }
                            if (currentPlayer.cookMushrooms(cookingmushrooms)) {
                                succesfullMove = true;
                            }
                        }
                        else{
                            Text errortext = createGameText("Wrong card chosen, try again!");
                            errortext.setFont(Font.font("", FontWeight.BOLD, 18));
                            Control_Panel_Border.setCenter(errortext);
                        }

                    }
                    else if(action_num==4){
                        int mushNumber = 0;
                        String mushType = "";
                        for(int i=0;i<1;i++){
                            mushType = currentPlayer.getHand().getElementAt(indexlist.get(i)).getName();
                            mushNumber+=1;
                            for(int j=1;j<indexlist.size();j++){
                                if(mushType.equals(currentPlayer.getHand().getElementAt(indexlist.get(j)).getName())){
                                    mushNumber+=1;
                                }
                                else {
                                    mushNumber=0;
                                    break;
                                }
                            }
                        }
                        if (currentPlayer.sellMushrooms(mushType, mushNumber)) {
                            succesfullMove = true;
                        }
                        else{
                            Text errortext = createGameText("Action failed, try again!");
                            errortext.setFont(Font.font("", FontWeight.BOLD, 18));
                            Control_Panel_Border.setCenter(errortext);
                        }
                    }
                    else if(action_num==5){
                        if (currentPlayer.putPanDown()) {
                            succesfullMove = true;
                        }
                        else{
                            Text errortext = createGameText("Action failed, try again!");
                            errortext.setFont(Font.font("", FontWeight.BOLD, 18));
                            Control_Panel_Border.setCenter(errortext);
                        }
                    }
                    if (succesfullMove) {
                        if (Board.getForest().size() > 0) {
                            Board.updateDecayPile();
                        }
                        if (Board.getForestCardsPile().pileSize() > 0) {
                            if(Board.getForestCardsPile().pileSize()>(8-Board.getForest().size())){
                                while(Board.getForest().size()<8){
                                    Board.getForest().add(Board.getForestCardsPile().drawCard());}
                            }
                            else{
                                for(int i=0; i<Board.getForestCardsPile().pileSize();i++){
                                    Board.getForest().add(Board.getForestCardsPile().drawCard());
                                }
                            }
                        }
                        displayBoard();
                        p1plays = !p1plays;
                        if (p1plays){
                            currentPlayer = p1;
                            Text p1turntext = createGameText("Player 1's turn");
                            p1turntext.setFont(Font.font("", FontWeight.BOLD, 24));
                            Control_Panel_Border.setCenter(p1turntext);}
                        else{
                            currentPlayer = p2;
                            Text p2turntext = createGameText("Player 2's turn");
                            p2turntext.setFont(Font.font("", FontWeight.BOLD, 24));
                            Control_Panel_Border.setCenter(p2turntext);
                        }
                    }
                    else{
                        Text errortext = createGameText("Action failed, try another action!");
                        errortext.setFont(Font.font("", FontWeight.BOLD, 18));
                        Control_Panel_Border.setCenter(errortext);
                    }
            }
            else{
                if (p1.getScore() > p2.getScore()) {
                    System.out.println("Player 1 wins");
                    Text result_text = createGameText("Player 1 wins");
                    result_text.setFont(Font.font("", FontWeight.BOLD, 24));
                    Control_Panel_Border.setCenter(result_text);
                    btn1.setDisable(true);
                    btn2.setDisable(true);
                    btn3.setDisable(true);
                    btn4.setDisable(true);
                    btn5.setDisable(true);
                    confirmbtn.setDisable(true);
                } else if (p2.getScore() > p1.getScore()) {
                    System.out.println("Player 2 wins");
                    Text result_text = createGameText("Player 2 wins");
                    result_text.setFont(Font.font("", FontWeight.BOLD, 24));
                    Control_Panel_Border.setCenter(result_text);
                    btn1.setDisable(true);
                    btn2.setDisable(true);
                    btn3.setDisable(true);
                    btn4.setDisable(true);
                    btn5.setDisable(true);
                    confirmbtn.setDisable(true);
                } else {
                    System.out.println("There was a tie");
                    Text result_text = createGameText("There was a tie");
                    result_text.setFont(Font.font("", FontWeight.BOLD, 24));
                    Control_Panel_Border.setCenter(result_text);
                    btn1.setDisable(true);
                    btn2.setDisable(true);
                    btn3.setDisable(true);
                    btn4.setDisable(true);
                    btn5.setDisable(true);
                    confirmbtn.setDisable(true);
                }
            }
        }
    };
    private void displayBoard(){
        P1Hand.getChildren().clear();
        P1Display.getChildren().clear();
        P2Hand.getChildren().clear();
        P2Display.getChildren().clear();
        decaybox.getChildren().clear();
        displayGameStatus();
        printgraphicDisplayableP1Display(p1.getDisplay());
        printgraphicDisplayableP1Hand(p1.getHand());
        printgraphicForest(Board.getForest());
        printgraphicDisplayableP2Display(p2.getDisplay());
        printgraphicDisplayableP2Hand(p2.getHand());
    }

    private void printgraphicDisplayableP1Hand(Displayable d) {
        Text Hand1 = new Text("Hand");
        P1Hand.getChildren().add(Hand1);
        String identity = "p1";
        FlowPane playercardpane = new FlowPane();
        playercardpane.setPrefSize(680,320);
        playercardpane.setVgap(4);
        playercardpane.setHgap(4);
        ImageView[] playercardsimageview = new ImageView[100];
        for (int i=0; i < d.size() ; i++) {
            BorderPane cardwithinfo = new BorderPane();
            cardwithinfo.setPrefSize(120,130);
            String nameofcard = d.getElementAt(i).getName();
            playercardsimageview[i] = new ImageView(new Image("file:img/"+nameofcard+".jpg",110,120,false,false));
            playercardsimageview[i].setUserData(new Object[]{identity,i});
            playercardsimageview[i].addEventFilter(MouseEvent.MOUSE_CLICKED,imageclickHandler);
//            int index = i+1;
//            Text number = new Text(index+"");
            Text numberandnight = new Text("(N)");
            if (d.getElementAt(i).getType().equals(CardType.NIGHTMUSHROOM)) {
                cardwithinfo.setTop(numberandnight);
                cardwithinfo.setCenter(playercardsimageview[i]);
                playercardpane.getChildren().add(cardwithinfo);		}
            else {
//                cardwithinfo.setTop(number);
                cardwithinfo.setCenter(playercardsimageview[i]);
                playercardpane.getChildren().add(cardwithinfo);
            }
        }
        P1Hand.getChildren().add(playercardpane);
    }
    private void printgraphicDisplayableP2Hand(Displayable d) {
        Text Hand2 = new Text("Hand");
        P2Hand.getChildren().add(Hand2);
        String identity = "p2";
        FlowPane playercardpane = new FlowPane();
        playercardpane.setPrefSize(680,320);
        playercardpane.setVgap(4);
        playercardpane.setHgap(4);
        ImageView[] playercardsimageview = new ImageView[100];
        for (int i=0; i < d.size() ; i++) {
            BorderPane cardwithinfo = new BorderPane();
            cardwithinfo.setPrefSize(120,130);
            String nameofcard = d.getElementAt(i).getName();
            playercardsimageview[i] = new ImageView(new Image("file:img/"+nameofcard+".jpg",110,120,false,false));
            playercardsimageview[i].setUserData(new Object[]{identity,i});
            playercardsimageview[i].addEventFilter(MouseEvent.MOUSE_CLICKED,imageclickHandler);
            //            int index = i+1;
//            Text number = new Text(index+"");
            Text numberandnight = new Text("(N)");
            if (d.getElementAt(i).getType().equals(CardType.NIGHTMUSHROOM)) {
                cardwithinfo.setTop(numberandnight);
                cardwithinfo.setCenter(playercardsimageview[i]);
                playercardpane.getChildren().add(cardwithinfo);		}
            else {
//                cardwithinfo.setTop(number);
                cardwithinfo.setCenter(playercardsimageview[i]);
                playercardpane.getChildren().add(cardwithinfo);
            }
        }
        P2Hand.getChildren().add(playercardpane);
    }
    private void printgraphicDisplayableP1Display(Displayable d) {
        Text Display1 = new Text("Display");
        P1Display.getChildren().add(Display1);
        String identity = "p1";
        FlowPane playercardpane = new FlowPane();
        playercardpane.setPrefSize(600,320);
        playercardpane.setVgap(4);
        playercardpane.setHgap(4);
        ImageView[] playercardsimageview = new ImageView[100];
        for (int i=0; i < d.size() ; i++) {
            BorderPane cardwithinfo = new BorderPane();
            cardwithinfo.setPrefSize(120,130);
            String nameofcard = d.getElementAt(i).getName();
            playercardsimageview[i] = new ImageView(new Image("file:img/"+nameofcard+".jpg",110,120,false,false));
            playercardsimageview[i].setUserData(new Object[]{identity,i});
            playercardsimageview[i].addEventFilter(MouseEvent.MOUSE_CLICKED,imageclickHandler);
            //            int index = i+1;
//            Text number = new Text(index+"");
            Text numberandnight = new Text("(N)");
            if (d.getElementAt(i).getType().equals(CardType.NIGHTMUSHROOM)) {
                cardwithinfo.setTop(numberandnight);
                cardwithinfo.setCenter(playercardsimageview[i]);
                playercardpane.getChildren().add(cardwithinfo);		}
            else {
//                cardwithinfo.setTop(number);
                cardwithinfo.setCenter(playercardsimageview[i]);
                playercardpane.getChildren().add(cardwithinfo);
            }
        }
        P1Display.getChildren().add(playercardpane);
    }
    private void printgraphicDisplayableP2Display(Displayable d) {
        Text Display2 = new Text("Display");
        P2Display.getChildren().add(Display2);
        String identity = "p2";
        FlowPane playercardpane = new FlowPane();
        playercardpane.setPrefSize(600,320);
        playercardpane.setVgap(4);
        playercardpane.setHgap(4);
        ImageView[] playercardsimageview = new ImageView[100];
        for (int i=0; i < d.size() ; i++) {
            BorderPane cardwithinfo = new BorderPane();
            cardwithinfo.setPrefSize(120,130);
            String nameofcard = d.getElementAt(i).getName();
            playercardsimageview[i] = new ImageView(new Image("file:img/"+nameofcard+".jpg",110,120,false,false));
            playercardsimageview[i].setUserData(new Object[]{identity,i});
            playercardsimageview[i].addEventFilter(MouseEvent.MOUSE_CLICKED,imageclickHandler);
            //            int index = i+1;
//            Text number = new Text(index+"");
            Text numberandnight = new Text("(N)");
            if (d.getElementAt(i).getType().equals(CardType.NIGHTMUSHROOM)) {
                cardwithinfo.setTop(numberandnight);
                cardwithinfo.setCenter(playercardsimageview[i]);
                playercardpane.getChildren().add(cardwithinfo);		}
            else {
//                cardwithinfo.setTop(number);
                cardwithinfo.setCenter(playercardsimageview[i]);
                playercardpane.getChildren().add(cardwithinfo);
            }
        }
        P2Display.getChildren().add(playercardpane);
    }

    private void printgraphicForest(CardList cl) {
        String identity = "forest";
        FlowPane forestcardpane = new FlowPane();
        forestcardpane.setPrefSize(1080,320);
        forestcardpane.setVgap(4);
        forestcardpane.setHgap(4);
        ImageView[] forestcardsimageview = new ImageView[100];
        for (int i=0; i < cl.size() ; i++) {
            BorderPane cardwithinfo = new BorderPane();
            cardwithinfo.setPrefSize(120,120);
            String nameofcard = cl.getElementAt(i).getName();
            int visual_index = Board.getForest().size()-i;
            forestcardsimageview[i] = new ImageView(new Image("file:img/"+nameofcard+".jpg",110,120,false,false));
            forestcardsimageview[i].setUserData(new Object[]{identity,visual_index});
            forestcardsimageview[i].addEventFilter(MouseEvent.MOUSE_CLICKED,imageclickHandler);
            Text number = new Text(visual_index+"");
            Text numberandnight = new Text(visual_index +"(N)");
            if (cl.getElementAt(i).getType().equals(CardType.NIGHTMUSHROOM)) {
                cardwithinfo.setTop(numberandnight);
                cardwithinfo.setCenter(forestcardsimageview[i]);
                forestcardpane.getChildren().add(cardwithinfo);		}
            else {
                cardwithinfo.setTop(number);
                cardwithinfo.setCenter(forestcardsimageview[i]);
                forestcardpane.getChildren().add(cardwithinfo);
            }
        }
        forestborder.setCenter(forestcardpane);
    }

    private static void displayGameStatus() {
        //show forestpile amount in text form
        System.out.println("\nThere are "+Board.getForestCardsPile().pileSize()+" cards in the Forest pile");
        Text ForestStatus = createGameText("There are "+Board.getForestCardsPile().pileSize()+" cards in the Forest pile");
        foreststatusborder.setRight(ForestStatus);

        //DecayPile(use flowpane)
        Text DecayText = createGameText("DecayPile");
        FlowPane DecayPile = new FlowPane();
        DecayPile.setPrefSize(400,1060);
        DecayPile.setVgap(4);
        DecayPile.setHgap(4);
        ImageView decaycards[] = new ImageView[4];
        //in terminal version, we dont show the day or night status of card
        System.out.print("Decay pile: ");
        for (Card c : Board.getDecayPile()) {
            System.out.print(c.getName()+"| ");
        }
        for (int i=0;i<Board.getDecayPile().size();i++){
            String nameofcard = Board.getDecayPile().get(i).getName();
            decaycards[i] = new ImageView(new Image("file:img/"+nameofcard+".jpg",110,120,false,false));
            DecayPile.getChildren().add(decaycards[i]);
        }
        decaybox.getChildren().addAll(DecayText,DecayPile);
;
        //print stick and score in Player1 and Player2
        System.out.println("\nPlayer 1: "+p1.getStickNumber()+" sticks, score: "+p1.getScore());
        System.out.println("Player 2: "+p2.getStickNumber()+" sticks, score: "+p2.getScore());
        Text P1Status = createGameText("Stick: "+p1.getStickNumber()+" Score: "+p1.getScore());
        Text P2Status = createGameText("Stick: "+p2.getStickNumber()+" Score: "+p2.getScore());
        player1statusborder.setRight(P1Status);
        player2statusborder.setRight(P2Status);
    }
    public static void main(String[] args){
        launch(args); // launch method is from the application library to launch the applicationll
    }

}
