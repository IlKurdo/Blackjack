package it.scuola.blackjack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import core.Carta;
import core.Partita;

public class BlackjackScreen extends ScreenAdapter {
    private Stage stage;
    private Skin skin;
    private Partita partita;

    private Table uiTable;
    private Label puntCroupierLabel, puntGiocatoreLabel, risultatoLabel;
    private HorizontalGroup areaCroupier, areaGiocatore;
    private TextButton btnPesca, btnStai, btnNuova;

    public BlackjackScreen() {
        stage = new Stage(new FitViewport(1000, 700));
        Gdx.input.setInputProcessor(stage);
        
        skin = new Skin(Gdx.files.internal("uiskin.json")); 
        partita = new Partita();
        
        costruisciUI();
        configuraAzioni();
        iniziaRound();
    }

    private void costruisciUI() {
        uiTable = new Table();
        uiTable.setFillParent(true);

        Label titolo = new Label("BlackJack", skin);
        titolo.setFontScale(2f);
        
        areaCroupier = new HorizontalGroup().space(10);
        areaGiocatore = new HorizontalGroup().space(10);
        
        puntCroupierLabel = new Label("Banco: ?", skin);
        puntGiocatoreLabel = new Label("Tu: 0", skin);
        risultatoLabel = new Label("", skin);
        risultatoLabel.setColor(Color.YELLOW);

        btnPesca = new TextButton("Pesca", skin);
        btnStai = new TextButton("Stai", skin);
        btnNuova = new TextButton("Nuova Partita", skin);

        // Layout
        uiTable.add(titolo).padBottom(30).row();
        
        uiTable.add(new Label("BANCO", skin)).row();
        uiTable.add(puntCroupierLabel).row();
        uiTable.add(areaCroupier).height(150).padBottom(20).row();
        
        uiTable.add(risultatoLabel).height(50).row();

        uiTable.add(new Label("TU", skin)).row();
        uiTable.add(puntGiocatoreLabel).row();
        uiTable.add(areaGiocatore).height(150).padBottom(30).row();

        Table btnTable = new Table();
        btnTable.add(btnPesca).width(120).padRight(20);
        btnTable.add(btnStai).width(120).padRight(20);
        btnTable.add(btnNuova).width(150);
        
        uiTable.add(btnTable).row();
        
        stage.addActor(uiTable);
    }
    
    private void configuraAzioni() {
        btnPesca.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ChangeListener() {
            @Override
            public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
                partita.giocatorePesca();
                aggiornaCarteGiocatore();
                int pt = partita.getManoGiocatore().calcolaPunteggio();
                puntGiocatoreLabel.setText("Tu: " + pt);
                if (pt > 21) finePartita();
            }
        });
        
        btnStai.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ChangeListener() {
            @Override
            public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
                finePartita();
            }
        });

        btnNuova.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ChangeListener() {
            @Override
            public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
                iniziaRound();
            }
        });
    }

    private void iniziaRound() {
        partita.iniziaNuovoRound();
        areaCroupier.clearChildren();
        areaGiocatore.clearChildren();
        risultatoLabel.setText("");
        
        btnPesca.setDisabled(false);
        btnStai.setDisabled(false);

        aggiornaCarteGiocatore();
        puntGiocatoreLabel.setText("Tu: " + partita.getManoGiocatore().calcolaPunteggio());

        Carta c1 = partita.getManoCroupier().getCarteInMano().get(0);
        areaCroupier.addActor(creaCartaActor(c1));
        areaCroupier.addActor(new Label("[Coperta]", skin)); // Carta coperta semplificata
        puntCroupierLabel.setText("Banco: ?");
    }

    private void aggiornaCarteGiocatore() {
        areaGiocatore.clearChildren();
        for (Carta c : partita.getManoGiocatore().getCarteInMano()) {
            areaGiocatore.addActor(creaCartaActor(c));
        }
    }

    private void finePartita() {
        btnPesca.setDisabled(true);
        btnStai.setDisabled(true);

        if (partita.getManoGiocatore().calcolaPunteggio() <= 21) {
            partita.turnoCroupier();
        }

        areaCroupier.clearChildren();
        for (Carta c : partita.getManoCroupier().getCarteInMano()) {
            areaCroupier.addActor(creaCartaActor(c));
        }
        puntCroupierLabel.setText("Banco: " + partita.getManoCroupier().calcolaPunteggio());

        String esito = partita.determinaVincitore();
        risultatoLabel.setText(esito);
    }
    
    private Label creaCartaActor(Carta c) {
        return new Label(c.toString(), skin);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.4f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}
