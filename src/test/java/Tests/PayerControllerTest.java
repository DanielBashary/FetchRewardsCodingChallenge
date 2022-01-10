package Tests;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PayerControllerTest {

    public static String addTransactionPost(String responseBody) throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post("http://localhost:8080/add_transaction") //sends a Json Object of Payer Type
                .header("Content-Type", "application/json")
                .body(responseBody)
                .asString();
        return response.getBody();
    }

    //ensures that the addTransaction endpoint is succesfully posting new Payer objects
    @Test
    void addTransaction() throws UnirestException {
        assertEquals("Added Succesfully", addTransactionPost("{ \"payerName\": \"DANNON\", \"points\": 1000, \"timestamp\": \"2020-11-02T14:00:00Z\" }"));
        assertEquals("Added Succesfully", addTransactionPost("{ \"payerName\": \"UNILEVER\", \"points\": 200, \"timestamp\": \"2020-10-31T11:00:00Z\" }"));
        assertEquals("Added Succesfully", addTransactionPost("{ \"payerName\": \"DANNON\", \"points\": 300, \"timestamp\": \"2020-10-31T10:00:00Z\" }"));
        assertEquals("Added Succesfully", addTransactionPost("{ \"payerName\": \"MILLER COORS\", \"points\": 10000, \"timestamp\": \"2020-11-01T14:00:00Z\" }"));
        assertEquals("Added Succesfully", addTransactionPost("{ \"payerName\": \"DANNON\", \"points\": -200, \"timestamp\": \"2020-10-31T15:00:00Z\" }"));
    }

    @Test
    void spendPoints() throws UnirestException { // Send a DELETE response in order to delete points
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.delete("http://localhost:8080/spend_points")
                .header("Content-Type", "application/json")
                .body("5000")
                .asString();
        assertEquals("[\"payer: DANNON -100\",\"payer: UNILEVER -200\",\"payer: MILLER COORS -4700\"]", response.getBody());
        //^ checks return of DELETE Method for sending a response of Points
    }

    @Test
    void getBalance() throws UnirestException { //Sends a Get Response for all the Payers and their points
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get("http://localhost:8080/get_balance")
                .asString();

        assertEquals("{\"UNILEVER\":0,\"MILLER COORS\":5300,\"DANNON\":1000}", response.getBody());
        //^Checks reponse for GET Method
    }
}