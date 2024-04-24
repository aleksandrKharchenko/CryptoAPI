package api.pojo.addressBalance;

import lombok.Data;

@Data
public class AddressBalanceResponse {
    private String status;
    private AddressBalanceData data;
}
