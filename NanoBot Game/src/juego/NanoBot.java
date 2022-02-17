package juego;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entorno.Entorno;

public class NanoBot {
	private BufferedImage nave;
	private double x;
	private double y;
	private double altura;
	private double base;
	private double angulo;
	private double velocidad;
	
	public NanoBot(double x, double y){
		this.x = x;
		this.y = y;
		this.altura = 64;
		this.base = 70;
		this.angulo = 0;
		this.velocidad = 0;
		try {
			this.nave = ImageIO.read(new File("nave.png"));
		} catch(IOException e) {
           e.printStackTrace();
		}
	}
	
	public void dibujarNanoBot(Entorno entorno){
		double x_base = this.x - getCoordenadaX(this.angulo, this.altura/2);
		double y_base = this.y - getCoordenadaY(this.angulo, this.altura/2);
		
		//Si se va en X
		if (this.angulo > (Math.PI + Math.PI/2) && this.angulo < 2* Math.PI){
			if (x_base + getCoordenadaX((Math.PI + Math.PI/2) + this.angulo, this.base/2) >= entorno.ancho()){
				if (getCoordenadaX((Math.PI/2) + this.angulo, this.base/2) > getCoordenadaX(this.angulo, this.altura/2)){
					this.x = 0 - getCoordenadaX((Math.PI/2) + this.angulo, this.base/2) + getCoordenadaX(this.angulo, this.altura/2);
				} else {
					this.x = 0 - getCoordenadaX(this.angulo, this.altura/2);
				}
			}
		} else if (this.angulo == 2*Math.PI || this.angulo == 0){
			if (x_base >= entorno.ancho()){
				this.x = 0 - getCoordenadaX(this.angulo, this.altura/2);
			}
		} else if (this.angulo > 0 && this.angulo < Math.PI/2){
			if (x_base + getCoordenadaX((Math.PI/2) + this.angulo, this.base/2) >= entorno.ancho()){
				if (getCoordenadaX((Math.PI + Math.PI/2) + this.angulo, this.base/2) > getCoordenadaX(this.angulo, this.altura/2)){
					this.x = 0 - getCoordenadaX((Math.PI + Math.PI/2) + this.angulo, this.base/2) + getCoordenadaX(this.angulo, this.altura/2);
				} else {
					this.x = 0 - getCoordenadaX(this.angulo, this.altura/2);
				}
			}
			
		} else if (this.angulo > Math.PI/2 && this.angulo < Math.PI){
			if (x_base + getCoordenadaX((Math.PI + Math.PI/2) + this.angulo, this.base/2) <= 0){
				if (getCoordenadaX((Math.PI/2) + this.angulo, this.base/2) < getCoordenadaX(this.angulo, this.altura/2)){
					this.x = entorno.ancho() - getCoordenadaX((Math.PI/2) + this.angulo, this.base/2) + getCoordenadaX(this.angulo, this.altura/2);
				} else {
					this.x = entorno.ancho() - getCoordenadaX(this.angulo, this.altura/2);
				}
			}
		} else if (this.angulo == Math.PI){
			if (x_base <= 0){
				this.x = entorno.ancho() - getCoordenadaX(this.angulo, this.altura/2);
			}
		} else if (this.angulo > Math.PI && this.angulo < (Math.PI + Math.PI/2)){
			if (x_base + getCoordenadaX((Math.PI/2) + this.angulo, this.base/2) <= 0){
				if (getCoordenadaX((Math.PI + Math.PI/2) + this.angulo, this.base/2) < getCoordenadaX(this.angulo, this.altura/2)){
					this.x = entorno.ancho() - getCoordenadaX((Math.PI + Math.PI/2) + this.angulo, this.base/2) + getCoordenadaX(this.angulo, this.altura/2);
				} else {
					this.x = entorno.ancho() - getCoordenadaX(this.angulo, this.altura/2);
				}
			}
		}
		
		//Si se va en Y
		if (this.angulo > 0 && this.angulo < (Math.PI/2)){
			if (y_base + getCoordenadaY((Math.PI + Math.PI/2) + this.angulo, this.base/2) >= entorno.alto()){
				if (getCoordenadaY((Math.PI/2) + this.angulo, this.base/2) > getCoordenadaY(this.angulo, this.altura/2)){
					this.y = 0 - getCoordenadaY((Math.PI/2) + this.angulo, this.base/2) + getCoordenadaY(this.angulo, this.altura/2);
				} else {
					this.y = 0 - getCoordenadaY(this.angulo, this.altura/2);
				}
			}
		} else if (this.angulo == (Math.PI/2)){
			if (y_base >= entorno.alto()){
				this.y = 0 - getCoordenadaY(this.angulo, this.altura/2);
			}
		} else if (this.angulo > (Math.PI/2) && this.angulo < Math.PI){
			if (y_base + getCoordenadaY((Math.PI/2) + this.angulo, this.base/2) >= entorno.alto()){
				if (getCoordenadaY((Math.PI + Math.PI/2) + this.angulo, this.base/2) > getCoordenadaY(this.angulo, this.altura/2)){
					this.y = 0 - getCoordenadaY((Math.PI + Math.PI/2) + this.angulo, this.base/2) + getCoordenadaY(this.angulo, this.altura/2);
				} else {
					this.y = 0 - getCoordenadaY(this.angulo, this.altura/2);
				}
			}
			
		} else if (this.angulo > Math.PI && this.angulo < (Math.PI + Math.PI/2)){
			if (y_base + getCoordenadaY((Math.PI + Math.PI/2) + this.angulo, this.base/2) <= 0){
				if (getCoordenadaY((Math.PI/2) + this.angulo, this.base/2) < getCoordenadaY(this.angulo, this.altura/2)){
					this.y = entorno.alto() - getCoordenadaY((Math.PI/2) + this.angulo, this.base/2) + getCoordenadaY(this.angulo, this.altura/2);
				} else {
					this.y = entorno.alto() - getCoordenadaY(this.angulo, this.altura/2);
				}
			}
		} else if (this.angulo == (Math.PI + Math.PI/2)){
			if (y_base <= 0){
				this.y = entorno.alto() - getCoordenadaY(this.angulo, this.altura/2);
			}
		} else if (this.angulo > (Math.PI + Math.PI/2) && this.angulo < 2*Math.PI){
			if (y_base + getCoordenadaY((Math.PI/2) + this.angulo, this.base/2) <= 0){
				if (getCoordenadaY((Math.PI + Math.PI/2) + this.angulo, this.base/2) < getCoordenadaY(this.angulo, this.altura/2)){
					this.y = entorno.alto() - getCoordenadaY((Math.PI + Math.PI/2) + this.angulo, this.base/2) + getCoordenadaY(this.angulo, this.altura/2);
				} else {
					this.y = entorno.alto() - getCoordenadaY(this.angulo, this.altura/2);
				}
			}
		}
		
		this.x = this.x + getCoordenadaX(this.angulo, this.velocidad);
		this.y = this.y + getCoordenadaY(this.angulo, this.velocidad);
		
		entorno.dibujarImagen(this.nave, this.x, this.y, this.angulo, 0.27);
		
		//entorno.dibujarTriangulo(this.x, this.y, this.altura, this.base, this.angulo, Color.BLUE);
	}
	
