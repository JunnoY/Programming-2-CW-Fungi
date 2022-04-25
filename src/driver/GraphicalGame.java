package driver;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.stage.Stage;
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

    public void start(Stage stage){
        AnchorPane startPane = createNewAnchorPane();
        MainWindow.setTitle("Fungi Game");

        Text welcometext = createText(startPane,100d,400d);
        welcometext.setText("Welcome to Fungi Game!");
        //can't set font in linux
        welcometext.setFont(Font.font("", FontWeight.BOLD,FontPosture.ITALIC,52));
        welcometext.setFill(Color.valueOf("#2E4053"));
        Button playbtn = createButtons("Play",startPane,200d,600d, 150,60);
        playbtn.setOnAction(event -> {play();});
        Button exitbtn = createButtons("Exit",startPane,300d,600d, 150,60);
        exitbtn.setOnAction(event -> {MainWindow.close();});
        MainWindow.show();
    }
    public static AnchorPane createNewAnchorPane(){
        AnchorPane Pane = new AnchorPane();
        Image backgroundImage = new Image("file:img/vintage_background.png",1920,1080,false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        Pane.setBackground(new Background(background));
        Scene Scene = new Scene(Pane,1920,1080);
        MainWindow.setScene(Scene);
        return Pane;
    }
    public static BorderPane createNewBorderPane(){
        BorderPane Pane = new BorderPane();
        Image backgroundImage = new Image("file:img/vintage_background.png",1920,1080,false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        Pane.setBackground(new Background(background));
        Scene Scene = new Scene(Pane,1920,1080);
        MainWindow.setScene(Scene);
        return Pane;
    }

    public Button createButtons(String button_name, AnchorPane pane, Double topd, Double leftd, int minWidth, int minHeight){
        Button button = new Button(button_name);
        AnchorPane.setTopAnchor(button,topd);
        AnchorPane.setLeftAnchor(button,leftd);
        button.setMinHeight(minHeight);
        button.setMinWidth(minWidth);
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
        pane.getChildren().add(button);
        return button;
    }

    public static Button createButtonsinControlPanel(String button_name){
        Button button = new Button(button_name);
        button.setMinHeight(80);
        button.setMinWidth(80);
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
        text.setFont(Font.font("", FontWeight.BOLD,15));
        text.setTextAlignment(TextAlignment.CENTER);
        return text;
    }

    public static void play() {
        BorderPane gameBorderPane = createNewBorderPane();
        //Three big boxes
        //Play box
        Playbox.setPrefSize(1280, 1080);
        Playbox.setStyle("-fx-background-color:#0877c7;");
        BorderPane PlayboxPane = new BorderPane();
        //Control Panel
        Control_Panel.setPrefSize(240, 1080);
        Control_Panel_Border.setPrefSize(240, 1080);
        Control_Panel_Border.setStyle("-fx-background-color:#0877c7;");
        //Decay Box
        decaybox.setPrefSize(400, 1080);
        decaybox.setStyle("-fx-background-color:#FFFFFF;");

        //Inside Play Box
        //Player1 Box
        player1box.setPrefSize(1280, 320);
        player1box.setStyle("-fx-background-color:#EB8634;"); //orange
        player1border.setPrefSize(1280, 320);
        player1statusborder.setPrefSize(1280, 20);
        P1Hand.setPrefSize(680, 340);
        P1Display.setPrefSize(600, 340);
        //Player2 Box
        player2box.setPrefSize(1280, 320);
        player2box.setStyle("-fx-background-color:#48CF0E;"); //green
        player2border.setPrefSize(1280, 320);
        player2statusborder.setPrefSize(1280, 20);
        P2Hand.setPrefSize(680, 320);
        P2Display.setPrefSize(600, 320);
        //forest
        forestbox.setPrefSize(1280, 380);
        forestborder.setPrefSize(1280, 380);
        foreststatusborder.setPrefSize(1280, 20);
        forestbox.setStyle("-fx-background-color:#0877c7;"); //blue
        //Text in boxes
        Text Control_Text = createGameText("Control Panel");
        Text Player1Text = createGameText("Player1");
        Text Player2Text = createGameText("Player2");
        Text DecayText = createGameText("DecayPile");
        Text ForestText = createGameText("Forest");
        Text Hand1 = new Text("Hand");
        Text Hand2 = new Text("Hand");
        Text Display1 = new Text("Display");
        Text Display2 = new Text("Display");
        P2Hand.getChildren().add(Hand2);
        P1Hand.getChildren().add(Hand1);
        P1Display.getChildren().add(Display1);
        P2Display.getChildren().add(Display2);
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
        ControlPanelPane.setPrefSize(240, 1060); //1080-20
        ControlPanelPane.setVgap(10);
        ControlPanelPane.setHgap(10);
        Button start = createButtonsinControlPanel("START");
        Button btn1 = createButtonsinControlPanel(1 + "");
        Button btn2 = createButtonsinControlPanel(2 + "");
        Button btn3 = createButtonsinControlPanel(3 + "");
        Button btn4 = createButtonsinControlPanel(4 + "");
        Button btn5 = createButtonsinControlPanel(5 + "");
        ControlPanelPane.setAlignment(Pos.CENTER);
        ControlPanelPane.getChildren().addAll(start,btn1, btn2, btn3, btn4, btn5);
        //popup message pane
        HBox messageboxP1 = new HBox();
        messageboxP1.setPrefSize(1280, 320);
        BorderPane messagePaneP1 = new BorderPane();
        messagePaneP1.setPrefSize(1280, 320);
        messageboxP1.setStyle("-fx-background-color:#2E4053;");
        HBox messageboxP2 = new HBox();
        messageboxP2.setPrefSize(1280, 320);
        BorderPane messagePaneP2 = new BorderPane();
        messagePaneP2.setPrefSize(1280, 320);
        messageboxP2.setStyle("-fx-background-color:#2E4053;");
        Button exitmessagebtn1 = new Button("Exit");
        exitmessagebtn1.setStyle("-fx-background-color:#FFFFFF;-fx-font-weight: bold;-fx-text-fill:#2E4053;");
        exitmessagebtn1.setOnAction(event -> {
            PlayboxPane.setBottom(player2box);
        });
        Button exitmessagebtn2 = new Button("Exit");
        exitmessagebtn2.setStyle("-fx-background-color:#FFFFFF;-fx-font-weight: bold;-fx-text-fill:#2E4053;");
        exitmessagebtn2.setOnAction(event -> {
            PlayboxPane.setTop(player1box);
        });
        //create Type in control panel
        VBox Typebox = new VBox();
        Typebox.setPrefSize(240,1080);
        Typebox.setStyle("-fx-background-color:#0877c7;");
        BorderPane TypeBorderPane = new BorderPane();
        TypeBorderPane.setPrefSize(240,1080);
        TextField textfield = new TextField();
        textfield.setPromptText("Enter your choice: ");
        Button typeboxexitbtn = createButtonsinControlPanel("Confirm");
        TypeBorderPane.setCenter(textfield);
        TypeBorderPane.setBottom(typeboxexitbtn);
        //add things in the 3 big boxes
        decaybox.getChildren().addAll(DecayText);
        Control_Panel_Border.setTop(Control_Text);
        Control_Panel_Border.setCenter(ControlPanelPane);
        Control_Panel.getChildren().addAll(Control_Panel_Border);
        PlayboxPane.setPrefSize(1280,1080);
        PlayboxPane.setTop(player1box);
        PlayboxPane.setCenter(forestbox);
        PlayboxPane.setBottom(player2box);
        Playbox.getChildren().addAll(PlayboxPane);
        gameBorderPane.setRight(Control_Panel);
        gameBorderPane.setCenter(Playbox);
        gameBorderPane.setLeft(decaybox);
        //Display board
//        displayBoard();
        start.setOnAction(Event->{
            start.setDisable(true);
            boolean p1plays = true;
            Player currentPlayer;
            Board.initialisePiles();
            Board.setUpCards();
            Board.getForestCardsPile().shufflePile();

            //Populate forest
            for (int i = 0; i < 8; i++) {
                Board.getForest().add(Board.getForestCardsPile().drawCard());
            }
            //Initialise players and populate player hands
            p1 = new Player();
            currentPlayer = p1;
            p2 = new Player();
            p1.addCardtoHand(Board.getForestCardsPile().drawCard());
            p1.addCardtoHand(Board.getForestCardsPile().drawCard());
            p1.addCardtoHand(Board.getForestCardsPile().drawCard());
            p2.addCardtoHand(Board.getForestCardsPile().drawCard());
            p2.addCardtoHand(Board.getForestCardsPile().drawCard());
            p2.addCardtoHand(Board.getForestCardsPile().drawCard());
            displayBoard();
            boolean messageboxP1set = false;
            boolean messageboxP2set = false;
            btn1.setOnAction(Event1 -> {
                textfield.setText("1");
            });
            btn2.setOnAction(Event1 -> {
                textfield.setText("2");
            });
            btn3.setOnAction(Event1 -> {
                textfield.setText("3");
            });
            btn4.setOnAction(Event1 -> {
                textfield.setText("4");
            });
            btn5.setOnAction(Event1 -> {
                textfield.setText("5");
            });

            while (Board.getForest().size()>0) {
                if (p1plays) {
                    if (!messageboxP1set) {
                        Text message = new Text("Player 1, which of the following actions are you going to do?\n" +
                                "1. Take one card from the forest\n" + "2. Take all the cards out of the decay pile\n" +
                                "3. Cook three or more identical types of mushrooms\n" + "4. Sell two or more identical types of mushrooms\n" +
                                "5. Put down one pan");
                        message.setFont(Font.font("", FontWeight.SEMI_BOLD, 20));
                        message.setFill(Color.valueOf("#FFFFFF"));
                        messagePaneP1.setTop(message);
                        messagePaneP1.setCenter(exitmessagebtn1);
                        messageboxP1.getChildren().add(messagePaneP1);
                        PlayboxPane.setBottom(messagePaneP1);
                        messageboxP1set = true;
                    } else {
                        PlayboxPane.setBottom(messagePaneP1);;
                    }
                }
                else {
                    if (!messageboxP2set) {
                        Text message = new Text("Player 2, which of the following actions are you going to do?\n" +
                                "1. Take one card from the forest\n" + "2. Take all the cards out of the decay pile\n" +
                                "3. Cook three or more identical types of mushrooms\n" + "4. Sell two or more identical types of mushrooms\n" +
                                "5. Put down one pan");
                        message.setFont(Font.font("", FontWeight.SEMI_BOLD, 20));
                        message.setFill(Color.valueOf("#FFFFFF"));
                        messagePaneP2.setTop(message);
                        messagePaneP2.setCenter(exitmessagebtn2);
                        messageboxP2.getChildren().add(messagePaneP2);
                        PlayboxPane.setTop(messagePaneP2);
                        messageboxP2set = true;
                    } else {
                        PlayboxPane.setTop(messagePaneP2);
                    }
                }
                if(btn1.isPressed()|btn2.isPressed()|btn3.isPressed()|btn4.isPressed()|btn5.isPressed()){
                    try {
                        Console keyboard = System.console();
                        int option1 = Integer.parseInt(keyboard.readLine("Enter the number for the action:"));
                        int option = Integer.parseInt(textfield.getText());
                        Boolean succesfullMove = false;
                        switch(option) {
                            case 1:
                                int position = Integer.parseInt(keyboard.readLine("Enter the number of the card you want to take:"));
                                if (currentPlayer.takeCardFromTheForest(position)) {
                                    if (Board.getForestCardsPile().pileSize()>0) {
                                        Board.getForest().add(Board.getForestCardsPile().drawCard());
                                    }
                                    succesfullMove=true;
                                }
                                break;
                            case 2:
                                if (currentPlayer.takeFromDecay()) {
                                    succesfullMove=true;
                                }
                                break;
                            case 3:
                                String cookMush = keyboard.readLine("What ingredients are you cooking? Type the position of the cards in your hand (use commas to separate):");
                                String[] splittedStringOfInts = cookMush.split(",");
                                ArrayList<Card> cookingmushrooms = new ArrayList<Card>();
                                for (int k=0;k<splittedStringOfInts.length;k++) {
                                    int inputInt=Integer.parseInt(splittedStringOfInts[k]);
                                    cookingmushrooms.add(currentPlayer.getHand().getElementAt(inputInt-1));
                                }
                                if (currentPlayer.cookMushrooms(cookingmushrooms)) {
                                    succesfullMove=true;
                                }
                                break;
                            case 4:
                                String mushType = keyboard.readLine("What type of mushrooms are you willing to sell? Type mushroom name:");
                                int mushNumber  = Integer.parseInt(keyboard.readLine("How many are willing to sell? Type number:"));
                                if (currentPlayer.sellMushrooms(mushType,mushNumber)) {
                                    succesfullMove=true;
                                }
                                break;
                            case 5:
                                if (currentPlayer.putPanDown()) {
                                    succesfullMove=true;
                                }
                                break;
                        }
                        String prompt="TRY AGAIN";
                        if (succesfullMove) {
                            if (Board.getForest().size()>0) {
                                Board.updateDecayPile();
                            }
                            if (Board.getForestCardsPile().pileSize()>0) {
                                Board.getForest().add(Board.getForestCardsPile().drawCard());
                            }
                            displayBoard();
                            p1plays=!p1plays;
                            if (p1plays)
                                currentPlayer=p1;
                            else
                                currentPlayer=p2;
                            prompt="NEW MOVE";
                        }
                        System.out.println();
                        displayGameStatus();
                        System.out.println("================================================================================================================");
                        System.out.println("                                         "+prompt+"                                                             ");
                        System.out.println("================================================================================================================");
                    }
                    catch(NumberFormatException e) {
                        System.out.println("This is not the appropriate format. Avoid white spaces and characters when a number is expected.");
                    };};}
            if (p1.getScore() > p2.getScore()) {
                System.out.println("Player 1 wins");
            } else if (p2.getScore() > p1.getScore()) {
                System.out.println("Player 2 wins");
            } else {
                System.out.println("There was a tie");
            }
            });

    }

    private static void displayBoard(){
        displayGameStatus();
        printgraphicDisplayableP1Display(p1.getDisplay());
        printgraphicDisplayableP1Hand(p1.getHand());
        printgraphicForest(Board.getForest());
        printgraphicDisplayableP2Display(p2.getDisplay());
        printgraphicDisplayableP2Hand(p2.getHand());
    }

    private static void printgraphicDisplayableP1Hand(Displayable d) {
        FlowPane playercardpane = new FlowPane();
        playercardpane.setPrefSize(680,320);
        playercardpane.setVgap(4);
        playercardpane.setHgap(4);
        ImageView[] playercardsimageview = new ImageView[100];
        for (int i=0; i < d.size() ; i++) {
            BorderPane cardwithinfo = new BorderPane();
            cardwithinfo.setPrefSize(120,120);
            String nameofcard = d.getElementAt(i).getName();
            playercardsimageview[i] = new ImageView(new Image("file:img/"+nameofcard+".jpg",110,120,false,false));
            int index = i+1;
            Text number = new Text(String.valueOf(index));
            Text numberandnight = new Text(number +"(N)");
            if (d.getElementAt(i).getType().equals(CardType.NIGHTMUSHROOM)) {
                cardwithinfo.setTop(numberandnight);
                cardwithinfo.setCenter(playercardsimageview[i]);
                playercardpane.getChildren().add(cardwithinfo);		}
            else {
                cardwithinfo.setTop(number);
                cardwithinfo.setCenter(playercardsimageview[i]);
                playercardpane.getChildren().add(cardwithinfo);
            }
        }
        P1Hand.getChildren().add(playercardpane);
    }
    private static void printgraphicDisplayableP2Hand(Displayable d) {
        FlowPane playercardpane = new FlowPane();
        playercardpane.setPrefSize(680,320);
        playercardpane.setVgap(4);
        playercardpane.setHgap(4);
        ImageView[] playercardsimageview = new ImageView[100];
        for (int i=0; i < d.size() ; i++) {
            BorderPane cardwithinfo = new BorderPane();
            cardwithinfo.setPrefSize(120,120);
            String nameofcard = d.getElementAt(i).getName();
            playercardsimageview[i] = new ImageView(new Image("file:img/"+nameofcard+".jpg",110,120,false,false));
            int index = i+1;
            Text number = new Text(String.valueOf(index));
            Text numberandnight = new Text(number +"(N)");
            if (d.getElementAt(i).getType().equals(CardType.NIGHTMUSHROOM)) {
                cardwithinfo.setTop(numberandnight);
                cardwithinfo.setCenter(playercardsimageview[i]);
                playercardpane.getChildren().add(cardwithinfo);		}
            else {
                cardwithinfo.setTop(number);
                cardwithinfo.setCenter(playercardsimageview[i]);
                playercardpane.getChildren().add(cardwithinfo);
            }
        }
        P2Hand.getChildren().add(playercardpane);
    }
    private static void printgraphicDisplayableP1Display(Displayable d) {
        FlowPane playercardpane = new FlowPane();
        playercardpane.setPrefSize(600,320);
        playercardpane.setVgap(4);
        playercardpane.setHgap(4);
        ImageView[] playercardsimageview = new ImageView[100];
        for (int i=0; i < d.size() ; i++) {
            BorderPane cardwithinfo = new BorderPane();
            cardwithinfo.setPrefSize(120,120);
            String nameofcard = d.getElementAt(i).getName();
            playercardsimageview[i] = new ImageView(new Image("file:img/"+nameofcard+".jpg",110,120,false,false));
            int index = i+1;
            Text number = new Text(String.valueOf(index));
            Text numberandnight = new Text(number +"(N)");
            if (d.getElementAt(i).getType().equals(CardType.NIGHTMUSHROOM)) {
                cardwithinfo.setTop(numberandnight);
                cardwithinfo.setCenter(playercardsimageview[i]);
                playercardpane.getChildren().add(cardwithinfo);}
            else {
                cardwithinfo.setTop(number);
                cardwithinfo.setCenter(playercardsimageview[i]);
                playercardpane.getChildren().add(cardwithinfo);
            }
        }
        P1Display.getChildren().add(playercardpane);
    }
    private static void printgraphicDisplayableP2Display(Displayable d) {
        FlowPane playercardpane = new FlowPane();
        playercardpane.setPrefSize(600,320);
        playercardpane.setVgap(4);
        playercardpane.setHgap(4);
        ImageView[] playercardsimageview = new ImageView[100];
        for (int i=0; i < d.size() ; i++) {
            BorderPane cardwithinfo = new BorderPane();
            cardwithinfo.setPrefSize(120,120);
            String nameofcard = d.getElementAt(i).getName();
            playercardsimageview[i] = new ImageView(new Image("file:img/"+nameofcard+".jpg",110,120,false,false));
            int index = i+1;
            Text number = new Text(String.valueOf(index));
            Text numberandnight = new Text(number +"(N)");
            if (d.getElementAt(i).getType().equals(CardType.NIGHTMUSHROOM)) {
                cardwithinfo.setTop(numberandnight);
                cardwithinfo.setCenter(playercardsimageview[i]);
                playercardpane.getChildren().add(cardwithinfo);		}
            else {
                cardwithinfo.setTop(number);
                cardwithinfo.setCenter(playercardsimageview[i]);
                playercardpane.getChildren().add(cardwithinfo);
            }
        }
        P2Display.getChildren().add(playercardpane);
    }

    private static void printgraphicForest(CardList cl) {
        FlowPane forestcardpane = new FlowPane();
        forestcardpane.setPrefSize(1080,320);
        forestcardpane.setVgap(4);
        forestcardpane.setHgap(4);
        ImageView[] forestcardsimageview = new ImageView[100];
        for (int i=0; i < cl.size() ; i++) {
            BorderPane cardwithinfo = new BorderPane();
            cardwithinfo.setPrefSize(120,120);
            String nameofcard = cl.getElementAt(i).getName();
            forestcardsimageview[i] = new ImageView(new Image("file:img/"+nameofcard+".jpg",110,120,false,false));
            int index = i+1;
            Text number = new Text(index+"");
            Text numberandnight = new Text(index +"(N)");
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
            decaycards[i] = new ImageView(new Image("file:img/"+nameofcard+".jpg"));
            DecayPile.getChildren().add(decaycards[i]);
        }

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
