package com.campusfind.service;

import com.campusfind.model.FoundReport;
import com.campusfind.model.LostReport;
import com.campusfind.model.MatchResult;
import java.util.ArrayList;
import java.util.List;

public class MatchService {
	private static final int MATCH_THRESHOLD = 50;
	private static final int ITEM_NAME_POINTS = 60;
	private static final int LOCATION_POINTS = 30;
	private static final int DESCRIPTION_POINTS = 10;

	public List<MatchResult> findMatches(List<LostReport> lostReports, List<FoundReport> foundReports) {
		List<MatchResult> matches = new ArrayList<>();
		for (LostReport lost : lostReports) {
			for (FoundReport found : foundReports) {
				int score = score(lost, found);
				if (score >= MATCH_THRESHOLD) matches.add(new MatchResult(lost, found, score));
			}
		}
		matches.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));
		return matches;
	}

	public int score(LostReport lost, FoundReport found) {
		int score = 0;
		if (same(lost.getItemName(), found.getItemName())) score += ITEM_NAME_POINTS;
		if (same(lost.getLocation(), found.getLocation())) score += LOCATION_POINTS;
		if (same(lost.getDescription(), found.getDescription())) score += DESCRIPTION_POINTS;
		return score;
	}

	private boolean same(String first, String second) { return first.trim().equalsIgnoreCase(second.trim()); }
}
