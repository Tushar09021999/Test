package com.ycs.naradaapi.config;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.ycs.naradaapi.dao.DbPool;
import com.ycs.naradaapi.exception.NaradaResponseException;
import com.ycs.naradaapi.jwt.OAuth2Keystore;

import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
public class OAuth2AuthorizationServerConfig {

    /**
     * @auhtor:Tushar Mule
     * @since: 28-FEB-25
     * @apiNote:AuthorizationServer
     */
    private final OAuth2Keystore keystore;

    @Bean
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        // http
        // .authorizeRequests(requests -> requests
        // .antMatchers("/oauth2/jwks").permitAll());
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        return http.build();
    }

    public OAuth2AuthorizationServerConfig(
            OAuth2Keystore keystore) {
        this.keystore = keystore;
    }

   
    @Bean
    public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder) {
        return new RegisteredClientRepository() {
    
            @Override
            public RegisteredClient findByClientId(String clientId) {
                String sql = "SELECT CLIENT_ID, CLIENT_SECRET FROM oauth_clients WHERE CLIENT_ID = ?";
                try (Connection connection = DbPool.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, clientId);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            String clientSecret = resultSet.getString("CLIENT_SECRET");
                            TokenSettings tokenSettings = TokenSettings.builder()
                                    .accessTokenTimeToLive(Duration.ofMinutes(1))
                                    .refreshTokenTimeToLive(Duration.ofHours(24))
                                    .reuseRefreshTokens(true)
                                    .build();
                            return RegisteredClient.withId(UUID.randomUUID().toString())
                                    .clientId(clientId)
                                    .clientSecret(clientSecret)
                                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                                    .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                                    .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                                    .scope("read")
                                    .scope("write")
                                    .scope("offline_access")
                                    .tokenSettings(tokenSettings)
                                    .build();
                        }
                    }
                } catch (SQLException e) {
                    throw new NaradaResponseException("Error retrieving client from DB");
                }
                return null;
            }
            @Override
            public RegisteredClient findById(String id) {
                return null;
            }

            @Override
            public void save(RegisteredClient registeredClient) {
                throw new UnsupportedOperationException("Save not supported.");
            }
        };
    }

    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .issuer("https://yalamanchili.in")
                .build();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() throws JOSEException {
        PrivateKey privateKey = keystore.encLoadPrivateKey();
        PublicKey publicKey = keystore.encLoadPublicKey();

        RSAKey rsaKey = new RSAKey.Builder((RSAPublicKey) publicKey)
                .privateKey(privateKey)
                .keyID("rsa-key")
                .keyUse(KeyUse.SIGNATURE)
                .build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(rsaKey));
        return jwkSource;
    }

}
