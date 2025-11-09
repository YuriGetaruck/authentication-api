package yurigetaruck.authentication.modules.auth.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ControllerTest {

    @GetMapping("test-user")
    public String endpointTesteUser() {
        return "SUCESSO USER";
    }

    @GetMapping("test-admin")
    public String endpointTesteAdmin() {
        return "SUCESSO ADMIN";
    }

    @GetMapping("test-tester")
    public String endpointTesteTester() {
        return "SUCESSO TESTER";
    }
}
