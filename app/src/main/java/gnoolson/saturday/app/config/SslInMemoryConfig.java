package gnoolson.saturday.app.config;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;

@Configuration
public class SslInMemoryConfig {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Value("${gnoolson.saturday.server.ssl}")
    private boolean ssl;

    /*
     *
     *
     * */
    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer() {
        if (!ssl)
            return (factory) -> {
            };

        return factory -> {
            try {
                char[] password = "sat_password".toCharArray();
                KeyStore memoryKeyStore = createInMemorySslKeyStore(password);

                factory.addConnectorCustomizers(connector -> {
                    connector.setSecure(true);
                    connector.setScheme("https");

                    org.apache.coyote.http11.AbstractHttp11JsseProtocol<?> protocol = (org.apache.coyote.http11.AbstractHttp11JsseProtocol<?>) connector.getProtocolHandler();
                    protocol.setSSLEnabled(true);

                    org.apache.tomcat.util.net.SSLHostConfig sslHostConfig;
                    org.apache.tomcat.util.net.SSLHostConfig[] configs = protocol.findSslHostConfigs();

                    if (configs != null && configs.length > 0) {
                        sslHostConfig = configs[0]; // Беремо вже створений Tomcat-ом конфіг
                    } else {
                        sslHostConfig = new org.apache.tomcat.util.net.SSLHostConfig();
                        sslHostConfig.setHostName("_default_");
                        protocol.addSslHostConfig(sslHostConfig);
                    }

                    org.apache.tomcat.util.net.SSLHostConfigCertificate certConfig =
                            new org.apache.tomcat.util.net.SSLHostConfigCertificate(
                                    sslHostConfig,
                                    org.apache.tomcat.util.net.SSLHostConfigCertificate.Type.RSA
                            );

                    certConfig.setCertificateKeystore(memoryKeyStore);
                    certConfig.setCertificateKeyAlias("inmemory-springboot");
                    certConfig.setCertificateKeystorePassword(new String(password));

                    sslHostConfig.getCertificates().clear();
                    sslHostConfig.addCertificate(certConfig);
                });

            } catch (Exception e) {
                throw new IllegalStateException("Exception", e);
            }
        };
    }

    private KeyStore createInMemorySslKeyStore(char[] password) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
        keyPairGenerator.initialize(2048, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        X500Name dnName = new X500Name("CN=localhost, O=Saturday, L=Dnipro, C=UA");
        BigInteger certSerialNumber = new BigInteger(Long.toString(System.currentTimeMillis()));

        Date startDate = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
        Date endDate = new Date(System.currentTimeMillis() + 365L * 24 * 60 * 60 * 1000);

        X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                dnName, certSerialNumber, startDate, endDate, dnName, keyPair.getPublic());

        ContentSigner signer = new JcaContentSignerBuilder("SHA256WithRSAEncryption")
                .setProvider("BC")
                .build(keyPair.getPrivate());

        X509Certificate certificate = new JcaX509CertificateConverter()
                .setProvider("BC")
                .getCertificate(certBuilder.build(signer));

        KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");
        keyStore.load(null, null);

        Certificate[] chain = new Certificate[]{certificate};
        keyStore.setKeyEntry("inmemory-springboot", keyPair.getPrivate(), password, chain);

        return keyStore;
    }

}
