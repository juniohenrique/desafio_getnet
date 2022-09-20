package restrictions;

import com.github.javafaker.Faker;
import org.json.simple.JSONObject;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class UserFunctionalTest extends UserBase {

	@Test
	@Tag("user")
	void shouldReturnListOfUsers() {
		Response response = getListUsersPage("2");
		Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());
	}

	@Test
	@Tag("user")
	void shouldReturnSingleUser() {
		Response response = getSingleUser("2");
		Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());
	}

	@Test
	@Tag("user")
	void notShouldReturnUser() {
		Response response = getSingleUser("23");
		Assertions.assertEquals(HttpStatus.SC_NOT_FOUND, response.statusCode());
	}

	@Test
	@Tag("unknown")
	void shouldReturnListOfUsersUnknown() {
		Response response = getListUsersUnknown();
		Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());
	}

	@Test
	@Tag("unknown")
	void shouldReturnSingleUserUnknown() {
		Response response = getSingleUserUnknown("2");
		Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());
	}

	@Test
	@Tag("unknown")
	void notShouldReturnSingleUserUnknown() {
		Response response = getSingleUserUnknown("23");
		Assertions.assertEquals(HttpStatus.SC_NOT_FOUND, response.statusCode());
	}

	@Test
	@Tag("user")
	void shouldCreateUser() {
		Faker faker = new Faker();
		String name = faker.name().firstName();
		String job = faker.job().position();
		Response response = postUser(name, job );
		Assertions.assertEquals(HttpStatus.SC_CREATED, response.statusCode());
	}

	@Test
	@Tag("user")
	void shouldUpdateUser() {
		Faker faker = new Faker();
		String name = faker.name().firstName();
		String job = faker.job().position();
		Response response = putUser(name, job );
		Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());
	}

	@Test
	@Tag("user")
	void shouldPatchUser() {
		Faker faker = new Faker();
		String name = faker.name().firstName();
		String job = faker.job().position();
		Response response = patchUser(name, job );
		Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());
	}

	@Test
	@Tag("user")
	void shouldDeletehUser() {
		Response response = deleteUser();
		Assertions.assertEquals(HttpStatus.SC_NO_CONTENT, response.statusCode());
	}

	@Test
	@Tag("register")
	void shouldRegisterUser() {
		String email = "eve.holt@reqres.in";
		String password = "pistol";
		Response response = postRegisterUser(email, password );
		Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());
	}

	@Test
	@Tag("register")
	void notShouldRegisterUser() {
		String email = "sydney@fife";
		Response response = postRegisterUserWithoutPassword(email);
		Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, response.statusCode());
	}

	@Test
	@Tag("login")
	void shouldLoginUser() {
		String email = "eve.holt@reqres.in";
		String password = "cityslicka";
		Response response = postLoginUser(email, password );
		Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());
	}

	@Test
	@Tag("login")
	void notShouldLoginUser() {
		String email = "peter@klaven";
		Response response = postLoginUserWithoutPassword(email);
		Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, response.statusCode());
	}

	@Test
	@Tag("delay")
	void shouldDelayedResponse() {
		Response response = postDelayedResponse("3");
		Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());
	}

	private Response getListUsersPage (String page){
		Response response = given()
				.contentType(ContentType.JSON)
				.pathParam("page", page)
				.when()
				.get(BASE_PATH_USER_PAGE)
				.then()
				.extract().response();
		return response;
	}

	private Response getListUsersUnknown (){
		Response response = given()
				.contentType(ContentType.JSON)
				.when()
				.get(BASE_PATH_UNKNOWN)
				.then()
				.extract().response();
		return response;
	}

	private Response getSingleUserUnknown (String user){
		Response response = given()
				.contentType(ContentType.JSON)
				.when()
				.get(BASE_PATH_UNKNOWN + "/" + user)
				.then()
				.extract().response();
		return response;
	}

	private Response getSingleUser (String user){
		Response response = given()
				.contentType(ContentType.JSON)
				.when()
				.get(BASE_PATH_USER +"/" +user)
				.then()
				.extract().response();
		return response;
	}

	private Response postUser (String name, String job){
		JSONObject requestBody = new JSONObject();
		requestBody.put("name", name);
		requestBody.put("job", job);

		Response response = given()
				.contentType(ContentType.JSON)
				.body(requestBody.toJSONString())
				.when()
				.post(BASE_PATH_USER)
				.then()
				.extract().response();
		return response;
	}

	private Response putUser (String name, String job){
		JSONObject requestBody = new JSONObject();
		requestBody.put("name", name);
		requestBody.put("job", job);

		Response response = given()
				.contentType(ContentType.JSON)
				.body(requestBody.toJSONString())
				.when()
				.put(BASE_PATH_USER + "/2")
				.then()
				.extract().response();
		return response;
	}

	private Response patchUser (String name, String job){
		JSONObject requestBody = new JSONObject();
		requestBody.put("name", name);
		requestBody.put("job", job);

		Response response = given()
				.contentType(ContentType.JSON)
				.body(requestBody.toJSONString())
				.when()
				.patch(BASE_PATH_USER + "/2")
				.then()
				.extract().response();
		return response;
	}

	private Response deleteUser (){
		Response response = given()
				.contentType(ContentType.JSON)
				.when()
				.delete(BASE_PATH_USER + "/2")
				.then()
				.extract().response();
		return response;
	}

	private Response postRegisterUser (String email, String password){
		JSONObject requestBody = new JSONObject();
		requestBody.put("email", email);
		requestBody.put("password", password);

		Response response = given()
				.contentType(ContentType.JSON)
				.body(requestBody.toJSONString())
				.when()
				.post(BASE_PATH_REGISTER)
				.then()
				.extract().response();
		return response;
	}

	private Response postRegisterUserWithoutPassword (String email){
		JSONObject requestBody = new JSONObject();
		requestBody.put("email", email);

		Response response = given()
				.contentType(ContentType.JSON)
				.body(requestBody.toJSONString())
				.when()
				.post(BASE_PATH_REGISTER)
				.then()
				.extract().response();
		return response;
	}

	private Response postLoginUser (String email, String password){
		JSONObject requestBody = new JSONObject();
		requestBody.put("email", email);
		requestBody.put("password", password);

		Response response = given()
				.contentType(ContentType.JSON)
				.body(requestBody.toJSONString())
				.when()
				.post(BASE_PATH_LOGIN)
				.then()
				.extract().response();
		return response;
	}

	private Response postLoginUserWithoutPassword (String email){
		JSONObject requestBody = new JSONObject();
		requestBody.put("email", email);

		Response response = given()
				.contentType(ContentType.JSON)
				.body(requestBody.toJSONString())
				.when()
				.post(BASE_PATH_LOGIN)
				.then()
				.extract().response();
		return response;
	}

	private Response postDelayedResponse (String delay){
		Response response = given()
				.contentType(ContentType.JSON)
				.pathParam("delay", delay)
				.when()
				.get(BASE_PATH_USER_DELAY)
				.then()
				.extract().response();
		return response;
	}




}
