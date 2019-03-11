package org.ksapala.rainaproximator.aproximation.domainfilters;

public class FiltersTest {

    private Filters filters;

    public FiltersTest() {
        this.filters = new Filters();
    }

//    @Test
//    public void testRemoveOutliersDifferenceMinusMedianThreeRemoved() {
//        List<RegressionPoint> points = new ArrayList<RegressionPoint>();
//        points.add(new RegressionPoint(new Distance(7), LocalDateTime.now()));
//        points.add(new RegressionPoint(new Distance(6), LocalDateTime.now()));
//        points.add(new RegressionPoint(new Distance(5), LocalDateTime.now()));
//        points.add(new RegressionPoint(new Distance(12), LocalDateTime.now()));
//        points.add(new RegressionPoint(new Distance(11), LocalDateTime.now()));
//        points.add(new RegressionPoint(new Distance(10), LocalDateTime.now()));
//        points.add(new RegressionPoint(new Distance(1), LocalDateTime.now()));
//
//        List<RegressionPoint> removed = this.filters.filterGoodFit(points);
//
//        assertEquals(points.size() - 3, removed.size());
//    }
//
//    @Test
//    public void testRemoveOutliersDifferenceMinusMedianNoRemove() {
//        List<RegressionPoint> points = new ArrayList<RegressionPoint>();
//        points.add(new RegressionPoint(new Distance(7), LocalDateTime.now()));
//        points.add(new RegressionPoint(new Distance(6), LocalDateTime.now()));
//        points.add(new RegressionPoint(new Distance(5), LocalDateTime.now()));
//        points.add(new RegressionPoint(new Distance(6), LocalDateTime.now()));
//        points.add(new RegressionPoint(new Distance(3), LocalDateTime.now()));
//
//        List<RegressionPoint> removed = this.calculator.removeOutliersDifference(points);
//
//        assertEquals(points.size(), removed.size());
//    }
//
//    @Test
//    public void testRemoveOutliersDifferenceThreeRemoved() {
//        List<RegressionPoint> points = new ArrayList<RegressionPoint>();
//        points.add(new RegressionPoint(new Distance(3), LocalDateTime.now()));
//        points.add(new RegressionPoint(new Distance(4), LocalDateTime.now()));
//        points.add(new RegressionPoint(new Distance(5), LocalDateTime.now()));
//        points.add(new RegressionPoint(new Distance(2), LocalDateTime.now()));
//        points.add(new RegressionPoint(new Distance(3), LocalDateTime.now()));
//        points.add(new RegressionPoint(new Distance(4), LocalDateTime.now()));
//        points.add(new RegressionPoint(new Distance(9), LocalDateTime.now()));
//
//        List<RegressionPoint> removed = this.calculator.removeOutliersDifference(points);
//
//        assertEquals(points.size() - 3, removed.size());
//    }
//
//    @Test
//    public void testRemoveOutliersDifferenceNoRemove() {
//        List<RegressionPoint> points = new ArrayList<RegressionPoint>();
//        points.add(new RegressionPoint(new Distance(3), LocalDateTime.now()));
//        points.add(new RegressionPoint(new Distance(4), LocalDateTime.now()));
//        points.add(new RegressionPoint(new Distance(5), LocalDateTime.now()));
//        points.add(new RegressionPoint(new Distance(4), LocalDateTime.now()));
//        points.add(new RegressionPoint(new Distance(7), LocalDateTime.now()));
//
//        List<RegressionPoint> removed = this.calculator.removeOutliersDifference(points);
//
//        assertEquals(points.size(), removed.size());
//    }
//
//    @Test
//    public void testRemoveOutliersDifferenceSecondLarge() {
//        List<RegressionPoint> points = new ArrayList<RegressionPoint>();
//        points.add(new RegressionPoint(new Distance(14), LocalDateTime.now()));
//        points.add(new RegressionPoint(new Distance(24), LocalDateTime.now()));
//        points.add(new RegressionPoint(new Distance(12), LocalDateTime.now()));
//        points.add(new RegressionPoint(new Distance(9), LocalDateTime.now()));
//        points.add(new RegressionPoint(new Distance(7), LocalDateTime.now()));
//        points.add(new RegressionPoint(new Distance(6), LocalDateTime.now()));
//        points.add(new RegressionPoint(new Distance(4), LocalDateTime.now()));
//        points.add(new RegressionPoint(new Distance(3), LocalDateTime.now()));
//        points.add(new RegressionPoint(new Distance(2), LocalDateTime.now()));
//
//        List<RegressionPoint> removed = this.calculator.removeOutliersDifference(points);
//
//        assertEquals(points.size() - 1, removed.size());
//        assertEquals(14, removed.get(0).getDistance().getValue());
//        assertEquals(12, removed.get(1).getDistance().getValue());
//        assertEquals(9, removed.get(2).getDistance().getValue());
//    }
}