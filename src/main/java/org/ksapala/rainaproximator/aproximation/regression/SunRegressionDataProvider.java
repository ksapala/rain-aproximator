/**
 * 
 */
package org.ksapala.rainaproximator.aproximation.regression;

import org.ksapala.rainaproximator.aproximation.cloud.CloudLine;
import org.ksapala.rainaproximator.aproximation.cloud.Distance;

import java.util.List;

/**
 * @author krzysztof
 *
 */
public class SunRegressionDataProvider extends RegressionDataProvider {

	/**
	 * 
	 */
	public SunRegressionDataProvider(List<CloudLine> cloudLines) {
		super(cloudLines);
	}

	@Override
	public Distance getDistance(int index) {
		CloudLine cloudLine = this.cloudLines.get(index);
		Distance distance = cloudLine.getFutureSunDistance();
		return distance;
	}

	@Override
	public String getDescription() {
		return "[Using Sun distance]";
	}
}
