package ndmstartup.joinstartup.Controllers;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.Services.Interfaces.EmployeeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;


}
