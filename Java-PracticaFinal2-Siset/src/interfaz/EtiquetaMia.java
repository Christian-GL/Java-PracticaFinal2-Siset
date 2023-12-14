// Christian Gil Ledesma
// Enlace vídeo: https://www.youtube.com/watch?v=EMiOLZMjey0

package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import logica.Carta;


public class EtiquetaMia extends JLabel
{
	/* --- ATRIBUTOS --- */
	private int dimX;
	private int dimY;
	private Carta carta;
	private boolean seleccionable = false;


	/* --- CONSTRUCTOR --- */
	public EtiquetaMia(int imagenInicial) {
		this.dimX = PracticaFinal.CARTADIMX;
		this.dimY = PracticaFinal.CARTADIMY;
		this.setSize(dimX, dimY);
		this.setBackground(PracticaFinal.COLORFONDO);

		this.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		this.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
		this.setHorizontalTextPosition(JLabel.CENTER);
		this.setVerticalTextPosition(JLabel.CENTER);
//						this.setBorder(new LineBorder(Color.BLACK, 1));
//						this.setLayout(new BorderLayout());

		cambiarImagen(imagenInicial);
	}

	/* --- METODOS --- */
	// Cambia la imagen de la etiqueta dependiendo del parametro numerico pasado
	public void cambiarImagen(int opcionImagen) {
		switch(opcionImagen) {
		// Fondo verde claro (simulando que no hay nada)
		case 1:
			this.setIcon(redimensionarImagen(new ImageIcon("cartas\\verdeClaro.png")));
			break;

			// Fondo verde oscuro (simulando hueco sin carta)
		case 2:
			this.setIcon(redimensionarImagen(new ImageIcon("cartas\\colorSinCarta.png")));
			break;

			// Imagen de la carta en cuestion
		case 3:
			this.setIcon(redimensionarImagen(new ImageIcon(carta.getRutaImagen())));
			break;

			// Imagen del dorso de las cartas
		case 4:
			this.setIcon(redimensionarImagen(new ImageIcon("cartas\\card_back_blue.png")));
			break;
		}
	}

	// Redimensionamiento de una imagen en base a las dimensiones de una etiqueta
	private ImageIcon redimensionarImagen(ImageIcon imagen) {
		Image imgEscalada = imagen.getImage().
				getScaledInstance(dimX, dimY, java.awt.Image.SCALE_DEFAULT);

		return new ImageIcon(imgEscalada); 
	}


	/* --- GETERS Y SETERS --- */
	public int getDimX() {
		return dimX;
	}
	public void setDimX(int dimX) {
		this.dimX = dimX;
	}

	public int getDimY() {
		return dimY;
	}
	public void setDimY(int dimY) {
		this.dimY = dimY;
	}

	public Carta getCarta() {
		return carta;
	}
	public void setCarta(Carta carta) {
		this.carta = carta;
	}

	public boolean isSeleccionable() {
		return seleccionable;
	}
	public void setSeleccionable(boolean seleccionable) {
		this.seleccionable = seleccionable;
	}    

}
