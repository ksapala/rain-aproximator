package org.ksapala.rainaproximator.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotBlank;
import java.util.List;

@ConfigurationProperties
@Getter
@Setter
public class Configuration {

    private Wind wind = new Wind();
    private Algorithm algorithm = new Algorithm();
    private Scanner scanner = new Scanner();

    @Getter
    @Setter
    public class Wind {
        private String url;
        private int retries;
    }

    @Getter
    @Setter
    public class Algorithm {
        private boolean useSideScans;
        private int[] sideScansAngles;
        private Cloud cloud;

        @Getter
        @Setter
        @NotBlank
        public class Cloud {
            private int replaceHolesMin;
            private int replaceHolesMax;
            private int replaceHolesStartMin;
            private int replaceHolesStartMax;
        }
    }

    @Getter
    @Setter
    @NotBlank
    public class Scanner {
        private String radarUrl;
        private String radarMainPage;
        private List<String> radarImageIdentifiers;
        private String lastRadarMapDateFormat;
        private String lastRadarMapDateZone;
        private String lastRadarMapDateElementId;
        private int radarMapTimeIntevalMinutes;
    }

}
