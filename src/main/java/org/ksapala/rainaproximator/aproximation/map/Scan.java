package org.ksapala.rainaproximator.aproximation.map;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class Scan {

    private List<ScannedMap> maps;
    private LocalDateTime lastMapTime;

    /**
     * Is from las two hours.
     * @return
     */
    public boolean isCurrentMoment() {
        return lastMapTime.isAfter(LocalDateTime.now().minusHours(2));
    }
}
