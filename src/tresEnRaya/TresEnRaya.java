package tresEnRaya;

import java.util.Scanner;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Juego del 3 en raya
 * @author Aar�n Arg�ello Villar
 */

public class TresEnRaya {
	
	//GLOBAL
	static Scanner teclado = new Scanner(System.in);
	private static Logger logger = LogManager.getLogger(TresEnRaya.class);
	
	public static void main(String[] args) {
		jugar();
	}
	
	public static void jugar() {
		
		//VARIABLES
		char j1 = 'X';
		char j2 = 'O';
		char vacio = '*';
		boolean turno = true;
		char tablero[][];
		tablero = new char[3][3];
		
		//Cargamos el tablero vacio
		CargarTablero(tablero, vacio);
		
		int fila, columna;
		boolean posicionValida;
		boolean correcto; //Booleano para validar
		
		while(!FinPartida(tablero, vacio)) {

			do { 
				//Mostramos el turno
				MostrarTurno(turno);
				logger.info("Se ha mostrado el turno a los jugadores.");
				//Y el tablero
				MostrarTablero(tablero);
				logger.info("Se ha mostrado el tablero del juego.");
				correcto = false;
				System.out.println("");
				//Pedimos fila y columna al jugador
				fila = PedirEntero("Escribe la fila: ");
				columna = PedirEntero("Escribe la columna: ");
				//Comprobamos si la posici�n es valida
				posicionValida = ValidarPosicion(tablero, fila, columna);
				logger.info("Se valida la posici�n introducida por los jugadores.");
				if(posicionValida) {
					if(!PosicionOcupada(tablero, fila, columna, vacio)) {
						correcto = true;
					}else {
						System.out.println("La posici�n est� ocupada.");
						logger.warn("La posici�n introducida por el usuario no es v�lida (est� ocupada).");
					}			
				}else {
					System.out.println("La posici�n no es v�lida.");
					logger.warn("La posici�n introducida por el usuario no es v�lida.");
				}
			}while(!correcto);
			
			if(turno) {
				Insertar(tablero, fila, columna, j1);
				logger.info("El jugador 1 ha introducido una ficha.");
			}else {
				Insertar(tablero, fila, columna, j2);
				logger.info("El jugador 2 ha introducido una ficha.");
			}

			//Cambiamos el turno del jugador
			turno=!turno;
		}
		//Mostramos el tablero y el ganador de la partida
		MostrarTablero(tablero);
		MostrarGanador(tablero, j1, j2, vacio);
		logger.info("Se muestra el tablero mostrando el jugador ganador.");
	}
	
	//M�todo que comprueba quien es el ganador del juego
	public static void MostrarGanador(char [][] matriz, char j1, char j2, char simDef) {
		char simbolo = CoincidenciaFila(matriz, simDef);
		
		if(simbolo != simDef) {
			if(simbolo == j1) {
				System.out.println("Ha ganado el jugador 1 por linea.");
			}else {
				System.out.println("Ha ganado el jugador 2 por linea.");
			}
		}
		
		simbolo = CoincidenciaColumna(matriz, simDef);
				
		if(simbolo != simDef) {
			if(simbolo == j1) {
				System.out.println("Ha ganado el jugador 1 por columna.");
			}else {
				System.out.println("Ha ganado el jugador 2 por columna.");
			}
		}
				
		simbolo = CoincidenciaDiagonal(matriz, simDef);
				
		if(simbolo != simDef) {
			if(simbolo == j1) {
				System.out.println("Ha ganado el jugador 1 por diagonal.");
			}else {
				System.out.println("Ha ganado el jugador 2 por diagonal.");
			}
		}
		
		System.out.println("Hay empate.");
	}
	
	//M�todo que trabaja con la inserci�n de fichas en el tablero
	public static void Insertar(char [][] tablero, int fila, int columna, char simbolo) {
		tablero[fila][columna] = simbolo;
	}
	
	//M�todo que muestra el turno del jugador
	public static void MostrarTurno(boolean turno) {
		if(turno) {
			System.out.println("Es el turno del jugador 1.");
		}else {
			System.out.println("Es el turno del jugador 2.");
		}
	}
	
	//M�todo que pedir� un dato al jugador
	public static int PedirEntero(String mensaje) {
		System.out.println(mensaje);
		int numero = teclado.nextInt();
		return numero;
	}
	
	//M�todo que carga el tablero vac�o
	public static void CargarTablero(char[][] matriz, char simbolo) {
		for(int i=0; i<matriz.length;i++) {
			for(int j=0; j<matriz.length;j++) {
				matriz[i][j] = simbolo;
			}
		}
	}

