package com.accounthub.accountweb.client;

import com.accounthub.accountweb.service.AccountService;
import com.accounthub.accountweb.service.CustomerService;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Configuration
public class ClientConfig {
    private final String BANK_ACCOUNT_API_URL = "http://bank-account";

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    AccountService accountClient(WebClient.Builder webClientBuilder) {
        WebClient client = getWebClient(webClientBuilder, BANK_ACCOUNT_API_URL);

        HttpServiceProxyFactory factory = getHttpServiceProxyFactory(client);

        return factory.createClient(AccountService.class);
    }

    @Bean
    CustomerService customerClient(WebClient.Builder webClientBuilder) {
        WebClient client = getWebClient(webClientBuilder, BANK_ACCOUNT_API_URL);

        HttpServiceProxyFactory factory = getHttpServiceProxyFactory(client);

        return factory.createClient(CustomerService.class);
    }

    private static WebClient getWebClient(WebClient.Builder webClientBuilder, String url) {
        return webClientBuilder
                .baseUrl(url)
                .build();
    }

    private static HttpServiceProxyFactory getHttpServiceProxyFactory(WebClient client) {
        return HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(client))
                .blockTimeout(Duration.ofSeconds(7))
                .build();
    }
}