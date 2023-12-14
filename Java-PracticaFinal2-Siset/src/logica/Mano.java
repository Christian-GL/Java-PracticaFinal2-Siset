// Christian Gil Ledesma
// Enlace vídeo: https://www.youtube.com/watch?v=EMiOLZMjey0

package logica;


public class Mano
{
	/* --- ATRIBUTOS --- */
	public static final int MAXCARTASJUGADOR = 13;
	private Carta[] cartas;
	private int cartasRestantes;


	/* --- CONSTRUCTOR --- */
	public Mano() {
		cartas = new Carta[MAXCARTASJUGADOR];
		cartasRestantes = 0;
	}


	/* --- METODOS --- */
	public void anyadirCarta(Carta carta) {
		cartas[cartasRestantes] = carta;
		cartasRestantes++;
	}

	// Para cuando el "jugador" seleccione una carta
	public void sacarCarta(Carta carta) {
		for (int i=0 ; i<Mano.MAXCARTASJUGADOR ; i++) {
			if (carta.equals(cartas[i])) {
				cartas[i].setColocada(true);
				cartasRestantes--;
				break;
			}
		}
	}


	/* --- TOSTRING --- */
	@Override
	public String toString() {
		String s = "";
		for (int i=0 ; i<cartasRestantes ; i++) {
			s += cartas[i].toString() + " ";
		}
		return s;
	}


	/* --- GETERS Y SETERS --- */
	public Carta[] getCartas() {
		return cartas;
	}
	public void setCartas(Carta[] cartas) {
		this.cartas = cartas;
	}

	public int getCartasRestantes() {
		return cartasRestantes;
	}
	public void setCartasRestantes(int cartasRestantes) {
		this.cartasRestantes = cartasRestantes;
	}

}
