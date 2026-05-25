package com.luluoutapi;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luluoutapi.Dto.PAMResponseDto;
import com.mastercard.developer.encryption.FieldLevelEncryption;
import com.mastercard.developer.encryption.FieldLevelEncryptionConfig;
import com.mastercard.developer.encryption.FieldLevelEncryptionConfig.FieldValueEncoding;
import com.mastercard.developer.encryption.FieldLevelEncryptionConfigBuilder;
import com.mastercard.developer.oauth.OAuth;

public class SandBoxTestCode {

    // out_pam_api

    public static void main(String[] args) {

        System.out.println("=== Starting SandBoxTestCode ===");

        try {

            constructRequest();

        } catch (Exception e) {

            System.out.println("Exception in main: " + e.getMessage());

            e.printStackTrace();

        }

    }

    // private static final String CLIENT_PEM_PATH =
    // "D:/MY-WORKSPACES/instapay-services-workspaces/KEYS/payment-account-management-ClientEnc1773049493568.crt";

    private static final String CLIENT_PEM_PATH = "/home/user/Documents/INSTAPAY/KEYS/payment-account-management-ClientEnc1773049493568.pem";

    private static final String certificatefingerprint = "76f2d3663367725da353ca899d3594f1ffa4b48ff7e90c9d7ff175860436e959";

    private static final String signingkeypath = "/home/user/Documents/INSTAPAY/KEYS/mastercard-sandbox.p12";

    private static final String keyalias = "mastercard";

    private static final String keypassword = "mastercard@123";

    private static final String consumerKey = "IWJUQ6lWmWBRrCLmgTCrQqtMYndofgqREKnv-BnAc0aeab40!68859f3f1be14ccc823c9eeaf34e8aec0000000000000000";

    private static final String url = "https://sandbox.api.mastercard.com/paa/paymentaccount/1/0/addAccount";

    // private static final String url =
    // "https://sandbox.api.mastercard.com/paa/paymentaccount/1/0/updateAccount";
// 526798
// 552616
    public static void constructRequest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("=== Constructing request payload ===");
       
        String payload = "{\n" + //
                        "  \"requestId\": \"12s34nhnhujujunne356\",\n" + //
                        "   \"reasonCode\": \"NEW_ACCOUNT\",\n" + //
                        "  \"encryptedPayload\": {\n" + //
                        "\"cardInfo\": {\n" + //
                        "        \"accountNumber\": \"5526160000006854\",\n" + //
                        "        \"expiryMonth\": \"05\",\n" + //
                        "        \"expiryYear\": \"31\",\n" + //
                        "        \"panSequenceNumber\": \"01\"\n" + //
                        "      }\n" + //
                        "\n" + //
                        "  }\n" + //
                        "}\n" + //
                        "";

