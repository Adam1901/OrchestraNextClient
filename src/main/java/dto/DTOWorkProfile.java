package dto;

public class DTOWorkProfile implements OrchestraDTO {
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
	@Override
	public String toString() {
		return name;
	}

	int id;
	String name;
	public String getIdAsString() {
		// TODO Auto-generated method stub
		return String.valueOf(id);
	}
}
