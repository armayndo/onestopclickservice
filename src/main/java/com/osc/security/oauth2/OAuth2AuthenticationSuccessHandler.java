package com.osc.security.oauth2;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.osc.config.AppProperties;
import com.osc.security.jwt.JwtTokenProvider;
import com.osc.server.exception.BadRequestException;
import com.osc.server.util.CookieUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

import static com.osc.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private JwtTokenProvider tokenProvider;

    private AppProperties appProperties;

    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    private static final Logger logger = LoggerFactory.getLogger(OAuth2AuthenticationSuccessHandler.class);
    

    @Autowired
    OAuth2AuthenticationSuccessHandler(JwtTokenProvider tokenProvider, AppProperties appProperties,
                                       HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository) {
        
    	logger.info("OAuth2AuthenticationSuccessHandler");
    	
    	this.tokenProvider = tokenProvider;
        this.appProperties = appProperties;
        this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);
        logger.info("onAuthenticationSuccess");
        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    	logger.info("determineTargetUrl");
    	
    	Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);
    	
    	logger.info("RedirectUri: "+redirectUri.get());

        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new BadRequestException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        //String token = tokenProvider.createToken(authentication);
        logger.info("Create Token..");
        logger.info("Username:" + authentication.getName());
        String token = tokenProvider.createToken(authentication.getName(), "ROLE_USER");
        logger.info("Sent TOKEN to Frontend: "+targetUrl+" with token "+token);
        
        URL url;
        URI uri;
		try {
			url = new URL("http://localhost:3000/#/oauth2/redirect");
			uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
			targetUrl = uri.toASCIIString();
			logger.info("URL REDIRECT: "+ targetUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
        
		logger.info("URL CHECK: "+ UriComponentsBuilder.fromUriString(targetUrl).encode()
                .queryParam("token", token)
                .build().toUriString());
		
        /*return UriComponentsBuilder.fromUriString(targetUrl).encode()
                .queryParam("token", token)
                .build().toUriString();*/
		
		return "http://localhost:3000/#/oauth2/redirect?token="+token;
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
    	 logger.info("clearAuthenticationAttributes");
    	
    	super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
    	 logger.info("isAuthorizedRedirectUri");
    	
    	URI clientRedirectUri = URI.create(uri);
    	
    	logger.info("Redirect Uri from client: "+clientRedirectUri.getHost());
    	logger.info("Redirect Uri from properties: "+appProperties.getOauth2().getAuthorizedRedirectUris().get(0));

        return appProperties.getOauth2().getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    // Only validate host and port. Let the clients use different paths if they want to
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    if(authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort()) {
                        return true;
                    }
                    return false;
                });
    }
}
