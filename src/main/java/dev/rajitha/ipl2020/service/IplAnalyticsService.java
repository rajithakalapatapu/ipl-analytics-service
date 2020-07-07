package dev.rajitha.ipl2020.service;

import java.util.List;
import java.util.Map;

import dev.rajitha.ipl2020.dto.*;

public interface IplAnalyticsService {

    public List<String> getTeamLabels();

    public List<PlayerDTO> getPlayersByTeam(String label);

    public List<RoleCountDTO> getCountsByRole(String label);

    public List<PlayerDTO> getPlayersByTeamAndRole(String label, String role);

    public List<TeamDTO> getAllTeamDetails();

    public List<TeamAmountDTO> getAmountSpentByTeams();

    public List<TeamAmountByRoleDTO> getAmountSpentByTeamAndRole(String label, String role);

    public List<PlayerDTO> getPlayersBySort(String fieldName);

    public Map<String,List<PlayerDTO>> getMaxPaidPlayersByRole();
}
