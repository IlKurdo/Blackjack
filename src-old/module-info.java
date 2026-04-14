module blackjack
{
    requires javafx.controls;

    opens ui to javafx.graphics;
    exports ui;
    exports core;
}