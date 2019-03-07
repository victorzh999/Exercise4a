
public class JokeReview {	
	private String reviewerID;
	private int jokeID;
	private String remark;
	private String score;
	
	public JokeReview() {
	}

	public JokeReview(String reviewerID, int jokeID, String score, String remark) {
		this.reviewerID = reviewerID;
		this.jokeID = jokeID;
		this.score = score;
		this.remark = remark;
	}

	public String getReviewerID() {
		return reviewerID;
	}

	public void setReviewerID(String reviewerID) {
		this.reviewerID = reviewerID;
	}

	public int getJokeID() {
		return jokeID;
	}

	public void setJokeID(int jokeID) {
		this.jokeID = jokeID;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	
}
