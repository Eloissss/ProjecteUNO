package uno;

import uno.interficie.*;
import uno.logica.*;
import uno.logica.cartes.Carta;

import java.util.ArrayList;

public class Partida {
    private int NOMBRE_INICIAL_CARTES = 7;

    private int quantitatJugadors;

    Mazo mazo = new Mazo(this);
    Pilo pilo = new Pilo();
    OrdreJugadors ordreJugadors = new OrdreJugadors();

    public Mazo getMazo() {
        return mazo;
    }

    public OrdreJugadors getOrdreJugadors() {
        return ordreJugadors;
    }

    public void jugar() {
        preparar();
        boolean partidaAcabada = false;
        do {
            partidaAcabada = torn();
        } while (!partidaAcabada);
        UI.victoria(ordreJugadors.getJugadorActiu());
    }

    private void preparar() {
        mazo.barrejar();

        ArrayList<String> nomsJugadors = UI.demanarJugadors();
        quantitatJugadors = nomsJugadors.size();
        for (String nomJugador : nomsJugadors) {
            ordreJugadors.crearJugador(nomJugador);
        }
        ordreJugadors.barrejarOrdre();
        UI.mostrarOrdreJugadors(ordreJugadors.getJugadors());

        repartirCartes();

        Carta primeraCarta = mazo.agafarCarta();
        pilo.addCarta(primeraCarta);
    }

    private boolean torn() {
        // torn() torna true si la partida s'acaba. Torna false si continua.
        Jugador jugadorActiu = ordreJugadors.getJugadorActiu();
        UI.tornJugador(jugadorActiu, pilo);

        boolean nomesAcumular = false;

        if (acumularCartes > 0){
            if (jugadorActiu.teAcumular()) {
                boolean volRespondre = UI.pregutar(acumularCartes);
                if (!volRespondre) {
                    aplicarRoboAcumulat(jugadorActiu);
                    return false;
                } else {
                    nomesAcumular = true;
                }
            } else  {
                UI.missatgeHasDeRobar(acumularCartes);
                aplicarRoboAcumulat(jugadorActiu);
                return false;
            }

        }
        boolean potTirar = jugadorActiu.potTirarCarta(pilo);
        if (!nomesAcumular){
            while (!potTirar) {
                if (mazo.esBuid()) {
                    mazo.reiniciar(pilo);
                } else {
                    UI.senseCartes();
                    jugadorActiu.robaCarta(mazo);
                    potTirar = jugadorActiu.potTirarCarta(pilo);
                }
            }

        }

        Carta cartaTirada = UI.demanarCarta(jugadorActiu, pilo, nomesAcumular);
        jugadorActiu.tirarCarta(cartaTirada,pilo);

        if (jugadorActiu.nombreDeCartes() <= 0) {
            return true;
        } else {
            ordreJugadors.passarTorn();
            return false;
        }
    }

    private void donarCartesAcumulades(Jugador jugadorActiu) {
        aplicarRoboAcumulat(jugadorActiu);
    }

    private void aplicarRoboAcumulat(Jugador jugadorActiu) {
        for (int i = 0; i < acumularCartes; i++){
            jugadorActiu.robaCarta(mazo);
        }

        UI.chupaCartes(jugadorActiu, acumularCartes);

        restarCartesAcomulades();
        ordreJugadors.passarTorn();
    }


    private void repartirCartes() {
        for (int i=0; i<quantitatJugadors*NOMBRE_INICIAL_CARTES; i++){
            ordreJugadors.getJugadorActiu().robaCarta(mazo);
            ordreJugadors.passarTorn();
        }
    }

    private int acumularCartes = 0;

    public void acumular(int quantitat) {
        this.acumularCartes = this.acumularCartes + quantitat;
    }

    public int getacumularCartrs() {
        return this.acumularCartes;
    }
    public void restarCartesAcomulades () {
        this.acumularCartes = 0;
    }
}
