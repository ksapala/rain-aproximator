package org.ksapala.rainaproximator.aproximation;

import org.ksapala.rainaproximator.util.Utils;

import java.util.Date;

public class AproximationResult {

	private AproximationResultType type;
	private Date predictTime;
	private String remark;
	
	public AproximationResult(AproximationResultType type, Date predictTime) {
		 this.type = type;
		 this.predictTime = predictTime;
	}
	
	public AproximationResult(AproximationResultType type, double predictTime) {
	   this(type, new Date((long) predictTime));
    }
	
	public AproximationResult(AproximationResultType type) {
	    this(type, null);
    }
	
	public AproximationResultType getType() {
		return this.type;
	}
	
	public Date getPredictTime() {
		return this.predictTime;
	}

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	@Override
	public String toString() {
		String dateString = Utils.format(this.predictTime);
		return "Type: " + this.type.toString() + ", date: " + dateString + ", remark: " + this.remark;
	}
	
	public String toFriendlyString() {
		return this.type.getUserFriendlyInfo() + ", date: " + Utils.format(this.predictTime);
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