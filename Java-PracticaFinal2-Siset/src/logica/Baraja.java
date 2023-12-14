// Christian Gil Ledesma
// Enlace vídeo: https://www.youtube.com/watch?v=EMiOLZMjey0

package logica;

import java.util.Random;


public class Baraja
{
	/* --- ATRIBUTOS --- */
	public static final int PALOS = 4;
	public static final int MAXCARTAS = Mano.MAXCARTASJUGADOR * PALOS;
	private Carta[] cartas;
	private int cartasRestantes;


	/* --- CONSTRUCTOR --- */
	public Baraja() {
		cartas = new Carta[MAXCARTAS];
		cartasRestantes = MAXCARTAS;
		int indice = 0;

		for (Palo palo : Palo.values()) {
			for (int i=1 ; i<=Mano.MAXCARTASJUGADOR ; i++) {
				cartas[indice] = new Carta(palo, i);
				indice++;
			}
		}
	}


	/* --- METODOS --- */
	public void mezclarBaraja() {
		// La implementacion "Durstenfeld" del algoritmo "Fisher-Yates"
		Random random = new Random();
		int pos;
		for (int i=MAXCARTAS-1 ; i>0 ; i--) {
			pos = random.nextInt(i+1);
			Carta c = cartas[pos];
			cartas[pos] = cartas[i];
			cartas[i] = c;
		}
	}

	public Carta sacarCarta() throws NoQuedanCartas {
		if (cartasRestantes == 0) {
			throw new NoQuedanCartas("No quedan cartas en la baraja");
		}
		Carta carta = cartas[cartasRestantes-1];
		cartasRestantes--;

		return carta;
	}

	public static class NoQuedanCartas extends Exception {
		public NoQuedanCartas(String e) {
			super(e);
		}
	}


	/* --- TOSTRING --- */
	@Override
	public String toString() {
		String s = "";
		for (int i=0 ; i<cartasRestantes ; i++) {
			s += cartas[i].toString() + " ";
			if (i==Mano.MAXCARTASJUGADOR-1 || i==Mano.MAXCARTASJUGADOR*2-1 || i==Mano.MAXCARTASJUGADOR*3-1) {
				s += "\n";
			}
		}
		return s;
	}


	/* --- GETERS Y SETERS --- */
	public static int getMaxcartas() {
		return MAXCARTAS;
	}

	public Carta[] getCartas() {
		return cartas;
	}
	public void setCartas(Carta[] cartasBaraja) {
		this.cartas = cartasBaraja;
	}

	public int getCartasRestantes() {
		return cartasRestantes;
	}
	public void setCartasRestantes(int cartasRestantes) {
		this.cartasRestantes = cartasRestantes;
	}


}
