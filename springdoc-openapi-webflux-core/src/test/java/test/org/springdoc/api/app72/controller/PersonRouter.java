package test.org.springdoc.api.app72.controller;

import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import test.org.springdoc.api.app72.handler.PersonHandler;
import test.org.springdoc.api.app72.service.PersonService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class PersonRouter {

	@RouterOperations({ @RouterOperation(path = "/getAllPersons", beanClass = PersonService.class, beanMethod = "getAll"),
			@RouterOperation(path = "/getPerson/{id}", beanClass = PersonService.class, beanMethod = "getById"),
			@RouterOperation(path = "/createPerson", beanClass = PersonService.class, beanMethod = "save"),
			@RouterOperation(path = "/deletePerson/{id}", beanClass = PersonService.class, beanMethod = "delete") })
	@Bean
	public RouterFunction<ServerResponse> personRoute(PersonHandler handler) {
		return RouterFunctions
				.route(GET("/getAllPersons").and(accept(MediaType.APPLICATION_JSON)), handler::findAll)
				.andRoute(GET("/getPerson/{id}").and(accept(MediaType.APPLICATION_STREAM_JSON)), handler::findById)
				.andRoute(POST("/createPerson").and(accept(MediaType.APPLICATION_JSON)), handler::save)
				.andRoute(DELETE("/deletePerson/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::delete);
	}

}
