package juego;

import java.awt.Color;

import entorno.Entorno;

public class Proyectil {
	private double x;
	private double y;
	private int altura;
	private int base;
	private double angulo;
	private double velocidad;
	private String tipo;
	
	public Proyectil(double x, double y, double altura, double angulo, double velocidad, String tipo){
		this.x = x + (Math.cos(angulo) * (altura/2));
		this.y = y + (Math.sin(angulo) * (altura/2));
		this.altura = 15;
		this.base = 7;
		this.angulo = angulo;
		this.velocidad=velocidad + 1.5;
		this.tipo = tipo;
	}
	
	public void dibujarProyectil(Entorno entorno){
		this.x = this.x + (Math.cos(this.angulo) * this.velocidad);
		this.y = this.y + (Math.sin(this.angulo) * this.velocidad);
		if (this.tipo == "alfa"){
			entorno.dibujarTriangulo(this.x, this.y, this.altura, this.base, this.angulo, Color.BLUE);
		}
		if (this.tipo == "beta"){
			entorno.dibujarTriangulo(this.x, this.y, this.altura, this.base, this.angulo, Color.BLACK);	
		}
	}
	
	public double getx(){
		return this.x;
	}
	
	public double gety(){
		return this.y;
	}
	
	public int getAltura(){
		return this.altura;
	}
	
	public String getTipo(){
		return this.tipo;
	}
	
	public boolean estaTocando(Celula celula) {
		double dx = this.x - celula.getx();
		double dy = this.y - celula.gety();
		double dist = Math.sqrt(dx*dx + dy*dy);
		
		if (dist <= celula.getDiametro()/2){
			return true;
		}		
		return false;
	}
}
