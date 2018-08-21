package org.ksapala.rainaproximator.aproximation;

import lombok.Getter;
import lombok.Setter;
import org.ksapala.rainaproximator.utils.Debug;
import org.ksapala.rainaproximator.utils.TimeUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Getter
public class AproximationResult {

    private final String TO_STRING_PATTERN = "dd/MM/yyyy HH:mm";

	private AproximationResultType type;
	private LocalDateTime predictTime;
	@Setter
    private String remark;
	@Getter
    private Debug debug;
	
	public AproximationResult(AproximationResultType type, LocalDateTime predictTime) {
		 this.type = type;
		 this.predictTime = predictTime;
		 this.debug = new Debug();

	}
	
	public AproximationResult(AproximationResultType type, double predictTime) {
	    this(type, TimeUtils.millisToLocalDateAndTime((long) predictTime));
    }
	
	public AproximationResult(AproximationResultType type) {
	    this(type, null);
    }

    public boolean isPredict() {
	    return this.predictTime != null;
    }

	@Override
	public String toString() {
        String timeString = "";
        if (isPredict()) {
            timeString = this.predictTime.format(DateTimeFormatter.ofPattern(TO_STRING_PATTERN));
        }
		return "Type: " + this.type.toString() + ", time: " + timeString + ", remark: " + this.remark;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (object instanceof AproximationResult) {
			AproximationResult other = (AproximationResult) object;
			boolean typeEquals = this.type.equals(other.type);
			if (this.predictTime == null && other.predictTime == null) {
				return true;
			}
			if (this.predictTime != null) {
				boolean predictTimeEquals = this.predictTime.equals(other.predictTime);
				return typeEquals && predictTimeEquals;
			}
		}
		return false;
	}

	
}