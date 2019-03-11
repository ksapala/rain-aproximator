package org.ksapala.rainaproximator.aproximation.result;

import lombok.Getter;
import lombok.Setter;
import org.ksapala.rainaproximator.aproximation.debug.Debug;
import org.ksapala.rainaproximator.utils.TimeUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class AproximationResult {

    private final String TO_STRING_PATTERN = "dd/MM/yyyy HH:mm";

	private AproximationResultType type;
	private LocalDateTime predictTime;
    private String remark;
    private Accuracy accuracy;
    private Debug debug;

    public AproximationResult(AproximationResultType type, Accuracy accuracy, LocalDateTime predictTime) {
		 this.type = type;
		 this.accuracy = accuracy;
         this.predictTime = predictTime;

	}

	public AproximationResult(AproximationResultType type, Accuracy accuracy, double predictTime) {
	    this(type, accuracy, TimeUtils.millisToLocalDateAndTime((long) predictTime));
    }

	public AproximationResult(AproximationResultType type, Accuracy accuracy) {
	    this(type, accuracy, null);
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
		return "Type: " + this.type.toString() + ", time: " + timeString + ", accuracy: " + accuracy.toString() + ", remark: " + this.remark;
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

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setDebug(Debug debug) {
        this.debug = debug;
    }
}