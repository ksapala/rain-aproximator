/**
 * 
 */
package org.ksapala.rainaproximator.rest.factory;

import org.ksapala.rainaproximator.aproximation.result.Aproximation;
import org.ksapala.rainaproximator.aproximation.result.AproximationResult;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.rest.bean.AproximationBean;
import org.ksapala.rainaproximator.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Locale;

/**
 * @author krzysztof
 *
 */
@Component
public class AproximationBeanFactory {


    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Configuration configuration;

    /**
	 * 
	 */
	public AproximationBeanFactory() {
	}
	
	/**
	 * @param aproximation
	 * @return
	 */
	public AproximationBean createBean(Aproximation aproximation) {
	    AproximationResult aproximationResult = aproximation.getAproximationResult();
        LocalDateTime predictTime = aproximationResult.getPredictTime();
        String predictTimeString = "";
        if (aproximationResult.isPredict()) {
            predictTimeString = predictTime.format(TimeUtils.getFormatter(configuration));
        }

        return new AproximationBean(aproximationResult.getType().getInfo(messageSource),
                predictTimeString, aproximationResult.getRemark(), aproximation.getAccuracy(), aproximationResult.getDebug());
	}

    /**
     *
     * @return
     */
	public AproximationBean createFriendlyErrorBean() {
        String info = messageSource.getMessage("AproximationResultBeanFactory.there.was.error", new Object[0],
                Locale.getDefault());
        return new AproximationBean("", null, info, null, null);
    }

    /**
     *
     * @return
     */
    public AproximationBean createEmptyScanBean() {
        String info = messageSource.getMessage("AproximationResultBeanFactory.empty.scan", new Object[0],
                Locale.getDefault());
        return new AproximationBean("", null, info, null, null);
    }

}
