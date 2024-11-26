package com.qacart.todo.Apis;

import com.qacart.todo.data.Route;
import com.qacart.todo.models.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserApi {
    public static Response register(User user){
        return given().baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON).body(user)
                .when()
                .post(Route.Register_Path)
                .then().log().all()
                .extract().response();
    }
    public static Response login(User user){
        return given().baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON).body(user)
                .when()
                .post(Route.Login_Path)
                .then().log().all()
                .extract().response();
    }
}
