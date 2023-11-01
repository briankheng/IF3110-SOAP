package webservices;

import clients.KBLRestClient;
import lombok.var;
import models.Subscription;
import repository.SubscriptionRepo;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebService
public class SubscriptionService extends AbstractWebservices implements SubscriptionInterface {
    @WebMethod
    public Subscription subscribe(int user_id, int album_id) {
        try {
            this.validateAndRecord(user_id, album_id);

            Subscription model = new Subscription(user_id, album_id, null);
            var result = SubscriptionRepo.getInstance().create(model);
            return result;
        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod
    public Subscription acceptSubscription(int user_id, int album_id) {
        try {
            this.validateAndRecord(user_id, album_id);

            Subscription model = new Subscription(user_id, album_id, Subscription.SubscriptionStatus.ACCEPTED);
            var result = SubscriptionRepo.getInstance().update(model);
            var httpResult = KBLRestClient.getInstance().callback(result);
            System.out.println("http result: " + httpResult);

            return result;
        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod
    public Subscription rejectSubscription(int user_id, int album_id) {
        try {
            this.validateAndRecord(user_id, album_id);

            Subscription model = new Subscription(user_id, album_id, Subscription.SubscriptionStatus.REJECTED);
            var result = SubscriptionRepo.getInstance().update(model);
            KBLRestClient.getInstance().callback(result);

            return result;
        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod
    public List<Subscription> getSubscriptions() {
        try {
            this.validateAndRecord();
            return SubscriptionRepo.getInstance().findAll();
        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod
    public List<Subscription> checkStatus(String userIds, String albumIds) {
        try {
            this.validateAndRecord(userIds, albumIds);
            List<Integer> intuserIds = Arrays
                    .stream(userIds.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            List<Integer> intalbumIds = Arrays
                    .stream(albumIds.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            List<Subscription> result = new ArrayList<>();
            for (int i=0;i<intuserIds.size() && i < intalbumIds.size();i++) {
                result.add(SubscriptionRepo.getInstance().findById(
                        intuserIds.get(i), intalbumIds.get(i))
                );
            }

            return result.stream().filter(s -> s != null).collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod
    public List<Subscription> getByStatus(Subscription.SubscriptionStatus status) {
        try {
            this.validateAndRecord(status);
            return SubscriptionRepo.getInstance().findByStatus(status);
        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}