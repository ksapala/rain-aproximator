package org.ksapala.rainaproximator.aproximation.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.ksapala.rainaproximator.aproximation.debug.Debug;
import org.ksapala.rainaproximator.utils.TimeUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class AproximationResult {

    private final String TO_STRING_PATTERN = "dd/MM/yyyy HH:mm";

	private AproximationResultType type;
	private LocalDateTime time;
    private String remark;
    private Debug debug;

    public AproximationResult(AproximationResultType type, LocalDateTime time) {
        this.type = type;
        this.time = time;
    }

	public AproximationResult(AproximationResultType type, double time) {
	    this(type, TimeUtils.millisToDate((long) time));
    }

	public AproximationResult(AproximationResultType type) {
	    this(type, null);
    }

    public boolean isPredict() {
	    return this.time != null;
    }

	@Override
	public String toString() {
        String timeString = "";
        if (isPredict()) {
            timeString = this.time.format(DateTimeFormatter.ofPattern(TO_STRING_PATTERN));
        }
		return "Type: " + this.type.toString() + ", time: " + timeString + ", remark: " + this.remark;
	}

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setDebug(Debug debug) {
        this.debug = debug;
    }

    public Optional<LocalDateTime> getTime() {
        return Optional.ofNullable(time);
    }
}