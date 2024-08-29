package com.war;

import java.util.Arrays;
import java.util.List;

public enum SoldiersAdvantageMapper {
	MILITIA("Militia", Arrays.asList("Spearmen", "LightCavalry"), 2),
	SPEARMEN("Spearmen", Arrays.asList("LightCavalry", "HeavyCavalry"), 2),
	LIGHTCAVALRY("LightCavalry", Arrays.asList("FootArcher", "CavalryArcher"), 2),
	HEAVYCAVALRY("HeavyCavalry", Arrays.asList("Militia", "FootArcher", "LightCavalry"), 2),
	CAVALRYARCHER("CavalryArcher", Arrays.asList("Spearmen", "HeavyCavalry"), 2),
	FOOTARCHER("FootArcher", Arrays.asList("Militia", "CavalryArcher"), 2);

	private final String name;
	private final List<String> advantageOver;
	private final int power;

	SoldiersAdvantageMapper(String name, List<String> advantageOver, int power) {
		this.name = name;
		this.advantageOver = advantageOver;
		this.power = power;
	}

	public static boolean hasAdvantage(String ownSoldierName, String opponentSoldierName) {
		for (SoldiersAdvantageMapper soldiersAdvantageMapper : values()) {
			if (ownSoldierName.equals(soldiersAdvantageMapper.name)
					&& soldiersAdvantageMapper.advantageOver.contains(opponentSoldierName)) {
				return true;
			}
		}
		return false;
	}

	public static int updateSoldierCount(String ownSoldierName, int currentSoldierCount) {
		for (SoldiersAdvantageMapper soldiersAdvantageMapper : values()) {
			if (ownSoldierName.equals(soldiersAdvantageMapper.name)) {
				return currentSoldierCount * soldiersAdvantageMapper.power;
			}
		}
		return 0;
	}

	@Override
	public String toString() {
		return "SoldiersAdvantageMapper{" + "name='" + name + '\'' + ", advantageOver=" + advantageOver + ", power="
				+ power + '}';
	}
}
