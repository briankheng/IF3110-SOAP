package webservices;

import lombok.var;
import models.Favorite;
import repository.FavoriteRepo;

import javax.jws.WebMethod;
import javax.jws.WebService;

import java.util.List;

@WebService
public class FavoriteService extends AbstractWebservices implements FavoriteInterface {
    @WebMethod
    public List<Integer> getFavorites(int user_id, String ipAddress) {
        try {
            this.validateAndRecord(user_id, ipAddress);

            List<Integer> favorites = FavoriteRepo.getInstance().findByUserId(user_id);
            return favorites;
        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod
    public Favorite addFavorite(int user_id, int album_id, String ipAddress) {
        try {
            this.validateAndRecord(user_id, album_id, ipAddress);

            Favorite existingFavorite = new Favorite(user_id, album_id);
            boolean isExistingFavorite = FavoriteRepo.getInstance().find(existingFavorite);

            if (isExistingFavorite) {
                return null;
            } else {
                Favorite newFavorite = new Favorite(user_id, album_id);
                var createdFavorite = FavoriteRepo.getInstance().create(newFavorite);
                return createdFavorite;
            }
        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod
    public Favorite removeFavorite(int user_id, int album_id, String ipAddress) {
        try {
            this.validateAndRecord(user_id, album_id, ipAddress);

            Favorite existingFavorite = new Favorite(user_id, album_id);
            boolean isExistingFavorite = FavoriteRepo.getInstance().find(existingFavorite);

            if (isExistingFavorite) {
                FavoriteRepo.getInstance().delete(existingFavorite);
                return existingFavorite;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @WebMethod
    public void removeFavoritesByAlbumId(int album_id, String ipAddress) {
        try {
            this.validateAndRecord(album_id, ipAddress);

            FavoriteRepo.getInstance().deleteByAlbumId(album_id);
        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
