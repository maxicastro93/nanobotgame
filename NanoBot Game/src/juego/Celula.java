package juego;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entorno.Entorno;

public class Celula {
	private BufferedImage celula;
	private double x;
	private double y;
	private int diametro;
	private double angulo;
	private double velocidad;
	private int level;
	private String tipo;
	
	public Celula(int x, int y, int random, int level, String tipo){
		this.x = x;
		this.y = y;
		this.angulo = (2 * Math.PI) - random;
		this.velocidad=0.22;
		this.level = level;
		this.tipo = tipo;
		if (level == 3){
			this.diametro = 25 * (level + 1);
		} else {
			this.diametro = 25 * level;
		}
		
		try {
			if (tipo == "buena"){
				this.celula = ImageIO.read(new File("celula_buena.png"));
			} else if (tipo == "mala"){
				this.celula = ImageIO.read(new File("celula_mala.png"));
			} else if (tipo == "anticuerpo"){
				this.celula = ImageIO.read(new File("anticuerpo.png"));
				if (level == 1){
					this.diametro = 40;
				} else {
					this.diametro = 60;
				}
			} else {
				throw new IOException("No existe el tipo de Célula");
			}
		} catch(IOException e) {
           e.printStackTrace();
		}
	}
	
	public Celula(double x, double y, double angulo, int level, String tipo){
		this.x = x;
		this.y = y;
		this.angulo = angulo;
		this.velocidad=0.32;
		this.level = level;
		this.tipo = tipo;
		if (level == 3){
			this.diametro = 25 * (level + 1);
		} else {
			this.diametro = 25 * level;
		}
		
		try {
			if (tipo == "buena"){
				this.celula = ImageIO.read(new File("celula_buena.png"));
			} else if (tipo == "mala"){
				this.celula = ImageIO.read(new File("celula_mala.png"));
			} else if (tipo == "anticuerpo"){
				this.celula = ImageIO.read(new File("anticuerpo.png"));
				if (level == 1){
					this.diametro = 40;
				} else {
					this.diametro = 60;
				}
			} else {
				throw new IOException("No existe el tipo de Célula");
			}
		} catch(IOException e) {
           e.printStackTrace();
		}
		
	}
	
	public void dibujarCelula(Entorno entorno){
		if (this.x >= (entorno.ancho() - this.diametro/2)){
			if ((this.angulo == (2 * Math.PI)) || (this.angulo == 0)){
				this.angulo = Math.PI;
			} else if ((this.angulo > 0) && (this.angulo < (Math.PI/2))){
				this.angulo = Math.PI - this.angulo;
			} else if ((this.angulo > (Math.PI + (Math.PI/2))) && (this.angulo < (2 * Math.PI))){
				this.angulo = (3 * Math.PI) - this.angulo;
			}
		} else if (this.x <= (this.diametro/2)){
			if ((this.angulo > Math.PI) && (this.angulo < (Math.PI + (Math.PI/2)))){
				this.angulo = (3 * Math.PI) - this.angulo;
			} else if (this.angulo == Math.PI){
				this.angulo = 0;
			} else if ((this.angulo > (Math.PI/2)) && (this.angulo < Math.PI)){
				this.angulo = Math.PI - this.angulo;
			}
		}

		if (this.y >= (entorno.alto() - this.diametro/2)){
			if ((this.angulo > (Math.PI/2)) && (this.angulo < Math.PI)){
				this.angulo = (2 * Math.PI) - this.angulo;
			} else if (this.angulo == (Math.PI/2)){
				this.angulo = this.angulo + Math.PI;
			} else if ((this.angulo > 0) && (this.angulo < (Math.PI/2))){
				this.angulo = (2 * Math.PI) - this.angulo;
			}
		} else if (this.y <= (this.diametro/2)){
			if ((this.angulo > (Math.PI + (Math.PI/2))) && (this.angulo < (2 * Math.PI))){
				this.angulo = (2 * Math.PI) - this.angulo;
			} else if (this.angulo == (Math.PI + (Math.PI/2))){
				this.angulo = this.angulo - Math.PI;
			} else if ((this.angulo > Math.PI) && (this.angulo < (Math.PI + (Math.PI/2)))){
				this.angulo = (2 * Math.PI) - this.angulo;
			}
		}

		
		this.x = this.x + (Math.cos(this.angulo) * this.velocidad);
		this.y = this.y + (Math.sin(this.angulo) * this.velocidad);
	
		if (this.tipo == "buena"){
			if (this.level == 1){
				entorno.dibujarImagen(this.celula, this.x, this.y, 0, 0.08);
			} else if (this.level == 2){
				entorno.dibujarImagen(this.celula, this.x, this.y, 0, 0.16);
			} else {
				entorno.dibujarImagen(this.celula, this.x, this.y, 0, 0.31);
			}
//entorno.dibujarCirculo(this.x, this.y, diametro, Color.GREEN);
		} else if (this.tipo == "mala"){
			if (this.level == 1){
				entorno.dibujarImagen(this.celula, this.x, this.y, 0, 0.065);
			} else if (this.level == 2){
				entorno.dibujarImagen(this.celula, this.x, this.y, 0, 0.13);
			} else {
				entorno.dibujarImagen(this.celula, this.x, this.y, 0, 0.26);
			}
//entorno.dibujarCirculo(this.x, this.y, diametro, Color.RED);
		} else if (this.tipo == "anticuerpo"){
			if (this.level == 1){
				entorno.dibujarImagen(this.celula, this.x, this.y, 0, 0.19);
			} else {
				entorno.dibujarImagen(this.celula, this.x, this.y, 0, 0.29);
			}
		}
	}
	
