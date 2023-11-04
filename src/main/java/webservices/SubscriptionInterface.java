package webservices;

import models.Subscription;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface SubscriptionInterface {
    @WebMethod
    public Subscription subscribe(int user_id, int album_id, String ipAddress);

    @WebMethod
    public Subscription acceptSubscription(int user_id, int album_id, String ipAddress);

    @WebMethod
    public Subscription rejectSubscription(int user_id, int album_id, String ipAddress);

    @WebMethod
    public List<Subscription> getSubscriptions(String ipAddress);

    @WebMethod
    public List<Subscription> checkStatus(String userIds, String albumIds, String ipAddress);

    @WebMethod
    public List<Subscription> getByStatus(Subscription.SubscriptionStatus status, String ipAddress);
}