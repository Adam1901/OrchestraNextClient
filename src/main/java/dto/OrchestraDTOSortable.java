package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public interface OrchestraDTOSortable {

	String getName();
	
	int getId();

}
