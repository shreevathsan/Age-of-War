package com.war;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class War {

	private static final String NO_CHANCE_OF_WINNING = "There is no chance of winning";

	public String beginWar(String soldiers, String opponentSquad) {

		List<String> ownSoldiers = fetchSoldierList(soldiers);
		List<String> opponentSoldiers = fetchSoldierList(opponentSquad);

		if (opponentSoldiers.size() > ownSoldiers.size()) {
			return NO_CHANCE_OF_WINNING;
		} else if ((null == opponentSoldiers || opponentSoldiers.isEmpty())
				&& (null != ownSoldiers || !ownSoldiers.isEmpty())) {
			return soldiers;
		}

		Map<String, List<String>> soldierWinningPossibilities = findWinningPossibilityOfEachSoldier(ownSoldiers,
				opponentSoldiers);

//		for (Map.Entry<String, List<String>> value : winningPossibilityMap.entrySet()) {
//			System.out.println("Key :::" + value.getKey() + "  Value ::" + value.getValue());
//		}

		List<Map.Entry<String, List<String>>> sortedSoldier = new ArrayList<>(soldierWinningPossibilities.entrySet());
		sortedSoldier.sort(Comparator.comparingInt(entry -> entry.getValue().size()));

		List<String> winningSoldiers = new ArrayList<>();
		List<String> losingOpponentSoldier = new ArrayList<>();
		Map<String, String> assignments = new HashMap<>();

		Set<String> assignedOpponents = new HashSet<>();

		for (Map.Entry<String, List<String>> soldier : sortedSoldier) {
			String soldierName = soldier.getKey();
			List<String> opponents = soldier.getValue();

			for (String opponent : opponents) {
				if (!assignedOpponents.contains(opponent)) {
					winningSoldiers.add(soldierName);
					losingOpponentSoldier.add(opponent);
					assignments.put(soldierName, opponent);
					assignedOpponents.add(opponent);
					break;
				}
			}
		}

//		for(Map.Entry<String, String> value : assignments.entrySet()) {
//			System.out.println("Key : "+value.getKey()+"Value : "+value.getValue());
//		}
//
//		System.out.println("Printing the winning soldiers list ::" + winningSoldiers);
//		System.out.println("Printing the losing opponent soldiers list ::"+losingOpponentSoldier);

		if (winningSoldiers.size() <= ownSoldiers.size() / 2) {
			return NO_CHANCE_OF_WINNING;
		} else {
			StringBuilder sb = new StringBuilder();
			for (String winningSoldier : winningSoldiers) {
				sb.append(winningSoldier).append(";");
			}

			for (String opponentSoldier : opponentSoldiers) {
				if (!losingOpponentSoldier.contains(opponentSoldier)) {
					sb.append(opponentSoldier).append(";");
				}
			}
			String output = sb.toString();
			return output.substring(0, output.length() - 1);
		}

	}

	/**
	 * This method returns a Map that contains each own troop soldier and a list of
	 * opponent soldiers that the respective soldier can potentially defeat.
	 */
	private static Map<String, List<String>> findWinningPossibilityOfEachSoldier(List<String> ownSoldiers,
			List<String> opponentSoldiers) {
		Map<String, List<String>> result = new HashMap<>();
		for (String soldier : ownSoldiers) {
			String ownUnitName = findUnitName(soldier);
			int ownUnitCount = findUnitCount(soldier);

			List<String> winningOverList = new ArrayList<>();
			for (String opponentSoldier : opponentSoldiers) {
				String opponentUnitName = findUnitName(opponentSoldier);
				int opponentUnitCount = findUnitCount(opponentSoldier);

				if (SoldiersAdvantageMapper.hasAdvantage(ownUnitName, opponentUnitName)) {
					int updatedCount = SoldiersAdvantageMapper.updateSoldierCount(ownUnitName, ownUnitCount);
					if (updatedCount > opponentUnitCount) {
						winningOverList.add(opponentSoldier);
					}
				} else if (ownUnitCount > opponentUnitCount) {
					winningOverList.add(opponentSoldier);
				}
			}
			result.put(soldier, winningOverList);
		}
		return result;
	}

	private static String findUnitName(String soldier) {
		return soldier.split("#")[0];
	}

	private static int findUnitCount(String soldier) {
		int value = 0;
		try {
			value = Integer.parseInt(soldier.split("#")[1]);
		} catch (NumberFormatException e) {
			System.out.println("Something went wrong, Kindly ensure it is valid input");
		}
		return value;
	}

	private static List<String> fetchSoldierList(String soldiers) {
		if (null == soldiers || 0 == soldiers.length()) {
			return new ArrayList<>();
		}
		return Arrays.asList(soldiers.split(";"));
	}
}
