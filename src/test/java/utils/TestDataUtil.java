package utils;

import java.util.Arrays;
import java.util.Random;
import models.Status;
import models.pet.Category;
import models.pet.Pet;
import models.pet.Tag;
import org.apache.commons.lang3.RandomStringUtils;

public class TestDataUtil {

    public static Pet generatePetWithAllFields(){
        Pet pet = Pet.builder()
                .id(getRandomInt())
                .name(getRandomString())
                .photoUrls(Arrays.asList(getRandomString(),getRandomString()))
                .category(Category.builder().id(getRandomInt()).name(getRandomString()).build())
                .tags(Arrays.asList(Tag.builder().id(getRandomInt()).name(getRandomString()).build()
                        ,Tag.builder().id(getRandomInt()).name(getRandomString()).build()))
                .status(Status.AVAILABLE.getText())
                .build();
        return pet;
    }

    public static Pet generatePetWithMinFields(){
        Pet pet = Pet.builder()
                .id(getRandomInt())
                .name(getRandomString())
                .build();
        return pet;
    }

    public static Object getWrongData(){
        return "{ \"id\": invalid}";
    }

    public static Integer getRandomInt(){
        return new Random().nextInt(10000);
    }

    public static String getRandomString(){
        return RandomStringUtils.randomAlphabetic(10);

    }
}
