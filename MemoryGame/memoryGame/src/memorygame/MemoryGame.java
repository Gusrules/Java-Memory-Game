/*
Nikolas Al- Bampoul ICSD 321/2020004
Charalampos Foustanellas ICSD 321/2020231
*/

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package memorygame;

/**
 *
 * @author User
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.application.Platform;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import javafx.stage.Modality;
import javafx.scene.layout.HBox;
import javafx.scene.control.ChoiceDialog;
import java.util.Optional;

// Klasi paixnidioy 
public class MemoryGame extends Application {

    private Scene menuScene;                                                                    // Arxiko menu paixnidiou
    private Card firstSelected = null;                                                          // Apothikeuei tin proti karta poy epilegei o paiktis
    private Board board;                                                                        // To board toy paixnidiou
    private int attempts = 0;                                                                   // Counter prospatheiwn
    private String playerName;                                                                  // Onoma paikti
    private long startTime;                                                                     // Xronos enarksis
    private Label welcomeLabel;                                                                 // Etiketa gia tin emfanisi onomatos paikti sto menu

    // Synartisi start (otan ksekinaei i efarmogi)
    @Override
    public void start(Stage primaryStage) {
        
        Label titleLabel = new Label("MEMORY GAME");                                            // Dimiourgia titlou
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: orange;");

        // Text kalwsorismatos
        welcomeLabel = new Label();
        welcomeLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");
        updateWelcomeMessage();                                                                  // Enimerosi tou minimatos kalwsorismatos

        // Dimiourgia koumpiwn sto arxiko menu
        Button startGameButton = new Button("Έναρξη Παιχνιδιού");
        Button playerInfoButton = new Button("Εισαγωγή Στοιχείων Παίκτη");
        Button helpButton = new Button("Οδηγίες Παιχνιδιού");
        Button aboutButton = new Button("About Us");
        Button exitButton = new Button("Έξοδος");

        // Prosthiki stul sta koumpia (Mple xrwma kai backround)
        String buttonStyle = "-fx-padding: 10px; -fx-font-size: 16px; -fx-border-color: blue; -fx-border-width: 2px; -fx-background-color: white;";
        startGameButton.setStyle(buttonStyle);
        playerInfoButton.setStyle(buttonStyle);
        helpButton.setStyle(buttonStyle);
        aboutButton.setStyle(buttonStyle);
        exitButton.setStyle(buttonStyle);

        // Leitourgies Koumpiwn
        startGameButton.setOnAction(e -> {
            if (playerName == null) {
                showNameInputDialog(primaryStage, true);                                        // Zita onoma apo ton paikti prin ksekinisei to paixnidi
            } else {
                chooseDifficulty(primaryStage);                                                 // Epilogi duskolias
            }
        });

        playerInfoButton.setOnAction(e -> showNameInputDialog(primaryStage, false));            // Koumpi gia na valei onoma 
        helpButton.setOnAction(e -> showHelpDialog());                                          // Odigies paixnidiou
        aboutButton.setOnAction(e -> showAboutDialog());                                        // About us
        exitButton.setOnAction(e -> Platform.exit());                                           // exit

        // Diataksi twn koumpiwn sto arxiko menu
        VBox menuBox = new VBox(15, titleLabel, welcomeLabel, startGameButton, playerInfoButton, helpButton, aboutButton, exitButton);
        menuBox.setStyle("-fx-alignment: center; -fx-spacing: 10; -fx-padding: 20px;");
        menuBox.setMinSize(400, 400);

        // Dimiourgia arxikis skinis
        menuScene = new Scene(menuBox, 600, 400);
        primaryStage.setScene(menuScene);
        primaryStage.setTitle("Memory Game");
        primaryStage.show();
    }

    // Update to minima kalwsorismatos me to onoma toy paikti
    private void updateWelcomeMessage() {
        if (playerName != null) {
            welcomeLabel.setText("Καλώς ήρθες, " + playerName + "!");
        } else {
            welcomeLabel.setText("");
        }
    }

    // Parathiro eisagwgis onomatos paikti
    private void showNameInputDialog(Stage primaryStage, boolean startGameAfter) {
        Label nameLabel = new Label("Όνομα Παίκτη:");
        TextField nameInput = new TextField();
        nameInput.setPromptText("Εισάγετε το όνομά σας");

        Button saveButton = new Button("Αποθήκευση");
        saveButton.setOnAction(e -> {
            playerName = nameInput.getText().trim();
            if (playerName.isEmpty()) {
                playerName = "Παίκτης";                                                         // Default onoma
            }
            updateWelcomeMessage();

            if (startGameAfter) {
                chooseDifficulty(primaryStage);                                                 // Zitaei tin duskolia
            } else {
                primaryStage.setScene(menuScene);
            }
        });

        VBox inputLayout = new VBox(10, nameLabel, nameInput, saveButton);
        inputLayout.setStyle("-fx-alignment: center; -fx-padding: 20px;");
        Scene inputScene = new Scene(inputLayout, 300, 200);
        primaryStage.setScene(inputScene);
    }

    // Parathiro epilogis duskolias
    private void chooseDifficulty(Stage primaryStage) {
        List<String> difficultyLevels = Arrays.asList("Εύκολο (2x2)", "Δύσκολο (4x4)");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Δύσκολο (4x4)", difficultyLevels);
        dialog.setTitle("Επιλογή Επιπέδου Δυσκολίας");
        dialog.setHeaderText("Επέλεξε επίπεδο δυσκολίας:");
        dialog.setContentText("Επίπεδο:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String choice = result.get();
            if (choice.equals("Εύκολο (2x2)")) {
                startGame(primaryStage, 2);
            } else {
                startGame(primaryStage, 4);
            }
        }
    }

    // Sunartisi ekkinisis paixnidioy me ton epilegmeno pinaka
    private void startGame(Stage primaryStage, int gridSize) {
        List<String> imageNames = new ArrayList<>(Arrays.asList("fish", "mouse", "pig", "owl", "bird", "fox", "bear", "lion"));
        Image backImage = new Image(getClass().getResourceAsStream("/resources/images/back.png"));

        if (gridSize == 2) {
            imageNames = imageNames.subList(0, 2);
        }

        board = new Board(gridSize, imageNames, backImage);
        startTime = System.currentTimeMillis();

        for (int i = 0; i < board.getCards().size(); i++) {
            Card card = board.getCards().get(i);
            card.setOnAction(e -> handleCardSelection(card, primaryStage));
        }

        VBox root = new VBox(10, board.getGrid());
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Sunartisi diaxeirisis tis epilogis kartas apo ton xristi
    // Elegxei an i karta einai idi anoixti, an einia JOKER, an iparxei zeugari
    // kai an exoun vrethei ola ta zeugaria
    private void handleCardSelection(Card card, Stage primaryStage) {                           // card -> i epilegmeni karta, primaryStage -> to kurio parathuro tou paixnidiou

        // if statement an o pinakas exei arxikopoiithei
        if (board == null) {
            System.err.println("Error: Board is not initialized.");
            return;
        }

        // if statement an i karta einai idi anoixti
        if (card.isRevealed()) {
            return;                                                                             // An i karta einai idi anoixti return
        }

        card.reveal();                                                                          // Apokaluptoume tin karta 
        attempts++;                                                                             // Auksanoume ton metriti
        
        // if statement an o arithmos twn attempts ftasei 35
        if (attempts >= 35) {
            showAttemptsLimitDialog(primaryStage);
            return;
        }

        // if statement an einai JOKER
        if (card.getImageName().endsWith("joker")) {
            // An vrei tin karta joker emfanizoume to minima
            Alert jokerAlert = new Alert(Alert.AlertType.INFORMATION);
            jokerAlert.setTitle("Κάρτα Joker");
            jokerAlert.setHeaderText(null);
            jokerAlert.setContentText("Βρήκατε την κάρτα Μπαλαντέρ!");
            jokerAlert.showAndWait();

            // Emfanizoume kai tin alli karta JOKER
            for (Card c : board.getCards()) {
                if (c.getImageName().equals(card.getImageName()) && !c.isRevealed()) {
                    c.reveal();                                                                     // Apokaluptoume tin alli karta JOKER
                }
            }
            return; 
        }

        // if statement gia na apothikeusoyme tin proti karta poy epilegoume
        if (firstSelected == null) {
            firstSelected = card;                                                                   // An einai i proti karta tin kratame
        } else {                                                                                    // An einai i deyteri karta pou epileksame
            if (firstSelected.getImageName().equals(card.getImageName())) {                         // Elegxos an tairiazoun
                firstSelected = null;                                                               // Midenizoume tin epilogi gia tin epomeni fora
            } else {
                // An den tairiazoun, tis kruvoume meta apo 1 deuterolepto
                Card tempFirst = firstSelected;
                firstSelected = null;

                new Thread(() -> {
                    try {
                        Thread.sleep(1000);                                                          // Anamoni 1 deuterolepto
                    } catch (InterruptedException ignored) {
                    }

                    // Kleinoume tis duo kartes poy den tairiazoun
                    Platform.runLater(() -> {
                        tempFirst.hide();
                        card.hide();
                    });
                }).start();
            }
        }

        // if statement gia an vrethoun ola ta zeugaria
        if (allPairsFound()) {
            long endTime = System.currentTimeMillis();                                                  // o xronos pou teleiwse
            long timeTaken = (endTime - startTime) / 1000;                                              // Xronos se deuterolepta

            // Parathyro apotelesmatwn
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Τέλος Παιχνιδιού");
            alert.setHeaderText("Συγχαρητήρια " + playerName + "!");
            alert.setContentText(
                    "Όνομα: " + playerName + "\n" +
                    "Προσπάθειες: " + attempts + "\n" +
                    "Χρόνος: " + timeTaken + " δευτερόλεπτα"
            );
            alert.showAndWait();
            // Emfanisi parathyrou me epilogi play again i exit
            showGameOverDialog(primaryStage);
        }
    }

    // Sunartisi gia to orio prospatheiwn
    private void showAttemptsLimitDialog(Stage primaryStage) {
        // Dimiourgia neou parathirou 
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle("Όριο Προσπαθειών");

        // Dimiourgia eidopoiisis
        Label messageLabel = new Label("Έφτασες το όριο των 35 προσπαθειών!\nΘες να παίξεις ξανά;");
        Button playAgainButton = new Button("Παίξε ξανά");                                                  // Koumpi gia na ksanapaiksei
        Button exitButton = new Button("Έξοδος");                                                           // Koumpi gia eksodo

        playAgainButton.setOnAction(e -> {
            dialogStage.close();                                                                            // Kleinoume to parathiro
            attempts = 0;                                                                                   // Midenizoume attempts
            primaryStage.setScene(menuScene);                                                               // Ksekinaei to paixnidi
        });

        exitButton.setOnAction(e -> Platform.exit());                                                       // Kleinei to programma

        // Diamorfosi parathyrou
        VBox layout = new VBox(10, messageLabel, playAgainButton, exitButton);
        layout.setStyle("-fx-alignment: center; -fx-padding: 20px;");

        // Dimiourgia skinis kai emfanisi parathyrou
        Scene scene = new Scene(layout, 300, 150);
        dialogStage.setScene(scene);
        dialogStage.show();
}



    // Elegxos an exoun vrethei ola ta zeugaria
    private boolean allPairsFound() {
        for (int i = 0; i < board.getCards().size(); i++) {
            if (!board.getCards().get(i).isRevealed()) {
                return false;
            }
        }
        return true;
    }

    private void showGameOverDialog(Stage primaryStage) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle("Τέλος Παιχνιδιού");

        Label messageLabel = new Label("Συγχαρητήρια! Βρήκες όλες τις κάρτες!");
        Button playAgainButton = new Button("Παίξε ξανά");
        Button exitButton = new Button("Έξοδος");

        playAgainButton.setOnAction(e -> {
            dialogStage.close();
            attempts = 0;
            start(primaryStage);
        });

        exitButton.setOnAction(e -> Platform.exit());

        HBox buttonBox = new HBox(15, playAgainButton, exitButton);
        VBox layout = new VBox(10, messageLabel, buttonBox);
        Scene scene = new Scene(layout, 300, 150);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    // Parathiro How to play
    private void showHelpDialog() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Οδηγίες Παιχνιδιού");
        alert.setHeaderText("Κανόνες Παιχνιδιού");
        alert.setContentText("Το παιχνίδι απαιτεί να βρείτε όλα τα ζευγάρια καρτών. "
                + "Αν επιλέξετε μια κάρτα, αυτή αποκαλύπτεται. Αν βρείτε δύο ίδιες κάρτες, "
                + "εκείνες παραμένουν ανοιχτές. Χρησιμοποιήστε την κάρτα Joker για να αποκαλύψετε "
                + "άλλη μια κάρτα με το ίδιο σχέδιο. ΠΡΟΣΟΧΗ έχετε μόνο 35 προσπάθειες");
        alert.showAndWait();
    }

    // Parathiro About us
    private void showAboutDialog() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About us");
        alert.setHeaderText("Μέλη Ομάδας");
        alert.setContentText("Nikolas Al-Baboul ICSD 321/2020004\nCharalampos Foustanellas ICSD 321/2020231");
        alert.showAndWait();
    }

    // Main
    public static void main(String[] args) {
        launch(args);
    }
}
