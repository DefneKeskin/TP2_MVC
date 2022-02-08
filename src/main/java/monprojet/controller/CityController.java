/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monprojet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.extern.slf4j.Slf4j;
import monprojet.dao.CityRepository;
import monprojet.dao.CountryRepository;
import monprojet.entity.City;
import monprojet.entity.Country;

/**
 *
 * @author defne
 */
@Controller // Cette classe est un Controller
@RequestMapping(path = "/cities") // Dans l'URL's commence avec  "/cities" (after Application path)
@Slf4j
public class CityController {

    @Autowired
    private CityRepository cityDao;
    @Autowired
    private CountryRepository countryDao;

    // On affichera par défaut la page 'cities.mustache'
    //mustache est ce qui fait le lien entre programme et la vue
    private static final String DEFAULT_VIEW = "cities";
    //private static final String CREATE_VIEW = "cities";
    /**
     * Affiche la page d'édition des villes
     *
     * @param model Les infos transmises à la vue (injecté par Spring)
     * @return le nom de la vue à afficher
     */
    @GetMapping(path = "show") //à l'URL http://localhost:8989/cities/show
    public String montreLesVilles(Model model) {
		log.info("On affiche les villes");
		// On initialise la ville avec des valeurs par défaut
		Country france = countryDao.findById(1).orElseThrow();
                //La classe facultative en Java est utilisée pour obtenir la valeur de 
                //cette instance facultative si elle est présente. S'il n'y a pas de valeur 
                //présente dans cette instance facultative, 
                //cette méthode lève l'exception générée à partir du fournisseur spécifié
                
                City nouvelle = new City("Nouvelle ville", france);
		nouvelle.setPopulation(50);
		model.addAttribute("cities", cityDao.findAll());
		model.addAttribute("city", nouvelle);
		model.addAttribute("countries", countryDao.findAll());
		return DEFAULT_VIEW;
	}
    
    
        @PostMapping(path="save") // Requête HTTP POST à l'URL http://localhost:8989/cities/save
	public String enregistreUneVille(City laVille) {
		cityDao.save(laVille);
		log.info("La ville {} a été enregistrée", laVille);
		// On redirige vers la page de liste des villes
		return "redirect:/cities/show";
	}
        
        @GetMapping(path = "edit")
	public String montreLeFormulairePourEdition(@RequestParam("id") int id, Model model) {
		model.addAttribute("city", cityDao.findById(id).get());
                model.addAttribute("countries",countryDao.findAll());
		return "edit";
	}
        
        @GetMapping(path = "delete")
	public String supprimeUneVillePuisMontreLaListe( @RequestParam("id") City laVille) {
		cityDao.delete(laVille); // Ici on peut avoir une erreur (Si il y a des produits dans cette catégorie par exemple)
		return "redirect:show"; // on se redirige vers l'affichage de la liste
	}
}
