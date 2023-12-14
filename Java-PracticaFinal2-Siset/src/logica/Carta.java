// Christian Gil Ledesma
// Enlace vídeo: https://www.youtube.com/watch?v=EMiOLZMjey0

package logica;


public class Carta
{
	/* --- ATRIBUTOS --- */
	private int numero;
	private Palo palo;
	private boolean colocada;				// Indica si la carta ya ha sido colocada en el tablero de juego
	private String rutaImagen;


	/* --- CONSTRUCTOR --- */
	public Carta(Palo palo, int numero) {
		this.numero = numero;
		this.palo = palo;
		this.colocada = false;
		rellenarRutasCartas();
	}


	/* --- METODOS --- */
	private void rellenarRutasCartas() {
		if (this.palo.equals(Palo.TREVOL)) {
			this.rutaImagen = "cartas\\" + this.numero + "_of_clubs.png";
		}
		else if (this.palo.equals(Palo.DIAMANTE)) {
			this.rutaImagen = "cartas\\" + this.numero + "_of_diamonds.png";
		}
		else if (this.palo.equals(Palo.CORAZON)) {
			this.rutaImagen = "cartas\\" + this.numero + "_of_hearts.png";
		}
		else {
			this.rutaImagen = "cartas\\" + this.numero + "_of_spades.png";
		}
	}


	/* --- TOSTRING --- */
	@Override
	public String toString() {
		if (numero < 10)
			return "[0" + numero  + " " + palo + "]";
		else
			return "[" + numero  + " " + palo + "]";
	}


	/* --- GETERS Y SETERS --- */
	public int getNumero() {
		return numero;
	}
	public void setNumero(int num) {
		this.numero = num;
	}

	public Palo getPalo() {
		return palo;
	}
	public void setPalo(Palo palo) {
		this.palo = palo;
	}

	public String getRutaImagen() {
		return rutaImagen;
	}
	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}

	public boolean isColocada() {
		return colocada;
	}
	public void setColocada(boolean colocada) {
		this.colocada = colocada;
	}

}
