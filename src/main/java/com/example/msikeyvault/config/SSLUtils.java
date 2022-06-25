package com.example.msikeyvault.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: uublog
 * @Package: com.lhc.uublog.utils
 * @ClassName: SSLUtils
 * @Author: lhc
 * @Description: Http重定向到Https
 */
@Configuration
public class SSLUtils {

//    @Bean
//    public Connector connector() {
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        connector.setSecure(false);
//        connector.setPort(8080);
//        connector.setRedirectPort(8081);
//        return connector;
//    }

//    @Bean
//    public TomcatServletWebServerFactory tomcatServletWebServerFactory(Connector connector) {
//        TomcatServletWebServerFactory webServerFactory = new TomcatServletWebServerFactory() {
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint securityConstraint = new SecurityConstraint();
//                securityConstraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection securityCollection = new SecurityCollection();
//                securityCollection.addPattern("/*");
//                securityConstraint.addCollection(securityCollection);
//                context.addConstraint(securityConstraint);
//            }
//        };
//        webServerFactory.addAdditionalTomcatConnectors(connector);
//        return webServerFactory;
//    }
}
