package org.ksapala.rainaproximator.aproximation.scan;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class Scan {

    public static final int CURRENT_MOMENT_HOURS_RANGE = 2;

    private List<ScannedMap> maps;
    private LocalDateTime lastMapTime;

    /**
     * Is from las two hours.
     * @return
     */
    public boolean isCurrentMoment() {
        return lastMapTime.isAfter(LocalDateTime.now().minusHours(CURRENT_MOMENT_HOURS_RANGE));
    }

    @Override
    public String toString() {
        return "Scan{" +
                "maps size: " + maps.size() +
                ", lastMapTime: " + lastMapTime +
                '}';
    }
}
