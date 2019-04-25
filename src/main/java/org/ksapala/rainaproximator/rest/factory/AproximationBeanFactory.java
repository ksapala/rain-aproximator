/**
 * 
 */
package org.ksapala.rainaproximator.rest.factory;

import org.ksapala.rainaproximator.aproximation.result.Aproximation;
import org.ksapala.rainaproximator.aproximation.result.AproximationResult;
import org.ksapala.rainaproximator.aproximation.result.AproximationResultType;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.rest.bean.AproximationBean;
import org.ksapala.rainaproximator.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

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
        Optional<LocalDateTime> time = aproximationResult.getTime();
        String day = time.map(this::getDay).orElse("");
        boolean notificationSuggested = !aproximation.getAproximationResult().getType().equals(AproximationResultType.RAIN_UNKNOWN);

        return new AproximationBean(aproximationResult.getType().getInfo(messageSource), day, time.orElse(null),
                aproximationResult.getRemark(), notificationSuggested, aproximation.getAccuracy(),
                aproximationResult.getDebug());
	}

    private String getDay(LocalDateTime time) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime tomorrow = today.plusDays(1);

        if (isTimeAtDay(time, today)) {
            return messageSource.getMessage("AproximationBeanFactory.today", new Object[0], Locale.getDefault());
        }

        if (isTimeAtDay(time, tomorrow)) {
            return messageSource.getMessage("AproximationBeanFactory.tomorrow", new Object[0], Locale.getDefault());
        }
        return "";
    }

    boolean isTimeAtDay(LocalDateTime time, LocalDateTime atDay) {
	    return time.getDayOfMonth() == atDay.getDayOfMonth()
                && time.getMonthValue() == atDay.getMonthValue()
                && time.getYear() == atDay.getYear();
    }

    /**
     *
     * @return
     */
	public AproximationBean createFriendlyErrorBean() {
        String info = messageSource.getMessage("AproximationBeanFactory.there.was.error", new Object[0],
                Locale.getDefault());
        return new AproximationBean("", "", null, info, true,null, null);
    }

    /**
     *
     * @return
     */
    public AproximationBean createEmptyScanBean() {
        String info = messageSource.getMessage("AproximationBeanFactory.empty.scan", new Object[0],
                Locale.getDefault());
        return new AproximationBean("", "", null, info, true,null, null);
    }

}
