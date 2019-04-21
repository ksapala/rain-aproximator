package org.ksapala.rainaproximator.aws;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.ksapala.rainaproximator.RainAproximatorApplication;
import org.ksapala.rainaproximator.rest.service.HelloService;
import org.ksapala.rainaproximator.rest.service.RainTimerService;
import org.ksapala.rainaproximator.rest.service.ScanService;
import org.ksapala.rainaproximator.rest.bean.ScanBean;
import org.ksapala.rainaproximator.utils.SpringBridge;

/**
 * @author krzysztof
 */
public class EventLambdaHandler implements RequestHandler<AwsEventRequest, AwsEventResponse> {

    private RainTimerService rainTimerService;
    private HelloService helloService;

    private static SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

    static {
        try {
            handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(RainAproximatorApplication.class, "lambda");
        } catch (ContainerInitializationException e) {
            // if we fail here. We re-throw the exception to force another cold start
            e.printStackTrace();
            throw new RuntimeException("Could not initialize Spring Boot application", e);
        }
    }


    @Override
    public AwsEventResponse handleRequest(AwsEventRequest input, Context context) {
        rainTimerService = SpringBridge.getRainTimerService();
        rainTimerService.scanAproximateAndNotify();
        return new AwsEventResponse();
    }

}
