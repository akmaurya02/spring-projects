package com.ssl;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;

public class SSLCertTest {

    static Logger log = Logger.getLogger(SSLCertTest.class);

    public static void main(String[] args) {
        BasicConfigurator.configure();
        try {
            String CERT_ALIAS = "myAlias", CERT_PASSWORD = "changeit";
            KeyStore keyStore = KeyStore.getInstance("jks");
            FileInputStream identityKeyStoreFile = new FileInputStream(new File("keystore.jks"));
            keyStore.load(identityKeyStoreFile, CERT_PASSWORD.toCharArray());

            SSLContext sslContext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, CERT_PASSWORD.toCharArray(), (aliases, socket) -> CERT_ALIAS)
                    .loadTrustMaterial(keyStore, null)
                    .build();

            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
                    new String[]{"TLSv1.1", "TLSv1.2"},
                    null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());

            CloseableHttpClient client = HttpClients.custom()
                    .setSSLSocketFactory(sslConnectionSocketFactory)
                    .build();

            callEndPoint(client, "https://localhost:8080/ssltest",
                    new JSONObject()
                            .put("mobileNo", "919711828848")
                            .put("body", "hi")
                            .put("timestamp", "12345676")
            );
        } catch (Exception ex) {
            log.error("EXCEPTION OCCURRED:: " + ex);
        }
    }

    private static void callEndPoint(CloseableHttpClient closeableHttpClient, String endPointURL, JSONObject jsonObject) {
        try {
            HttpPost post = new HttpPost(endPointURL);
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");
            post.setEntity(new StringEntity(jsonObject.toString()));
            log.info("Request Url:: " + post.getURI());
            log.info("Request Payload:: " + jsonObject);
            HttpResponse response = closeableHttpClient.execute(post);
            int responseCode = response.getStatusLine().getStatusCode();
            log.info("Response Code:: " + responseCode);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            log.info("Response:: " + sb.toString());
        } catch (Exception ex) {
            log.error("EXCEPTION OCCURRED:: " + ex);
        }

    }

}