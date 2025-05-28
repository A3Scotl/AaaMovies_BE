package movies.be.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequest {
    @NotBlank(message = "Token is required")
    private String token;

    @NotBlank(message = "PassWord is required")
    @Size(min = 6, message = "PassWord must be at least 6 characters")
    private String passWord;
}