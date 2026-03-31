package core;
import java.util.*;

public class Mazzo
{
    private List<Carta> carte;

    public Mazzo()
    {
        carte = new ArrayList<>();

        //CREA LE 52 CARTE CON TUTTI I SIMBOLI E RANGHI POSSIBILI
        for(Seme s: Seme.values())
        {
            for(Rango r: Rango.values())
            {
                carte.add(new Carta(s, r));
            }
        }
    }

    public void mescola()
    {
        Collections.shuffle(carte);
    }

    public Carta pesca()
    {
        return carte.remove(0);
    }
}