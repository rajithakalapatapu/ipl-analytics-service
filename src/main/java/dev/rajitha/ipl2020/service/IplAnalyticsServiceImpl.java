package dev.rajitha.ipl2020.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dev.rajitha.ipl2020.dto.*;
import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import dev.rajitha.ipl2020.domain.Player;
import dev.rajitha.ipl2020.domain.Team;
import dev.rajitha.ipl2020.util.JSONReaderUtil;
import org.modelmapper.PropertyMap;

public class IplAnalyticsServiceImpl implements IplAnalyticsService {

	private List<Team> teams = new ArrayList<>();
	private ModelMapper modelMapper = new ModelMapper();

	public IplAnalyticsServiceImpl(String path) {
		try {
			teams = JSONReaderUtil.fromJson(path);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> getTeamLabels() {
		return teams.stream().map(team -> team.getLabel()).collect(Collectors.toList());
	}

	@Override
	public List<PlayerDTO> getPlayersByTeam(String label) {
		/*
		 * match team's label with label return players if it matches
		 */
		List<PlayerDTO> players = teams.stream().filter(team -> team.getLabel().equalsIgnoreCase(label))
				.flatMap(team -> team.getPlayers().stream()).map(player -> modelMapper.map(player, PlayerDTO.class))
				.collect(Collectors.toList());

		return players;
	}

	@Override
	public List<PlayerDTO> getPlayersByTeamAndRole(String label, String role) {
		/*
		 * extend above with filter and for players that match role
		 */
		List<PlayerDTO> players = teams.stream().filter(team -> team.getLabel().equalsIgnoreCase(label))
				.flatMap(team -> team.getPlayers().stream()).filter(player -> player.getRole().equalsIgnoreCase(role))
				.map(player -> modelMapper.map(player, PlayerDTO.class)).collect(Collectors.toList());

		return players;
	}

	@Override
	public List<RoleCountDTO> getCountsByRole(String label) {
		Map<String, Long> roleCountMap = teams.stream().filter(team -> team.getLabel().equalsIgnoreCase(label))
				.flatMap(team -> team.getPlayers().stream())
				.collect(Collectors.groupingBy(Player::getRole, Collectors.counting()));

		List<RoleCountDTO> roleCountDTOs = new ArrayList<>();
		for (Map.Entry<String, Long> entry: roleCountMap.entrySet()) {
			roleCountDTOs.add(new RoleCountDTO(entry.getKey(), entry.getValue()));
		}
		return roleCountDTOs;
	}

	@Override
	public List<TeamDTO> getAllTeamDetails() {
		Stream<Object> details = teams.stream().flatMap(
				team -> Stream.of(team.getCity(), team.getCoach(), team.getHome(), team.getTeam(), team.getLabel()));

		List<TeamDTO> allTeamDetails = details.map(object -> modelMapper.map(object, TeamDTO.class))
				.collect(Collectors.toList());
		return allTeamDetails;
	}

	@Override
	public List<TeamAmountDTO> getAmountSpentByTeams() {
		Map<String, Long> amountSpentByTeams = teams.stream().collect(Collectors.groupingBy(Team::getLabel, Collectors
				.summingLong(team -> team.getPlayers().stream().mapToLong(player -> player.getPrice()).sum())));

		List<TeamAmountDTO> amountDTOs = new ArrayList<>();
		for (Map.Entry<String, Long> entry: amountSpentByTeams.entrySet()) {
			amountDTOs.add(new TeamAmountDTO(entry.getKey(), entry.getValue()));
		}
		return amountDTOs;
	}

	@Override
	public List<TeamAmountByRoleDTO> getAmountSpentByTeamAndRole(String label, String role) {
		long price = teams.stream().filter(team -> team.getLabel().equalsIgnoreCase(label)).flatMap(team -> team.getPlayers().stream())
				.filter(player -> player.getRole().equalsIgnoreCase(role)).mapToLong(player -> player.getPrice()).sum();
		TeamAmountByRoleDTO t = new TeamAmountByRoleDTO(label, role, price);
		List<TeamAmountByRoleDTO> teamAmountByRoleDTOS = new ArrayList<TeamAmountByRoleDTO>();
		teamAmountByRoleDTOS.add(t);
		return teamAmountByRoleDTOS;
	}

	@Override
	public List<PlayerDTO> getPlayersBySort(String fieldName) {
		Comparator<Player> roleComparator = (p1, p2) -> p1.getRole().compareTo(p2.getRole());
		Comparator<Player> playerComparator = (p1, p2) -> p1.getPlayer().compareTo(p2.getPlayer());
		Comparator<Player> priceComparator = (p1, p2) -> p1.getPrice().compareTo(p2.getPrice());
		Comparator<Player> comparatorToUse = roleComparator;
		if (fieldName.equalsIgnoreCase("role")) {
			comparatorToUse = roleComparator;
		} else if (fieldName.equalsIgnoreCase("price")) {
			comparatorToUse = priceComparator.reversed();
		} else if (fieldName.equalsIgnoreCase("name")) {
			comparatorToUse = playerComparator;
		}

		List<PlayerDTO> playerDTOs = teams.stream().flatMap(team -> team.getPlayers().stream()).sorted(comparatorToUse).map(player -> modelMapper.map(player, PlayerDTO.class)).collect(Collectors.toList());
		return playerDTOs;
	}

	@Override
	public Map<String, List<PlayerDTO>> getMaxPaidPlayersByRole() {
		return null;
	}

}
