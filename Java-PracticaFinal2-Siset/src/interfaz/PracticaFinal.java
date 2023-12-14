// Christian Gil Ledesma
// Enlace vídeo: https://www.youtube.com/watch?v=EMiOLZMjey0

package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logica.Baraja;
import logica.Jugador;
import logica.Mano;
import logica.Tablero;
import logica.Baraja.NoQuedanCartas;
import logica.Carta;


public class PracticaFinal
{
	/* --- ATRIBUTOS --- */
	// INTERFAZ
	private JFrame ventana;										// JFrame que contendra todos los elementos del programa
	private JFrame ventanaGanador;								// JFrame que aparecera cuando un jugador gane la partida
	private Container panelContenidos;							// JPanel del JFrame "ventana"
	public static final int VENTANADIMX = 1200;					// Dimension X del JFrame "ventana"
	public static final int VENTANADIMY = 900;					// Dimension Y del JFrame "ventana"
	public static final int CARTADIMX = 80;						// 500 px en la imagen original (se redimensiona a lo que valga "cartaDimX")
	public static final int CARTADIMY = 116;					// 726 px en la imagen original (se redimensiona a lo que valga "cartaDimY")
	public static final Color COLORFONDO = new Color(0, 102, 0);		// Color de fondo del tablero de juego (verde claro)
	public static final Color COLORSINCARTA = new Color(28, 80, 39);	// Color de un hueco vacío para cartas (verde oscuro)
	private JPanel panelSuperior;										// JPanel que contiene 3 subpaneles (puntuacionesIA, tablero, cartasPlayer)
	private TableroEtiquetasMio panelPuntuacionesIA;			// Array de Etiquetas (JLabels modificados) que mostrara las puntuaciones de cada IA (situado en el "panelSuperior")
	private TableroEtiquetasMio panelTableroJuego;				// Array de Etiquetas (JLabels modificados) que mostrara el tablero de juego (situado en el "panelSuperior")
	private TableroEtiquetasMio panelCartasPlayer;				// Array de Etiquetas (JLabels modificados) que mostrara las cartas de la mano del Player (situado en el "panelSuperior")
	private JPanel panelInferior;								// JPanel que contiene 2 subpaneles ("panelBotones", "panelTexto")
	private JLabel numCartasPlayer;								// JLabel que muestra el numero de cartas restantes del jugador (situado en el "panelTexto")
	private JPanel panelBotones;								// JPanel que contendra todos los botones para interactuar con el JFrame "ventana" (situado en el "panelInferior")
	private JButtonMio botonMezclar;							// JButton que mezclara las cartas del tablero de juego (situado en el "panelBotones")
	private JButtonMio botonJugar;								// JButton que iniciara un nuevo juego (situado en el "panelBotones")
	private JButtonMio botonReiniciar;							// JButton que reiniciara toda la interfaz y toda la logica a como estaba al principio de la ejecucion del programa (situado en el "panelBotones")
	private JButtonMio botonPasar;								// JButton que permitira pasar el turno del Player (situado en el "panelBotones")
	private JButtonMio botonTurnoJugador;						// JButton que permitira pasar el turno de las IAs (situado en el "panelBotones")
	private JTextField labelTexto;								// JLabel que mostrara los acontecimientos de la partida
	private ActionListener gestorEventosLogica;					// Gestor de eventos de botones
	private MouseListener gestorEventosMouse;					// Gestor de eventos de raton
	// LOGICA
	private static final int NUMJUGADORES = 4;								// Jugadores totales de la partida
	private Jugador[] jugadoresLogica = new Jugador[NUMJUGADORES];			// Jugadores totales (IAs + Player) ("jugadoresLogica[0]" siempre sera el "Player")
	private Baraja barajaLogica = new Baraja();								// Baraja con las 52 Cartas y todas sus funcionalidades
	private Tablero tableroLogica = new Tablero();							// Tablero de juego con todas sus funcionalidades
	private boolean turnoPlayer = true;										// Indica si es el turno del jugador humano "Player" (si es true)
	private int turnoIA = 1;												// Indica a que IA pertenece el siguiente turno de IA


