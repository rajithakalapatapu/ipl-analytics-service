package dev.rajitha.ipl2020.service;

import dev.rajitha.ipl2020.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class IplAnalyticsServiceimplTest {

	private IplAnalyticsService iplAnalyticsService;

	@BeforeEach
	void setUp() throws Exception {
		iplAnalyticsService = new IplAnalyticsServiceImpl("src/test/resources/ipl2020test.json");
	}

	@Test
	void testGetTeamLabels() {
		List<String> labels = iplAnalyticsService.getTeamLabels();
		assertNotNull(labels);
		assertEquals(2, labels.size());
		assertEquals("MI", labels.get(0));
		assertEquals("DC", labels.get(1));
	}

	@Test
	void testGetPlayersByTeamThatExists() {
		String label = "MI";
		List<PlayerDTO> players = iplAnalyticsService.getPlayersByTeam(label);
		assertNotNull(players);
		assertEquals(24, players.size());

		label = "dc";
		players = iplAnalyticsService.getPlayersByTeam(label);
		assertNotNull(players);
		assertEquals(22, players.size());
	}

	@Test
	void testGetPlayersByTeamThatDoesNotExist() {
		String label = "DOESNOTEXIST";
		List<PlayerDTO> players = iplAnalyticsService.getPlayersByTeam(label);
		assertNotNull(players);
		assertEquals(0, players.size());
	}

	@Test
	void testGetPlayersByTeamAndRole_TeamDoesNotExist() {
		String label = "DOESNOTEXIST";
		String role = "Bowler";
		List<PlayerDTO> players = iplAnalyticsService.getPlayersByTeamAndRole(label, role);
		assertNotNull(players);
		assertEquals(0, players.size());
	}

	@Test
	void testGetPlayersByTeamAndRole_RoleDoesNotExist() {
		String label = "MI";
		String role = "DOESNOTEXIST";
		List<PlayerDTO> players = iplAnalyticsService.getPlayersByTeamAndRole(label, role);
		assertNotNull(players);
		assertEquals(0, players.size());
	}

	@Test
	void testGetPlayersByTeamAndRole_TeamAndRoleDoesNotExist() {
		String label = "DOESNOTEXIST";
		String role = "DOESNOTEXIST";
		List<PlayerDTO> players = iplAnalyticsService.getPlayersByTeamAndRole(label, role);
		assertNotNull(players);
		assertEquals(0, players.size());
	}

	@Test
	void testGetPlayersByTeamAndRole_TeamAndRoleExists() {
		String label = "MI";
		String role = "Bowler";
		List<PlayerDTO> players = iplAnalyticsService.getPlayersByTeamAndRole(label, role);
		assertNotNull(players);
		assertEquals(8, players.size());
	}

	@Test
	void testGetCountsByRole_TeamExists() {
		String label = "MI";
		List<RoleCountDTO> roleCounts = iplAnalyticsService.getCountsByRole(label);
		assertNotNull(roleCounts);
		assertEquals(4, roleCounts.size());
		assertEquals("All-Rounder", roleCounts.get(0).getRole());
		assertEquals(6, roleCounts.get(0).getCount());
		assertEquals("Batsman", roleCounts.get(1).getRole());
		assertEquals(7, roleCounts.get(1).getCount());
		assertEquals("Wicket Keeper", roleCounts.get(2).getRole());
		assertEquals(3, roleCounts.get(2).getCount());
		assertEquals("Bowler", roleCounts.get(3).getRole());
		assertEquals(8, roleCounts.get(3).getCount());
	}

	@Test
	void testGetCountsByRole_TeamDoesNotExist() {
		String label = "DOESNOTEXIST";
		List<RoleCountDTO> roleCounts = iplAnalyticsService.getCountsByRole(label);
		assertNotNull(roleCounts);
		assertEquals(0, roleCounts.size());
	}

	@Test
	void testGetAllTeamDetails() {
		List<TeamDTO> allTeamDetails = iplAnalyticsService.getAllTeamDetails();
		assertNotNull(allTeamDetails);
	}

	@Test
	void testGetAmountSpentByTeams() {
		List<TeamAmountDTO> amountsByTeams = iplAnalyticsService.getAmountSpentByTeams();
		assertNotNull(amountsByTeams);
		assertEquals(2, amountsByTeams.size());
		System.out.println(amountsByTeams);
		assertEquals("MI", amountsByTeams.get(0).getLabel());
		assertEquals(830500000, amountsByTeams.get(0).getAmount());
		assertEquals("DC", amountsByTeams.get(1).getLabel());
		assertEquals(760000000, amountsByTeams.get(1).getAmount());
	}

	@Test
	void testGetAmountSpentByTeamAndRole() {
		String label = "MI";
		String role = "Bowler";
		List<TeamAmountByRoleDTO> amountByRoleDTOS = iplAnalyticsService.getAmountSpentByTeamAndRole(label, role);
		assertNotNull(amountByRoleDTOS);
		assertEquals(1, amountByRoleDTOS.size());
		assertEquals("MI", amountByRoleDTOS.get(0).getLabel());
		assertEquals("Bowler", amountByRoleDTOS.get(0).getRole());
		assertEquals(226500000, amountByRoleDTOS.get(0).getAmount());
	}

	@Test
	void testGetPlayersBySort_SortByRole() {
		String fieldName = "role";
		List<PlayerDTO> playerDTOS = iplAnalyticsService.getPlayersBySort(fieldName);
		assertNotNull(playerDTOS);
		assertEquals(46, playerDTOS.size());
		assertEquals("All-Rounder", playerDTOS.get(0).getRole());
	}

	@Test
	void testGetPlayersBySort_SortByPrice() {
		String fieldName = "price";
		List<PlayerDTO> playerDTOS = iplAnalyticsService.getPlayersBySort(fieldName);
		assertNotNull(playerDTOS);
		assertEquals(46, playerDTOS.size());
		assertEquals(150000000, playerDTOS.get(0).getPrice().longValue());
	}

	@Test
	void testGetPlayersBySort_SortByName() {
		String fieldName = "name";
		List<PlayerDTO> playerDTOS = iplAnalyticsService.getPlayersBySort(fieldName);
		assertNotNull(playerDTOS);
		assertEquals(46, playerDTOS.size());
		assertEquals("Aditya Tare (R)", playerDTOS.get(0).getPlayer());
	}
}
