package clients;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import utils.ConfigHandler;
import utils.ClientWrapper;

public class KBLRestClient {
    private static KBLRestClient instance;
    private String KBL_REST_URL;
    private final String KBL_REST_URL_KEY = "kbl_rest.url";

    private KBLRestClient() {
        this.KBL_REST_URL = ConfigHandler.getInstance().get(KBL_REST_URL_KEY);
    }

    public static KBLRestClient getInstance() {
        if (instance == null) {
            instance = new KBLRestClient();
        }
        return instance;
    }

    public String[] getUserEmails(List<Integer> userIds) {
        // Make a SOAP request using the SoapClientWrapper for GET
        ClientWrapper soapClient = new ClientWrapper(this.KBL_REST_URL);

        // Call the SOAP service method using GET with query parameters
        Map<String, String> soapGetParams = new HashMap<>();
        soapGetParams.put("userIds", userIds.stream().map(Object::toString).collect(Collectors.joining(",")));
        
        try {
            ClientWrapper.Result soapGetResult = soapClient.get("/api/user/emails", soapGetParams);

            // Process the SOAP response and return the result
            if (soapGetResult != null && soapGetResult.getStatus() == 200) {
                return soapGetResult.getContent().split(",");
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new String[]{};
    }
}