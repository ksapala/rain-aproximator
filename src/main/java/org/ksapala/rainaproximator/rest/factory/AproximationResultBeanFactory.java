/**
 * 
 */
package org.ksapala.rainaproximator.rest.factory;

import org.ksapala.rainaproximator.aproximation.AproximationResult;
import org.ksapala.rainaproximator.messages.Messages;
import org.ksapala.rainaproximator.rest.bean.AproximationResultBean;
import org.ksapala.rainaproximator.utils.TimeComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author krzysztof
 *
 */
@Component
public class AproximationResultBeanFactory {

    @Autowired
    private TimeComponent timeComponent;

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

		AproximationResultBean bean = new AproximationResultBean(aproximationResult.getType().getUserFriendlyInfo(),
                predictTimeString, aproximationResult.getRemark());
		return bean;
	}

	public AproximationResultBean createFriendlyErrorBean() {
        return new AproximationResultBean("", null, Messages.getString("AproximationResultBeanFactory.there.was.error"));
    }


}
