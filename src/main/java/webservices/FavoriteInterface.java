package webservices;

import models.Favorite;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface FavoriteInterface {
    @WebMethod
    public List<Integer> getFavorites(int user_id, String ipAddress);

    @WebMethod
    public boolean isFavorite(int user_id, int album_id, String ipAddress);

    @WebMethod
    public Favorite addFavorite(int user_id, int album_id, String ipAddress);

    @WebMethod
    public Favorite removeFavorite(int user_id, int album_id, String ipAddress);
}