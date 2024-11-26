package com.qacart.todo.testcases;

import com.qacart.todo.Apis.UserApi;
import com.qacart.todo.models.Error;
import com.qacart.todo.models.User;
import com.qacart.todo.steps.UserSteps;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
@Feature("User Feature")
public class UserTest {
    @Story("Should Be Able To Register")
    @Test(description = "Should Be Able To Register")
    public void shouldBeAbleToRegister(){
        //File body = new File("src/test/resources/signup.json");
        User user = UserSteps.generateUser();

        Response response = UserApi.register(user);
        User returnUser = response.body().as(User.class);
        assertThat(response.statusCode(),equalTo(201));
        assertThat(returnUser.getFirstName(),equalTo(user.getFirstName()));

    }
    @Story("ShouldNot Be Able To Register With The Same Email")
    @Test(description = "ShouldNot Be Able To Register With The Same Email")
    public void shouldNotBeAbleToRegisterWithTheSameEmail(){
        User user = UserSteps.RegisteredUser();

        Response response = UserApi.register(user);
        assertThat(response.statusCode(),equalTo(400));
        Error returnedError = response.body().as(Error.class);
        assertThat(returnedError.getMessage(),equalTo(com.qacart.todo.data.Error.EmailRegisterdBefore));

    }
    @Story("Should Be Able To Login")
    @Test(description = "Should Be Able To Login")
    public void shouldBeAbleToLogin(){
        User user =UserSteps.RegisteredUser();
        User LoginUser = new User(user.getEmail(), user.getPassword());
       Response response= UserApi.login(LoginUser);
        assertThat(response.statusCode(),equalTo(200));
        User returnedUser = response.body().as(User.class);
        assertThat(returnedUser.getFirstName(),equalTo(user.getFirstName()));
        assertThat(returnedUser.getAccessToken(),not(equalTo(null)));

    }
    @Story("ShouldNot Be Able To Login With In Correct Email Or Password")
    @Test(description = "ShouldNot Be Able To Login With In Correct Email Or Password")
    public void shouldNotBeAbleToLoginWithInCorrectEmailOrPassword(){
        User user =UserSteps.RegisteredUser();
        User LoginUser = new User(user.getEmail(), "user.getPassword()");


        Response response= UserApi.login(LoginUser);

        assertThat(response.statusCode(),equalTo(401));
        Error returnError = response.body().as(Error.class);
        assertThat(returnError.getMessage(),equalTo(com.qacart.todo.data.Error.EmailorPasswordareincorrect));
    }
}
