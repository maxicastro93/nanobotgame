package juego;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private BufferedImage fondo;
	NanoBot nave;
	Proyectil[] bala;
	Celula[] celula_buena;
	Celula[] celula_mala;
	Celula[] anticuerpo;
	Random random = new Random();
	int proporcion_celulas_buenas;
	int proporcion_celulas_malas;
	int cantidad_celulas_buenas = 0;
	int cantidad_celulas_malas = 0;
	int disparos_actuales=0;
	private int tiempo_disparo = 0; //en milisegundos
	private int fin_juego = 0; 
	private int atrapo_anticuerpo = 0; //0=NO , 1=SI
	private int tiempo_anticuerpo = (600 + random.nextInt(401))*10; 
	private int anticuerpo_orden = 0;
	private int ancho_ventana = 800;
	private int alto_ventana = 600;
	
	// Variables y métodos propios de cada grupo
	// ...
	
	Juego()
	{	
		try {
			fondo = ImageIO.read(new File("fondo.png"));
		} catch(IOException e) {
           e.printStackTrace();
		}
		proporcion_celulas_buenas = 15 - random.nextInt(5);
		proporcion_celulas_malas = 6 - random.nextInt(4);
		int Proporcion_celulas_temp; 
		int level_temp;
		int x_temp;
		int y_temp;
		
		celula_buena = new Celula[proporcion_celulas_buenas * 2]; //Máximo de celulas posible aunque siga con el suero constructor. Para evitar superar una vez alcanzado este limite debera haber un mensaje diciendo que se llego al límite de células posible
		celula_mala = new Celula[proporcion_celulas_buenas * 2];
		anticuerpo = new Celula[2]; //Maximo de anticuerpos en el juego
		
		bala = new Proyectil[12]; //Máximo de balas ya sea de cualquier tipo
		
		nave= new NanoBot((ancho_ventana/2), (alto_ventana/2));
		
		Proporcion_celulas_temp = proporcion_celulas_buenas;
		for(int i = 0; i < celula_buena.length; i++){
			if (Proporcion_celulas_temp != 0){
				if (Proporcion_celulas_temp <= 3) {
					level_temp = Proporcion_celulas_temp;
					Proporcion_celulas_temp = 0;
				} else {
					level_temp = 1 + random.nextInt(3);
					Proporcion_celulas_temp = Proporcion_celulas_temp - level_temp;
				}
				celula_buena[i] = new Celula(random.nextInt(ancho_ventana), random.nextInt(alto_ventana), random.nextInt(7), level_temp, "buena");
				cantidad_celulas_buenas++;
			}
		}
		
		Proporcion_celulas_temp = proporcion_celulas_malas;
		for(int i = 0; i < celula_mala.length; i++){
			if (Proporcion_celulas_temp != 0){
				if (Proporcion_celulas_temp <= 3) {
					level_temp = Proporcion_celulas_temp;
					Proporcion_celulas_temp = 0;
				} else {
					level_temp = 1 + random.nextInt(3);
					Proporcion_celulas_temp = Proporcion_celulas_temp - level_temp;
				}
				x_temp = random.nextInt(ancho_ventana);
				y_temp = random.nextInt(alto_ventana);
				while ((x_temp > (ancho_ventana/2 - 80) && x_temp < (ancho_ventana/2 + 80)) && (y_temp > (alto_ventana/2 - 80) && y_temp < (alto_ventana/2 + 80))){
					x_temp = random.nextInt(ancho_ventana);
					y_temp = random.nextInt(alto_ventana);
				}
				celula_mala[i] = new Celula(x_temp, y_temp, random.nextInt(7), level_temp, "mala");
				cantidad_celulas_malas++;
			}
		}
		// Inicializa el objeto entorno
		entorno = new Entorno(this, "NanoBot Arena - Grupo 6 - V1", ancho_ventana, alto_ventana);
		
		// Inicializar lo que haga falta para el juego
		// ...

		// Inicia el juego!
		entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	@Override
	public void tick() //x milisegundos
	{	
		if (fin_juego == 1){
			entorno.cambiarFont("arial", 30, Color.RED);
			entorno.escribirTexto("PERDISTE - HAY MAS CELULAS MALAS", 10, entorno.alto()/2);
		} else if (fin_juego == 2){
			entorno.cambiarFont("arial", 30, Color.GREEN);
			entorno.escribirTexto("GANASTE - NO HAY MAS CELULAS MALAS", 10, entorno.alto()/2);
		} else if (fin_juego == 3){ 
			entorno.cambiarFont("arial", 30, Color.RED);
			entorno.escribirTexto("PERDISTE - Nave Colisionó con Celula MALA", 10, entorno.alto()/2);
		}
		
		else {
			
			entorno.dibujarImagen(this.fondo, ancho_ventana/2, alto_ventana/2, 0, 2);
			proporcion_celulas_buenas = 0;
			proporcion_celulas_malas = 0;
			
			if (tiempo_anticuerpo == 0){
				for(int i = 0; i < anticuerpo.length; i++){
					if (anticuerpo[i] == null){
						anticuerpo[i] = new Celula(random.nextInt(ancho_ventana), random.nextInt(alto_ventana), random.nextInt(7), 1 + random.nextInt(2), "anticuerpo");
						tiempo_anticuerpo = (600 + random.nextInt(401))*10;
						break;
					}
				}
			} else {
				tiempo_anticuerpo = tiempo_anticuerpo - 10;
			}
			
			for(int i = 0; i < celula_buena.length; i++){
				if (!(celula_buena[i] == null)){
					celula_buena[i].dibujarCelula(entorno);
					proporcion_celulas_buenas = proporcion_celulas_buenas + celula_buena[i].getLevel();
				}
			}
			
			for(int i = 0; i < celula_mala.length; i++){
				if (!(celula_mala[i] == null)){
					celula_mala[i].dibujarCelula(entorno);
					proporcion_celulas_malas = proporcion_celulas_malas + celula_mala[i].getLevel();
				}
			}
			
			if (atrapo_anticuerpo == 1){
				anticuerpo[anticuerpo_orden].setAngulo(nave.getAngulo());
				anticuerpo[anticuerpo_orden].setX(nave.getx());
				anticuerpo[anticuerpo_orden].setY(nave.gety());
				anticuerpo[anticuerpo_orden].setVelocidad(nave.getVelocidad());
			}
			
			for(int i = 0; i < anticuerpo.length; i++){
				if (!(anticuerpo[i] == null)){
					anticuerpo[i].dibujarCelula(entorno);
				}
			}
			
			nave.dibujarNanoBot(entorno);
			
			if (proporcion_celulas_buenas < proporcion_celulas_malas){
				fin_juego = 1;			
			}
			if (proporcion_celulas_malas == 0){
				fin_juego = 2;
			}
			
			if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
				nave.cambiarDireccion(-0.04);
			} else if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
				nave.cambiarDireccion(0.04);
			}
			
			if (entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
				nave.cambiarVelocidad(1.01);
			} else if (entorno.estaPresionada(entorno.TECLA_ABAJO)) {
				nave.cambiarVelocidad(0.99);
			}
			
			if (tiempo_disparo == 0){
				if (atrapo_anticuerpo == 0){ //No puede disparar si tiene un anticuerpo agarrado
					if (entorno.estaPresionada('A')){
						if (disparos_actuales == bala.length){
							System.out.println("No quedan Disparos");
						} else {
							for(int i = 0; i < bala.length; i++){
								if (bala[i] == null){
									bala[i] = new Proyectil(nave.getx(), nave.gety(), nave.getAltura(), nave.getAngulo(), nave.getVelocidad(), "alfa");
									disparos_actuales++;
									tiempo_disparo = 600;
									break;
								}
							}
						}
					} else if (entorno.estaPresionada('S')){
						if (disparos_actuales == bala.length){
							System.out.println("No quedan Disparos");
						} else {
							for(int i = 0; i < bala.length; i++){
								if (bala[i] == null){
									bala[i] = new Proyectil(nave.getx(), nave.gety(), nave.getAltura(), nave.getAngulo(), nave.getVelocidad(), "beta");
									disparos_actuales++;
									tiempo_disparo = 600;
									break;
								}
							}
						}
					}
				}
			}
			else {
				tiempo_disparo = tiempo_disparo -10;
			}
			
			
			for(int i = 0; i < bala.length; i++){
				if (!(bala[i] == null)){
					bala[i].dibujarProyectil(entorno);
					if ((bala[i].getx() > (entorno.ancho() + bala[i].getAltura())) || (bala[i].getx() < (0 - bala[i].getAltura())) || (bala[i].gety() > (entorno.alto() + bala[i].getAltura())) || (bala[i].gety() < (0 - bala[i].getAltura()))){
						bala[i] = null;
						disparos_actuales--;
					}
				}
			}
			// si se estan tocando celulas de diferente tipo
			for(int i = 0; i < celula_mala.length; i++) {
				if (!(celula_mala[i] == null)){
					for(int k = 0; k < celula_buena.length; k++) {
						if (!(celula_buena[k] == null)){
							if (celula_mala[i].estaTocando(celula_buena[k])){
								celula_mala[i].cambiarDireccion(celula_buena[k]);
								if (cantidad_celulas_malas == celula_mala.length){
									System.out.println("Maximo de Celulas alcanzado");
								} else{
									for (int j=0; j < celula_mala.length; j++){
										if (celula_mala[j] == null){
											celula_buena[k].cambiarDireccion(celula_mala[i]);
											celula_mala[j] = new Celula(celula_buena[k].getx(), celula_buena[k].gety(), celula_buena[k].getAngulo(), celula_buena[k].getLevel(), "mala");
											cantidad_celulas_malas++;
											celula_buena[k] = null;
											cantidad_celulas_buenas--;
											break;
										}
									}
									break;
								}
							}
						}
					}
				}
			}
			
			// si se estan tocando celulas del mismo tipo
			for(int i = 0; i < celula_mala.length; i++) {
				if (!(celula_mala[i] == null)){
					for(int k = 0; k < celula_mala.length; k++) {
						if (!(celula_mala[k] == null)){
							if (celula_mala[i].estaTocando(celula_mala[k])){
								celula_mala[i].cambiarDireccion(celula_mala[k]);
								celula_mala[k].cambiarDireccion(celula_mala[i]);
			
								break;
							}
						}
					}
				}
			}
			
			for(int i = 0; i < celula_buena.length; i++) {
				if (!(celula_buena[i] == null)){
					for(int k = 0; k < celula_buena.length; k++) {
						if (!(celula_buena[k] == null)){
							if (celula_buena[i].estaTocando(celula_buena[k])){
								celula_buena[i].cambiarDireccion(celula_buena[k]);
								celula_buena[k].cambiarDireccion(celula_buena[i]);
			
								break;
							}
						}
					}
				}
			}
			
			// si se estan tocando anticuerpos
			for(int i = 0; i < anticuerpo.length; i++) {
				if (!(anticuerpo[i] == null)){
					for(int k = 0; k < anticuerpo.length; k++) {
						if (!(anticuerpo[k] == null)){
							if (anticuerpo[i].estaTocando(anticuerpo[k])){
								if (anticuerpo[i].getVelocidad() > anticuerpo[k].getVelocidad()){
									anticuerpo[k].setVelocidad(anticuerpo[i].getVelocidad());
								} else if (anticuerpo[i].getVelocidad() < anticuerpo[k].getVelocidad()){
									anticuerpo[i].setVelocidad(anticuerpo[k].getVelocidad());
								}
								anticuerpo[i].cambiarDireccion(anticuerpo[k]);
								anticuerpo[k].cambiarDireccion(anticuerpo[i]);
			
								break;
							}
						}
					}
				}
			}
			// si se esta tocando celula buena con el anticuerpo
			for(int i = 0; i < anticuerpo.length; i++) {
				if (!(anticuerpo[i] == null)){
					for(int k = 0; k < celula_buena.length; k++) {
						if (!(celula_buena[k] == null)){
							if (anticuerpo[i].estaTocando(celula_buena[k])){
								if (atrapo_anticuerpo == 1 && anticuerpo_orden == i){
									atrapo_anticuerpo = 0;
								}
								anticuerpo[i] = null;
								break;
							}
						}
					}
				}
			}
			
			// si se esta tocando celula mala con el anticuerpo
			for(int i = 0; i < anticuerpo.length; i++) {
				if (!(anticuerpo[i] == null)){
					for(int k = 0; k < celula_mala.length; k++) {
						if (!(celula_mala[k] == null)){
							if (anticuerpo[i].estaTocando(celula_mala[k])){
								if (atrapo_anticuerpo == 1 && anticuerpo_orden == i){
									atrapo_anticuerpo = 0;
								}
								anticuerpo[i] = null;
								if (cantidad_celulas_buenas == celula_buena.length){
									System.out.println("Maximo de Celulas alcanzado");
								} else {
									for (int j=0; j < celula_buena.length; j++){
										if (celula_buena[j] == null){
											celula_buena[j] = new Celula(celula_mala[k].getx(), celula_mala[k].gety(), celula_mala[k].getAngulo(), celula_mala[k].getLevel(), "buena");
											cantidad_celulas_buenas++;
											celula_mala[k] = null;
											cantidad_celulas_malas--;
											break;
										}
									}
								}
								break;
							}
						}
					}
				}
			}
			
			// si se esta tocando la nave con las celulas malas --- si es buena no pasa nada
			for(int i = 0; i < celula_mala.length; i++) {
				if (!(celula_mala[i] == null)){
					if (nave.estaTocando(celula_mala[i])){
						fin_juego = 3;
						break;
					}
				}
			}
			
			// si se estan tocando anticuerpo con la nave
			if (atrapo_anticuerpo == 0){
				for(int i = 0; i < anticuerpo.length; i++) {
					if (!(anticuerpo[i] == null)){
						if (nave.estaTocando(anticuerpo[i])){
							if (entorno.estaPresionada('D')){
								atrapo_anticuerpo = 1;
								anticuerpo_orden = i;
							}
						}
					}
				}
			} else if (atrapo_anticuerpo == 1){
				if (entorno.estaPresionada(entorno.TECLA_ESPACIO)){
					atrapo_anticuerpo = 0;
				}
			}
				
			//si el disparo choca con una celula
			for(int i = 0; i < celula_buena.length; i++) {
				if (!(celula_buena[i] == null)){
					for (int j=0; j < bala.length; j++) {
						if (!(bala[j] == null)){
							if (bala[j].estaTocando(celula_buena[i])){
								if (bala[j].getTipo() == "alfa"){
									if (celula_buena[i].getLevel() < 3){
										celula_buena[i].aumentarLevel();
									} else {
										if (cantidad_celulas_buenas == celula_buena.length){
											System.out.println("Maximo de Celulas alcanzado");
										} else {
											celula_buena[i].setAngulo(celula_buena[i].getAngulo() - (Math.PI/2));
											for (int k=0; k < celula_buena.length; k++){
												if (celula_buena[k] == null){
													if (celula_buena[i].getAngulo() > (Math.PI + (Math.PI/2))){
														celula_buena[k] = new Celula(celula_buena[i].getx(), celula_buena[i].gety(), ((2*Math.PI) - celula_buena[i].getAngulo()), celula_buena[i].getLevel(), "buena");
													}
													else {
														celula_buena[k] = new Celula(celula_buena[i].getx(), celula_buena[i].gety(), (celula_buena[i].getAngulo() + (Math.PI/2)), celula_buena[i].getLevel(), "buena");
													}
													cantidad_celulas_buenas++;
													break;
												}
											}
										}
									}
								} else if (bala[j].getTipo() == "beta"){
									if (celula_buena[i].getLevel() == 1){
										celula_buena[i]= null;
										cantidad_celulas_buenas--;
									} else {
										if (cantidad_celulas_buenas == celula_buena.length){
											System.out.println("Maximo de Celulas alcanzado");
										} else {
											celula_buena[i].disminuirLevel();
											celula_buena[i].setAngulo(celula_buena[i].getAngulo() - (Math.PI/2));
											for (int k=0; k < celula_buena.length; k++){
												if (celula_buena[k] == null){
													if (celula_buena[i].getAngulo() > (Math.PI + (Math.PI/2))){
														celula_buena[k] = new Celula(celula_buena[i].getx(), celula_buena[i].gety(), ((2*Math.PI) - celula_buena[i].getAngulo()), celula_buena[i].getLevel(), "buena");
													}
													else {
														celula_buena[k] = new Celula(celula_buena[i].getx(), celula_buena[i].gety(), (celula_buena[i].getAngulo() + (Math.PI/2)), celula_buena[i].getLevel(), "buena");
													}
													cantidad_celulas_buenas++;
													break;
												}
											}
										}
									}
								}
								bala[j] = null;
								disparos_actuales--;
							}
						}
					}	
				}
			}
			
			for(int i = 0; i < celula_mala.length; i++) {
				if (!(celula_mala[i] == null)){
					for (int j=0; j < bala.length; j++) {
						if (!(bala[j] == null)){
							if (bala[j].estaTocando(celula_mala[i])){
								if (bala[j].getTipo() == "alfa"){
									if (celula_mala[i].getLevel() < 3){
										celula_mala[i].aumentarLevel();
									} else {
										if (cantidad_celulas_malas == celula_mala.length){
											System.out.println("Maximo de Celulas alcanzado");
										} else {
											celula_mala[i].setAngulo(celula_mala[i].getAngulo() - (Math.PI/2));
											for (int k=0; k < celula_mala.length; k++){
												if (celula_mala[k] == null){
													if (celula_mala[i].getAngulo() > (Math.PI + (Math.PI/2))){
														celula_mala[k] = new Celula(celula_mala[i].getx(), celula_mala[i].gety(), ((2*Math.PI) - celula_mala[i].getAngulo()), celula_mala[i].getLevel(), "mala");
													}
													else {
														celula_mala[k] = new Celula(celula_mala[i].getx(), celula_mala[i].gety(), (celula_mala[i].getAngulo() + (Math.PI/2)), celula_mala[i].getLevel(), "mala");
													}
													cantidad_celulas_malas++;
													break;
												}
											}
										}
									}
								} else if (bala[j].getTipo() == "beta"){
									if (celula_mala[i].getLevel() == 1){
										celula_mala[i]= null;
										cantidad_celulas_malas--;
									} else {
										if (cantidad_celulas_malas == celula_mala.length){
											System.out.println("Maximo de Celulas alcanzado");
										} else {
											celula_mala[i].disminuirLevel();
											celula_mala[i].setAngulo(celula_mala[i].getAngulo() - (Math.PI/2));
											for (int k=0; k < celula_mala.length; k++){
												if (celula_mala[k] == null){
													if (celula_mala[i].getAngulo() > (Math.PI + (Math.PI/2))){
														celula_mala[k] = new Celula(celula_mala[i].getx(), celula_mala[i].gety(), ((2*Math.PI) - celula_mala[i].getAngulo()), celula_mala[i].getLevel(), "mala");
													}
													else {
														celula_mala[k] = new Celula(celula_mala[i].getx(), celula_mala[i].gety(), (celula_mala[i].getAngulo() + (Math.PI/2)), celula_mala[i].getLevel(), "mala");
													}
													cantidad_celulas_malas++;
													break;
												}
											}
										}
									}
								}
								bala[j] = null;
								disparos_actuales--;
							}
						}
					}	
				}
			}
			
			for(int i = 0; i < anticuerpo.length; i++) {
				if (!(anticuerpo[i] == null)){
					for (int j=0; j < bala.length; j++) {
						if (!(bala[j] == null)){
							if (bala[j].estaTocando(anticuerpo[i])){
								if (bala[j].getTipo() == "beta"){
									anticuerpo[i]= null;
								}
								bala[j] = null;
								disparos_actuales--;
							}
						}
					}	
				}
			}
	
	
			// Procesamiento de un instante de tiempo
			// ...
		}
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
