/**
 * 
 */
package org.ksapala.rainaproximator.rest.factory;

import org.ksapala.rainaproximator.aproximation.AproximationResult;
import org.ksapala.rainaproximator.rest.bean.AproximationResultBean;
import org.ksapala.rainaproximator.utils.TimeComponent;
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
    private TimeComponent timeComponent;

    @Autowired
    private MessageSource messageSource;

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
        String predictTimeString = predictTime.format(timeComponent.getFormatter());

		AproximationResultBean bean = new AproximationResultBean(aproximationResult.getType().getInfo(messageSource),
                predictTimeString, aproximationResult.getRemark());
		return bean;
	}

    /**
     *
     * @return
     */
	public AproximationResultBean createFriendlyErrorBean() {
        String info = messageSource.getMessage("AproximationResultBeanFactory.there.was.error", new Object[0],
                Locale.getDefault());
        return new AproximationResultBean("", null, info);
    }


}
