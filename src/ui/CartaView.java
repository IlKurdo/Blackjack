package ui;

import core.Carta;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CartaView extends StackPane {

    public CartaView(Carta carta) {
        Rectangle rettangolo = new Rectangle(70, 100);
        rettangolo.setFill(Color.WHITE);
        rettangolo.setStroke(Color.BLACK); // bordo nero
        
        String nome = carta.toString();
        Text testo = new Text(nome);
        testo.setFont(new Font(10));
        
        // Colora di rosso cuori e quadri
        if(nome.contains("CUORI") || nome.contains("QUADRI")) {
            testo.setFill(Color.RED);
        } else {
            testo.setFill(Color.BLACK);
        }
        
        this.getChildren().add(rettangolo);
        this.getChildren().add(testo);
    }
    
    // costruttore per la carta coperta
    public CartaView() {
        Rectangle rettangolo = new Rectangle(70, 100);
        rettangolo.setFill(Color.BLUE); 
        rettangolo.setStroke(Color.BLACK);
        
        Text testo = new Text("???");
        testo.setFill(Color.WHITE);
        
        this.getChildren().add(rettangolo);
        this.getChildren().add(testo);
    }
}