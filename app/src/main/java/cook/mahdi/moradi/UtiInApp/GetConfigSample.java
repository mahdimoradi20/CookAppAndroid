package cook.mahdi.moradi.UtiInApp;

public class GetConfigSample {
    public static  String HOSTNAME= "Your hostname goes here";
    public static String API_KEY = "your api key from sever goes here";

    public static String getApiKey() {
        return API_KEY;
    }

    public static void setApiKey(String apiKey) {
        API_KEY = apiKey;
    }

    public static String getHOSTNAME() {
        return HOSTNAME;
    }

    public static void setHOSTNAME(String HOSTNAME) {
        GetConfigSample.HOSTNAME = HOSTNAME;
    }



}
