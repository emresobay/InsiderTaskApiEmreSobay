package helpers;

import clients.RestAssuredClient;
import configurations.APIConstant.PetEndpoint;
import io.restassured.response.Response;
import models.pet.Pet;

public class PetHelper extends RestAssuredClient {

    public PetHelper(){
        super(PetEndpoint.BASE_URL);
    }

    public Response getPet(String id){
        Response response = get(""+ id);
        return response;
    }

    public Response postPet(Pet pet){
        Response response = post("", pet);
        return response;
    }

    public Response postPet(Object pet){
        Response response = post("", pet);
        return response;
    }

    public Response updatePet(Pet pet){
        return put("", pet);
    }

    public Response updatePet(Object pet){
        return put("", pet);
    }

    public Response deletePet(String id){
        return delete(""+id);
    }
}
