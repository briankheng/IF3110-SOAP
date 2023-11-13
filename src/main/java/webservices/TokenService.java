package webservices;

import models.Token;
import repository.TokenRepo;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public class TokenService extends AbstractWebservices implements TokenInterface {
    @WebMethod
    public Token findByTokenString(String tokenString, String ipAddress) {
        try {
            this.validateAndRecord(tokenString, ipAddress);
            return TokenRepo.getInstance().findByTokenString(tokenString);
        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod
    public List<Token> getAllTokens(String ipAddress) {
        try {
            this.validateAndRecord(ipAddress);
            return TokenRepo.getInstance().findAll();
        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
