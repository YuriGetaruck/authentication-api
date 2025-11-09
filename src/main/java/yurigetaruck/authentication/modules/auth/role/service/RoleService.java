package yurigetaruck.authentication.modules.auth.role.service;

import ch.qos.logback.core.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yurigetaruck.authentication.modules.auth.common.exception.ValidationException;
import yurigetaruck.authentication.modules.auth.role.dto.request.RoleRegisterRequest;
import yurigetaruck.authentication.modules.auth.role.dto.request.RoleUpdateRequest;
import yurigetaruck.authentication.modules.auth.role.dto.response.RoleResponse;
import yurigetaruck.authentication.modules.auth.role.model.Role;
import yurigetaruck.authentication.modules.auth.role.repository.RoleRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;

    public RoleResponse create(RoleRegisterRequest roleRegisterRequest) {
        var code = generateRoleCodeFromName(roleRegisterRequest.name());
        validadeUniqueRoleCode(code);
        return RoleResponse.of(repository.save(Role.of(roleRegisterRequest, code)));
    }

    public List<RoleResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(RoleResponse::of)
                .collect(Collectors.toList());
    }

    public RoleResponse update(RoleUpdateRequest roleUpdateRequest) {
        validadeUniqueRoleCode(roleUpdateRequest.name());
        var role = repository.findById(roleUpdateRequest.code());

        if (role.isPresent()) {
            return RoleResponse.of(repository.save(Role.of(roleUpdateRequest)));
        } else {
            throw new ValidationException("Role não encontrada.");
        }
    }

    public Set<Role> getRolesFromNames(List<String> codes) {
        var roles = repository.findByCodeIn(codes);
        if (!roles.isEmpty() && new HashSet<>(roles.stream().map(Role::getCode).toList()).containsAll(codes)) {
            return roles;
        } else {
            throw new ValidationException("Roles inválidas.");
        }
    }

    private void validadeUniqueRoleCode(String code) {
        if (repository.findByCode(code).isPresent()) {
            throw new ValidationException("Role já cadastrada.");
        }
    }

    private static String generateRoleCodeFromName(String name) {
        if (StringUtil.isNullOrEmpty(name) || !name.trim().matches("[A-Za-z0-9 ]+")) {
            throw new ValidationException("Nome para ROLE inválido.");
        }

        var sanitizedToUpperCaseCode = name
                .trim()
                .toUpperCase()
                .replaceAll("[^A-Z0-9]", "_")
                .replaceAll("_+", "_");

        return sanitizedToUpperCaseCode.startsWith("ROLE_")
                ? sanitizedToUpperCaseCode
                : "ROLE_".concat(sanitizedToUpperCaseCode);
    }
}