	public double getx(){
		return this.x;
	}
	
	public double gety(){
		return this.y;
	}
	
	public int getDiametro(){
		return this.diametro;
	}
	
	public double getAngulo(){
		return this.angulo;
	}
	
	public double getVelocidad(){
		return this.velocidad;
	}
	
	public int getLevel(){
		return this.level;
	}
	
	public String getTipo(){
		return this.tipo;
	}
	
	public void aumentarLevel(){
		this.level= this.level + 1;
		if (this.level == 3){
			this.diametro = 25 * (level + 1);
		} else {
			this.diametro = 25 * level;
		}
	}
	
	public void disminuirLevel(){
		this.level= this.level - 1;
		if (this.level == 3){
			this.diametro = 25 * (level + 1);
		} else {
			this.diametro = 25 * level;
		}
	}
	
	public void setX(double x){
		this.x = x;
	}
	
	public void setY(double y){
		this.y = y;
	}
	
	public void setVelocidad(double velocidad){
		this.velocidad = velocidad;
	}
	
	public void setAngulo(double angulo){
		this.angulo = angulo;
		if (this.angulo > (2 * Math.PI)){
			this.angulo = this.angulo - (2 * Math.PI);
		} else if (this.angulo < 0){
			this.angulo = (2 * Math.PI) + this.angulo;
		}
	}
	
	public boolean estaTocando(Celula celula) {
		double dx = this.x - celula.getx();
		double dy = this.y - celula.gety();
		double dist = Math.sqrt(dx*dx + dy*dy);
		
		if (dist < ((this.diametro/2) + (celula.getDiametro()/2))){
			return true;
		}		
		return false;
	}
	
	public void cambiarDireccion(Celula celula){
		if (this.x < celula.getx()){
			if ((this.angulo == (2 * Math.PI)) || (this.angulo == 0)){
				this.angulo = Math.PI;
			} else if ((this.angulo > 0) && (this.angulo < (Math.PI/2))){
				this.angulo = Math.PI - this.angulo;
			} else if ((this.angulo > (Math.PI + (Math.PI/2))) && (this.angulo < (2 * Math.PI))){
				this.angulo = (3 * Math.PI) - this.angulo;
			}
		} else if (this.x > celula.getx()){
			if ((this.angulo > Math.PI) && (this.angulo < (Math.PI + (Math.PI/2)))){
				this.angulo = (3 * Math.PI) - this.angulo;
			} else if (this.angulo == Math.PI){
				this.angulo = 0;
			} else if ((this.angulo > (Math.PI/2)) && (this.angulo < Math.PI)){
				this.angulo = Math.PI - this.angulo;
			}
		} 
		
		if (this.y < celula.gety()){
			if ((this.angulo > (Math.PI/2)) && (this.angulo < Math.PI)){
				this.angulo = (2 * Math.PI) - this.angulo;
			} else if (this.angulo == (Math.PI/2)){
				this.angulo = this.angulo + Math.PI;
			} else if ((this.angulo > 0) && (this.angulo < (Math.PI/2))){
				this.angulo = (2 * Math.PI) - this.angulo;
			}
		} else if (this.y > celula.gety()){
			if ((this.angulo > (Math.PI + (Math.PI/2))) && (this.angulo < (2 * Math.PI))){
				this.angulo = (2 * Math.PI) - this.angulo;
			} else if (this.angulo == (Math.PI + (Math.PI/2))){
				this.angulo = this.angulo - Math.PI;
			} else if ((this.angulo > Math.PI) && (this.angulo < (Math.PI + (Math.PI/2)))){
				this.angulo = (2 * Math.PI) - this.angulo;
			}
		}
	}
}
