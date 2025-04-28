package cn.huangsy.resource;

import java.util.List;

import com.blazebit.persistence.CriteriaBuilderFactory;

import cn.huangsy.domain.Person;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/person")
public class PersonResource {

    @Inject EntityManager entityManager;
    @Inject CriteriaBuilderFactory cbf;

    @Transactional
    @POST
    public Person add(Person person) {
        Person.persist(person);
        return person;
    }

    @Transactional
    @GET
    @Path("/{id}")
    public Person get(@PathParam("id") Long id) {
        return Person.findById(id);
    }

    @GET
    @Path("/by-blaze")
    public List<Person> findByBlaze(@QueryParam("name") String name) {
        return cbf.create(entityManager, Person.class)
                .whereOr()
                .where("firstName").eq(name)
                .where("lastName").eq(name)
                .endOr()
                .getResultList();
    }

}
