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
public class Token extends ModelInterface {
    private Integer tokenId;
    private String tokenString;
    private Integer coinValue;

    @Override
    public void constructFromSQL(ResultSet rs) throws SQLException {
        this.tokenId = rs.getInt("token_id");
        this.tokenString = rs.getString("token_string");
        this.coinValue = rs.getInt("coin_value");
    }
}