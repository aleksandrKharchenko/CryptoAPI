package api.pojo.balanceData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BalanceData {
    private String network;
    private String availableBalance;
    private String pendingReceivedBalance;
}
