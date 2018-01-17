package dto;

import com.qmatic.qp.api.connectors.dto.ServicePoint;

public class DTOServicePoint extends ServicePoint implements OrchestraDTOSortable, OrchestraDTO {
    public String getIdAsString() {
        return String.valueOf(getId());
    }

    @Override
    public String toString() {
        return getName();
    }

}
