package uno.logica.cartes;

import uno.Partida;
import uno.interficie.UI;
import uno.logica.Jugador;
import uno.logica.OrdreJugadors;

public class CartaMes2 extends Carta{
    public CartaMes2(Color color, Partida partida) {
        this.simbol = "+2";
        this.color = color;
        this.partida = partida;

        assert color != Color.Incolor;
    }

    @Override
    public void accio() {

        partida.acumular(2);
    }
}
