package webservices;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.annotation.Resource;

@WebService
public class TestingService {
    @Resource
    private WebServiceContext context;
    
    @WebMethod
    public String HelloWorld (String name) {
        return "Hello " + name;
    }
    
    @WebMethod
    public String accessIp() {
        if (context != null) {
            // Get the servlet request from the WebServiceContext
            HttpServletRequest request = (HttpServletRequest) context.getMessageContext().get(MessageContext.SERVLET_REQUEST);
            
            // Get the client's IP address from the request
            String clientIpAddress = request.getRemoteAddr();
            
            return "Client IP Address: " + clientIpAddress;
        } else {
            return "Web service context is not properly injected.";
        }
    }
}