        System.out.println("Request Payload:\n" + payload);
        Certificate encryptionCertificate = loadEncryptionCertificate(CLIENT_PEM_PATH);
        System.out.println("Encryption certificate loaded from: " + CLIENT_PEM_PATH);
        System.out.println("encryptionCertificate get Type: " + encryptionCertificate.getType());
        PrivateKey decryptionKey = loadDecryptionKey(signingkeypath, keyalias, keypassword);
        System.out.println("Decryption key loaded. Algorithm=" + decryptionKey.getAlgorithm() + ", Format="
                + decryptionKey.getFormat());
        FieldLevelEncryptionConfig config = FieldLevelEncryptionConfigBuilder.aFieldLevelEncryptionConfig()
                .withEncryptionPath("$.encryptedPayload", "$.encryptedPayload")
                .withDecryptionPath("$.encryptedPayload", "$.encryptedPayload")
                .withEncryptionCertificate(encryptionCertificate).withDecryptionKey(decryptionKey)
                .withOaepPaddingDigestAlgorithm("SHA-512").withEncryptedValueFieldName("encryptedData")
                .withEncryptedKeyFieldName("encryptedKey").withIvFieldName("iv")
                .withOaepPaddingDigestAlgorithmFieldName("oaepHashingAlgorithm")
                .withEncryptionKeyFingerprintFieldName("publicKeyFingerprint")
                .withFieldValueEncoding(FieldValueEncoding.HEX)
                .withEncryptionCertificateFingerprint(certificatefingerprint).build();
        System.out.println("FieldLevelEncryptionConfig built successfully.");
        // String encData = objectMapper.writeValueAsString(payload);
        // System.out.println("Encrypted encData:\n" + encData);
        String encPayload = FieldLevelEncryption.encryptPayload(payload,
                config);
        System.out.println("Encrypted Payload:\n" + encPayload);
        pamapicall(url, encPayload);

    }

    private static Certificate loadEncryptionCertificate(String certificatePath) throws Exception {
        System.out.println("Loading encryption certificate from: " + certificatePath);
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        return factory.generateCertificate(new FileInputStream(certificatePath));
    }

    public static PrivateKey loadDecryptionKey(String pkcs12KeyFilePath, String decryptionKeyAlias,
            String decryptionKeyPassword) throws Exception {
        System.out.println("Loading decryption key from: " + pkcs12KeyFilePath + " with alias: " + decryptionKeyAlias);
        KeyStore pkcs12KeyStore = KeyStore.getInstance("PKCS12");
        pkcs12KeyStore.load(new FileInputStream(pkcs12KeyFilePath), decryptionKeyPassword.toCharArray());
        return (PrivateKey) pkcs12KeyStore.getKey(decryptionKeyAlias, decryptionKeyPassword.toCharArray());
    }

    public static String apiCallFun(String url, String request) throws Exception {
        System.out.println("=== Executing API Call ===");
        System.out.println("Target URL: " + url);
        System.out.println("Request Body:\n" + request);
        PrivateKey decryptionKey = loadDecryptionKey(signingkeypath, keyalias, keypassword);
        URI uri = URI.create(url);
        String method = "POST";
        Charset charset = StandardCharsets.UTF_8;
        String authHeader = OAuth.getAuthorizationHeader(uri, method, request, charset, consumerKey, decryptionKey);
        System.out.println("Generated OAuth Authorization Header:\n" + authHeader);
        final DefaultHttpClient httpClient = new DefaultHttpClient();
        final HttpPost postRequest = new HttpPost(url);
        postRequest.setHeader("Authorization", authHeader);
        postRequest.setHeader("Content-Type", "application/json");
        final StringEntity input = new StringEntity(request);
        postRequest.setEntity(input);
        System.out.println("Sending HTTP POST request...");
        HttpResponse response = httpClient.execute(postRequest);
        int flag = response.getStatusLine().getStatusCode();
        System.out.println("Response Status Code: " + flag);
        BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String output = br.readLine();
        System.out.println("Response Body:\n" + output);
        if (output == null || output.isEmpty()) {
            throw new RuntimeException("Response not found");
        }
        return output;
    }

    public static String pamapicall(String url, String request)
            throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        String output;
        RestTemplate restTemplate = new RestTemplate();
        PrivateKey decryptionKey = loadDecryptionKey(signingkeypath, keyalias, keypassword);
        URI uri = URI.create(url);
        String method = "POST";
        Charset charset = StandardCharsets.UTF_8;
        String authHeader = OAuth.getAuthorizationHeader(
                uri, method, request, charset, consumerKey, decryptionKey);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authHeader);
        HttpEntity<String> entity = new HttpEntity<>(request, headers);
        ResponseEntity<PAMResponseDto> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                PAMResponseDto.class);
        int statusCode = response.getStatusCodeValue();
        System.out.println(" Flag Response::::" + statusCode);
        output = response.getBody().getResponseId();
        System.out.println("Response::::" + objectMapper.writeValueAsString(response.getBody()));
        if (statusCode != 200) {
            if (output == null || output.isEmpty()) {
                throw new RuntimeException("Response not found");
            }

        }

        return output;
    }

}
