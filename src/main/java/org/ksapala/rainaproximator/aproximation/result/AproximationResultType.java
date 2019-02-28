package org.ksapala.rainaproximator.aproximation.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.MessageSource;

import java.util.Locale;

@AllArgsConstructor
public enum AproximationResultType {

	RAIN_AT_TIME("AproximationResult.code.rain.at", 0),
	SUN_AT_TIME("AproximationResult.code.sun.at", 1),
	RAIN_UNSURE("AproximationResult.code.rain.unsure", 2),
	SUN_UNSURE("AproximationResult.code.sun.unsure", 3),
	RAIN_UNKNOWN("AproximationResult.code.rain.unknown", 4),
	SUN_UNKNOWN("AproximationResult.code.sun.unknown", 5);

	private String code;
	@Getter
	private int weatherDominance;


	public String getInfo(MessageSource messageSource) {
        return messageSource.getMessage(code, new Object[0], Locale.getDefault());

    }
}