package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.qmatic.qp.api.connectors.dto.UserStatus;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DTOUserStatus extends UserStatus implements OrchestraDTO {
}