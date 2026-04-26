package uno.logica;

import uno.logica.cartes.Carta;

import java.util.ArrayList;

public class Jugador {
    private final String nom;
    private ArrayList<Carta> cartes;

    public Jugador(String nomJugador) {
        nom = nomJugador;
        cartes = new ArrayList<>();
    }

    public String getNom(){return nom;}
    public ArrayList<Carta> getCartes(){return cartes;}

    public int nombreDeCartes() {
        return cartes.size();
    }

    public void tirarCarta(Carta carta, Pilo pilo) {
        cartes.remove(carta);
        pilo.addCarta(carta);
        carta.accio();
    }

    public void robaCarta(Mazo mazo) {
        cartes.add(mazo.agafarCarta());
    }

    public boolean potTirarCarta(Pilo pilo) {
        boolean jugadorPotTirar = false;
        Carta ultimaCarta = pilo.consultarCarta();
        for (Carta carta : cartes) {
            if (ultimaCarta.sonCartesCompatibles(carta)){
                jugadorPotTirar = true;
            }
        }
        return jugadorPotTirar;
    }

    public boolean teAcumular() {
        for (int i = 0; i < cartes.size(); i++) {
            Carta carta = cartes.get(i);
            if (carta.getSimbol().equals("+2") || carta.getSimbol().equals("+4")) {
                return true;
            }
        }
        return false;
    }
}
