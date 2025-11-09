package yurigetaruck.authentication.modules.auth.user.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yurigetaruck.authentication.modules.auth.user.dto.request.AddUserRolesRequest;
import yurigetaruck.authentication.modules.auth.user.dto.response.AddUserRolesResponse;
import yurigetaruck.authentication.modules.auth.user.service.UserService;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("add-roles")
    public ResponseEntity<AddUserRolesResponse> addUserRoles(@RequestBody AddUserRolesRequest addUserRoles) {
        return ResponseEntity.ok().body(userService.addUserRoles(addUserRoles));
    }
}
