package clients;

import lombok.var;
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

    public String[] getAdminEmails() {
        var res = new ClientWrapper(this.KBL_REST_URL + "/admin-emails").get();
        System.out.println(res);
        return res.getContent().replace("\"","").split(",");
    }
}