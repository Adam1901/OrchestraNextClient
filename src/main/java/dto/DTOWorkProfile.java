package dto;

import com.qmatic.qp.api.connectors.dto.WorkProfile;

public class DTOWorkProfile extends WorkProfile implements OrchestraDTOSortable, OrchestraDTO {

    public String getIdAsString() {
        return String.valueOf(getId());
    }

    @Override
    public String toString() {
        return getName();
    }
}

