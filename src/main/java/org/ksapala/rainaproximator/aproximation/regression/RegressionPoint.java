package org.ksapala.rainaproximator.aproximation.regression;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ksapala.rainaproximator.aproximation.cloud.Distance;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class RegressionPoint {

	private Distance distance;
	private LocalDateTime time;

}