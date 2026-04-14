package ui;

import core.Carta;
import core.Seme;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Rappresentazione grafica di una singola carta da gioco.
 * Mostra simbolo del seme, rango e colore corretto (rosso/nero).
 */
public class CartaView extends StackPane {

    private static final double CARD_WIDTH  = 80;
    private static final double CARD_HEIGHT = 110;
    private static final double ARC         = 10;

    public CartaView(Carta carta) {
        build(carta, false);
    }

    /** Carta coperta (retro) */
    public CartaView() {
        buildBack();
    }

    // ------------------------------------------------------------------ //

    private void build(Carta carta, boolean coperta) {
        Rectangle bg = new Rectangle(CARD_WIDTH, CARD_HEIGHT);
        bg.setArcWidth(ARC);
        bg.setArcHeight(ARC);
        bg.getStyleClass().add("card-bg");

        String nomeRango = rangoLabel(carta);
        String simbolo   = semeSimbolo(carta);
        boolean rosso    = carta.toString().contains("CUORI") || carta.toString().contains("QUADRI");

        Text topLabel = new Text(nomeRango + "\n" + simbolo);
        topLabel.getStyleClass().add(rosso ? "card-text-red" : "card-text-black");
        topLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");

        Text centerSymbol = new Text(simbolo);
        centerSymbol.getStyleClass().add(rosso ? "card-text-red" : "card-text-black");
        centerSymbol.setStyle("-fx-font-size: 32px;");

        VBox content = new VBox(4, topLabel, centerSymbol);
        content.setAlignment(Pos.CENTER);

        getChildren().addAll(bg, content);
        getStyleClass().add("card");
        setPrefSize(CARD_WIDTH, CARD_HEIGHT);
        setMaxSize(CARD_WIDTH, CARD_HEIGHT);
    }

    private void buildBack() {
        Rectangle bg = new Rectangle(CARD_WIDTH, CARD_HEIGHT);
        bg.setArcWidth(ARC);
        bg.setArcHeight(ARC);
        bg.getStyleClass().add("card-back-bg");

        Text symbol = new Text("🂠");
        symbol.setStyle("-fx-font-size: 48px;");
        symbol.getStyleClass().add("card-back-text");

        getChildren().addAll(bg, symbol);
        getStyleClass().add("card");
        setPrefSize(CARD_WIDTH, CARD_HEIGHT);
        setMaxSize(CARD_WIDTH, CARD_HEIGHT);
    }

    // ------------------------------------------------------------------ //

    private String rangoLabel(Carta carta) {
        // usa toString() "RANGO di SEME" → prende la parte prima di " di "
        String s = carta.toString().split(" di ")[0];
        return switch (s) {
            case "DUE"     -> "2";
            case "TRE"     -> "3";
            case "QUATTRO" -> "4";
            case "CINQUE"  -> "5";
            case "SEI"     -> "6";
            case "SETTE"   -> "7";
            case "OTTO"    -> "8";
            case "NOVE"    -> "9";
            case "DIECI"   -> "10";
            case "JACK"    -> "J";
            case "REGINA"  -> "Q";
            case "RE"      -> "K";
            case "ASSO"    -> "A";
            default        -> s;
        };
    }

    private String semeSimbolo(Carta carta) {
        String s = carta.toString().split(" di ")[1];
        return switch (s) {
            case "CUORI"  -> "♥";
            case "QUADRI" -> "♦";
            case "FIORI"  -> "♣";
            case "PICCHE" -> "♠";
            default       -> "?";
        };
    }
}