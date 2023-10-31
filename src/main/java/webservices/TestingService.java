package webservices;

import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService
public class TestingService {
    
    @WebMethod
    public String HelloWorld (String name) {
        return "Hello" + name;
    }
}
