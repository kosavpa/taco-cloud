package tacos.controllers.web_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import tacos.data.IngredientRepository;
import tacos.entity.Ingredient;

@Component
public class IngrediaentByIdConverter implements Converter<String, Ingredient>{
	
	private final IngredientRepository ingredientRepo;
	
	@Autowired
	public IngrediaentByIdConverter(IngredientRepository ingredientRepo) {
		this.ingredientRepo = ingredientRepo;
	}
	
	@Override
	public Ingredient convert(String id) {

		return ingredientRepo.findById(id).orElse(null);
	}
}
