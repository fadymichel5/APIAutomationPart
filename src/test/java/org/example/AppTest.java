package org.example;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AppTest 
{
    @Test
    public void shouldAnswerWithTrue()
    {
        RestAssured.baseURI = "https://620e3da1585fbc3359db4edf.mockapi.io/api/v1/users";
        RequestSpecification request = RestAssured.given();

        JSONObject profile = new JSONObject();
        profile.put("firstName","Fady");
        profile.put("lastName","Michel");
        profile.put("orders",new JSONArray());

        JSONObject user = new JSONObject();
        user.put("profile",profile);
        user.put("username","fadyMichel5");
        user.put("name","fadyMichel");

        JSONArray usersArray = new JSONArray();
        usersArray.put(user);

        request.header("Content-Type", "application/json");
        request.body(usersArray.toString());

        Response response = request.post();

        Assert.assertEquals(response.statusCode(), 201);
        String id=response.jsonPath().getString("id");
        System.out.println(id);

        Response response2 = request.get(id);
        String actual = response2.jsonPath().getString("0.profile.firstName");
        String expected = "Fady";
        Assert.assertEquals(actual,expected);


    }
}
