package org.ksapala.rainaproximator.aproximation.scan;

import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.exception.AproximationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class Scanner {

    private final Logger logger = LoggerFactory.getLogger(Scanner.class);

    @Autowired
    private Configuration configuration;

    @Autowired
    private LastRadarMapTimeParser lastRadarMapTimeParser;

    public Scanner() {
    }

    /**
     *
     * @return
     * @throws AproximationException
     */
    public Scan scan() throws AproximationException {
        List<BufferedImage> images;
        LocalDateTime parsedTime;
        try {
            parsedTime = this.lastRadarMapTimeParser.parseLastRadarMapTime();
        } catch (Exception e) {
            throw new AproximationException("Error while parsing last radar map date on main page.", e);
        }
        try {
            images = getImages();
        } catch (Exception e) {
            throw new AproximationException("Error while scanning radar maps", e);
        }

        int intervalMinutes = configuration.getScanner().getRadarMapTimeIntevalMinutes();

        List<ScannedMap> maps = IntStream.range(0, images.size())
                .mapToObj(i -> new ScannedMap(images.get(i), parsedTime.minusMinutes(intervalMinutes * (images.size() - i - 1))))
                .filter(ScannedMap::isClear)
                .collect(Collectors.toList());

        LocalDateTime lastMapTime = maps.stream().map(ScannedMap::getTime)
                .reduce((first, second) -> second)
                .orElse(parsedTime);
        return new Scan(maps, lastMapTime);
    }

    private List<BufferedImage> getImages() throws IOException {
        List<BufferedImage> images = new ArrayList<>(configuration.getScanner().getRadarImageIdentifiers().size());
        for (String radarImageIdentifier : configuration.getScanner().getRadarImageIdentifiers()) {
            BufferedImage image = getImage(radarImageIdentifier);
            images.add(image);
        }
        logger.debug("Map images read from url. Size = " + images.size());
        return images;
    }

    private BufferedImage getImage(String radarImageIdentifier) throws IOException {
        String urlString = MessageFormat.format(configuration.getScanner().getRadarUrl(), radarImageIdentifier);
        URL url = new URL(urlString);
        return ImageIO.read(url);
    }
}
