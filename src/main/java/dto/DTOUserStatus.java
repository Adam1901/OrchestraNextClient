package dto;

import java.util.List;
import java.util.Map;

public class DTOUserStatus {

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public String getUserState() {
		return userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

	public String getVisitState() {
		return visitState;
	}

	public void setVisitState(String visitState) {
		this.visitState = visitState;
	}

	public int getServicePointId() {
		return servicePointId;
	}

	public void setServicePointId(int servicePointId) {
		this.servicePointId = servicePointId;
	}

	public String getServicePointName() {
		return servicePointName;
	}

	public void setServicePointName(String servicePointName) {
		this.servicePointName = servicePointName;
	}

	public String getServicePointState() {
		return servicePointState;
	}

	public void setServicePointState(String servicePointState) {
		this.servicePointState = servicePointState;
	}

	public int getWorkProfileId() {
		return workProfileId;
	}

	public void setWorkProfileId(int workProfileId) {
		this.workProfileId = workProfileId;
	}

	public String getWorkProfileName() {
		return workProfileName;
	}

	public void setWorkProfileName(String workProfileName) {
		this.workProfileName = workProfileName;
	}

	public List<String> getServicePointDeviceTypes() {
		return servicePointDeviceTypes;
	}

	public void setServicePointDeviceTypes(List<String> servicePointDeviceTypes) {
		this.servicePointDeviceTypes = servicePointDeviceTypes;
	}

	private Visit visit;
	private int branchId;
	private String userState;
	private String visitState;

	private int servicePointId;
	private String servicePointName;
	private String servicePointState;
	private int workProfileId;
	private String workProfileName;
	private List<String> servicePointDeviceTypes;

	public class Visit {
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

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((appointmentTime == null) ? 0 : appointmentTime.hashCode());
			result = prime * result + ((branchCurrentTime == null) ? 0 : branchCurrentTime.hashCode());
			result = prime * result + ((currentVisitService == null) ? 0 : currentVisitService.hashCode());
			result = prime * result + ((customerIds == null) ? 0 : customerIds.hashCode());
			result = prime * result + id;
			result = prime * result + (noshowAllowed ? 1231 : 1237);
			result = prime * result + ((parameterMap == null) ? 0 : parameterMap.hashCode());
			result = prime * result + (recallAllowed ? 1231 : 1237);
			result = prime * result + (recycleAllowed ? 1231 : 1237);
			result = prime * result + ((servedVisitServices == null) ? 0 : servedVisitServices.hashCode());
			result = prime * result + ((ticketId == null) ? 0 : ticketId.hashCode());
			result = prime * result + timeSinceCalled;
			result = prime * result + totalWaitingTime;
			result = prime * result + ((unservedVisitServices == null) ? 0 : unservedVisitServices.hashCode());
			result = prime * result + ((visitMarks == null) ? 0 : visitMarks.hashCode());
			result = prime * result + waitingTime;
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
			Visit other = (Visit) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (appointmentTime == null) {
				if (other.appointmentTime != null)
					return false;
			} else if (!appointmentTime.equals(other.appointmentTime))
				return false;
			if (branchCurrentTime == null) {
				if (other.branchCurrentTime != null)
					return false;
			} else if (!branchCurrentTime.equals(other.branchCurrentTime))
				return false;
			if (currentVisitService == null) {
				if (other.currentVisitService != null)
					return false;
			} else if (!currentVisitService.equals(other.currentVisitService))
				return false;
			if (customerIds == null) {
				if (other.customerIds != null)
					return false;
			} else if (!customerIds.equals(other.customerIds))
				return false;
			if (id != other.id)
				return false;
			if (noshowAllowed != other.noshowAllowed)
				return false;
			if (parameterMap == null) {
				if (other.parameterMap != null)
					return false;
			} else if (!parameterMap.equals(other.parameterMap))
				return false;
			if (recallAllowed != other.recallAllowed)
				return false;
			if (recycleAllowed != other.recycleAllowed)
				return false;
			if (servedVisitServices == null) {
				if (other.servedVisitServices != null)
					return false;
			} else if (!servedVisitServices.equals(other.servedVisitServices))
				return false;
			if (ticketId == null) {
				if (other.ticketId != null)
					return false;
			} else if (!ticketId.equals(other.ticketId))
				return false;
			if (timeSinceCalled != other.timeSinceCalled)
				return false;
			if (totalWaitingTime != other.totalWaitingTime)
				return false;
			if (unservedVisitServices == null) {
				if (other.unservedVisitServices != null)
					return false;
			} else if (!unservedVisitServices.equals(other.unservedVisitServices))
				return false;
			if (visitMarks == null) {
				if (other.visitMarks != null)
					return false;
			} else if (!visitMarks.equals(other.visitMarks))
				return false;
			if (waitingTime != other.waitingTime)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Visit [id=" + id + ", totalWaitingTime=" + totalWaitingTime + ", waitingTime=" + waitingTime
					+ ", servedVisitServices=" + servedVisitServices + ", ticketId=" + ticketId + ", visitMarks="
					+ visitMarks + ", customerIds=" + customerIds + ", recycleAllowed=" + recycleAllowed
					+ ", noshowAllowed=" + noshowAllowed + ", recallAllowed=" + recallAllowed + ", timeSinceCalled="
					+ timeSinceCalled + ", parameterMap=" + parameterMap + ", appointmentTime=" + appointmentTime
					+ ", branchCurrentTime=" + branchCurrentTime + ", unservedVisitServices=" + unservedVisitServices
					+ ", currentVisitService=" + currentVisitService + "]";
		}

		private DTOUserStatus getOuterType() {
			return DTOUserStatus.this;
		}
		
		public String getIdAsString() {
			return String.valueOf(id);
		}

	}

	@Override
	public String toString() {
		return "DTOUserStatus [visit=" + visit + ", branchId=" + branchId + ", userState=" + userState + ", visitState="
				+ visitState + ", servicePointId=" + servicePointId + ", servicePointName=" + servicePointName
				+ ", servicePointState=" + servicePointState + ", workProfileId=" + workProfileId + ", workProfileName="
				+ workProfileName + ", servicePointDeviceTypes=" + servicePointDeviceTypes + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + branchId;
		result = prime * result + ((servicePointDeviceTypes == null) ? 0 : servicePointDeviceTypes.hashCode());
		result = prime * result + servicePointId;
		result = prime * result + ((servicePointName == null) ? 0 : servicePointName.hashCode());
		result = prime * result + ((servicePointState == null) ? 0 : servicePointState.hashCode());
		result = prime * result + ((userState == null) ? 0 : userState.hashCode());
		result = prime * result + ((visit == null) ? 0 : visit.hashCode());
		result = prime * result + ((visitState == null) ? 0 : visitState.hashCode());
		result = prime * result + workProfileId;
		result = prime * result + ((workProfileName == null) ? 0 : workProfileName.hashCode());
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
		DTOUserStatus other = (DTOUserStatus) obj;
		if (branchId != other.branchId)
			return false;
		if (servicePointDeviceTypes == null) {
			if (other.servicePointDeviceTypes != null)
				return false;
		} else if (!servicePointDeviceTypes.equals(other.servicePointDeviceTypes))
			return false;
		if (servicePointId != other.servicePointId)
			return false;
		if (servicePointName == null) {
			if (other.servicePointName != null)
				return false;
		} else if (!servicePointName.equals(other.servicePointName))
			return false;
		if (servicePointState == null) {
			if (other.servicePointState != null)
				return false;
		} else if (!servicePointState.equals(other.servicePointState))
			return false;
		if (userState == null) {
			if (other.userState != null)
				return false;
		} else if (!userState.equals(other.userState))
			return false;
		if (visit == null) {
			if (other.visit != null)
				return false;
		} else if (!visit.equals(other.visit))
			return false;
		if (visitState == null) {
			if (other.visitState != null)
				return false;
		} else if (!visitState.equals(other.visitState))
			return false;
		if (workProfileId != other.workProfileId)
			return false;
		if (workProfileName == null) {
			if (other.workProfileName != null)
				return false;
		} else if (!workProfileName.equals(other.workProfileName))
			return false;
		return true;
	}

}
