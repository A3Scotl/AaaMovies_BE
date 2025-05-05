package movies.be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ToggleActiveRequest {
    private Boolean isActive;
}