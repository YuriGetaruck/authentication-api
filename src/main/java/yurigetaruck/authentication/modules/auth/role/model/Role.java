package yurigetaruck.authentication.modules.auth.role.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;
import yurigetaruck.authentication.modules.auth.role.enums.EAplication;
import yurigetaruck.authentication.modules.auth.role.dto.request.RoleRegisterRequest;
import yurigetaruck.authentication.modules.auth.role.dto.request.RoleUpdateRequest;

@Entity
@Getter
@Setter
@Builder
@Table(name = "ROLE")
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(unique = true)
    private String code;
    @Enumerated(EnumType.STRING)
    private EAplication aplication;

    public static Role of(RoleRegisterRequest roleRegisterRequest, String code) {

        return Role.builder()
                .name(StringUtils.capitalize(roleRegisterRequest.name().trim()))
                .code(code)
                .aplication(EAplication.valueOf(roleRegisterRequest.application().trim().toUpperCase()))
                .build();
    }

    public static Role of(RoleUpdateRequest roleUpdateRequest) {
        return Role.builder()
                .id(roleUpdateRequest.code())
                .name(roleUpdateRequest.name())
                .build();
    }
}
