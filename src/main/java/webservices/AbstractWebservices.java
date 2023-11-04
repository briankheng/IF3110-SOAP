package webservices;

import lombok.var;
import models.ApiKey;
import models.Logging;
import repository.ApiKeyRepo;
import repository.LoggingRepo;

import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.sql.Timestamp;
/* import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; */

public abstract class AbstractWebservices {
    @Resource
    WebServiceContext context;

    protected void recordClient(String endpoint, String description, String ipAddr) throws SQLException {
        System.out.println("Client " + ipAddr + " called " + endpoint + " with description: " + description);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String s = ts.toString().split("\\.")[0];

        Logging model = new Logging(description, ipAddr, endpoint, s);
        Logging log = LoggingRepo.getInstance().create(model);
    }

    /* private String getRemoteAddr() {
        HttpServletRequest request = (HttpServletRequest) context.getMessageContext().get(MessageContext.SERVLET_REQUEST);
        HttpServletResponse response = (HttpServletResponse) context.getMessageContext().get(MessageContext.SERVLET_RESPONSE);

        if (request != null) {
            System.out.println(request);
            System.out.println(response);
            String remoteAddr = request.getRemoteAddr();
            System.out.println("Remote Address: " + remoteAddr);
            return remoteAddr;
        } else {
            System.out.println("HttpServletRequest not available1111.");
            return "Unknown";
        }
    } */

    protected String getClientByApiKey() throws Exception {
        MessageContext mc = context.getMessageContext();
        Map<String, List<String>> requestHeader = (Map) mc.get(MessageContext.HTTP_REQUEST_HEADERS);
        String apiKey = requestHeader.get("api-key").get(0);
        System.out.println("api key: " + apiKey);

        List<ApiKey> validApiKeys = ApiKeyRepo.getInstance().findAll();
        for (ApiKey validApiKey : validApiKeys) {
            if (validApiKey.getKey().equals(apiKey)) {
                return validApiKey.getClient();
            }
        }
        throw new Exception("Invalid API key");
    }

    protected void validateAndRecord(Object... params) throws Exception {
        String client = this.getClientByApiKey();
        var ptrTrace = Thread.currentThread().getStackTrace()[2];
        String endpoint = ptrTrace.getClassName() + "." + ptrTrace.getMethodName();
        
        // Check if params is not empty
        if (params.length > 0) {
            // Extract the last parameter from the array
            Object lastParam = params[params.length - 1];
            
            // Remove the last parameter from the params array
            Object[] paramsWithoutLast = new Object[params.length - 1];
            System.arraycopy(params, 0, paramsWithoutLast, 0, paramsWithoutLast.length);
            
            // Store the rest of the parameters in buildDesc
            String buildDesc = buildDesc(client, paramsWithoutLast);
    
            // Use the lastParam instead of this.getRemoteAddr()
            String remoteAddr = lastParam.toString();
    
            this.recordClient(endpoint, buildDesc, remoteAddr);
        }
    }    

    private String buildDesc(String client, Object... params) {
        String paramsStr = Arrays.stream(params)
                .map(e -> "[" + e + "]")
                .reduce((a, b) -> a + "," + b)
                .orElse("");
        return client + ":parameters{" + paramsStr + "}";
    }
}
