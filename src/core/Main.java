package core;
import java.util.*;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        Partita partita = new Partita();

        System.out.println("Benvenuto al BlackJack!");
        partita.iniziaNuovoRound();

        System.out.println("Le tue carte: " + partita.getManoGiocatore().getCarteInMano());
        System.out.println("Punteggio: " + partita.getManoGiocatore().calcolaPunteggio());

        System.out.println("Carte del Croupier: " + partita.getManoCroupier().getCarteInMano());
        System.out.println("Punteggio: " + partita.getManoCroupier().calcolaPunteggio());

        while(partita.getManoGiocatore().calcolaPunteggio() <= 21)
        {
            System.out.println("Vuoi pescare (h) o stare (s)?");
            String opzione = scanner.nextLine();

            if(opzione.equalsIgnoreCase("h"))
            {
                partita.giocatorePesca();
                System.out.println("Nuova mano: " + partita.getManoGiocatore().getCarteInMano());
                System.out.println("Punteggio: " + partita.getManoGiocatore().calcolaPunteggio());
            }
            else
            {
                break;
            }
        }

        if(partita.getManoGiocatore().calcolaPunteggio() <= 21)
        {
            System.out.println("Il Banco sta per pescare...");
            partita.turnoCroupier();
            System.out.println("Mano finale Croupier: " + partita.getManoCroupier().getCarteInMano());
            System.out.println("Punteggio: " + partita.getManoCroupier().calcolaPunteggio());
        }

        System.out.println("---RISULTATO---");
        System.out.println(partita.determinaVincitore());
    }
}