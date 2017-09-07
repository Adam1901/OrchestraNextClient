package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class visit {
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

    public String getIdAsString() {
        return String.valueOf(id);
    }

    @Override
    public String toString() {
        return "visit{" +
                "id=" + id +
                ", totalWaitingTime=" + totalWaitingTime +
                ", waitingTime=" + waitingTime +
                ", servedVisitServices=" + servedVisitServices +
                ", ticketId='" + ticketId + '\'' +
                ", visitMarks=" + visitMarks +
                ", customerIds=" + customerIds +
                ", recycleAllowed=" + recycleAllowed +
                ", noshowAllowed=" + noshowAllowed +
                ", recallAllowed=" + recallAllowed +
                ", timeSinceCalled=" + timeSinceCalled +
                ", parameterMap=" + parameterMap +
                ", appointmentTime='" + appointmentTime + '\'' +
                ", branchCurrentTime='" + branchCurrentTime + '\'' +
                ", currentVisitService=" + currentVisitService +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof visit)) return false;

        visit visit = (visit) o;

        if (getId() != visit.getId()) return false;
        if (getTotalWaitingTime() != visit.getTotalWaitingTime()) return false;
        if (getWaitingTime() != visit.getWaitingTime()) return false;
        if (isRecycleAllowed() != visit.isRecycleAllowed()) return false;
        if (isNoshowAllowed() != visit.isNoshowAllowed()) return false;
        if (isRecallAllowed() != visit.isRecallAllowed()) return false;
        if (getTimeSinceCalled() != visit.getTimeSinceCalled()) return false;
        if (getServedVisitServices() != null ? !getServedVisitServices().equals(visit.getServedVisitServices()) : visit.getServedVisitServices() != null)
            return false;
        if (getTicketId() != null ? !getTicketId().equals(visit.getTicketId()) : visit.getTicketId() != null)
            return false;
        if (getVisitMarks() != null ? !getVisitMarks().equals(visit.getVisitMarks()) : visit.getVisitMarks() != null)
            return false;
        if (getCustomerIds() != null ? !getCustomerIds().equals(visit.getCustomerIds()) : visit.getCustomerIds() != null)
            return false;
        if (getParameterMap() != null ? !getParameterMap().equals(visit.getParameterMap()) : visit.getParameterMap() != null)
            return false;
        if (getAppointmentTime() != null ? !getAppointmentTime().equals(visit.getAppointmentTime()) : visit.getAppointmentTime() != null)
            return false;
        if (getBranchCurrentTime() != null ? !getBranchCurrentTime().equals(visit.getBranchCurrentTime()) : visit.getBranchCurrentTime() != null)
            return false;
        return getCurrentVisitService() != null ? getCurrentVisitService().equals(visit.getCurrentVisitService()) : visit.getCurrentVisitService() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getTotalWaitingTime();
        result = 31 * result + getWaitingTime();
        result = 31 * result + (getServedVisitServices() != null ? getServedVisitServices().hashCode() : 0);
        result = 31 * result + (getTicketId() != null ? getTicketId().hashCode() : 0);
        result = 31 * result + (getVisitMarks() != null ? getVisitMarks().hashCode() : 0);
        result = 31 * result + (getCustomerIds() != null ? getCustomerIds().hashCode() : 0);
        result = 31 * result + (isRecycleAllowed() ? 1 : 0);
        result = 31 * result + (isNoshowAllowed() ? 1 : 0);
        result = 31 * result + (isRecallAllowed() ? 1 : 0);
        result = 31 * result + getTimeSinceCalled();
        result = 31 * result + (getParameterMap() != null ? getParameterMap().hashCode() : 0);
        result = 31 * result + (getAppointmentTime() != null ? getAppointmentTime().hashCode() : 0);
        result = 31 * result + (getBranchCurrentTime() != null ? getBranchCurrentTime().hashCode() : 0);
        result = 31 * result + (getCurrentVisitService() != null ? getCurrentVisitService().hashCode() : 0);
        return result;
    }
}