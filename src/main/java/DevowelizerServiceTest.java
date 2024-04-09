import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DevowelizerServiceTest {

    // Base URL of the Devowelizer service
    private static final String BASE_URL = "http://localhost:8080/";

    @Test
    public void testDevowelizerService() {
        try {
            // Test cases: pairs of input and expected output
            String[][] testCases = {
                    {"hello", "hll"},
                    {"world", "wrld"},
                    {"openai", "pn"},
                    {"casumo", "csm"},
                    {"banana", "bnn"},
                    {"apple", "ppl"},
                    {"", ""},
                    {"AEIOU", ""},
                    {"AEIOUaeiou", ""},
                    {"123456", "123456"},
                    {"qwerty", "qwrt"},
                    {"This is a test", "Ths s  tst"}
            };

            // Iterate through test cases
            for (String[] testCase : testCases) {
                String input = testCase[0];
                String expectedOutput = testCase[1];

                // Make HTTP GET request to the Devowelizer service
                String actualOutput = sendGet(BASE_URL + input);

                // Assert that the actual output matches the expected output
                assertEquals(expectedOutput, actualOutput);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method to send GET request and return response
    private String sendGet(String url) throws Exception {
        HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();

        // Set request method to GET
        httpClient.setRequestMethod("GET");

        // Get response code
        int responseCode = httpClient.getResponseCode();

        // If response code is not 200 OK, throw exception
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
        }

        // Read response
        BufferedReader in = new BufferedReader(new InputStreamReader(httpClient.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = in.readLine()) != null) {
            response.append(line);
        }

        // Close resources
        in.close();
        httpClient.disconnect();

        // Return response
        return response.toString();
    }
}
