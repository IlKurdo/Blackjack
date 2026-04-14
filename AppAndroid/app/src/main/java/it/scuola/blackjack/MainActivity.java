package it.scuola.blackjack;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;

// Importo la logica copiando il core
import core.Partita;
import core.Carta;

public class MainActivity extends Activity {

    Partita partita;
    
    TextView tvCarteBanco;
    TextView tvCarteGiocatore;
    TextView tvRisultato;
    
    Button btnPesca;
    Button btnStai;
    Button btnNuova;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        partita = new Partita();
        
        tvCarteBanco = findViewById(R.id.tvCarteBanco);
        tvCarteGiocatore = findViewById(R.id.tvCarteGiocatore);
        tvRisultato = findViewById(R.id.tvRisultato);
        
        btnPesca = findViewById(R.id.btnPesca);
        btnStai = findViewById(R.id.btnStai);
        btnNuova = findViewById(R.id.btnNuova);
        
        btnPesca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                partita.giocatorePesca();
                aggiornaSchermo();
                if(partita.getManoGiocatore().calcolaPunteggio() > 21){
                    finePartita();
                }
            }
        });
        
        btnStai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finePartita();
            }
        });
        
        btnNuova.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniziaPartita();
            }
        });
        
        iniziaPartita();
    }
    
    private void iniziaPartita() {
        partita.iniziaNuovoRound();
        tvRisultato.setText("Stai giocando...");
        btnPesca.setEnabled(true);
        btnStai.setEnabled(true);
        
        aggiornaSchermo();
        
        List<Carta> banco = partita.getManoCroupier().getCarteInMano();
        tvCarteBanco.setText(banco.get(0).toString() + "\n[Carta Coperta]");
    }
    
    private void aggiornaSchermo() {
        String sueCarte = "";
        for(Carta c : partita.getManoGiocatore().getCarteInMano()) {
            sueCarte = sueCarte + c.toString() + "\n";
        }
        sueCarte = sueCarte + "\nPunteggio: " + partita.getManoGiocatore().calcolaPunteggio();
        tvCarteGiocatore.setText(sueCarte);
    }
    
    private void finePartita() {
        btnPesca.setEnabled(false);
        btnStai.setEnabled(false);
        
        if (partita.getManoGiocatore().calcolaPunteggio() <= 21) {
            partita.turnoCroupier();
        }
        
        String bancoStr = "";
        for(Carta c : partita.getManoCroupier().getCarteInMano()) {
            bancoStr = bancoStr + c.toString() + "\n";
        }
        bancoStr = bancoStr + "\nPunteggio Banco: " + partita.getManoCroupier().calcolaPunteggio();
        tvCarteBanco.setText(bancoStr);
        
        tvRisultato.setText(partita.determinaVincitore());
    }
}
