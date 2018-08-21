package org.ksapala.rainaproximator.withmain;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class Main {

    public static void main(String[] args) throws Exception {
		new Main().test();
    }

    public void test() {
        DescriptiveStatistics statistics = new DescriptiveStatistics();
        statistics.addValue(4);
        statistics.addValue(5);
        statistics.addValue(6);
        statistics.addValue(7);
        statistics.addValue(33);

        System.out.println(statistics.getStandardDeviation());
    }

}
