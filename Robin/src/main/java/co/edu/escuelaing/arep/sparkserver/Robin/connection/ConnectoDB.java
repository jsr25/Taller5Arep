package co.edu.escuelaing.arep.sparkserver.Robin.connection;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ConnectoDB {
    public String getResult(String valor, String path) {

        System.out.println(path + "?datos=" + valor);
        CloseableHttpClient httpClient = HttpClients.custom().build();
        HttpGet httpGet = new HttpGet("http://" + path + "?datos=" + valor);
        StringBuilder data = new StringBuilder();

        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            data.append(reader.readLine());
            System.out.println(data);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return String.valueOf(data);
    }
}
