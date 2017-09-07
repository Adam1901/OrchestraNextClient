package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DTOUserStatus implements OrchestraDTO {
    private visit visit;
    private int branchId;
    private String userState;
    private String visitState;

    private int servicePointId;
    private String servicePointName;
    private String servicePointState;
    private int workProfileId;
    private String workProfileName;
    private List<String> servicePointDeviceTypes;

    public visit getVisit() {
        return visit;
    }

    public void setVisit(visit visit) {
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

    @Override
    public String toString() {
        return "DTOUserStatus{" +
                "visit=" + visit +
                ", branchId=" + branchId +
                ", userState='" + userState + '\'' +
                ", visitState='" + visitState + '\'' +
                ", servicePointId=" + servicePointId +
                ", servicePointName='" + servicePointName + '\'' +
                ", servicePointState='" + servicePointState + '\'' +
                ", workProfileId=" + workProfileId +
                ", workProfileName='" + workProfileName + '\'' +
                ", servicePointDeviceTypes=" + servicePointDeviceTypes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DTOUserStatus)) return false;

        DTOUserStatus that = (DTOUserStatus) o;

        if (getBranchId() != that.getBranchId()) return false;
        if (getServicePointId() != that.getServicePointId()) return false;
        if (getWorkProfileId() != that.getWorkProfileId()) return false;
        if (getVisit() != null ? !getVisit().equals(that.getVisit()) : that.getVisit() != null) return false;
        if (getUserState() != null ? !getUserState().equals(that.getUserState()) : that.getUserState() != null)
            return false;
        if (getVisitState() != null ? !getVisitState().equals(that.getVisitState()) : that.getVisitState() != null)
            return false;
        if (getServicePointName() != null ? !getServicePointName().equals(that.getServicePointName()) : that.getServicePointName() != null)
            return false;
        if (getServicePointState() != null ? !getServicePointState().equals(that.getServicePointState()) : that.getServicePointState() != null)
            return false;
        if (getWorkProfileName() != null ? !getWorkProfileName().equals(that.getWorkProfileName()) : that.getWorkProfileName() != null)
            return false;
        return getServicePointDeviceTypes() != null ? getServicePointDeviceTypes().equals(that.getServicePointDeviceTypes()) : that.getServicePointDeviceTypes() == null;
    }

    @Override
    public int hashCode() {
        int result = getVisit() != null ? getVisit().hashCode() : 0;
        result = 31 * result + getBranchId();
        result = 31 * result + (getUserState() != null ? getUserState().hashCode() : 0);
        result = 31 * result + (getVisitState() != null ? getVisitState().hashCode() : 0);
        result = 31 * result + getServicePointId();
        result = 31 * result + (getServicePointName() != null ? getServicePointName().hashCode() : 0);
        result = 31 * result + (getServicePointState() != null ? getServicePointState().hashCode() : 0);
        result = 31 * result + getWorkProfileId();
        result = 31 * result + (getWorkProfileName() != null ? getWorkProfileName().hashCode() : 0);
        result = 31 * result + (getServicePointDeviceTypes() != null ? getServicePointDeviceTypes().hashCode() : 0);
        return result;
    }
}
