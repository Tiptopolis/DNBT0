package Metatron.Core.Primitive.Data;

import java.io.Serializable;

import org.hibernate.annotations.Any;

import jakarta.persistence.*;

public class aDescription implements Serializable{

	
	private String description;
	
	@Any(
	        metaDef = "EntityDescriptionMetaDef",
	        metaColumn = @Column(name = "entity_type"))
	    @JoinColumn(name = "entity_id")
	private Serializable subject;
	
}
