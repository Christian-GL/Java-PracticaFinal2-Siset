// Christian Gil Ledesma
// Enlace vídeo: https://www.youtube.com/watch?v=EMiOLZMjey0

package logica;


public class Jugador
{
	/* --- ATRIBUTOS --- */
	private String nombre;
	private Mano mano;


	/* --- CONSTRUCTOR --- */
	public Jugador(String nombre) {
		this.nombre = nombre;
		this.mano = new Mano();
	}


	/* --- METODOS --- */
	public void anyadirCarta(Carta carta) {
		mano.anyadirCarta(carta);
	}

	public void sacarCarta(Carta carta) {
		mano.sacarCarta(carta);
	}


	/* --- TOSTRING --- */
	@Override
	public String toString() {
		return "Jugador [" + nombre + "] ";
	}


	/* --- GETERS Y SETETS --- */
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Mano getMano() {
		return mano;
	}
	public void setMano(Mano mano) {
		this.mano = mano;
	}

}
