package com.war;

public class Main {
	public static void main(String[] args) {
		War war = new War();
		System.out.println(war.beginWar("Spearmen#10;Militia#30;FootArcher#20;LightCavalry#1000;HeavyCavalry#120",
				"Militia#10;Spearmen#10;FootArcher#1000;LightCavalry#120;CavalryArcher#100"));
	}

}
