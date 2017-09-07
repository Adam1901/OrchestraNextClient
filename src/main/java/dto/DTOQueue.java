package dto;

public class DTOQueue implements OrchestraDTOSortable {
	private int id;
	private String name;
	private int customersWaiting;
	private int waitingTime;
	private String queueType;

	public int getId() {
		return id;
	}

	public String getIdAsString() {
		return String.valueOf(id);
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

	public int getCustomersWaiting() {
		return customersWaiting;
	}

	public void setCustomersWaiting(int customersWaiting) {
		this.customersWaiting = customersWaiting;
	}

	public int getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}
	
	public String getQueueType() {
		return queueType;
	}

	public void setQueueType(String queueType) {
		this.queueType = queueType;
	}

	@Override
	public String toString() {
		return "DTOQueue{" +
				"id=" + id +
				", name='" + name + '\'' +
				", customersWaiting=" + customersWaiting +
				", waitingTime=" + waitingTime +
				", queueType='" + queueType + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof DTOQueue)) return false;

		DTOQueue dtoQueue = (DTOQueue) o;

		if (getId() != dtoQueue.getId()) return false;
		if (getCustomersWaiting() != dtoQueue.getCustomersWaiting()) return false;
		if (getWaitingTime() != dtoQueue.getWaitingTime()) return false;
		if (getName() != null ? !getName().equals(dtoQueue.getName()) : dtoQueue.getName() != null) return false;
		return getQueueType() != null ? getQueueType().equals(dtoQueue.getQueueType()) : dtoQueue.getQueueType() == null;
	}

	@Override
	public int hashCode() {
		int result = getId();
		result = 31 * result + (getName() != null ? getName().hashCode() : 0);
		result = 31 * result + getCustomersWaiting();
		result = 31 * result + getWaitingTime();
		result = 31 * result + (getQueueType() != null ? getQueueType().hashCode() : 0);
		return result;
	}
}
