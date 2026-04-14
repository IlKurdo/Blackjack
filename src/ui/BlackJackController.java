package ui;

import core.Carta;
import core.Partita;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.List;

public class BlackJackController {

    private Partita partita = new Partita();

    private VBox root = new VBox(20);
    private HBox areaCroupier = new HBox(10);
    private HBox areaGiocatore = new HBox(10);

    private Label labelCroupier = new Label("Banco: ?");
    private Label labelGiocatore = new Label("Tu: 0");
    private Label labelRisultato = new Label("Inizia la partita!");

    private Button btnPesca = new Button("Pesca Carta");
    private Button btnStai = new Button("Stai (Passa turno)");
    private Button btnNuova = new Button("Nuova Partita");

    public BlackJackController() {
        root.setAlignment(Pos.CENTER);
        // Sfondo verde facile
        root.setStyle("-fx-background-color: lightgreen;");

        labelRisultato.setFont(new Font(20));
        
        areaCroupier.setAlignment(Pos.CENTER);
        areaGiocatore.setAlignment(Pos.CENTER);
        
        HBox pulsanti = new HBox(10);
        pulsanti.setAlignment(Pos.CENTER);
        pulsanti.getChildren().add(btnPesca);
        pulsanti.getChildren().add(btnStai);
        pulsanti.getChildren().add(btnNuova);

        root.getChildren().add(new Label("--- CARTE DEL BANCO ---"));
        root.getChildren().add(labelCroupier);
        root.getChildren().add(areaCroupier);
        
        root.getChildren().add(labelRisultato);
        
        root.getChildren().add(new Label("--- LE TUE CARTE ---"));
        root.getChildren().add(labelGiocatore);
        root.getChildren().add(areaGiocatore);
        
        root.getChildren().add(pulsanti);

        configuraPulsanti();
        iniziaPartita();
    }

    public VBox getRoot() {
        return root;
    }

    private void configuraPulsanti() {
        btnPesca.setOnAction(e -> {
            partita.giocatorePesca();
            aggiornaSchermo();
            
            if (partita.getManoGiocatore().calcolaPunteggio() > 21) {
                finePartita();
            }
        });

        btnStai.setOnAction(e -> {
            finePartita();
        });

        btnNuova.setOnAction(e -> {
            iniziaPartita();
        });
    }

    private void iniziaPartita() {
        partita.iniziaNuovoRound();
        labelRisultato.setText("Stai giocando...");
        btnPesca.setDisable(false);
        btnStai.setDisable(false);
        
        aggiornaSchermo();
        
        // Il banco fa vedere una carta e una la nasconde
        areaCroupier.getChildren().clear();
        List<Carta> carteBanco = partita.getManoCroupier().getCarteInMano();
        areaCroupier.getChildren().add(new CartaView(carteBanco.get(0)));
        areaCroupier.getChildren().add(new CartaView()); // carta coperta
        
        labelCroupier.setText("Banco: ?");
    }

    private void aggiornaSchermo() {
        areaGiocatore.getChildren().clear();
        // Ciclo for normale base
        for (Carta carta : partita.getManoGiocatore().getCarteInMano()) {
            areaGiocatore.getChildren().add(new CartaView(carta));
        }
        labelGiocatore.setText("Il tuo punteggio: " + partita.getManoGiocatore().calcolaPunteggio());
    }

    private void finePartita() {
        btnPesca.setDisable(true);
        btnStai.setDisable(true);

        if (partita.getManoGiocatore().calcolaPunteggio() <= 21) {
            partita.turnoCroupier();
        }

        areaCroupier.getChildren().clear();
        for (Carta carta : partita.getManoCroupier().getCarteInMano()) {
            areaCroupier.getChildren().add(new CartaView(carta));
        }
        labelCroupier.setText("Punteggio Banco: " + partita.getManoCroupier().calcolaPunteggio());

        String esito = partita.determinaVincitore();
        labelRisultato.setText(esito);
    }
}