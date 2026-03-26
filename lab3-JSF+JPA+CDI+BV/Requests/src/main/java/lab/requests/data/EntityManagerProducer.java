package lab.requests.data;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.Produces;

@Dependent
@Alternative
@Priority(1)
public class EntityManagerProducer {
    @PersistenceContext
    private EntityManager em;


    @Produces
    @RequestScoped
    public EntityManager getEntityManager() {
        return em;
    }

}
