package api.pojo.addressBalance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressBalance {
    @JsonProperty("available_balance")
    private String availableBalance;
    private String address;
}
