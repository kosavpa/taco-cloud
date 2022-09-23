package tacos.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import tacos.data.IngredientRepository;
import tacos.entity.Ingredient;
import tacos.entity.Taco;
import tacos.entity.TacoOrder;
import tacos.entity.Ingredient.Type;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignController {

	private final IngredientRepository ingredientRepo;

	@Autowired
	public DesignController(IngredientRepository ingredientRepo) {
		this.ingredientRepo = ingredientRepo;
	}

	@ModelAttribute
	public void addIngredientsToModel(Model model) {
		List<Ingredient> ingredients = new ArrayList<>();

		ingredientRepo.findAll().forEach(ingredients::add);

		Type[] types = Ingredient.Type.values();
		for(Type type : types) {
			model.addAttribute(type.toString().toLowerCase(), 
					fillterByType(ingredients, type));
		}
	}
	
	private List<Ingredient> fillterByType(
			List<Ingredient> ingredients, Type type) {
	
		return ingredients
				.stream()
				.filter(x -> x.getType().equals(type))
				.collect(Collectors.toList());
	}
	
	@ModelAttribute(name = "taco")
	public Taco taco() {
		return new Taco();
	}
	
	@ModelAttribute(name = "tacoOrder")
	public TacoOrder tacoOrder() {
		return new TacoOrder();
	}
	
	@GetMapping
	public String showDesignForm() {
		return "design";
	}
	
	@PostMapping
	public String processTaco(
			@Valid Taco taco, Errors errors,
			@ModelAttribute TacoOrder tacoOrder) {
		
		if(errors.hasErrors())
			return "design";
		
		tacoOrder.addTaco(taco);
		log.info("Processing taco: {}", taco);
		
		return "redirect:/orders/current";
	}
}