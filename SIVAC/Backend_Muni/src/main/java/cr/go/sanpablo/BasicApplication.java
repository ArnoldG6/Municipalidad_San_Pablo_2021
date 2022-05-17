package cr.go.sanpablo;

import java.util.Collections;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BasicApplication {
    //private static EntityManager manager;
    //private static EntityManagerFactory manFact;
    public static void main(String[] args) {
        //SpringApplication.run(BasicApplication.class, args);
        SpringApplication app = new SpringApplication(BasicApplication.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", "8083"));
        //.singletonMap("server.port", "8083"));
        app.run(args);
        /*manFact = Persistence.createEntityManagerFactory("Persistencia");
        manager = manFact.createEntityManager();
        List<AdminFile> files;
        files = (List<AdminFile>) manager.createQuery("From file").getResultList();
        System.out.println("Cantidad de expedientes en la base de datos: " + files.size());*/
    }

}
