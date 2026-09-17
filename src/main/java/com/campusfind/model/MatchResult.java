package com.campusfind.model;

public class MatchResult {
	private final LostReport lostReport;
	private final FoundReport foundReport;
	private final int score;

	public MatchResult(LostReport lostReport, FoundReport foundReport, int score) {
		this.lostReport = lostReport;
		this.foundReport = foundReport;
		this.score = score;
	}

	public LostReport getLostReport() {
		return lostReport;
	}

	public FoundReport getFoundReport() {
		return foundReport;
	}

	public int getScore() {
		return score;
	}

	@Override
	public String toString() {
		return "Match " + score + "%: lost [" + lostReport + "] / found [" + foundReport + "]";
	}
}
