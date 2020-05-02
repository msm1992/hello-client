package com.example.tracing.hello.client;

import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.opentracing.propagation.TextMapInjectAdapter;
import io.opentracing.util.GlobalTracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class HelloClientApplication implements CommandLineRunner{

	private static final Logger log = LoggerFactory.getLogger(HelloClientApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(HelloClientApplication.class, args);
	}

	@Bean(initMethod="init")
	public InitTracer tracer() {
		return new InitTracer();
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Override
	public void run(String ...args) throws Exception {
		Tracer tracer = GlobalTracer.get();

		//start apim client span
		Span sp = tracer.buildSpan("apim-client").start();

		//set tracing headers
		Map<String, String> tracerSpecificCarrier = new HashMap<>();
		SpanContext spanContext = sp.context();
		tracer.inject(spanContext, Format.Builtin.HTTP_HEADERS, new TextMapInjectAdapter(tracerSpecificCarrier));

		//set text map key value pairs as HTTP headers for the API request
		HttpHeaders headers = new HttpHeaders();
		for (Map.Entry<String,String> entry : tracerSpecificCarrier.entrySet()) {
			headers.set(entry.getKey(), entry.getValue());
		}

		//set Authorization Header, replace the token with a valid token
		headers.set("Authorization", "Bearer <token>");

		// build the request
		HttpEntity request = new HttpEntity(headers);

		// make an HTTP GET request with headers
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:8280/test/1.0/hello?name=sachini",
				HttpMethod.GET,
				request,
				String.class
		);

		log.info(response.toString());

		sp.finish();
	}


}
