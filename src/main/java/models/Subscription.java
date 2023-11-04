package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class Subscription extends ModelInterface {
    private Integer userId;
    private Integer albumId;
    private SubscriptionStatus status;

    @XmlEnum(String.class)
    public enum SubscriptionStatus {
        PENDING,
        ACCEPTED,
        REJECTED;

        public static SubscriptionStatus fromStatusCode(String value) {
            for (SubscriptionStatus status : SubscriptionStatus.values()) {
                if (status.toString().equalsIgnoreCase(value)) {
                    return status;
                }
            }
            return null;
        }
    }

    @Override
    public void constructFromSQL(ResultSet rs) throws SQLException {
        this.userId = rs.getInt("user_id");
        this.albumId = rs.getInt("album_id");
        this.status = Subscription.SubscriptionStatus.fromStatusCode(rs.getString("status"));
    }

    public Integer getUserId() {
        return this.userId;    
    }

    public Integer getAlbumId() {
        return this.albumId;
    }

    public Subscription.SubscriptionStatus getStatus() {
        return this.status;
    }

}