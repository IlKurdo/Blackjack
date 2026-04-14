package core;
import java.util.*;

public class Mano
{
    private List<Carta> carteInMano;

    public Mano()
    {
        carteInMano = new ArrayList<>();
    }

    public void aggiungiCarta(Carta c)
    {
        carteInMano.add(c);
    }

    public List<Carta> getCarteInMano()
    {
        return carteInMano;
    }
    public int calcolaPunteggio()
    {
        int punteggio = 0;
        int assiTrovati = 0;

        for(Carta c : carteInMano)
        {
            punteggio += c.getValore();
            if(c.getValore() == 11)
            {
                assiTrovati++;
            }
        }

        while(punteggio > 21 && assiTrovati > 0)
        {
            punteggio -= 10;
            assiTrovati--;
        }
        return punteggio;
    }

    public void svuota()
    {
        carteInMano.clear();
    }
}