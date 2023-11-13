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
public class Favorite extends ModelInterface {
    private Integer userId;
    private Integer albumId;

    @Override
    public void constructFromSQL(ResultSet rs) throws SQLException {
        this.userId = rs.getInt("user_id");
        this.albumId = rs.getInt("album_id");
    }

    public Integer getUserId() {
        return this.userId;    
    }

    public Integer getAlbumId() {
        return this.albumId;
    }
}
