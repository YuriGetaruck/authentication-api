package yurigetaruck.authentication.modules.auth.role.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum EAplication {

    AUTHENTICATION ("AUTHENTICATION", "AUTH"),
    PORTFOLIO("PORTFOLIO", "PRTF");

    private String name;
    private String code;
}
