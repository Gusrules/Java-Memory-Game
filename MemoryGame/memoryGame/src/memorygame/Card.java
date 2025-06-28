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
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card extends Button {
    private final String imageName; // Onoma eikonas
    private final Image frontImage; // Eikona mprostinis opsis
    private final Image backImage;  // Eikona pisw opsis
    private boolean isRevealed;     // An i karta einai anoixti

    // Constructor Card
    public Card(String imageName, Image frontImage, Image backImage) {
        this.imageName = imageName;
        this.frontImage = frontImage;
        this.backImage = backImage;
        this.isRevealed = false;    // Arxika i karta einai kleisti

        // Orismos pisw pleuras
        setGraphic(new ImageView(backImage));
        setStyle("-fx-padding: 5; -fx-background-color: lightgray;");
    }

    // Sunartisi epistrofis onomatos kartas
    public String getImageName() {
        return imageName;
    }

    // Sunartisi elegxou an einai anoixti i karta i oxi 
    public boolean isRevealed() {
        return isRevealed;
    }

    // Sunartisi apokalipsis kartas
    public void reveal() {
        this.isRevealed = true;
        setGraphic(new ImageView(frontImage));
    }

    // Sunartisi emfanisis pisw eikonas (kleisimo kartas)
    public void hide() {
        this.isRevealed = false;
        setGraphic(new ImageView(backImage));
    }
}

