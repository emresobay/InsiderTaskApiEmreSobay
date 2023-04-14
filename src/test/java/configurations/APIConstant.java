package configurations;

import helpers.PropertyManager;

public class APIConstant {

    static PropertyManager propertyManager = new PropertyManager();
    public static class PetEndpoint {
        public static final String BASE_URL = propertyManager.getProperty("application.properties","url");
    }
}
