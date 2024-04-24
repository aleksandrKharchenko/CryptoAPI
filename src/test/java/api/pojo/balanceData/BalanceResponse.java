package api.pojo.balanceData;

import lombok.Data;

@Data
public class BalanceResponse {
    private String status;
    private BalanceData data;
}
