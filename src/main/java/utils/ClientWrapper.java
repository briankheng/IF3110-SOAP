package utils;

import lombok.Getter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.Arrays;
import java.util.Map;
import com.google.gson.Gson;

@Getter
public class ClientWrapper {
    private String url;

    public ClientWrapper(String url) {
        this.url = url;
    }

    public static class Result {
        private final int status;
        private final String content;

        public Result(int status, String content) {
            this.status = status;
            this.content = content;
        }

        public String getContent() {
            return this.content;
        }

        public int getStatus() {
            return this.status;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "status=" + status +
                    ", content='" + content + '\'' +
                    '}';
        }
    }

    public static class Response {
        private String message;
        private String[] data;

        public String getMessage() {
            return this.message;
        }

        public String[] getData() {
            return this.data;
        }

        @Override
        public String toString() {
            return "Response{" +
                    "message='" + message + '\'' +
                    ", data=" + Arrays.toString(data) +
                    '}';
        }
    }

    public Result get(String restEndpoint, Map<String, String> queryParams) throws Exception {
        // Build url, based on query params
        String fullUrl = buildUrl(this.url + restEndpoint, queryParams);

        // Make httpget request
        URL restUrl = new URL(fullUrl);
        HttpURLConnection connection = (HttpURLConnection) restUrl.openConnection();
        connection.setRequestMethod("GET");

        // Process REST Response
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // Close HttpURLConnection
        connection.disconnect();
        String fullResp = response.toString();

        // Parsing the response
        Result parsedResult = parseResponse(fullResp);
        return parsedResult;
    }

    private ClientWrapper.Result parseResponse(String jsonResponse) {
        try {
            Gson gson = new Gson();
            Response response = gson.fromJson(jsonResponse, Response.class);

            // Sent it finals
            if (response.getMessage().equals("OK")) {
                return new Result(200, String.join(",", response.getData()));
            }
        } catch (Exception e) {
            // Handle Gson exceptions or other parsing errors
            e.printStackTrace();
            return new Result(-1, "Error parsing response");
        }
        return null;
    }

    private String buildUrl(String baseUrl, Map<String, String> queryParams) {
        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        if (queryParams != null && !queryParams.isEmpty()) {
            urlBuilder.append("?");
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        return urlBuilder.toString();
    }
}
