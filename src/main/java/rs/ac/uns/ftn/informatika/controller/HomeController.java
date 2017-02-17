package rs.ac.uns.ftn.informatika.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(HomeController.BASE_API_URL)
public class HomeController {

    public static final String BASE_API_URL = "/api";

	@RequestMapping(value = { "", "/home" }, method = RequestMethod.GET)
    public String sayHello() {
        return "Hello from scientific-papers-manager API!";
    }

}
