package core;

public class Partita
{
    private Mazzo mazzo;
    private Mano manoGiocatore;
    private Mano manoCroupier;

    public Partita()
    {
        mazzo = new Mazzo();
        manoGiocatore = new Mano();
        manoCroupier = new Mano();
    }

    public void iniziaNuovoRound()
    {
        mazzo = new Mazzo();
        mazzo.mescola();

        manoCroupier.svuota();
        manoGiocatore.svuota();

        manoGiocatore.aggiungiCarta(mazzo.pesca());
        manoCroupier.aggiungiCarta(mazzo.pesca());
        manoGiocatore.aggiungiCarta(mazzo.pesca());
        manoCroupier.aggiungiCarta(mazzo.pesca());
    }

    public void giocatorePesca()
    {
        manoGiocatore.aggiungiCarta(mazzo.pesca());
    }

    public void turnoCroupier()
    {
        while(manoCroupier.calcolaPunteggio() < 17)
        {
            manoCroupier.aggiungiCarta(mazzo.pesca());
        }
    }

    public String determinaVincitore()
    {
        int puntiGiocatore = manoGiocatore.calcolaPunteggio();
        int puntiCroupier = manoCroupier.calcolaPunteggio();

        if(puntiGiocatore > 21)
        {
            return "Hai sballato! Vince il Banco";
        }
        else if(puntiCroupier > 21)
        {
            return "Il Banco ha sballato! Hai vinto";
        }
        else if(puntiGiocatore > puntiCroupier)
        {
            return "Hai vinto!";
        }
        else if(puntiGiocatore < puntiCroupier)
        {
            return "Il Banco ha vinto!";
        }
        else
        {
            return "Pareggio!";
        }
    }

    public Mano getManoGiocatore()
    {
        return manoGiocatore;
    }

    public Mano getManoCroupier()
    {
        return manoCroupier;
    }
}