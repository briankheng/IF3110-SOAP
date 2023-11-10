package clients;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.var;
import models.Subscription;
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
        // Setup parameters
        Map<String, String> params = new HashMap<>();
        params.put("userIds", userIds.stream().map(Object::toString).collect(Collectors.joining(",")));

        // Capture results
        ClientWrapper.Result result = new ClientWrapper(KBL_REST_URL + "/api/user/emails").get(params);
    
        if (result != null && result.getStatus() == 200) {
            System.out.println(result.getContent().split(","));
            return result.getContent().split(",");
        } else {
            // Handle error
            return new String[]{};
        }
    }

    public ClientWrapper.Result callback(Subscription model) {
        Map<String, String> params = new HashMap<>();
        params.put("user_id", String.valueOf(model.getUserId()));
        params.put("album_id", String.valueOf(model.getAlbumId()));
        params.put("status", model.getStatus().toString());

        return new ClientWrapper(this.KBL_REST_URL + "/api/subscription/callback").post(params);
    }
}