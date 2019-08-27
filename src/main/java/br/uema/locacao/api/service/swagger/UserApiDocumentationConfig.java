package br.uema.locacao.api.service.swagger;

import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;

@SwaggerDefinition(
		info = @Info(
				description = "Detalhes da API LOCAÇÃO",
				version = "V0.0.1-SNAPSHOP",
				title = "API LOCAÇÃO",
				contact = @Contact(
						name = "Lucas Ferreira",
						email = "lucasraphael.fernandes@gmail.com",
						url = ""),
				license = @License(
						name = "Apache 2.0",
						url = "http://www.apache.org/licenses/LICENSE-2.0")),
		consumes = {"application/json"},
		produces = {"application/json"},
		schemes = {SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS}
)
public interface UserApiDocumentationConfig {

}
