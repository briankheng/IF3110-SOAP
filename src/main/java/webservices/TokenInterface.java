package webservices;

import models.Token;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface TokenInterface {
    @WebMethod
    public Token findByTokenString(String tokenString, String ipAddress);

    @WebMethod
    public List<Token> getAllTokens(String ipAddress);
}

