package dev.rajitha.ipl2020.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.rajitha.ipl2020.domain.Team;

public class JSONReaderUtil {
	static ObjectMapper mapper = new ObjectMapper();

	public static List<Team> fromJson(String path) throws JsonParseException, JsonMappingException, IOException {
		List<Team> teams = mapper.readValue(new File(path), new TypeReference<List<Team>>() {
		});
		return teams;
	}
}
