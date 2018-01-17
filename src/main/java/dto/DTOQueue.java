package dto;

import com.qmatic.qp.api.connectors.dto.Queue;

public class DTOQueue extends Queue implements OrchestraDTOSortable {

	public String getIdAsString() {
		return String.valueOf(getId());
	}

}
