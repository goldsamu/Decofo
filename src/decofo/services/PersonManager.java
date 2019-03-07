package decofo.services;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import decofo.entities.Person;

@Stateful
public class PersonManager {

    @PersistenceContext(unitName = "decofoDatabaseUnit")
    private EntityManager em;

    private Person user;

    public PersonManager() {
	setUser(new Person());
    }

    public Person getUser() {
	return user;
    }

    public void setUser(Person user) {
	this.user = user;
    }

    public void check(Person person) {
	Person p = em.find(Person.class, person.getId());

	if (p == null) {
	    this.user = createPerson(person);
	} else {
	    this.user = p;
	}
    }

    public void logout() {
	this.user = new Person();
    }

    public Person createPerson(Person person) {
	em.persist(person);

	return person;
    }

    public void removePerson(Person person) {
	Person p = em.find(Person.class, person.getId());

	if (p != null)
	    em.remove(p);
    }
}
