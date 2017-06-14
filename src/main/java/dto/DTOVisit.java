package dto;

import java.util.List;
import java.util.Map;

public class DTOVisit {
	private int id;
	private int totalWaitingTime;
	private int waitingTime;
	private Map<String, Object> servedVisitServices;
	private String ticketId;
	private Map<String, Object> visitMarks;
	private List<String> customerIds;
	private boolean recycleAllowed;
	private boolean noshowAllowed;
	private boolean recallAllowed;
	private int timeSinceCalled;
	private Map<String, Object> parameterMap;
	private String appointmentTime;
	private String branchCurrentTime;
	private Map<String, Object> unservedVisitServices;
	private Map<String, Object> currentVisitService;

	public int getTotalWaitingTime() {
		return totalWaitingTime;
	}

	public void setTotalWaitingTime(int totalWaitingTime) {
		this.totalWaitingTime = totalWaitingTime;
	}

	public int getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}

	public Map<String, Object> getServedVisitServices() {
		return servedVisitServices;
	}

	public void setServedVisitServices(Map<String, Object> servedVisitServices) {
		this.servedVisitServices = servedVisitServices;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public Map<String, Object> getVisitMarks() {
		return visitMarks;
	}

	public void setVisitMarks(Map<String, Object> visitMarks) {
		this.visitMarks = visitMarks;
	}

	public List<String> getCustomerIds() {
		return customerIds;
	}

	public void setCustomerIds(List<String> customerIds) {
		this.customerIds = customerIds;
	}

	public boolean isRecycleAllowed() {
		return recycleAllowed;
	}

	public void setRecycleAllowed(boolean recycleAllowed) {
		this.recycleAllowed = recycleAllowed;
	}

	public boolean isNoshowAllowed() {
		return noshowAllowed;
	}

	public void setNoshowAllowed(boolean noshowAllowed) {
		this.noshowAllowed = noshowAllowed;
	}

	public boolean isRecallAllowed() {
		return recallAllowed;
	}

	public void setRecallAllowed(boolean recallAllowed) {
		this.recallAllowed = recallAllowed;
	}

	public int getTimeSinceCalled() {
		return timeSinceCalled;
	}

	public void setTimeSinceCalled(int timeSinceCalled) {
		this.timeSinceCalled = timeSinceCalled;
	}

	public Map<String, Object> getParameterMap() {
		return parameterMap;
	}

	public void setParameterMap(Map<String, Object> parameterMap) {
		this.parameterMap = parameterMap;
	}

	public String getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(String appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public String getBranchCurrentTime() {
		return branchCurrentTime;
	}

	public void setBranchCurrentTime(String branchCurrentTime) {
		this.branchCurrentTime = branchCurrentTime;
	}

	public Map<String, Object> getUnservedVisitServices() {
		return unservedVisitServices;
	}

	public void setUnservedVisitServices(Map<String, Object> unservedVisitServices) {
		this.unservedVisitServices = unservedVisitServices;
	}

	public Map<String, Object> getCurrentVisitService() {
		return currentVisitService;
	}

	public void setCurrentVisitService(Map<String, Object> currentVisitService) {
		this.currentVisitService = currentVisitService;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
