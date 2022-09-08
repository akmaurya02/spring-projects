package com.ssl;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.Objects;

@Component
public class SSLFactoryGenerator {

    static Logger log = Logger.getLogger(SSLFactoryGenerator.class);

    private SSLConnectionSocketFactory sslConnectionSocketFactory = null;

    private SSLConnectionSocketFactory createSocketFactory() {
        BasicConfigurator.configure();
        try (FileInputStream identityKeyStoreFile = new FileInputStream(new File("identity.jks"));
             FileInputStream trustKeyStoreFile = new FileInputStream(new File("truststore.jks"))) {

            String certAlias = "myAlias";
            String certPassword = "changeit";

            KeyStore identityKeyStore = KeyStore.getInstance("jks");
            identityKeyStore.load(identityKeyStoreFile, certPassword.toCharArray());

            KeyStore trustKeyStore = KeyStore.getInstance("jks");
            trustKeyStore.load(trustKeyStoreFile, certPassword.toCharArray());

            SSLContext sslContext = SSLContexts.custom()
                    .loadKeyMaterial(identityKeyStore, certPassword.toCharArray(), (aliases, socket) -> certAlias)
                    .loadTrustMaterial(trustKeyStore, null)
                    .build();

            sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
                    new String[]{"TLSv1.2"},
                    null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        } catch (Exception ex) {
            log.error("SSLFactoryGenerator::createSocketFactory::EXCEPTION WHILE CREATING SSL SOCKET FACTORY::", ex);
        }
        return sslConnectionSocketFactory;
    }

    public CloseableHttpClient getHttpClient() {
        CloseableHttpClient client = null;
        try {
            if (Objects.isNull(sslConnectionSocketFactory)) {
                sslConnectionSocketFactory = createSocketFactory();
            }
            client = HttpClients.custom()
                    .setSSLSocketFactory(sslConnectionSocketFactory)
                    .build();
        } catch (Exception ex) {
            log.error("SSLFactoryGenerator::getHttpClient::EXCEPTION WHILE GETTING HTTP CLIENT::", ex);
        }
        return client;
    }


}
