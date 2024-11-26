package com.qacart.todo.Apis;

import com.qacart.todo.base.Specs;
import com.qacart.todo.data.Route;
import com.qacart.todo.models.Todo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TodoApi {
    public static Response addTodo(Todo todo,String token){
        return given().spec(Specs.getRequestSpec())
                .body(todo)
                .auth().oauth2(token)
                .when()
                .post(Route.Todo_path)
                .then()
                .log().all()
                .extract().response();
    }
    public static Response getAllTodo(String token){
        return given().spec(Specs.getRequestSpec())
                .auth().oauth2(token)
                .when()
                .get(Route.Todo_path)
                .then()
                .log().all()
                .extract().response();
    }
    public static Response getTodo(String token,String TaskId){
        return given().baseUri("https://qacart-todo.herokuapp.com")
                .auth()
                .oauth2(token)
                .when()
                .get(Route.Todo_path+"/"+TaskId)
                .then()
                .extract().response();
    }
    public static Response UpdateTodo(Todo todo,String token,String TaskId){
        return given()
                .spec(Specs.getRequestSpec())                .body(todo)
                .auth()
                .oauth2(token)
                .when()
                .put(Route.Todo_path+"/"+TaskId)
                .then().log().all()
                .extract().response();
    }
    public static Response DeleteTodo(String token,String TaskId){
        return given()
                .spec(Specs.getRequestSpec())
                .auth()
                .oauth2(token)
                .when()
                .delete(Route.Todo_path+"/"+TaskId)
                .then().log().all()
                .extract().response();
    }
}
