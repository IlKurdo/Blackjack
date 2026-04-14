package core;

public enum Rango
{
    DUE(2),
    TRE(3),
    QUATTRO(4),
    CINQUE(5),
    SEI(6),
    SETTE(7),
    OTTO(8),
    NOVE(9),
    DIECI(10),
    JACK(10),
    REGINA(10),
    RE(10),
    ASSO(11);

    private final int valore;

    Rango(int valore)
    {
        this.valore = valore;
    }

    public int getValore()
    {
        return valore;
    }
}