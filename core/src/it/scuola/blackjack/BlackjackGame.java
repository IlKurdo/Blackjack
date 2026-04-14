package it.scuola.blackjack;

import com.badlogic.gdx.Game;

public class BlackjackGame extends Game {
    @Override
    public void create() {
        setScreen(new BlackjackScreen());
    }
}
