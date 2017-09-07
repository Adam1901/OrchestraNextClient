package dto;

public class DTOBranch implements OrchestraDTOSortable, OrchestraDTO {

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

    public String getIdAsString() {
        return String.valueOf(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DTOBranch)) return false;

        DTOBranch dtoBranch = (DTOBranch) o;

        if (getId() != dtoBranch.getId()) return false;
        return getName() != null ? getName().equals(dtoBranch.getName()) : dtoBranch.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + getId();
        return result;
    }
}
