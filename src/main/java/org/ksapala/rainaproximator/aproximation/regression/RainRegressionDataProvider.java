package org.ksapala.rainaproximator.aproximation.regression;

import org.ksapala.rainaproximator.aproximation.cloud.CloudLine;
import org.ksapala.rainaproximator.aproximation.cloud.Distance;

import java.util.List;

public class RainRegressionDataProvider extends RegressionDataProvider {

	public RainRegressionDataProvider(List<CloudLine> cloudLines) {
		super(cloudLines);
	}

	@Override
	public Distance getDistance(int index) {
		CloudLine cloudLine = this.cloudLines.get(index);
		Distance distance = cloudLine.getRainDistance();
		return distance;
	}
	
	@Override
	public String getDescription() {
		return "[Using Rain distance]";
	}

}
