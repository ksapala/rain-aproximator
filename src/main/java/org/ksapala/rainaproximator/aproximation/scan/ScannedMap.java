package org.ksapala.rainaproximator.aproximation.scan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ksapala.rainaproximator.aproximation.image.RainImage;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

@Getter
@AllArgsConstructor
public class ScannedMap {

    private BufferedImage image;
    private LocalDateTime time;

    public boolean isClear() {
        return IntStream.range(0, 5)
                .noneMatch(i -> image.getRGB(RainImage.BASE_X, RainImage.BASE_Y + i) == RainImage.RGB_YELLOW);
    }
}
