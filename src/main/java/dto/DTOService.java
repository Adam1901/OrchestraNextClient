package dto;

import com.qmatic.qp.api.connectors.dto.Service;

public class DTOService extends Service implements OrchestraDTOSortable ,OrchestraDTO{

	public String getIdAsString() {
		return String.valueOf(getId());
	}

	@Override
	public String toString() {
		return getExternalName();
	}

	public String getName(){
		return getExternalName();
	}

}
