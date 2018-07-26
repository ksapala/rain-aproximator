package org.ksapala.rainaproximator.aproximation.map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.exception.AproximationException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Scanner {

    private final static Logger LOGGER = LogManager.getLogger(Scanner.class);

    private Configuration.Scanner scannerConfiguration;

    private LastRadarMapDateParser lastRadarMapDateParser;

    public Scanner(Configuration.Scanner scannerConfiguration) {
        this.scannerConfiguration = scannerConfiguration;
        this.lastRadarMapDateParser = new LastRadarMapDateParser(scannerConfiguration);
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
            mapTime = mapTime.minusMinutes(scannerConfiguration.getRadarMapTimeIntevalMinutes());
        }

        return new Scan(maps, lastRadarMapTime);
    }

    private List<BufferedImage> getImages() throws IOException {
        List<BufferedImage> images = new ArrayList<>(scannerConfiguration.getRadarImageIdentifiers().size());
        for (String radarImageIdentifier : scannerConfiguration.getRadarImageIdentifiers()) {
            BufferedImage image = getImage(radarImageIdentifier);
            images.add(image);
        }
        return images;
    }

    private BufferedImage getImage(String radarImageIdentifier) throws IOException {
        String urlString = MessageFormat.format(scannerConfiguration.getRadarUrl(), radarImageIdentifier);
        URL url = new URL(urlString);
        BufferedImage bufferedImage = ImageIO.read(url);
        return bufferedImage;
    }
}
