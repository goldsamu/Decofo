
package decofo.services;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import decofo.entities.Nature;
import decofo.entities.Person;

@Stateful
public class PersonManager {

    @PersistenceContext(unitName = "decofoDatabaseUnit")
    private EntityManager em;

    /**
     * The current user logged.
     */
    private Person user = new Person();

    public Person getUser() {
	return user;
    }

    /**
     * Check if a person is in the database. If the person is not in the table, then
     * he is added and set to user. Else if he is found, he's just set to user.
     * 
     * @param person The person to check.
     */
    public void check(Person person) {
	Person p = em.find(Person.class, person.getLogin());

	if (p == null) {
	    person.setAdmin(false);
	    this.user = createPerson(person);
	} else {
	    this.user = p;
	}
    }

    /**
     * Reset the user to default value.
     */
    public void logout() {
	this.user = new Person();
    }

    /**
     * Add a new person to the database.
     * 
     * @param person The person to add.
     * @return The person added.
     */
    public Person createPerson(Person person) {
	em.persist(person);

	return person;
    }

    /**
     * Remove a person of the database.
     * 
     * @param person The person to remove.
     */
    public void removePerson(Person person) {
	Person p = em.find(Person.class, person.getLogin());

	if (p != null)
	    em.remove(p);
    }
    
    public Person findPersonByLogin(String login)
    {
	return em.find(Person.class, login);
    }
    
    public List<Person> findPersonsByName(String name)
    {
	TypedQuery<Person> q = em.createQuery("FROM Activity WHERE lower(name) LIKE lower(:name)", Person.class);
	q.setParameter("name", name + "%");
	return q.getResultList();
    }
    
    public List<Person> findAllPersons() {
	return em.createQuery("From Person", Person.class).getResultList();
}
}
