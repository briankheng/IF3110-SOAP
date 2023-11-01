package clients;

import java.util.HashMap;
import java.util.Map;

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

    public ClientWrapper.Result callback(Subscription model) {
        Map<String, String> params = new HashMap<>();
        params.put("user_id", String.valueOf(model.getUserId()));
        params.put("album_id", String.valueOf(model.getAlbumId()));
        params.put("status", model.getStatus().toString());

        return new ClientWrapper(this.KBL_REST_URL + "/api/subscription/callback").post(params);
    }
}