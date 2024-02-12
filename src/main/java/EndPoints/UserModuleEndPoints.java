package EndPoints;

import Payloads.UserModule;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class UserModuleEndPoints {
    public static Response createUser(UserModule payload)
    {

        Response response=given().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload).when().post(Routes.Create_User);
        return response;
    }
    public static Response getUser(String userName)
    {
        Response response=given().contentType(ContentType.JSON).accept(ContentType.JSON).pathParam("username",userName).when().get(Routes.Get_User);
        return response;
    }
    public static Response updateUser(String username,UserModule payload)
    {
        Response response=given().contentType(ContentType.JSON).accept(ContentType.JSON).pathParam("username",username).body(payload).when().put(Routes.Update_User);
        return response;
    }
    public static Response deleteUser(String userName)
    {
        Response response=given().pathParam("username",userName).when().delete(Routes.Delete_User);
        return response;
    }
}
