package org.ksapala.rainaproximator.aproximation.scan;

import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.exception.AproximationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class Scanner {

    @Autowired
    private Configuration configuration;

    @Autowired
    private LastRadarMapDateParser lastRadarMapDateParser;

    public Scanner() {
    }

    /**
     *
     * @return
     * @throws AproximationException
     */
    public Scan scan() throws AproximationException {
        List<BufferedImage> images;
        LocalDateTime lastRadarMapTime;
        try {
            lastRadarMapTime = this.lastRadarMapDateParser.parseLastRadarMapDate();
        } catch (IOException e) {
            throw new AproximationException("Error while parsing last radar map date on main page.", e);
        }
        try {
            images = getImages();
        } catch (IOException e) {
            throw new AproximationException("Error while scanning radar maps", e);
        }

        LocalDateTime mapTime = LocalDateTime.from(lastRadarMapTime);
        List<ScannedMap> maps = new ArrayList<>(images.size());
        for (int i = images.size() - 1; i >= 0; i--) {
            maps.add(new ScannedMap(images.get(i), mapTime));
            mapTime = mapTime.minusMinutes(configuration.getScanner().getRadarMapTimeIntevalMinutes());
        }

        return new Scan(maps, lastRadarMapTime);
    }

    private List<BufferedImage> getImages() throws IOException {
        List<BufferedImage> images = new ArrayList<>(configuration.getScanner().getRadarImageIdentifiers().size());
        for (String radarImageIdentifier : configuration.getScanner().getRadarImageIdentifiers()) {
            BufferedImage image = getImage(radarImageIdentifier);
            images.add(image);
        }
        return images;
    }

    private BufferedImage getImage(String radarImageIdentifier) throws IOException {
        String urlString = MessageFormat.format(configuration.getScanner().getRadarUrl(), radarImageIdentifier);
        URL url = new URL(urlString);
        BufferedImage bufferedImage = ImageIO.read(url);
        return bufferedImage;
    }
}
