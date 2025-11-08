package yurigetaruck.authentication.modules.auth.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ControllerTest {

    @GetMapping("test")
    public String endpointTeste() {
        return "SUCESSO";
    }
}
