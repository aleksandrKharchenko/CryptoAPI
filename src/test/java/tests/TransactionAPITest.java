package tests;

import api.pojo.addressBalance.AddressBalance;
import api.pojo.addressBalance.AddressBalanceResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;

public class TransactionAPITest extends Assert {

    private static final String API_KEY = "api_key";
    private static final String API_VALUE = "5049-e34f-e839-0da1";
    private static final String KEY_ADDRESS = "addresses";
    private static final String EXPECTED_RECIPIENT = "2Mu2na67Uw6P5wGXowTxo5QnHrK9XqYS5m3";
    private static final String STATUS_KEY = "status";
    private static final String STATUS_VALUE = "success";
    private static final String EXPECTED_AMOUNT = "0.00011670";
    private static final String NETWORK_KEY = "data.network";
    private static final String NETWORK_VALUE = "BTCTEST";

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://block.io/api/v2";
    }

    @Test
    public void testGenerateNewAddress() {
        Response response = given()
                .param(API_KEY, API_VALUE)
                .when()
                .get("/get_new_address");

        assertEquals(response.getStatusCode(), 200);
        assertEquals(response.jsonPath().get(STATUS_KEY), STATUS_VALUE);
        assertEquals(response.jsonPath().get(NETWORK_KEY), NETWORK_VALUE);

        List<String> keysToCheck = Stream.of("data.user_id", "data.address", "data.label")
                .collect(Collectors.toList());

        keysToCheck.forEach(key -> assertNotNull(response.jsonPath().get(key)));
    }

    @Test
    public void testAddressBalance() {
        Response response = given()
                .param(API_KEY, API_VALUE)
                .param(KEY_ADDRESS, EXPECTED_RECIPIENT)
                .when()
                .get("/get_address_balance");

        AddressBalanceResponse addressBalanceResponse = response.getBody().as(AddressBalanceResponse.class);

        assertEquals(200, response.getStatusCode());
        assertEquals(STATUS_VALUE, addressBalanceResponse.getStatus());
        assertEquals(NETWORK_VALUE, addressBalanceResponse.getData().getNetwork());

        String actualBalance = Stream.of(addressBalanceResponse.getData().getBalances())
                .map(AddressBalance::getAvailableBalance)
                .findFirst()
                .orElse(null);

        assertEquals(EXPECTED_AMOUNT, actualBalance);
    }

    @Test
    public void testRecentTransactions() {
        String response = given()
                .queryParam(API_KEY, API_VALUE)
                .queryParam("type", "received")
                .when()
                .get("/get_transactions")
                .then()
                .extract().asString();

        assertTrue(response.contains("\"status\": \"success\""));
        assertTrue(response.contains("\"network\": \"BTCTEST\""));

        List<String> recipients =
                from(response)
                        .getList("data.txs.amounts_received[0].recipient");
        List<String> amounts =
                from(response)
                        .getList("data.txs.amounts_received[0].amount");

        assertTrue(recipients.contains(EXPECTED_RECIPIENT));
        assertTrue(amounts.contains(EXPECTED_AMOUNT));
    }
}
