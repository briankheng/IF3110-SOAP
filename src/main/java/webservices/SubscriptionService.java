package webservices;

import lombok.var;
import models.Subscription;
import repository.SubscriptionRepo;
import utils.EmailUtil;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.mail.internet.AddressException;

import clients.KBLRestClient;

import java.util.List;

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
                Subscription model = new Subscription(user_id, album_id);
                var result = SubscriptionRepo.getInstance().update(model);
                return result;
            } else {
                // Subscription doesn't exist, create a new one
                this.validateAndRecord(user_id, album_id, ipAddress);
                Subscription newSubscription = new Subscription(user_id, album_id);
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
    public void unsubscribe(int user_id, int album_id, String ipAddress) {
        try {
            this.validateAndRecord(user_id, album_id, ipAddress);

            Subscription existingSubscription = SubscriptionRepo.getInstance().findById(user_id, album_id);
            SubscriptionRepo.getInstance().delete(existingSubscription);
        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @WebMethod
    public Subscription verifySubscription(int user_id, int album_id, String ipAddress) {
        try {
            this.validateAndRecord(user_id, album_id, ipAddress);
            Subscription existingSubscription = SubscriptionRepo.getInstance().findById(user_id, album_id);
            System.out.println(existingSubscription);
            return existingSubscription;
        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod
    public void notifySubscriber(int album_id, String album_name, String ipAddress) {
        try {
            this.validateAndRecord(album_id, ipAddress);
            // Get all the user ids of subscribed album
            List<Integer> userIds = SubscriptionRepo.getInstance().findUserByAlbumId(album_id);

            if (userIds != null) {
                // Get all the user emails based on user ids
                String[] emails = KBLRestClient.getInstance().getUserEmails(userIds);
                // Notify by sending emails to them
                for (int i = 0; i < emails.length; i++) {
                    try {
                        System.out.println("Sending email to " + emails[i]);
                        EmailUtil.getInstance().send(emails[i], "New Video is Recently Uploaded!",
                        "There is new video from your subscribed album, " + album_name + ", Go check it out!");
                        System.out.println("Successfully send email to " + emails[i]);
                    } catch (AddressException ex) {
                        System.out.println("Failed to send email to " + emails[i]);
                        ex.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @WebMethod
    public void removeSubscriptionByAlbumId(int album_id, String ipAddress) {
        try {
            this.validateAndRecord(album_id, ipAddress);

            SubscriptionRepo.getInstance().deleteByAlbumId(album_id);
        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}