	/* --- CONSTRUCTOR --- */
	public PracticaFinal() {
		crearJugadores();										// Creamos al jugador "Player" y a las IAs
		tableroLogica.llenarTablero(barajaLogica);				// Llenamos todas las filas y columnas del tablero de juego con las cartas ordenadas

		anyadirEventos();										// Anyadimos los diferentes eventos al programa

		configurarJFrame();										// Configuramos el JFrame "ventana"
		configurarPanelSuperior();								// Configuramos el JPanel "panelSuperior"
		configurarPanelInferior();								// Configuramos el JPanel "panelInferior"

		ventana.setContentPane(panelContenidos);
		ventana.setVisible(true);
	}


	/* --- METODOS PRINCIPALES DE LA INTERFAZ --- */
	// Gestion de Eventos
	private void anyadirEventos() {
		gestorEventosLogica = new ActionListener()  { 
			@Override
			public void actionPerformed(ActionEvent evento)  { 
				switch (evento.getActionCommand()) {

				case "Mezclar":
					barajaLogica = new Baraja();						// Creamos una nueva baraja (con las cartas ordenadas)...
					barajaLogica.mezclarBaraja();						// ...las desordenamos...
					tableroLogica.llenarTablero(barajaLogica);			// ...y las colocamos en el tablero de juego
					labelTexto.setText("Baraja mezclada");
					rePintarCaraCentro(2);
					rePintarBotones(OpcionRepaintBotones.MEZCLAR_JUGAR_REINICIAR);
					break;

				case "Jugar":
					repartirCartas();
					for (int y=0 ; y<Mano.MAXCARTASJUGADOR ; y++) {
						panelCartasPlayer.setSeleccionable(true, 0, y);
					}
					rePintarCaraNorte(2);
					barajaLogica = new Baraja();						// Creamos una nueva baraja (con las cartas ordenadas) y la introducimos en el "tableroLogica"...
					tableroLogica.llenarTablero(barajaLogica);			// ...para tener ordenadas las posiciones de las cartas para cuando haya que comprobar si estan colocadas
					labelTexto.setText("Comienza el juego");
					rePintarCaraCentro(1);
					rePintarCaraSur(2);
					rePintarBotones(OpcionRepaintBotones.PASAR_REINICIAR);
					break;

				case "Reiniciar":
					barajaLogica = new Baraja();						// Creamos una nueva baraja (con las cartas ordenadas) y la introducimos en el "tableroLogica"...
					tableroLogica.llenarTablero(barajaLogica);			// ...para tener ordenadas las posiciones de las cartas para cuando haya que comprobar si estan colocadas
					for (int i=0 ; i<jugadoresLogica.length ; i++) {	// Reseteamos las manos del "Player" y las IAs
						jugadoresLogica[i].setMano(new Mano());
					}
					turnoPlayer = true;
					turnoIA  = 1;
					labelTexto.setText("Antes de jugar mezcla la baraja");
					rePintarCaraNorte(1);
					rePintarCaraCentro(2);
					rePintarCaraSur(1);
					rePintarBotones(OpcionRepaintBotones.MEZCLAR_NOJUGAR_NOREINICIAR);
					break;

				case "Pasar":
					turnoPlayer = false;
					rePintarBotones(OpcionRepaintBotones.TURNOJUGADOR_REINICIAR);
					break;

				case "Turno jugador":
					turnoIA(turnoIA);					// Realizamos un turno de la IA a la que le toque
					comprobarGanador(turnoIA);			// Comprobamos si esa IA ha ganado
					if (turnoIA != 3) {					// Si la IA que ha hecho su turno no es la ultima...
						turnoIA++;						// ...indica la siguiente IA para jugar como la siguiente en orden (+1)
					}
					else {								// Si la IA que ha hecho su turno es la ultima...
						turnoIA = 1;					// ...indica que a la siguiente IA a la que le toque para jugar sera la primera
						turnoPlayer = true;			// ...y que el siguiente turno sera el del "Player"
						rePintarBotones(OpcionRepaintBotones.PASAR_REINICIAR);
					}
					break;

				case "Ok":
					ventanaGanador.dispose();			// Cierra esta ventana emergente
					rePintarBotones(OpcionRepaintBotones.NOTURNOJUGADOR_REINICIAR);
					break;
				}
			}
		};

		gestorEventosMouse = new MouseListener() {
			@Override
			public void mousePressed(MouseEvent evento) {
				Carta carta;
				if (turnoPlayer) {
					for (int y=0 ; y<Mano.MAXCARTASJUGADOR ; y++) {																					// Por cada carta en la mano del "Player"
						if (evento.getSource() == panelCartasPlayer.getEtiquetas()[0][y]) {															// Buscamos el label que hemos clickado
							carta = panelCartasPlayer.getEtiquetas()[0][y].getCarta();																// Guardamos la carta seleccionada en una variable para mas comodidad al manipularla
							if (tableroLogica.comprobarHueco(carta) == true) {																		// Si la carta se puede colocar
								jugadoresLogica[0].getMano().sacarCarta(carta);																		// Sacamos la carta de la mano del jugador
								numCartasPlayer.setText(String.valueOf(jugadoresLogica[0].getMano().getCartasRestantes()));							// Indicamos el total de cartas del jugador
								panelCartasPlayer.cambiarImagenEtiqueta(1, 0, y);																	// Hacemos desaparecer la carta cambiando la imagen por una imagen del mismo color que el fondo
								panelTableroJuego.setCartaToEtiqueta(carta, tableroLogica.getNumFilaPalo(carta.getPalo()), (carta.getNumero()-1));		// Metemos la carta en el label correspondiente del "panelTableroJuego"
								panelTableroJuego.cambiarImagenEtiqueta(3, tableroLogica.getNumFilaPalo(carta.getPalo()), (carta.getNumero()-1));		// Cambiamos la imagen de ese JLabel para mostrar la carta
								labelTexto.setText("Colocas la carta " + carta.toString());
								turnoPlayer = false;
								rePintarBotones(OpcionRepaintBotones.TURNOJUGADOR_REINICIAR);
								comprobarGanador(0);
							}
							else if (carta.isColocada()) {														// Si se selecciona un hueco de carta vacio (carta ya colocada)
								labelTexto.setText("Ya has colocado esta carta " + carta.toString());
							}
							else {																				// Si no se puede colocar la carta
								labelTexto.setText("NO puedes colocar la carta " + carta.toString());
								Toolkit.getDefaultToolkit().beep();												// Pitido que indica que no se puede seleccionar una carta
							}
							break;
						}
					}
				}
				else {
					labelTexto.setText("NO es tu turno");
					Toolkit.getDefaultToolkit().beep();															// Pitido que indica que no se puede seleccionar una carta
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}   
		};
	}

	// JFrame ventana
	private void configurarJFrame() {
		ventana = new JFrame();
		ventana.setSize(VENTANADIMX, VENTANADIMY);
		ventana.setTitle("Practica final - Siset");
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.setLayout(null);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

		panelContenidos = ventana.getContentPane();
		panelContenidos.setLayout(new BorderLayout());
	}

	// JPanel panelSuperior (donde se desarrolla el juego)
	private void configurarPanelSuperior() {
		// CONFIGURAR "panelSuperior"
		panelSuperior = new JPanel();
		panelSuperior.setBackground(COLORFONDO);
		panelSuperior.setLayout(new BorderLayout());
		panelContenidos.add(panelSuperior, BorderLayout.CENTER);

		// CONFIGURAR Cara Norte
		panelPuntuacionesIA = new TableroEtiquetasMio(1, 9);
		panelPuntuacionesIA.setPreferredSize(new Dimension(VENTANADIMX/6, VENTANADIMY/6));	
		panelSuperior.add(panelPuntuacionesIA, BorderLayout.NORTH);
		rePintarCaraNorte(1);

		// CONFIGURAR Cara Centro
		panelTableroJuego = new TableroEtiquetasMio(Baraja.PALOS, Mano.MAXCARTASJUGADOR);
		panelTableroJuego.setBackground(COLORFONDO);
		panelSuperior.add(panelTableroJuego, BorderLayout.CENTER);
		rePintarCaraCentro(2);

		// CONFIGURAR Cara Sur
		JPanel panelSur = new JPanel();													// JPanel que contendrá el label "numCartasPlayer" y y el JPanel "cartasPlayer"
		numCartasPlayer = new JLabel();
		panelCartasPlayer = new TableroEtiquetasMio(1, Mano.MAXCARTASJUGADOR);

		for (int y=0 ; y<Mano.MAXCARTASJUGADOR ; y++) {									// Anyadimos los eventos de las cartas del Player para que se pueda interactuar con los JLabel
			panelCartasPlayer.setEventoMouse(gestorEventosMouse, 0, y);
		}
		panelCartasPlayer.setBackground(COLORFONDO);
		numCartasPlayer.setBackground(COLORFONDO);
		numCartasPlayer.setPreferredSize(new Dimension(VENTANADIMX/35, VENTANADIMY/35));
		numCartasPlayer.setFont(new Font("TimesRoman", Font.ITALIC|Font.BOLD, 24));
		numCartasPlayer.setForeground(Color.WHITE);
		numCartasPlayer.setText(String.valueOf(jugadoresLogica[0].getMano().getCartasRestantes()));
		numCartasPlayer.setBorder(BorderFactory.createEmptyBorder(0, (int)(CARTADIMX/2.5), 5, 0));					// Alineacion del texto del JLabel de "numCartasPlayer"
		panelSur.setBackground(COLORFONDO);
		panelSur.setLayout(new BorderLayout());
		panelSur.add(numCartasPlayer, BorderLayout.NORTH);
		panelSur.add(panelCartasPlayer, BorderLayout.CENTER);
		panelSur.setPreferredSize(new Dimension(VENTANADIMX/6, VENTANADIMY/6));
		panelSuperior.add(panelSur, BorderLayout.SOUTH);
		rePintarCaraSur(1);
	}

	// JPanel panelInferior (Donde estan los botones y el cuadro de texto)
	private void configurarPanelInferior() {
		// CONFIGURAR "panelInferior"
		panelInferior = new JPanel();
		panelInferior.setBackground(COLORFONDO);
		panelInferior.setLayout(new BorderLayout());
		panelContenidos.add(panelInferior, BorderLayout.SOUTH);

		// CONFIGURAR "panelBotones"
		panelBotones = new JPanel();
		botonMezclar = new JButtonMio("Mezclar", gestorEventosLogica);
		botonJugar = new JButtonMio("Jugar", gestorEventosLogica);
		botonReiniciar = new JButtonMio("Reiniciar", gestorEventosLogica);
		botonPasar = new JButtonMio("Pasar", gestorEventosLogica);
		botonTurnoJugador = new JButtonMio("Turno jugador", gestorEventosLogica);
		rePintarBotones(OpcionRepaintBotones.MEZCLAR_NOJUGAR_NOREINICIAR);

		// CONFIGURAR "panelTexto"
		JPanel panelTexto = new JPanel();
		labelTexto = new JTextField();
		labelTexto.setText("Antes de jugar mezcla la baraja");
		labelTexto.setFont(new Font("Arial", Font.BOLD, 12));
		panelTexto.setLayout(new GridLayout());						// Para que el JTextField ocupe todo de izquierda a derecha
		panelTexto.add(labelTexto);
		panelInferior.add(panelTexto, BorderLayout.SOUTH);

	}

	// JFrame que emerge cuando se quiere mostrar un jugador ganador
	private void ventanaEmergente(int jugadorGanador) {
		ventanaGanador = new JFrame("¡¡¡  Uep  !!!"); 
		Container panelContenidos = ventanaGanador.getContentPane();
		JLabel label = new JLabel();
		JButtonMio botonOk = new JButtonMio("Ok", gestorEventosLogica);
		//		Image imgEscalada;
		String rutaImagen;

		ventanaGanador.setSize(new Dimension(250, 250));
		panelContenidos.setLayout(new BorderLayout()); 
		label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		label.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
		panelContenidos.setBackground(Color.LIGHT_GRAY);
		panelContenidos.add(label, BorderLayout.CENTER);
		panelContenidos.add(botonOk, BorderLayout.SOUTH);

		rutaImagen = "cartas\\jug" + jugadorGanador + "Riu.png";
		//		imgEscalada = new ImageIcon(rutaImagen).getImage().getScaledInstance(ventanaEmergente.getWidth(), ventanaEmergente.getHeight(), java.awt.Image.SCALE_DEFAULT);
		label.setIcon(new ImageIcon(rutaImagen));
		label.setText("¡¡¡  Has ganado [" + jugadoresLogica[jugadorGanador].getNombre() + "]  !!!");

		ventanaGanador.setResizable(false);
		ventanaGanador.setLocationRelativeTo(null);
		//		ventanaEmergente.pack();
		ventanaGanador.setVisible(true);
	}


	/* --- METODOS SECUNDARIOS LA INTERFAZ --- */
	private void rePintarCaraNorte(int opcion) {
		switch (opcion) {
		case 1:
			for (int i=1 ; i<jugadoresLogica.length ; i++) {
				panelPuntuacionesIA.getEtiquetas()[0][i*2].cambiarImagen(2);
				panelPuntuacionesIA.getEtiquetas()[0][i*2].setText(String.valueOf(jugadoresLogica[i].getMano().getCartasRestantes()));
				panelPuntuacionesIA.getEtiquetas()[0][i*2].setFont(new Font("TimesRoman", Font.ITALIC|Font.BOLD, 60));
				panelPuntuacionesIA.getEtiquetas()[0][i*2].setForeground(Color.WHITE);
			}	
			break;

		case 2:
			for (int i=1 ; i<jugadoresLogica.length ; i++) {
				panelPuntuacionesIA.getEtiquetas()[0][i*2].cambiarImagen(4);
				panelPuntuacionesIA.getEtiquetas()[0][i*2].setText(String.valueOf(jugadoresLogica[i].getMano().getCartasRestantes()));
			}	
			break;
		}
	}

	private void rePintarCaraCentro(int opcion) {
		switch (opcion) {
		// Cambiamos la imagen de todas los espacios de cartas del tablero a "colorSinCarta" (verde oscuro simulando hueco vacio)
		case 1:
			for (int x=0 ; x<Baraja.PALOS ; x++) {
				for (int y=0 ; y<Mano.MAXCARTASJUGADOR ; y++) {
					panelTableroJuego.cambiarImagenEtiqueta(2, x, y);
				}
			}
			break;

			// Cambiamos las cartas del "panelTableroJuego" para que muestren el contenido de "tableroLogica"
		case 2:
			for (int x=0 ; x<Baraja.PALOS ; x++) {
				for (int y=0 ; y<Mano.MAXCARTASJUGADOR ; y++) {
					panelTableroJuego.setCartaToEtiqueta(tableroLogica.getCartas()[x][y], x, y);			// Metemos una la carta de la posicion correspondiente del tablero en el "panelTableroJuego"
					panelTableroJuego.cambiarImagenEtiqueta(3, x, y);										// Decimos que se muestre la carta de ese label en concreto
				}
			}
			break;
		}
	}

	private void rePintarCaraSur(int opcion) {
		switch (opcion) {
		// Mostramos solamente la primara carta como hueco sin carta (color verde fuerte "colorSinFondo")
		case 1:
			numCartasPlayer.setText(String.valueOf(jugadoresLogica[0].getMano().getCartasRestantes()));				// Indicamos el total de cartas del jugador
			for (int y=0 ; y<Mano.MAXCARTASJUGADOR ; y++) {
				// Si es la primera posicion ("colorSinFondo")
				if (y == 0) {
					panelCartasPlayer.cambiarImagenEtiqueta(2, 0, y);
				}
				// El resto de posiciones simulando que no hay carta
				else {
					panelCartasPlayer.cambiarImagenEtiqueta(1, 0, y);
				}
			}
			break;

			//
		case 2:
			numCartasPlayer.setText(String.valueOf(jugadoresLogica[0].getMano().getCartasRestantes()));				// Indicamos el total de cartas del jugador
			for (int y=0 ; y<Mano.MAXCARTASJUGADOR ; y++) {
				// Si la carta aun esta en la mano y se puede introducir en el tablero (en caso de que sea posible)
				if (!jugadoresLogica[0].getMano().getCartas()[y].isColocada()) {
					//					String.valueOf(jugadoresLogica[0].getMano().getCartas()[0]);
					panelCartasPlayer.setCartaToEtiqueta(jugadoresLogica[0].getMano().getCartas()[y], 0, y);
					panelCartasPlayer.cambiarImagenEtiqueta(3, 0, y);
				}
				// Si ya se selecciono y coloco en el tablero previamente
				else {
					panelCartasPlayer.cambiarImagenEtiqueta(1, 0, y);
					panelCartasPlayer.setSeleccionable(false, 0, y);
				}
			}
			break;
		}
	}

	private void rePintarBotones(OpcionRepaintBotones opcion) {
		// Botones: "mezclar", "jugar" (NO interactuable), "reiniciar" (NO interactuable)
		if (opcion.equals(OpcionRepaintBotones.MEZCLAR_NOJUGAR_NOREINICIAR)) {
			panelBotones.removeAll();
			botonMezclar.interactuable(true);
			botonJugar.interactuable(false);
			botonReiniciar.interactuable(false);
			panelBotones.add(botonMezclar);
			panelBotones.add(botonJugar);
			panelBotones.add(botonReiniciar);
			panelBotones.repaint();
			panelBotones.validate();
			panelInferior.add(panelBotones, BorderLayout.NORTH);
		}
		// Botones: "mezclar", "jugar", "reiniciar"
		else if (opcion.equals(OpcionRepaintBotones.MEZCLAR_JUGAR_REINICIAR)) {
			panelBotones.removeAll();
			botonMezclar.interactuable(true);
			botonJugar.interactuable(true);
			botonReiniciar.interactuable(true);
			panelBotones.add(botonMezclar);
			panelBotones.add(botonJugar);
			panelBotones.add(botonReiniciar);
			panelBotones.repaint();
			panelBotones.validate();
			panelInferior.add(panelBotones, BorderLayout.NORTH);
		}
		// Botones: "pasar", "reiniciar"
		else if (opcion.equals(OpcionRepaintBotones.PASAR_REINICIAR)) {
			panelBotones.removeAll();
			botonPasar.interactuable(true);
			botonReiniciar.interactuable(true);
			panelBotones.add(botonPasar);
			panelBotones.add(botonReiniciar);
			panelBotones.repaint();
			panelBotones.validate();
			panelInferior.add(panelBotones, BorderLayout.NORTH);
		}
		// Botones: "TurnoJugador", "reiniciar"
		else if (opcion.equals(OpcionRepaintBotones.TURNOJUGADOR_REINICIAR)) {
			panelBotones.removeAll();
			botonTurnoJugador.interactuable(true);
			botonReiniciar.interactuable(true);
			panelBotones.add(botonTurnoJugador);
			panelBotones.add(botonReiniciar);
			panelBotones.repaint();
			panelBotones.validate();
			panelInferior.add(panelBotones, BorderLayout.NORTH);
		}
		// Botones: "botonTurnoJugador" (NO interactuable), "reiniciar"
		else {
			panelBotones.removeAll();
			botonTurnoJugador.interactuable(false);
			botonReiniciar.interactuable(true);
			panelBotones.add(botonTurnoJugador);
			panelBotones.add(botonReiniciar);
			panelBotones.repaint();
			panelBotones.validate();
			panelInferior.add(panelBotones, BorderLayout.NORTH);
		}
	}


	/* --- METODOS DE LOGICA --- */
	// Crea las IAs y el jugador humano "Player"
	private void crearJugadores() {
		// Creacion de los jugadores ("Player" + IAs)
		for (int i=0 ; i<NUMJUGADORES ; i++) {
			jugadoresLogica[i] = new Jugador("IA-" + i);
		}
		// El jugador 0 siempre se considerara el jugador humano (Player)
		jugadoresLogica[0].setNombre("Player");
	}

	// Reparte las cartas del tablero de juego entre los diferentes jugadores
	private void repartirCartas() {
		// Bucle que reparte las cartas entre los jugadores
		Mano mano;
		try {
			for (int y=0 ; y<NUMJUGADORES ; y++) {
				mano = new Mano();
				for (int x=0 ; x<Mano.MAXCARTASJUGADOR ; x++) {
					mano.anyadirCarta(tableroLogica.sacarCarta(y, x));
				}
				jugadoresLogica[y].setMano(mano);
			}
		}
		catch (NoQuedanCartas e) {
			e.printStackTrace();
		}
	}

	// Realiza el turno de la IA pasada por parámetro
	private void turnoIA(int numIA) {
		Carta carta = null;
		boolean colocada = false;
		for (int y=0 ; y<Mano.MAXCARTASJUGADOR ; y++) {
			carta = jugadoresLogica[numIA].getMano().getCartas()[y];												// Guardamos la carta seleccionada en una variable para mas comodidad al manipularla
			if (tableroLogica.comprobarHueco(carta) == true) {														// Si la carta se puede colocar
				jugadoresLogica[numIA].getMano().sacarCarta(carta);																					// Sacamos la carta de la mano de la IA
				panelPuntuacionesIA.getEtiquetas()[0][turnoIA*2].setText(String.valueOf(jugadoresLogica[turnoIA].getMano().getCartasRestantes()));	// Indicamos el total de cartas de la IA
				panelTableroJuego.setCartaToEtiqueta(carta, tableroLogica.getNumFilaPalo(carta.getPalo()), (carta.getNumero()-1));					// Metemos la carta en el label correspondiente del "panelTableroJuego"
				panelTableroJuego.cambiarImagenEtiqueta(3, tableroLogica.getNumFilaPalo(carta.getPalo()), (carta.getNumero()-1));					// Cambiamos la imagen de ese JLabel para mostrar la carta
				colocada = true;
				break;
			}
		}
		if (colocada == true) {
			labelTexto.setText("La [IA" + numIA + "] ha colocado la carta " + carta.toString());
		}
		else {
			labelTexto.setText("La [IA" + numIA + "] no tenia ninguna carta disponible para poner");
		}
	}

	// Comprueba si un jugador en concreto se ha quedado sin cartas, si es asi indica que ha ganado
	private boolean comprobarGanador(int numJugador) {
		if (jugadoresLogica[numJugador].getMano().getCartasRestantes() == 0) {
			labelTexto.setText("El jugador [" + jugadoresLogica[numJugador].getNombre() + "] ha ganado");
			ventanaEmergente(numJugador);
			rePintarBotones(OpcionRepaintBotones.NOTURNOJUGADOR_REINICIAR);
			return true;
		}
		return false;
	}


	/* --- MAIN --- */
	public static void main(String[] args)
	{
		new PracticaFinal();
	}

}