	//M�todo que comprueba si la posici�n elegida por el jugador es v�lida
	public static boolean ValidarPosicion(char [][] tablero, int fila, int columna) {
		if(fila>=0 && fila<tablero.length && columna >=0 && columna<tablero.length) {
			return true;
		}
		return false;
	}
	
	//M�todo que comprueba si la casilla est� ya ocupada
	public static boolean PosicionOcupada(char[][]tablero, int fila, int columna, char simboloDef) {
		if(tablero[fila][columna] != simboloDef) {
			return true;
		}
		return false;
	}
	

	
	//M�todo que muestra el tablero
	public static void MostrarTablero(char[][] matriz) {
		for(int i=0; i<matriz.length;i++) {
			for(int j=0; j<matriz[0].length;j++) {
				System.out.print(matriz[i][j] + "");
			}
			System.out.println("");
		}
	}
	
	//M�todo que indica el final de la partida
	public static boolean FinPartida(char[][] matriz, char simboloDef) {
		if(TableroLleno(matriz, simboloDef) //Comprobamos si el resultado de la coincidencia es diferente al caracter vacio
						  || CoincidenciaFila(matriz, simboloDef) != simboloDef
						  || CoincidenciaColumna(matriz, simboloDef) != simboloDef
						  || CoincidenciaDiagonal(matriz, simboloDef) != simboloDef) {
			return true;
		}
		return false;
	}
	
	//M�todo que comprueba si la matriz est� llena
	public static boolean TableroLleno(char [][]matriz, char simboloDef) {
		for(int i=0; i<matriz.length;i++) {
			for(int j=0; j<matriz[0].length;j++) {
				if(matriz[i][j]==simboloDef) {
					return false;
				}
			}
		}
		return true; 
	}
	
	//M�todo que comprueba la combinaci�n en la fila
	public static char CoincidenciaFila(char [][] matriz, char simboloDef) {
		
		char simbolo;
		boolean coincidencia;
		
		for(int i=0; i<matriz.length; i++) {
			
			coincidencia = true;
			//Guardamos en simbolo el dato que hay en la primera posici�n de cada fila
			simbolo = matriz[i][0];
			//Si el simbolo de la primera posici�n es distinto al vacio
			if(simbolo != simboloDef) {
				for(int j=1; j<matriz[0].length; j++) { //Recorremos las dos siguientes columnas de la primera fila
					if(simbolo != matriz[i][j]) {
						coincidencia = false;
					}
					
				}
				
				if(coincidencia) {
					return simbolo;
				}
				
			}
		}
		
		return simboloDef;
		
	}
	
	//M�todo que comprueba la combinaci�n en la columna
	public static char CoincidenciaColumna(char [][] matriz, char simboloDef) {
		char simbolo;
		boolean coincidencia;
		
		for(int j=0; j<matriz.length; j++) {
			
			coincidencia = true;
			//Guardamos en simbolo el dato que hay en la primera posici�n del tablero
			simbolo = matriz[0][j];
			//Si el simbolo de la primera posici�n es distinto al vacio
			if(simbolo != simboloDef) {
				for(int i=1; i<matriz[0].length; i++) { //Recorremos las dos siguientes columnas de la primera fila
					if(simbolo != matriz[i][j]) {
						coincidencia = false;
					}
					
				}
				
				if(coincidencia) {
					return simbolo;
				}
				
			}
		}
		
		return simboloDef;
	}
	
	//M�todo que comprueba la combinaci�n en las diagonales
	public static char CoincidenciaDiagonal(char [][] matriz, char simboloDef) {
		char simbolo;
		boolean coincidencia = true;
		
		//Diagonal principal
		simbolo = matriz[0][0]; //Empezamos desde la posici�n superior izquierda
		if(matriz[0][0]!=simboloDef) {
			for(int i=1; i<matriz.length; i++) {
				if(simbolo!=matriz[i][i]) {
					coincidencia = false;
				}
				
			}
			if(coincidencia) {
				return simbolo;
			}
		}
		
		//Diagonal inversa
		simbolo = matriz[0][2]; //Empezamos desde la posici�n superior derecha
		if(simbolo!=simboloDef) {
			for(int i=1, j=1; i<matriz.length; i++, j--) {
				if(simbolo!=matriz[i][j]) {
					coincidencia = false;
				}
				
			}
			if(coincidencia) {
				return simbolo;
			}
		}
		return simboloDef;
	}

}
