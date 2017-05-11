package dto;

import java.util.List;
import java.util.Map;

public class DTOServicePoint {
	@Override
	public String toString() {
		return "DTOServicePoint [id=" + id + ", name=" + name + ", unitId=" + unitId + ", parameters=" + parameters
				+ ", deviceTypes=" + deviceTypes + ", profileUnitName=" + profileUnitName + "]";
	}

	int id;
	String name;
	String unitId;
	Map<String, String> parameters;
	List<String> deviceTypes;
	String profileUnitName;
}
