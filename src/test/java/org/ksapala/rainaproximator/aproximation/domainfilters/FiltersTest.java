package org.ksapala.rainaproximator.aproximation.domainfilters;

import org.junit.Test;
import org.ksapala.rainaproximator.aproximation.cloud.Distance;
import org.ksapala.rainaproximator.aproximation.regression.RegressionPoint;
import org.ksapala.rainaproximator.aproximation.structure.RegressionPointStructure;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FiltersTest {

    private Filters filters;

    public FiltersTest() {
        this.filters = new Filters();
    }

    @Test
    public void testRemoveOutliersDifferenceMinusMedianThreeRemoved() {
        List<RegressionPointStructure> points = new ArrayList<>();
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(7), LocalDateTime.now()), null));
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(6), LocalDateTime.now()), null));
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(5), LocalDateTime.now()), null));
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(12), LocalDateTime.now()), null));
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(11), LocalDateTime.now()), null));
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(10), LocalDateTime.now()), null));
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(1), LocalDateTime.now()), null));

        List<RegressionPointStructure> filtered = this.filters.filterGoodFit(points);

        assertEquals(points.size() - 3, filtered.size());
    }

    @Test
    public void testRemoveOutliersDifferenceMinusMedianNoRemove() {
        List<RegressionPointStructure> points = new ArrayList<>();
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(7), LocalDateTime.now()), null));
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(6), LocalDateTime.now()), null));
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(5), LocalDateTime.now()), null));
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(4), LocalDateTime.now()), null));
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(3), LocalDateTime.now()), null));

        List<RegressionPointStructure> filtered = this.filters.filterGoodFit(points);

        assertEquals(points.size(), filtered.size());
    }

    @Test
    public void testRemoveOutliersDifferenceThreeRemoved() {
        List<RegressionPointStructure> points = new ArrayList<>();
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(3), LocalDateTime.now()), null));
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(4), LocalDateTime.now()), null));
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(5), LocalDateTime.now()), null));
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(2), LocalDateTime.now()), null));
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(3), LocalDateTime.now()), null));
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(4), LocalDateTime.now()), null));
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(9), LocalDateTime.now()), null));

        List<RegressionPointStructure> filtered = this.filters.filterGoodFit(points);

        assertEquals(points.size() - 3, filtered.size());
    }

    @Test
    public void testRemoveOutliersDifferenceNoRemove() {
        List<RegressionPointStructure> points = new ArrayList<>();
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(3), LocalDateTime.now()), null));
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(4), LocalDateTime.now()), null));
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(5), LocalDateTime.now()), null));
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(6), LocalDateTime.now()), null));
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(7), LocalDateTime.now()), null));

        List<RegressionPointStructure> filtered = this.filters.filterGoodFit(points);

        assertEquals(points.size(), filtered.size());
    }

    @Test
    public void testRemoveOutliersDifferenceOneVeryLargeOneVerSmall() {
        List<RegressionPointStructure> points = new ArrayList<>();
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(14), LocalDateTime.now()), null));
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(24), LocalDateTime.now()), null));
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(12), LocalDateTime.now()), null));
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(11), LocalDateTime.now()), null));
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(10), LocalDateTime.now()), null));
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(9), LocalDateTime.now()), null));
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(1), LocalDateTime.now()), null));
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(7), LocalDateTime.now()), null));
        points.add(new RegressionPointStructure(new RegressionPoint(new Distance(6), LocalDateTime.now()), null));

        List<RegressionPointStructure> filtered = this.filters.filterGoodFit(points);

        assertEquals(points.size() - 2, filtered.size());
        assertEquals(14, filtered.get(0).getRegressionPoint().getDistance().getValue());
        assertEquals(12, filtered.get(1).getRegressionPoint().getDistance().getValue());
        assertEquals(11, filtered.get(2).getRegressionPoint().getDistance().getValue());
    }
}