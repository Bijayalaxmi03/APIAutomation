package TestCase;

import EndPoints.UserModuleEndPoints;
import Payloads.UserModule;
import Utilities.DataProvaider;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataDrivenTest {
    @Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProvaider.class)
    public void testPostUser(String userID, String userName, String fname, String lname, String userEmail, String pwd, String phnum) {
        UserModule userPayload = new UserModule();
        userPayload.setId(Integer.parseInt(userID));
        userPayload.setUsername(userName);
        userPayload.setFirstName(fname);
        userPayload.setLastName(lname);
        userPayload.setEmail(userEmail);
        userPayload.setPassword(pwd);
        userPayload.setPhone(phnum);
        Response response = UserModuleEndPoints.createUser(userPayload);
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 2, dataProvider = "UserNames", dataProviderClass = DataProvaider.class)
    public void testDeleteUserByName(String userName) {
        Response response = UserModuleEndPoints.deleteUser(userName);
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
