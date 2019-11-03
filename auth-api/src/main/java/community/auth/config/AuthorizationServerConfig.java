package community.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@RequiredArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;
  private final TokenStore jdbcTokenStore;
  private final PasswordEncoder passwordEncoder;

  @Value("${security.oauth2.client.client-id}")
  private String clientId;
  @Value("${security.oauth2.client.client-secret}")
  private String clientSecret;

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) {
    security
        .checkTokenAccess("isAuthenticated()");
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.inMemory()
        .withClient(clientId)
        .secret(passwordEncoder.encode(clientSecret))
        .scopes("read", "write")
        .authorizedGrantTypes("password", "refresh_token")
        .refreshTokenValiditySeconds(86400 * 365)
        .accessTokenValiditySeconds(3600);
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
    endpoints
        .tokenStore(jdbcTokenStore)
        .userDetailsService(userDetailsService)
        .reuseRefreshTokens(false)
        .authenticationManager(authenticationManager);
  }
}
