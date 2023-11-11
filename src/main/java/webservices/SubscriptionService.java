package webservices;

import lombok.var;
import models.Subscription;
import repository.SubscriptionRepo;

import javax.jws.WebMethod;
import javax.jws.WebService;

import clients.KBLRestClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebService
public class SubscriptionService extends AbstractWebservices implements SubscriptionInterface {
    @WebMethod
    public Subscription subscribe(int user_id, int album_id, String ipAddress) {
        try {
            Subscription existingSubscription = SubscriptionRepo.getInstance().findById(user_id, album_id);
            System.out.println(existingSubscription);

            if (existingSubscription != null) {
                // Subscription already exists, update it
                this.validateAndRecord(user_id, album_id, ipAddress);
                Subscription model = new Subscription(user_id, album_id, Subscription.SubscriptionStatus.PENDING);
                var result = SubscriptionRepo.getInstance().update(model);
                return result;
            } else {
                // Subscription doesn't exist, create a new one
                this.validateAndRecord(user_id, album_id, ipAddress);
                Subscription newSubscription = new Subscription(user_id, album_id, Subscription.SubscriptionStatus.PENDING);
                var createdSubscription = SubscriptionRepo.getInstance().create(newSubscription);
                return createdSubscription;
            }
        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod
    public Subscription acceptSubscription(int user_id, int album_id, String ipAddress) {
        try {
            this.validateAndRecord(user_id, album_id, ipAddress);

            Subscription model = new Subscription(user_id, album_id, Subscription.SubscriptionStatus.ACCEPTED);
            var result = SubscriptionRepo.getInstance().update(model);
            return result;
        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod
    public Subscription rejectSubscription(int user_id, int album_id, String ipAddress) {
        try {
            this.validateAndRecord(user_id, album_id, ipAddress);

            Subscription model = new Subscription(user_id, album_id, Subscription.SubscriptionStatus.REJECTED);
            var result = SubscriptionRepo.getInstance().update(model);
            return result;
        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod
    public List<Subscription> getSubscriptions(String ipAddress) {
        try {
            this.validateAndRecord(ipAddress);
            return SubscriptionRepo.getInstance().findAll();
        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod
    public List<Subscription> checkStatus(String userIds, String albumIds, String ipAddress) {
        try {
            this.validateAndRecord(userIds, albumIds, ipAddress);
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
    public List<Subscription> getByStatus(Subscription.SubscriptionStatus status, String ipAddress) {
        try {
            this.validateAndRecord(status, ipAddress);
            return SubscriptionRepo.getInstance().findByStatus(status);
        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod
    public void notifySubscriber(int album_id, String ipAddress) {
        try {
            this.validateAndRecord(album_id, ipAddress);
            // Get all the user ids of subscribed album
            List<Integer> userIds = SubscriptionRepo.getInstance().findUserByAlbumId(album_id);
            // Get all the user emails based on user ids
            String[] emails = KBLRestClient.getInstance().getUserEmails(userIds);
            // Notify by sending emails to them
            for (int i = 0; i < emails.length; i++) {
                System.out.println(emails[i]);
            }

        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}