package api.pojo.newAddress;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NewAddressData {
    private String network;
    @JsonProperty("user_id")
    private int userId;
    private String address;
    private String label;
}
