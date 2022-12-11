package tric.tricproject.Model;

import javax.persistence.*;

/**
 * Role class holds user roles for authorization
 * includes nested Enum ERole
 *
 * @author Bogdan Mezei
 * @version 1.0, October 2022
 */
@Entity
@Table(name = "roles", schema ="admin_login")
public class Role {

	public enum ERole {
		ROLE_USER,
		ROLE_MODERATOR,
		ROLE_ADMIN
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;

	public Role() {

	}

	public Role(ERole name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ERole getName() {
		return name;
	}

	public void setName(ERole name) {
		this.name = name;
	}
}
