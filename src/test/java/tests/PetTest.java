package tests;


import helpers.PetHelper;
import helpers.PropertyManager;
import io.restassured.response.Response;
import models.pet.Pet;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.TestDataUtil;

public class PetTest {

    PropertyManager propertyManager = new PropertyManager();
    PetHelper petHelper = new PetHelper();
    Pet pet;

    @BeforeTest
    public void before(){
        pet = TestDataUtil.generatePetWithAllFields();
    }

    //GET
    @Test
    public void getPetHappyPath(){
        petHelper.postPet(pet);
        Response response = petHelper.getPet(pet.getId().toString());
        Assert.assertEquals(response.statusCode(),HttpStatus.SC_OK);
    }

    @Test(dataProvider = "getPetFail")
    public void getPetFail(String id, int statusCode, String code, String message, String type){
        Response response = petHelper.getPet(id);
        Assert.assertEquals(response.statusCode(), statusCode);
        Assert.assertEquals(response.jsonPath().getString("message"), message);
        Assert.assertEquals(response.jsonPath().getString("type"), type);
        Assert.assertEquals(response.jsonPath().getString("code"), code);
    }

    @DataProvider(name = "getPetFail")
    public Object[][] getPetFailProvider(){
        return new Object[][]{
                {propertyManager.getProperty("pet.properties", "invalidIdCharacter"), HttpStatus.SC_NOT_FOUND, "404", propertyManager.getProperty("pet.properties","numberFormatExceptionMessage") +"\"" + propertyManager.getProperty("pet.properties","invalidIdCharacter")+"\"" , "unknown"},
                {propertyManager.getProperty("pet.properties", "invalidIdLong"), HttpStatus.SC_NOT_FOUND, "404", propertyManager.getProperty("pet.properties","numberFormatExceptionMessage") +"\"" + propertyManager.getProperty("pet.properties","invalidIdLong")+"\"" , "unknown"},
                {propertyManager.getProperty("pet.properties", "invalidIdNegative"), HttpStatus.SC_NOT_FOUND, "1", propertyManager.getProperty("pet.properties","petNotFoudMessage"), "error"},
        };
    }

    //POST
    @Test
    public void postPetHappyPath(){
        Response response = petHelper.postPet(pet);
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);
    }

    @Test
    public void postPetHappyPath2(){
        Response response = petHelper.postPet(TestDataUtil.generatePetWithMinFields());
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);
    }

    @Test
    public void postPetFail(){
        Response response = petHelper.postPet(TestDataUtil.getWrongData());
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_BAD_REQUEST);
        Assert.assertEquals(response.jsonPath().getString("message"), propertyManager.getProperty("pet.properties","badInput"));
        Assert.assertEquals(response.jsonPath().getString("type"), "unknown");
        Assert.assertEquals(response.jsonPath().getString("code"), "400");
    }

    //PUT
    @Test
    public void updatePetHappyPath(){
        petHelper.postPet(pet);
        Pet newPet = pet.toBuilder().name(propertyManager.getProperty("pet.properties","newPetName")).build();
        Response response = petHelper.updatePet(newPet);
        Assert.assertEquals(response.statusCode(),HttpStatus.SC_OK);
        Response response1 = petHelper.getPet(pet.getId().toString());
        Assert.assertEquals(response1.jsonPath().getString("name"),propertyManager.getProperty("pet.properties","newPetName"));
    }

    @Test
    public void updatePetHappyPath2(){
        Response response = petHelper.updatePet(pet);
        Assert.assertEquals(response.statusCode(),HttpStatus.SC_OK);
        Response response1 = petHelper.getPet(pet.getId().toString());
        Assert.assertEquals(response1.jsonPath().getString("name"),pet.getName());
    }

    @Test
    public void updatePetFail(){
        Response response = petHelper.updatePet(TestDataUtil.getWrongData());
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_BAD_REQUEST);
        Assert.assertEquals(response.jsonPath().getString("message"), propertyManager.getProperty("pet.properties","badInput"));
        Assert.assertEquals(response.jsonPath().getString("type"), "unknown");
        Assert.assertEquals(response.jsonPath().getString("code"), "400");
    }

    //DELETE
    @Test
    public void deletePetHappyPath(){
        petHelper.postPet(pet);
        Response response = petHelper.deletePet(String.valueOf(pet.getId()));
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK);
        Response response1 = petHelper.getPet(String.valueOf(pet.getId()));
        Assert.assertEquals(response1.statusCode(),HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void deletePetFail(){
        Response response = petHelper.deletePet(propertyManager.getProperty("pet.properties","invalidIdNegative"));
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test(dataProvider = "deletePetFail")
    public void deletePetFail2(String id, int statusCode, String code, String message, String type){
        Response response = petHelper.deletePet(id);
        Assert.assertEquals(response.statusCode(), statusCode);
        Assert.assertEquals(response.jsonPath().getString("message"), message);
        Assert.assertEquals(response.jsonPath().getString("type"), type);
        Assert.assertEquals(response.jsonPath().getString("code"), code);
    }

    @DataProvider(name = "deletePetFail")
    public Object[][] deletePetFailProvider() {
        return new Object[][]{
                {propertyManager.getProperty("pet.properties", "invalidIdCharacter"), HttpStatus.SC_NOT_FOUND, "404", propertyManager.getProperty("pet.properties","numberFormatExceptionMessage") +"\"" + propertyManager.getProperty("pet.properties","invalidIdCharacter")+"\"" , "unknown"},
                {propertyManager.getProperty("pet.properties", "invalidIdLong"), HttpStatus.SC_NOT_FOUND, "404", propertyManager.getProperty("pet.properties","numberFormatExceptionMessage") +"\"" + propertyManager.getProperty("pet.properties","invalidIdLong")+"\"", "unknown"},
        };
    }
}
