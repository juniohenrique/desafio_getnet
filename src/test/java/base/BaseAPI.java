package base;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseAPI {

	@BeforeAll
	public static void init() {
		RestAssured.baseURI = "https://reqres.in";
		RestAssured.port = 443;
		RestAssured.basePath = "/api";
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

	}

	@AfterAll
	public static void cleanUp() {

	}

}
