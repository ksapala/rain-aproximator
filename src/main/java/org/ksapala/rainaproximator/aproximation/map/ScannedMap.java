package org.ksapala.rainaproximator.aproximation.map;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScannedMap {

    private BufferedImage image;
    private LocalDateTime time;
}
