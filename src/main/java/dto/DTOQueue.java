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
}
