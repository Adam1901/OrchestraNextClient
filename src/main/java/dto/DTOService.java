package dto;

public class DTOService implements OrchestraDTOSortable ,OrchestraDTO{
	String externalName;
	String internalDescription;
	String externalDescription;
	int targetTransactionTime;
	String internalName;
	int id;

	public String getExternalName() {
		return externalName;
	}

	public void setExternalName(String externalName) {
		this.externalName = externalName;
	}

	public String getInternalDescription() {
		return internalDescription;
	}

	public void setInternalDescription(String internalDescription) {
		this.internalDescription = internalDescription;
	}

	public String getExternalDescription() {
		return externalDescription;
	}

	public void setExternalDescription(String externalDescription) {
		this.externalDescription = externalDescription;
	}

	public int getTargetTransactionTime() {
		return targetTransactionTime;
	}

	public void setTargetTransactionTime(int targetTransactionTime) {
		this.targetTransactionTime = targetTransactionTime;
	}

	public String getInternalName() {
		return internalName;
	}

	public void setInternalName(String internalName) {
		this.internalName = internalName;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int getId() {
		return id;
	}

	public String getIdAsString() {
		return String.valueOf(id);
	}

	@Override
	public String toString() {
		return externalName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((externalDescription == null) ? 0 : externalDescription.hashCode());
		result = prime * result + ((externalName == null) ? 0 : externalName.hashCode());
		result = prime * result + id;
		result = prime * result + ((internalDescription == null) ? 0 : internalDescription.hashCode());
		result = prime * result + ((internalName == null) ? 0 : internalName.hashCode());
		result = prime * result + targetTransactionTime;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DTOService other = (DTOService) obj;
		if (externalDescription == null) {
			if (other.externalDescription != null)
				return false;
		} else if (!externalDescription.equals(other.externalDescription))
			return false;
		if (externalName == null) {
			if (other.externalName != null)
				return false;
		} else if (!externalName.equals(other.externalName))
			return false;
		if (id != other.id)
			return false;
		if (internalDescription == null) {
			if (other.internalDescription != null)
				return false;
		} else if (!internalDescription.equals(other.internalDescription))
			return false;
		if (internalName == null) {
			if (other.internalName != null)
				return false;
		} else if (!internalName.equals(other.internalName))
			return false;
		if (targetTransactionTime != other.targetTransactionTime)
			return false;
		return true;
	}

	@Override
	public String getName() {
		
		return externalName;
	}
}
