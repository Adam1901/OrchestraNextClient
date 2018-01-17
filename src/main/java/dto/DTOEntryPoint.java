package dto;

import com.qmatic.qp.api.connectors.dto.EntryPoint;

public class DTOEntryPoint extends EntryPoint implements OrchestraDTOSortable, OrchestraDTO {

    public String getIdAsString(){
        return String.valueOf(getId());
    }

    @Override
    public String toString() {
        return getName();
    }
}
