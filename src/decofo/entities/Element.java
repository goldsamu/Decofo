package decofo.entities;

import javax.persistence.*;

@Entity
public class Element {
	@Id
	@Column(name = "code", nullable = false)
	private String code;
}
