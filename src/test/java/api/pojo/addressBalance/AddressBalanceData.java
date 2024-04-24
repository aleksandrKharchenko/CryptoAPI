package api.pojo.addressBalance;

import lombok.Data;

@Data
public class AddressBalanceData {

    private String network;
    private String available_balance;
    private String pending_received_balance;
    private AddressBalance[] balances;
}
