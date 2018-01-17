package dto;

import com.qmatic.qp.api.connectors.dto.Branch;

public class DTOBranch extends Branch implements OrchestraDTOSortable, OrchestraDTO {

    public String getIdAsString() {
        return String.valueOf(getId());
    }

    @Override
    public String toString() {
        return getName();
    }


}
