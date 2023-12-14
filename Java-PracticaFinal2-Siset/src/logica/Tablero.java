// Christian Gil Ledesma
// Enlace vídeo: https://www.youtube.com/watch?v=EMiOLZMjey0

package logica;

import logica.Baraja.NoQuedanCartas;


public class Tablero
{
	/* --- ATRIBUTOS --- */
	private Carta[][] cartas;			// Primero se indica la fila (y) y luego la columna (x) --> [y][x] ; Ej: [3][5] = "5 de corazones"
	private int cartasRestantes;


	/* --- CONSTRUCTOR --- */
	public Tablero() {
		cartas = new Carta[Baraja.PALOS][Mano.MAXCARTASJUGADOR];
		cartasRestantes = 0;
	}


	/* --- METODOS --- */
	// Asigna una carta a cada posicion del tablero sacandolas de la baraja
	public void llenarTablero(Baraja baraja) {
		try {
			// Tenemos que hacer un doble bucle inverso porque la baraja saca cartas desde abajo
			for (int i=cartas.length-1 ; i>-1 ; i--) {
				for (int y=cartas[i].length-1 ; y>-1 ; y--) {
					cartas[i][y] = baraja.sacarCarta();
				}
			}
			cartasRestantes = Baraja.MAXCARTAS;
		}
		catch (NoQuedanCartas e) {
			e.printStackTrace();
		}
	}

	public Carta sacarCarta(int palo, int num) throws NoQuedanCartas {
		if (cartasRestantes == 0) {
			throw new NoQuedanCartas("No quedan cartas en el tablero");
		}
		Carta carta = cartas[palo][num];
		this.cartas[palo][num].setColocada(false);
		cartasRestantes--;

		return carta;
	}

	public void meterCarta(Carta carta, int posY, int posX) {
		this.cartas[posY][posX] = carta;
	}

	// Comprueba si el hueco de una carta en cuestion esta ocupado, si NO lo esta y PUEDE ser ocupado...
	// ...(carta de la derecha o izquierda ocupada segun toque), coloca la carta en ese hueco
	public boolean comprobarHueco(Carta carta) {
		Palo palo = carta.getPalo();
		int numero = carta.getNumero();

		if (carta.getNumero() == 7) {
			if (!cartas[getNumFilaPalo(palo)][6].isColocada()) {
				cartas[getNumFilaPalo(palo)][6] = carta;
				cartas[getNumFilaPalo(palo)][6].setColocada(true);
				return true;
			}
		}
		else if (carta.getNumero() == 1) {
			if (!cartas[getNumFilaPalo(palo)][0].isColocada() && cartas[getNumFilaPalo(palo)][1].isColocada()) {
				cartas[getNumFilaPalo(palo)][0] = carta;
				cartas[getNumFilaPalo(palo)][0].setColocada(true);
				return true;
			}
		}
		else if (carta.getNumero() < 7) {
			if (!cartas[getNumFilaPalo(palo)][numero-1].isColocada() && cartas[getNumFilaPalo(palo)][numero].isColocada()) {
				cartas[getNumFilaPalo(palo)][numero-1] = carta;
				cartas[getNumFilaPalo(palo)][numero-1].setColocada(true);
				return true;
			}
		}
		else if (carta.getNumero() == 13) {
			if (!cartas[getNumFilaPalo(palo)][12].isColocada() && cartas[getNumFilaPalo(palo)][11].isColocada()) {
				cartas[getNumFilaPalo(palo)][12] = carta;
				cartas[getNumFilaPalo(palo)][12].setColocada(true);
				return true;
			}
		}
		else if (carta.getNumero() > 7) {
			if (!cartas[getNumFilaPalo(palo)][numero-1].isColocada() && cartas[getNumFilaPalo(palo)][numero-2].isColocada()) {
				cartas[getNumFilaPalo(palo)][numero-1] = carta;
				cartas[getNumFilaPalo(palo)][numero-1].setColocada(true);
				return true;
			}
		}

		return false;
	}

	// Devuelve un int que representa la fila en el array bidimensional de un palo.
	public int getNumFilaPalo(Palo palo) {
		if (palo.equals(Palo.TREVOL)) {
			return 0;
		}
		else if (palo.equals(Palo.DIAMANTE)) {
			return 1;
		}
		else if (palo.equals(Palo.CORAZON)) {
			return 2;
		}
		else {
			return 3;
		}
	}


	/* --- TOSTRING --- */
	@Override
	public String toString() {
		String s = "";

		for (int i=0 ; i<Baraja.PALOS ; i++){
			for (int y=0 ; y<Mano.MAXCARTASJUGADOR ; y++) {
				if (cartas[i][y].getNumero() == 0) 		// Si la carta esta vacia NO se muestra
					s += "[     ] ";
				else
					s += cartas[i][y].toString() + " ";
			}
			s += "\n";
		}

		return s;
	}


	/* --- GETERS Y SETERS --- */
	public Carta[][] getCartas() {
		return cartas;
	}
	public void setCartas(Carta[][] tablero) {
		this.cartas = tablero;
	}

}
