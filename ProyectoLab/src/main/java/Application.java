

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import configuracion.Configuracion;
import lombok.Cleanup;

@SpringBootApplication
@ComponentScan(basePackages = "configuracion")
public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//@Cleanup
		//AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Configuracion.class);
		
		SpringApplication.run(Application.class, args);
		
	}

}
