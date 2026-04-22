package uno.logica.cartes;

import uno.Partida;
import uno.interficie.UI;
import uno.logica.Jugador;
import uno.logica.OrdreJugadors;

public class CartaMes4 extends Carta{
    public CartaMes4(Partida partida) {
        this.simbol = "+4";
        this.color = Color.Incolor;
        this.partida = partida;
    }

    @Override
    public void accio() {
        this.color = UI.demanarColor();

      partida.acumular(4);
    }
}
