package dto;

public class DTOBranch {
	@Override
	public String toString() {
		return "DTOBranch [name=" + name + ", id=" + id + "]";
	}
	String name;
	int id;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
