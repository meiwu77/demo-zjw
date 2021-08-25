package com.yuantu.demo.web.config.offline;


import com.yuantu.plancenter.config.OfflineBeanConfig;
import com.yuantu.plancenter.manager.PlancenterManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class OfflineRegBean {

    @Bean
    public PlancenterFacade plancenterFacade(PlancenterManager plancenterManager) {

        System.out.println(plancenterManager);
        PlancenterFacade plancenterFacade = new PlancenterFacade();
        plancenterFacade.setPlancenterManager(plancenterManager);
        return plancenterFacade;
    }





}
