/**
 * 
 */
package org.ksapala.rainaproximator.rest.answer;

import org.ksapala.rainaproximator.aproximation.result.Aproximation;
import org.ksapala.rainaproximator.aproximation.result.AproximationResult;
import org.ksapala.rainaproximator.aproximation.result.AproximationResultType;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.utils.TimeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Locale;
import java.util.Optional;

/**
 * @author krzysztof
 *
 */
@Component
public class AproximationAnswerFactory {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private TimeFactory timeFactory;

    @Autowired
    private Configuration configuration;

    /**
	 * 
	 */
	public AproximationAnswerFactory() {
	}
	
	/**
	 * @param aproximation
	 * @return
	 */
	public AproximationAnswer createAproximationAnswer(Aproximation aproximation) {
	    AproximationResult aproximationResult = aproximation.getAproximationResult();
        Optional<LocalDateTime> time = aproximationResult.getTime();
        String day = time.map(this::getDay).orElse("");
        boolean notificationSuggested = isNotificationSuggested(aproximation);

        return new AproximationAnswer(aproximationResult.getType().getInfo(messageSource), day, time.orElse(null),
                aproximationResult.getRemark(), notificationSuggested, aproximation.getAccuracy(),
                aproximationResult.getDebug());
	}

	boolean isNotificationSuggested(Aproximation aproximation) {
        AproximationResultType type = aproximation.getAproximationResult().getType();
        if (type.equals(AproximationResultType.RAIN_AT_TIME)) {
            return aproximation.getAproximationResult().getTime()
                    .map(time -> isTimeInComingMinutes(time, configuration.getAlgorithm().getNotificationSugestedMinutes()))
                    .orElse(true);

        }
        if (type.equals(AproximationResultType.RAIN_GOES_AWAY) || type.equals(AproximationResultType.RAIN_UNKNOWN)) {
            return false;
        }
        return true;
    }

    private boolean isTimeInComingMinutes(LocalDateTime time, int minutes) {
	    // time < now + minutes
        return time.isBefore(timeFactory.now().plusMinutes(minutes));
    }

    private String getDay(LocalDateTime time) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime tomorrow = today.plusDays(1);

        if (isTimeAtDay(time, today)) {
            return messageSource.getMessage("AproximationAnswerFactory.today", new Object[0], Locale.getDefault());
        }

        if (isTimeAtDay(time, tomorrow)) {
            return messageSource.getMessage("AproximationAnswerFactory.tomorrow", new Object[0], Locale.getDefault());
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
	public AproximationAnswer createFriendlyErrorBean() {
        String info = messageSource.getMessage("AproximationAnswerFactory.there.was.error", new Object[0],
                Locale.getDefault());
        return new AproximationAnswer("", "", null, info, true,null, null);
    }

    /**
     *
     * @return
     */
    public AproximationAnswer createEmptyScanBean() {
        String info = messageSource.getMessage("AproximationAnswerFactory.empty.scan", new Object[0],
                Locale.getDefault());
        return new AproximationAnswer("", "", null, info, true,null, null);
    }

}
