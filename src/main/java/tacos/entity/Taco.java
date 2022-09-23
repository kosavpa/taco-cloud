package tacos.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.rest.core.annotation.RestResource;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@RestResource(rel = "taco", path = "tacos")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class Taco implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Size(min = 5, message = "Name at least 5 characters long")
	private String name;

	private Date createdAt = new Date();

	@NotNull
	@Size(min = 1, message = "You must choose at least 1 ingredient")
	@ManyToMany
	private List<Ingredient> ingredients = new ArrayList<>();

	public void addIngredients(Ingredient ingredient){
		this.ingredients.add(ingredient);
	}
}