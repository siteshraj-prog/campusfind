package com.campusfind;

import com.campusfind.model.FoundReport;
import com.campusfind.model.LostReport;
import com.campusfind.model.MatchResult;
import com.campusfind.service.MatchService;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MatchServiceTest {
	@Test
	void matchingItemAndLocationProducesHighScore() {
		LostReport lost = new LostReport("A", "Blue bag", "Books", "Library", LocalDate.now());
		FoundReport found = new FoundReport("B", "blue bag", "Books", "library", LocalDate.now());
		List<MatchResult> matches = new MatchService().findMatches(List.of(lost), List.of(found));
		assertEquals(1, matches.size());
		assertEquals(100, matches.get(0).getScore());
	}

	@Test
	void unrelatedItemsDoNotMatch() {
		LostReport lost = new LostReport("A", "Phone", "Black", "Gym", LocalDate.now());
		FoundReport found = new FoundReport("B", "Keys", "Silver", "Library", LocalDate.now());
		assertTrue(new MatchService().findMatches(List.of(lost), List.of(found)).isEmpty());
	}
}
