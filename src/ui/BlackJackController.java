package ui;

import core.Carta;
import core.Partita;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.util.List;

/**
 * Controller e costruttore della scena principale.
 * Collega la logica di gioco (core.Partita) con i componenti JavaFX.
 */
public class BlackJackController
{

    private final Partita partita = new Partita();

    // Layout principale
    private final BorderPane root = new BorderPane();

    // Aree carte
    private final HBox areaCroupier  = new HBox(10);
    private final HBox areaGiocatore = new HBox(10);

    // Labels punteggio
    private final Label labelPunteggioCroupier  = new Label("Banco: ?");
    private final Label labelPunteggioGiocatore = new Label("Tu: 0");

    // Label risultato
    private final Label labelRisultato = new Label("");

    // Pulsanti
    private final Button btnNuovaPartita = new Button("🔄  Nuova Partita");
    private final Button btnPesca        = new Button("👆  Pesca");
    private final Button btnStai         = new Button("✋  Stai");

    // ------------------------------------------------------------------ //

    public BlackJackController()
    {
        costruisciUI();
        configuraAzioni();
        iniziaRound();
    }

    public BorderPane getRoot()
    {
        return root;
    }

    // ------------------------------------------------------------------ //

    private void costruisciUI()
    {
        root.getStyleClass().add("root-bg");
        root.setPrefSize(1000, 700);

        // ---- HEADER ----
        Label titolo = new Label("♠ BlackJack ♥");
        titolo.getStyleClass().add("titolo");
        HBox header = new HBox(titolo);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20, 0, 10, 0));
        root.setTop(header);

        // ---- CENTRO ----
        VBox centro = new VBox(20);
        centro.setAlignment(Pos.CENTER);
        centro.setPadding(new Insets(20));

        // Area Croupier
        Label lblCroupier = new Label("BANCO");
        lblCroupier.getStyleClass().add("label-sezione");
        labelPunteggioCroupier.getStyleClass().add("label-punteggio");
        areaCroupier.setAlignment(Pos.CENTER);
        areaCroupier.setMinHeight(130);
        VBox boxCroupier = new VBox(8, lblCroupier, labelPunteggioCroupier, areaCroupier);
        boxCroupier.setAlignment(Pos.CENTER);

        // Separatore con risultato
        labelRisultato.getStyleClass().add("label-risultato");
        HBox separatore = new HBox(labelRisultato);
        separatore.setAlignment(Pos.CENTER);
        separatore.setMinHeight(50);

        // Area Giocatore
        Label lblGiocatore = new Label("TU");
        lblGiocatore.getStyleClass().add("label-sezione");
        labelPunteggioGiocatore.getStyleClass().add("label-punteggio");
        areaGiocatore.setAlignment(Pos.CENTER);
        areaGiocatore.setMinHeight(130);
        VBox boxGiocatore = new VBox(8, lblGiocatore, labelPunteggioGiocatore, areaGiocatore);
        boxGiocatore.setAlignment(Pos.CENTER);

        centro.getChildren().addAll(boxCroupier, separatore, boxGiocatore);
        root.setCenter(centro);

        // ---- BOTTOM PULSANTI ----
        btnPesca.getStyleClass().add("btn-azione");
        btnStai.getStyleClass().add("btn-azione");
        btnNuovaPartita.getStyleClass().add("btn-nuova");

        HBox pulsanti = new HBox(20, btnPesca, btnStai, btnNuovaPartita);
        pulsanti.setAlignment(Pos.CENTER);
        pulsanti.setPadding(new Insets(20));
        root.setBottom(pulsanti);
    }

    private void configuraAzioni()
    {
        btnPesca.setOnAction(e -> {
            partita.giocatorePesca();
            aggiornaCarteGiocatore();
            int punteggio = partita.getManoGiocatore().calcolaPunteggio();
            labelPunteggioGiocatore.setText("Tu: " + punteggio);
            if (punteggio > 21)
            {
                finePartita();
            }
        });

        btnStai.setOnAction(e -> finePartita());

        btnNuovaPartita.setOnAction(e -> iniziaRound());
    }

    // ------------------------------------------------------------------ //

    private void iniziaRound()
    {
        partita.iniziaNuovoRound();

        areaCroupier.getChildren().clear();
        areaGiocatore.getChildren().clear();
        labelRisultato.setText("");
        labelRisultato.getStyleClass().removeAll("risultato-vittoria", "risultato-sconfitta", "risultato-pareggio");

        btnPesca.setDisable(false);
        btnStai.setDisable(false);

        // Mostra le carte del giocatore
        aggiornaCarteGiocatore();
        labelPunteggioGiocatore.setText("Tu: " + partita.getManoGiocatore().calcolaPunteggio());

        // Mostra 1 carta scoperta + 1 coperta per il croupier
        List<Carta> carteCroupier = partita.getManoCroupier().getCarteInMano();
        CartaView cv1 = new CartaView(carteCroupier.get(0));
        CartaView cv2 = new CartaView(); // coperta
        animaEntrata(cv1);
        animaEntrata(cv2);
        areaCroupier.getChildren().addAll(cv1, cv2);
        labelPunteggioCroupier.setText("Banco: ?");
    }

    private void aggiornaCarteGiocatore()
    {
        areaGiocatore.getChildren().clear();
        for (Carta c : partita.getManoGiocatore().getCarteInMano())
        {
            CartaView cv = new CartaView(c);
            animaEntrata(cv);
            areaGiocatore.getChildren().add(cv);
        }
    }

    private void finePartita()
    {
        btnPesca.setDisable(true);
        btnStai.setDisable(true);

        // Turno croupier (solo se il giocatore non ha sballato)
        if (partita.getManoGiocatore().calcolaPunteggio() <= 21)
        {
            partita.turnoCroupier();
        }

        // Mostra tutte le carte del croupier
        areaCroupier.getChildren().clear();
        for (Carta c : partita.getManoCroupier().getCarteInMano())
        {
            CartaView cv = new CartaView(c);
            animaEntrata(cv);
            areaCroupier.getChildren().add(cv);
        }

        labelPunteggioCroupier.setText("Banco: " + partita.getManoCroupier().calcolaPunteggio());

        // Mostra risultato
        String esito = partita.determinaVincitore();
        labelRisultato.setText(esito);

        if (esito.equals("Hai vinto!") || esito.equals("Il Banco ha sballato! Hai vinto"))
        {
            labelRisultato.getStyleClass().add("risultato-vittoria");
        }
        else if (esito.equals("Pareggio!"))
        {
            labelRisultato.getStyleClass().add("risultato-pareggio");
        }
        else
        {
            labelRisultato.getStyleClass().add("risultato-sconfitta");
        }
    }

    // ------------------------------------------------------------------ //

    /** Animazione di entrata per ogni carta (scivola dall'alto con fade) */
    private void animaEntrata(CartaView cv)
    {
        cv.setOpacity(0);

        FadeTransition fade = new FadeTransition(Duration.millis(400), cv);
        fade.setFromValue(0);
        fade.setToValue(1);

        TranslateTransition slide = new TranslateTransition(Duration.millis(300), cv);
        slide.setFromY(-30);
        slide.setToY(0);

        fade.play();
        slide.play();
    }
}