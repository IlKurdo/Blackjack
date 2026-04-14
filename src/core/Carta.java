package core;

public class Carta
{
    private final Seme seme;
    private final Rango rango;

    public Carta(Seme seme, Rango rango)
    {
        this.seme = seme;
        this.rango = rango;
    }

    public int getValore()
    {
        return rango.getValore();
    }

    /*
    public String getNomeImmagine()
    {
        return rango.name() + "_" + seme.name() + ".png";
    }*/

    @Override
    public String toString()
    {
        return rango.name() + " di " + seme.name();
    }
}