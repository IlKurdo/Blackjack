package core;

public enum Seme
{
    CUORI("Rosso"),
    QUADRI("Rosso"),
    FIORI("Nero"),
    PICCHE("Nero");

    private final String colore;

    Seme(String colore)
    {
        this.colore = colore;
    }

    public String getColore()
    {
        return colore;
    }
}