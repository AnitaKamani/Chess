package gui;

import org.junit.Test;

import game.Main;

public class timer {

	@Test
	public void test() {

		new Timer_Request();
		while (Main.m == 0 && Main.n == 0) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println(Main.m + " " + Main.n);

	}

}
