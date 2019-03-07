package com.microservice.authserver.config;



import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@EnableAuthorizationServer
@Configuration
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter{

	
	@Autowired
	DataSource dataSource;
	
	AuthenticationManager authenticationManager;

    public AuthServerConfig(AuthenticationConfiguration authenticationConfiguration) throws Exception {
       
    	this.authenticationManager =
            authenticationConfiguration.getAuthenticationManager();
    }
	
	PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	
	
	@Bean
    TokenStore jdbcTokenStore() {
        return new JdbcTokenStore(dataSource);
    }

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

		security.tokenKeyAccess("permitAll()")
        .checkTokenAccess("isAuthenticated()");		
		
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

	clients.jdbc(dataSource).passwordEncoder(encoder);		 
		
		
//		clients
//		.inMemory()
//		.withClient("web")
//		.secret(encoder.encode("webpass"))
//		.scopes("READ","WRITE")
//		.authorizedGrantTypes("authorization_code","implicit")
//		.redirectUris("http://localhost:8082/ui/users/homeUser")
//		.and()
//		.withClient("my-client-with-registered-redirect")
//			.secret(encoder.encode("pass"))
//	        .authorizedGrantTypes("authorization_code", "client_credentials","implicit")
//	        .authorities("ROLE_CLIENT")
//	        .scopes("read", "trust")
//	        .redirectUris("http://localhost:8082/ui/users/homeUser");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		endpoints.tokenStore(jdbcTokenStore());
		endpoints.authenticationManager(authenticationManager);
		
	}	

}
