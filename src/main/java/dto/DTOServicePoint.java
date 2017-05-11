package dto;

import java.util.List;
import java.util.Map;

public class DTOServicePoint {
	@Override
	public String toString() {
		return name;
	}

	int id;
	String name;
	String unitId;
	Map<String, String> parameters;
	List<String> deviceTypes;
	String profileUnitName;
}
