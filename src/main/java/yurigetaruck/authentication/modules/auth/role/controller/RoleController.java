package yurigetaruck.authentication.modules.auth.role.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yurigetaruck.authentication.modules.auth.role.dto.request.RoleRegisterRequest;
import yurigetaruck.authentication.modules.auth.role.dto.request.RoleUpdateRequest;
import yurigetaruck.authentication.modules.auth.role.dto.response.RoleResponse;
import yurigetaruck.authentication.modules.auth.role.service.RoleService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/roles")
public class RoleController {

    private final RoleService service;

    @PostMapping
    public ResponseEntity<RoleResponse> create(@RequestBody RoleRegisterRequest roleRegisterRequest) {
        log.info("Cadastrando role: {}", roleRegisterRequest.name());
        var createdRole = service.create(roleRegisterRequest);
        var response = ResponseEntity.ok().body(createdRole);
        log.info("Cadastrado com sucesso, role: {}", createdRole.code());

        return response;
    }

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAll() {
        log.info("Buscando roles...");
        return ResponseEntity.ok().body(service.getAll());
    }

    @PutMapping
    public ResponseEntity<RoleResponse> update(@RequestBody RoleUpdateRequest roleUpdateRequest) {
        log.info("Atualizando role: {}", roleUpdateRequest.code());
        var response = ResponseEntity.ok().body(service.update(roleUpdateRequest));
        log.info("Role {} atuaizada com sucesso.", roleUpdateRequest.code());

        return response;
    }

}
