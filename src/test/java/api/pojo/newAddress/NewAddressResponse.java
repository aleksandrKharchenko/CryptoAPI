package api.pojo.newAddress;

import lombok.Data;

@Data
public class NewAddressResponse {
    private String status;
    private NewAddressData data;
}
