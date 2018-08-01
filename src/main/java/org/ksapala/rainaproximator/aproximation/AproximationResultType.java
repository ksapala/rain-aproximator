package org.ksapala.rainaproximator.aproximation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.MessageSource;

import java.util.Locale;

@AllArgsConstructor
public enum AproximationResultType {

	RAIN_AT_TIME("AproximationResult.code.rain.at", 5),
	SUN_AT_TIME("AproximationResult.code.sun.at", 4),
	RAIN_UNSURE("AproximationResult.code.rain.unsure", 3),
	SUN_UNSURE("AproximationResult.code.sun.unsure", 2),
	RAIN_UNKNOWN("AproximationResult.code.rain.unknown", 1),
	SUN_UNKNOWN("AproximationResult.code.sun.unknown", 0);

	private String code;
	@Getter
	private int weatherDominance;


	public String getInfo(MessageSource messageSource) {
        return messageSource.getMessage(code, new Object[0], Locale.getDefault());

    }
}