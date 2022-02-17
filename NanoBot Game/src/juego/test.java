package juego;

import java.util.Random;

public class test {

	public static void main(String[] args) {
		Random random;
		
		random = new Random();
		for (int i=0; i<12;i++)
		System.out.println(random.nextInt(6));

	}

}
