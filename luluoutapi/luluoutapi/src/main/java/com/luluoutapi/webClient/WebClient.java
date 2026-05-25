package com.luluoutapi.webClient;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luluoutapi.Dao.DBUtils;
import com.luluoutapi.Dao.UpdateStatusDao;
import com.luluoutapi.Dto.PAMResponseDto;
import com.luluoutapi.constants.AppConstants;
import com.mastercard.developer.encryption.FieldLevelEncryption;
import com.mastercard.developer.encryption.FieldLevelEncryptionConfig;
import com.mastercard.developer.encryption.FieldLevelEncryptionConfig.FieldValueEncoding;
import com.mastercard.developer.encryption.FieldLevelEncryptionConfigBuilder;
import com.mastercard.developer.oauth.OAuth;
import com.mastercard.developer.utils.EncryptionUtils;

import lombok.extern.log4j.Log4j2;

@Component
@PropertySource("classpath:pamcommon.properties")
@Log4j2
public class WebClient {

        private final RestTemplate restTemplate;

        private final ObjectMapper objectMapper;

        private final UpdateStatusDao updateAccountDao;

        private WebClient(ObjectMapper objectMapper, RestTemplate restTemplate,
                        UpdateStatusDao updateAccountDao) {
                this.objectMapper = objectMapper;
                this.restTemplate = restTemplate;
                this.updateAccountDao = updateAccountDao;
        }

        // static ResourceBundle PamcommonBundle =
        // ResourceBundle.getBundle("pamcommon");

        @Value("${encryptioncerpath}")
        String encryptioncertificate;

        @Value("${certificatefingerprint}")
        String certificatefingerprint;

        @Value("${signingkeypath}")
        String signingkey;

        @Value("${keyalias}")
        String keyalias;

        @Value("${keypassword}")
        String keypassword;

        @Value("${consumerKey}")
        String consumerKey;

        public void pamapicall(@NonNull String url, String request, String apikey,Date date)
                        throws Exception {
                log.info("Inside pamapicall....");

                String output;
                 log.info("Loading decryption key from: ");
                PrivateKey decryptionKey = EncryptionUtils.loadDecryptionKey(signingkey, keyalias, keypassword);
                URI uri = URI.create(url);
                String method = "POST";
                Charset charset = StandardCharsets.UTF_8;
                String authHeader = OAuth.getAuthorizationHeader(
                                uri, method, request, charset, consumerKey, decryptionKey);
                 log.info("Generated OAuth Authorization Header:\n" + authHeader);

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
                log.info("Response Code::::" + statusCode);

                output = objectMapper.writeValueAsString(response.getBody());
                log.info("apikey" + apikey + " Response::::" + output);
                String status = "F";
                PAMResponseDto body = response.getBody();
                if (body != null) {
                        if (body.getErrors() != null) {
                                log.info(" Status:::: F");
                                status = "F";
                        } else {
                                log.info(" Status:::: C");
                                status = "C";
                        }

                }
                updateAccountDao.checkandUpdateStatus(DBUtils.getConnection(), String.valueOf(statusCode),
                                body != null ? body.getResponseId() : null, apikey, output, "P", status,date);
               
        }

        public void constructRequest(String payload, String apikey,Date date) throws Exception {
                log.info("text to encrypt for apikey  {} - {}", apikey, payload);

                Certificate encryptionCertificate = EncryptionUtils.loadEncryptionCertificate(
                                encryptioncertificate);
                PrivateKey decryptionKey = EncryptionUtils.loadDecryptionKey(signingkey, keyalias, keypassword);

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

                String encPayload = FieldLevelEncryption.encryptPayload(payload, config);
                log.info("encPayload for apikey {} - {}", apikey, encPayload);

                pamapicall(AppConstants.UPD_ACC_URL,
                                encPayload, apikey,date);

        }
}
