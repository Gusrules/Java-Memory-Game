/*
Nikolas Al- Bampoul ICSD 321/2020004
Charalampos Foustanellas ICSD 321/2020231
*/

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package memorygame;

/**
 *
 * @author User
 */


import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Board {
    private List<Card> cards;                                                                   // Lista pou periexei oles tis kartes toy board
    private GridPane grid;                                                                      // Plegma sto opoio mpainoun oi kartes

    // Constructor 
    public Board(int gridSize, List<String> imageNames, Image backImage) {
        this.cards = new ArrayList<>();                                                         // Dimiourgia listas gia tis kartes                                                       
        this.grid = new GridPane();                                                             // Arxikopoiisi tou board vs GridPane;

        // Epilegoume mia karta tuxaia gia JOKER
        Random random = new Random();
        String jokerImage = imageNames.remove(random.nextInt(imageNames.size()));

        // Dimiourgoume duo kartes JOKER
        Card joker1 = new Card(jokerImage + "joker", new Image(getClass().getResourceAsStream("/resources/images/" + jokerImage + "joker.png")), backImage);
        Card joker2 = new Card(jokerImage + "joker", new Image(getClass().getResourceAsStream("/resources/images/" + jokerImage + "joker.png")), backImage);

        // Anakateuoume ta onomata twn eikonwn gia na exoume random diataksi
        Collections.shuffle(imageNames);
        
        // for loop gia na dimiourgisoume ena zeugari gia kathe eikona
        for (int i = 0; i < imageNames.size(); i++) {
            String imageName = imageNames.get(i);                                           // Pairnoume to onoma tis eikonas stin thesi i
            
            Card card1 = new Card(imageName, new Image(getClass().getResourceAsStream("/resources/images/" + imageName + ".png")), backImage);
            Card card2 = new Card(imageName, new Image(getClass().getResourceAsStream("/resources/images/" + imageName + ".png")), backImage);
            
            // Prosthetoume tis duo idies kartes stin lista
            cards.add(card1);
            cards.add(card2);
        }

        // Prosthetoume tis duo kartes JOKER stin lista
        cards.add(joker1);
        cards.add(joker2);

        // Anakateuoume tis kartes prin tis valoume sto board
        Collections.shuffle(cards);

        // Dimiourgoume to grid
        int index = 0;
        // for loop gia kathe grammi tou board
        for (int row = 0; row < gridSize; row++) {
            // for loop gia kathe column toy board
            for (int col = 0; col < gridSize; col++) {
                // if statement gia na elegksoume an uparxoun diathesimes kartes
                if (index < cards.size()) {
                    grid.add(cards.get(index), col, row);                               // Prosthetoume tin karta sto grid
                    index++;                                                            // auksanoume ton metriti
                }
            }
        }
    }

    // Sunartisi epistrofis grid poy periexei tis kartes tou paixnidiou
    public GridPane getGrid() {
        return grid;
    }

    // Sunartisi pou epistrefei tin lista me oles tis kartes tou board
    public List<Card> getCards() {
        return cards;
    }
}


