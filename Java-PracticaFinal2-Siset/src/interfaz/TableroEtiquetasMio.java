// Christian Gil Ledesma
// Enlace vídeo: https://www.youtube.com/watch?v=EMiOLZMjey0

package interfaz;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import logica.Carta;


public class TableroEtiquetasMio extends JPanel
{
	/* --- ATRIBUTOS --- */
	private EtiquetaMia[][] etiquetas;
	private static int FILAS;
	private static int COLUMNAS;


	/* --- CONSTRUCTOR --- */
	public TableroEtiquetasMio(int filas, int columnas) {
		super();
		TableroEtiquetasMio.FILAS = filas;
		TableroEtiquetasMio.COLUMNAS = columnas;
		this.etiquetas = new EtiquetaMia[FILAS][COLUMNAS];

		this.setLayout(new GridLayout(FILAS, COLUMNAS, 0, 0));
		this.setBackground(PracticaFinal.COLORFONDO);

		// Creamos etiquetas con la imagen por defecto "verdeClaro" que simula que no hay carta
		for (int x=0 ; x<FILAS ; x++) {
			for (int y=0 ; y<COLUMNAS ; y++) {
				this.etiquetas[x][y] = new EtiquetaMia(1);
				this.add(this.etiquetas[x][y]);
			}    
		}
	}


	/* --- METODOS --- */
	// Setea un evento de raton a una etiqueta
	public void setEventoMouse(MouseListener evento, int posX, int posY) {
		this.etiquetas[posX][posY].addMouseListener(evento);
	}

	// Setea una carta a una etiqueta
	public void setCartaToEtiqueta(Carta carta, int posX, int posY) {
		this.etiquetas[posX][posY].setCarta(carta);
	}

	// Cambia la imagen de una etiqueta
	public void cambiarImagenEtiqueta(int imagen, int posX, int posY) {
		this.etiquetas[posX][posY].cambiarImagen(imagen);
	}

	// Setea si una etiqueta tiene una carta que se pueda colocar en el tablero de juego
	public void setSeleccionable(boolean seleccionable, int posX, int posY) {
		this.etiquetas[posX][posY].setSeleccionable(seleccionable);
	}


	/* --- GETERS Y SETERS --- */
	public EtiquetaMia[][] getEtiquetas() {
		return etiquetas;
	}
	public void setEtiquetas(EtiquetaMia[][] etiquetas) {
		this.etiquetas = etiquetas;
	}

	public static int getFILAS() {
		return FILAS;
	}
	public static void setFILAS(int fILAS) {
		FILAS = fILAS;
	}

	public static int getCOLUMNAS() {
		return COLUMNAS;
	}
	public static void setCOLUMNAS(int cOLUMNAS) {
		COLUMNAS = cOLUMNAS;
	}

}