	public void cambiarDireccion(double a){
		this.angulo = this.angulo + a;
		if (this.angulo > (2 * Math.PI)){
			this.angulo = this.angulo - (2 * Math.PI);
		} else if (this.angulo < 0){
			this.angulo = (2 * Math.PI) + this.angulo;
		}
	}
	
	public void cambiarVelocidad(double a){
		if (this.velocidad == 0){
			this.velocidad = 0.5;
		}
		this.velocidad = this.velocidad * a;
	}
	
	public double getx(){
		return this.x;
	}
	
	public double gety(){
		return this.y;
	}
	
	public double getAngulo(){
		return this.angulo;
	}
	
	public double getVelocidad(){
		return this.velocidad;
	}
	
	public double getAltura(){
		return this.altura;
	}
	
	public double getBase(){
		return this.base;
	}
	
	private static double getCoordenadaX(double angulo, double hipotenusa){
		return (Math.cos(angulo) * hipotenusa);
	}
	
	private static double getCoordenadaY(double angulo, double hipotenusa){
		return (Math.sin(angulo) * hipotenusa);
	}
	

	public boolean estaTocando(Celula celula) {
		double dx=0;
		double dy=0;
		double dist=0;
		//Si es un anticuerpo tiene que tocar con el medio de la nave para poder agarrar
		if (celula.getTipo() == "anticuerpo"){
			dx = this.x - celula.getx();
			dy = this.y - celula.gety();
			dist = Math.sqrt(dx*dx + dy*dy);
			
			if (dist < (celula.getDiametro()/2)){
				return true;
			}
			
			return false;
		} else {
			
			//Si toca con la punta de la nave
			dx = (this.x + getCoordenadaX(this.angulo, this.altura/2)) - celula.getx();
			dy = (this.y + getCoordenadaY(this.angulo, this.altura/2)) - celula.gety();
			dist = Math.sqrt(dx*dx + dy*dy);
			
			if (dist < (celula.getDiametro()/2 - 5)){
				return true;
			}
			
			//Si toca con la base de la nave
			dx = (this.x - getCoordenadaX(this.angulo, this.altura/2)) - celula.getx();
			dy = (this.y - getCoordenadaY(this.angulo, this.altura/2)) - celula.gety();
			dist = Math.sqrt(dx*dx + dy*dy);
			
			if (dist < (celula.getDiametro()/2 - 5)){
				return true;
			}
			
			//Si toca con una de las puntas de la base de la nave
			double x_temp = this.x - getCoordenadaX(this.angulo, this.altura/2); //Base de la nave
			double y_temp = this.y - getCoordenadaY(this.angulo, this.altura/2); //Base de la nave
			
			dx = x_temp + getCoordenadaX((Math.PI/2 + this.angulo),this.base/2) - celula.getx();
			dy = y_temp + getCoordenadaY((Math.PI/2 + this.angulo),this.base/2) - celula.gety();
	
			dist = Math.sqrt(dx*dx + dy*dy);
			
			if (dist < (celula.getDiametro()/2 - 5)){
				return true;
			}
			
			//Si toca con la otra de las puntas de la base de la nave
			dx = x_temp + getCoordenadaX((Math.PI + Math.PI/2) + this.angulo,this.base/2) - celula.getx();
			dy = y_temp + getCoordenadaY((Math.PI + Math.PI/2) + this.angulo,this.base/2) - celula.gety();
			
			dist = Math.sqrt(dx*dx + dy*dy);
			
			if (dist < (celula.getDiametro()/2 - 5)){
				return true;
			}
			
			return false;
		}
	}
}
