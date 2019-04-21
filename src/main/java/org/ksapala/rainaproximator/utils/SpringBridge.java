package org.ksapala.rainaproximator.utils;

import org.ksapala.rainaproximator.rest.service.HelloService;
import org.ksapala.rainaproximator.rest.service.RainTimerService;
import org.ksapala.rainaproximator.rest.service.ScanService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author krzysztof
 */
@Component
public class SpringBridge implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static ScanService getScanService() {
        return applicationContext.getBean(ScanService.class);
    }

    public static RainTimerService getRainTimerService() {
        return applicationContext.getBean(RainTimerService.class);
    }

    public static HelloService getHelloService() {
        return applicationContext.getBean(HelloService.class);
    }
}
