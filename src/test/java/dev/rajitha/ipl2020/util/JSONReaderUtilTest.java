package dev.rajitha.ipl2020.util;

import dev.rajitha.ipl2020.domain.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JSONReaderUtilTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testReadingArrayOfTeams() {
		try {
			List<Team> teams = JSONReaderUtil.fromJson("src/test/resources/ipl2020test.json");
			assertNotNull(teams);
			assertEquals(2, teams.size());

			Team team = teams.get(0);
			assertEquals("Mumbai, Maharashtra", team.getCity());
			assertEquals("Mahela Jaywardene", team.getCoach());
			assertEquals("Wankhede Stadium, Mumbai", team.getHome());
			assertEquals("Mumbai Indians", team.getTeam());
			assertEquals("MI", team.getLabel());
			assertEquals(24, team.getPlayers().size());

			team = teams.get(1);
			assertEquals(22, team.getPlayers().size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
