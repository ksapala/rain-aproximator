/**
 * 
 */
package org.ksapala.rainaproximator.rest.factory;

import org.ksapala.rainaproximator.aproximation.AproximationResult;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.rest.bean.AproximationResultBean;
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
public class AproximationResultBeanFactory {


    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Configuration configuration;

    /**
	 * 
	 */
	public AproximationResultBeanFactory() {
	}
	
	/**
	 * @param aproximationResult
	 * @return
	 */
	public AproximationResultBean createBean(AproximationResult aproximationResult) {
        LocalDateTime predictTime = aproximationResult.getPredictTime();
        String predictTimeString = "";
        if (aproximationResult.isPredict()) {
            predictTimeString = predictTime.format(TimeUtils.getFormatter(configuration));
        }

		AproximationResultBean bean = new AproximationResultBean(aproximationResult.getType().getInfo(messageSource),
                predictTimeString, aproximationResult.getRemark(), aproximationResult.getDebug());
		return bean;
	}

    /**
     *
     * @return
     */
	public AproximationResultBean createFriendlyErrorBean() {
        String info = messageSource.getMessage("AproximationResultBeanFactory.there.was.error", new Object[0],
                Locale.getDefault());
        return new AproximationResultBean("", null, info, "");
    }

    /**
     *
     * @return
     */
    public AproximationResultBean createEmptyScanBean() {
        String info = messageSource.getMessage("AproximationResultBeanFactory.empty.scan", new Object[0],
                Locale.getDefault());
        return new AproximationResultBean("", null, info, "");
    }
}
