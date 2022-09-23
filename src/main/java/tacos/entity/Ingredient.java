package tacos.entity;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class Ingredient implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private final String id;
	private final String name;
	private final Type type;
	
	public enum Type{
		WRAP, PROTEIN, CHEESE, SAUCE, VEGGIES
	}
}