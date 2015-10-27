package com.bhsconsultants.vonneumanns.config;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.server.Neo4jServer;
import org.springframework.data.neo4j.server.RemoteServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.bhsconsultants.vonneumanns.dataimport.GameDbApiClient;
import com.bhsconsultants.vonneumanns.dataimport.GameDbLocalStorage;

@Configuration
@EnableNeo4jRepositories(basePackages = "com.bhsconsultants.vonneumanns.repositories")
@EnableTransactionManagement
@ComponentScan(excludeFilters = @Filter(type=FilterType.ANNOTATION, value={org.springframework.stereotype.Controller.class}),
        basePackages = "com.bhsconsultants.vonneumanns")
public class StoreConfiguration extends Neo4jConfiguration {

    //private final String NEO4J_USERNAME = "store_user";
    //private final String NEO4J_PASSWORD = "thisisapasswordnoreally";

    private final String NEO4J_USERNAME = "neo4j";
    private final String NEO4J_PASSWORD = "madGRAPH3r";

    @Bean
    public Neo4jServer neo4jServer() {
        return new RemoteServer("http://morpheus:7474", this.NEO4J_USERNAME, this.NEO4J_PASSWORD);
    }
    
    @Bean
    public SessionFactory getSessionFactory() {        
        return new SessionFactory("com.bhsconsultants.vonneumanns.entities");
    }
    
    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Session getSession() throws Exception {
        return super.getSession();
    }

    @Bean
    public GameDbApiClient getGameDbApiClient() {
        return new GameDbApiClient("821be19ca8c2da4f3adfa889de6e05e43bd51ff5", "json");
    }
    
    @Bean
    public GameDbLocalStorage getGameDbLocalStorage() {
        return new GameDbLocalStorage("data/json");
    }
}
