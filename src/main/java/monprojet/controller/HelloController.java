package monprojet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.extern.slf4j.Slf4j;
import monprojet.dao.CityRepository;
import monprojet.entity.City;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/hello") // This means URL's start with /hello (after Application path)
@Slf4j
public class HelloController {
	//On injecte le repository dans le controlleur
	@Autowired
	CityRepository dao;

	// On affichera par défaut la page 'hello.html'
	private static final String DEFAULT_VIEW = "hello";
	
	@GetMapping(path = "villes")
	public String afficheLesVilles(Model model) {
		List<City> cities = dao.findAll();
		model.addAttribute("message", "World");//transmission des données à la vue
		model.addAttribute("cities", cities);
		return DEFAULT_VIEW;
	}

	@GetMapping(path = "noParam")
	public String sayHello(Model model) {
		model.addAttribute("message", "World");
		return DEFAULT_VIEW;
	}

	@GetMapping(path = "requestParam")
	// cf. https://www.baeldung.com/spring-request-param
	public String sayHelloTo(
		@RequestParam(required = false) String name, 
		Model model
	) {
		model.addAttribute("message", name);
		return DEFAULT_VIEW;
	}

	@GetMapping(path = "defaultValue")
	public String sayHelloToDefault(
		@RequestParam(defaultValue = "Inconnu") 
		String name, 
		Model model
	) {
		model.addAttribute("message", name);
		return DEFAULT_VIEW;
	}
}