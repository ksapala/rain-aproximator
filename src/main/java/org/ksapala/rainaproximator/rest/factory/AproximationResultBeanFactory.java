/**
 * 
 */
package org.ksapala.rainaproximator.rest.factory;

import org.ksapala.rainaproximator.aproximation.AproximationResult;
import org.ksapala.rainaproximator.messages.Messages;
import org.ksapala.rainaproximator.rest.bean.AproximationResultBean;

/**
 * @author krzysztof
 *
 */
public class AproximationResultBeanFactory {

    public final static AproximationResultBean FRIENDLY_EXCEPTION_RESULT = new AproximationResultBean("", null,
            Messages.getString("AproximationResultBeanFactory.there.was.error"));
    /**
	 * 
	 */
	public AproximationResultBeanFactory() {
	}
	
	/**
	 * @param aproximationResult
	 * @return
	 */
	public static AproximationResultBean createBean(AproximationResult aproximationResult) {
		AproximationResultBean bean = new AproximationResultBean(aproximationResult.getType().getUserFriendlyInfo(), 
				aproximationResult.getPredictTime(), aproximationResult.getRemark());
		return bean;
	}


}
