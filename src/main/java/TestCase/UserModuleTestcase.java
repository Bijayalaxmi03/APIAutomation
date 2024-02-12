package TestCase;

import EndPoints.UserModuleEndPoints;
import Payloads.UserModule;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserModuleTestcase {
    Faker faker;
    UserModule userpayload;
    public Logger logger;
    public ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setup() {
        faker = new Faker();
        userpayload = new UserModule();
        userpayload.setId(faker.idNumber().hashCode());
        userpayload.setFirstName(faker.name().firstName());
        userpayload.setLastName(faker.name().lastName());
        userpayload.setEmail(faker.internet().safeEmailAddress());
        userpayload.setPassword(faker.internet().password(5, 10));
        userpayload.setPhone(faker.phoneNumber().cellPhone());
        userpayload.setUsername(faker.name().username());
       //Logs
        logger= LogManager.getLogger(this.getClass());

    }

    @Test(priority = 1)
    public void Test_Create_User() {
        logger.info("*** Creating a User *******");
        Response response = UserModuleEndPoints.createUser(userpayload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("*** User is created ***");
    }

    @Test(priority = 2)
    public void Test_Get_User() {
        logger.info("*** Fetching  user *******");
        Response response = UserModuleEndPoints.getUser(this.userpayload.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("*** user info is displayed *******");
    }

    @Test(priority = 3)
    @Description("Verify user able tp update the details")
    public void Test_Update_User() {
        logger.info("*** Updateing user info *******");
        String newFirstName = faker.name().firstName();
        userpayload.setFirstName(newFirstName);
        Response response = UserModuleEndPoints.updateUser(this.userpayload.getUsername(), userpayload);
        response.then().log().body();
        Assert.assertEquals(response.getStatusCode(), 200);
        //Verifying the exact update happened or not
        Response responseAfterUpdate = UserModuleEndPoints.getUser(this.userpayload.getUsername());
        responseAfterUpdate.then().log().body();
        Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
        JsonPath jsonPath = responseAfterUpdate.jsonPath();
        String updatedFirstName = jsonPath.getString("firstName");
        Assert.assertEquals(updatedFirstName, newFirstName, "First name is not updated as expected.");
        logger.info("*** Updated the user info *******");
    }

    @Test(priority = 4)
    public void Test_Delete_User() {
        logger.info("*** =Deleting the user info *******");

        Response response = UserModuleEndPoints.deleteUser(this.userpayload.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("*** =Deleted  the user info *******");

    }
}
