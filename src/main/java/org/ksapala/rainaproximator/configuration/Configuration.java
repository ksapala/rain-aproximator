package org.ksapala.rainaproximator.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@ConfigurationProperties
@Getter
@Setter
@Validated
public class Configuration {

    @NotBlank private String userTimeFormat;
    @NotBlank private String testTimeFormat;
    @NotBlank private String mobileTimeFormat;
    @NotBlank private String firebaseTopic;

    private Wind wind = new Wind();
    private Algorithm algorithm = new Algorithm();
    private Scanner scanner = new Scanner();

    @Getter
    @Setter
    public class Wind {

        @NotBlank private String url;
        @NotNull private int retries;
    }

    @Getter
    @Setter
    public class Algorithm {
        @NotEmpty private int[] aroundAngles;
        @NotEmpty private int[] aroundFinalAngles;
        @NotNull private int defaultWind;
        @NotNull private double goodFitExpandFactor;
        @NotNull private int radarMapTimeIntevalMinutes;

        private Mode mode = new Mode();
        private Cloud cloud = new Cloud();

        @Getter
        @Setter
        public class Mode {
            @NotBlank private String name;
            @NotBlank private String compare;
        }

        @Getter
        @Setter
        public class Cloud {

            @NotNull private int replaceHolesMin;
            @NotNull private int replaceHolesMax;
            @NotNull private int replaceHolesStartMin;
            @NotNull private int replaceHolesStartMax;
            @NotNull private int cloudLength;
        }
    }

    @Getter
    @Setter
    public class Scanner {

        @NotBlank private String radarUrl;
        @NotBlank private String radarMainPage;
        @NotEmpty private List<String> radarImageIdentifiers;
        @NotBlank private String lastRadarMapDateFormat;
        @NotBlank private String lastRadarMapDateZone;
        @NotBlank private String lastRadarMapDateElementId;
        @NotNull private int radarMapTimeIntevalMinutes;
    }

}
