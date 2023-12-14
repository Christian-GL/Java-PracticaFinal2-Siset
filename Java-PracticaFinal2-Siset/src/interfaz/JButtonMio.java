// Christian Gil Ledesma
// Enlace vídeo: https://www.youtube.com/watch?v=EMiOLZMjey0

package interfaz;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;


public class JButtonMio extends JButton
{
	/* --- CONSTRUCTOR --- */
	public JButtonMio(String nombre, ActionListener evento) {
		this.setText(nombre);
		this.setFont(new Font("Arial", Font.BOLD, 16));
		this.addActionListener(evento);
		this.setBackground(new Color(0, 135, 255));
		this.setForeground(Color.BLACK);
	}


	/* --- METODOS --- */
	public void interactuable(boolean interactuable) {
		if (interactuable) {
			this.setBackground(new Color(0, 135, 255));
			this.setForeground(Color.BLACK);
			this.setEnabled(true);
		}
		else {
			this.setBackground(new Color(245, 245, 245));
			this.setForeground(Color.LIGHT_GRAY);
			this.setEnabled(false);
		}
	}
}
