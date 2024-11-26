package com.qacart.todo.testcases;

import com.qacart.todo.Apis.TodoApi;
import com.qacart.todo.models.Error;
import com.qacart.todo.models.Todo;
import com.qacart.todo.steps.TodoSteps;
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
@Feature("Todo Feature")

public class TodoTest {
    @Story("Should Be Able To Add Task")
    @Test (description = "Should Be Able To Add Task")
    public void shouldBeAbleToAddTask(){
        Todo todo = TodoSteps.generateToDo();
        String token = UserSteps.GetUserToken();

        Response response = TodoApi.addTodo(todo,token);
        assertThat(response.statusCode(),equalTo(201));
        Todo returnedTask =response.body().as(Todo.class);
        assertThat(returnedTask.getItem(),equalTo(todo.getItem()));
        assertThat(returnedTask.getIsCompleted(),equalTo(todo.getIsCompleted()));

    }
    @Story("ShouldNot Be Able To Add Task If Is Completed Is Missing")
    @Test(description = "ShouldNot Be Able To Add Task If Is Completed Is Missing")
    public void shouldNotBeAbleToAddTaskIfIsCompletedIsMissing(){
        String token = UserSteps.GetUserToken();
            Todo todo =new Todo("Learn Appium");
        Response response =TodoApi.addTodo(todo,token);
        assertThat(response.statusCode(),equalTo(400));
        Error returnedTask = response.body().as(Error.class);
        assertThat(returnedTask.getMessage(),equalTo(com.qacart.todo.data.Error.isCompleteRequired));
    }
    @Story("Should Be Able To Get All Tasks")
    @Test(description = "Should Be Able To Get All Tasks")
    public void shouldBeAbleToGetAllTasks(){
        String token = UserSteps.GetUserToken();

       Response response = TodoApi.getAllTodo(token);
       assertThat(response.statusCode(),equalTo(200));

    }
    @Story("Should Be Able To Get A Task")
    @Test (description = "Should Be Able To Get A Task")
    public void shouldBeAbleToGetATask(){
        Todo todo = TodoSteps.generateToDo();
        String token = UserSteps.GetUserToken();
        String TaskId=TodoSteps.getTodoID(todo,token);
Response response = TodoApi.getTodo(token,TaskId);
assertThat(response.statusCode(),equalTo(200));
        Todo returnedTask =response.body().as(Todo.class);

assertThat(returnedTask.getItem(),equalTo(todo.getItem()));


    }
    @Story("Should Be Able To Update Task")
    @Test(description = "Should Be Able To Update Task")
    public void shouldBeAbleToUpdateTask(){
        String token = UserSteps.GetUserToken();
        Todo todo1 = new Todo(true,"Learn Appium");
        String TaskId=TodoSteps.getTodoID(todo1,token);
        Response response =TodoApi.UpdateTodo(todo1,token,TaskId);
        assertThat(response.statusCode(),equalTo(200));
        Todo returnedTask =response.body().as(Todo.class);

        assertThat(returnedTask.getIsCompleted(),equalTo(todo1.getIsCompleted()));

    }
    @Story("Should Be Able To Delete A Task")
    @Test (description = "Should Be Able To Delete A Task")
    public void shouldBeAbleToDeleteATask(){
        Todo todo = TodoSteps.generateToDo();
        String token = UserSteps.GetUserToken();
        String TaskId=TodoSteps.getTodoID(todo,token);

        Response response = TodoApi.DeleteTodo(token,TaskId);
        assertThat(response.statusCode(),equalTo(200));
    }


}
