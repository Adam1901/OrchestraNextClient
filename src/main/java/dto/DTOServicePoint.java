package dto;

import java.util.List;
import java.util.Map;

public class DTOServicePoint {
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public Map<String, String> getParameters() {
		return parameters;
	}
	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}
	public List<String> getDeviceTypes() {
		return deviceTypes;
	}
	public void setDeviceTypes(List<String> deviceTypes) {
		this.deviceTypes = deviceTypes;
	}
	public String getProfileUnitName() {
		return profileUnitName;
	}
	public void setProfileUnitName(String profileUnitName) {
		this.profileUnitName = profileUnitName;
	}
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
