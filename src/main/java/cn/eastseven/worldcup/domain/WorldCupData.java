package cn.eastseven.worldcup.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class WorldCupData {

	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
	
	public static final String key = "worldcupdata:";
	
	private String id;
	private String teamLeft;
	private String teamRight;
	
	private String startTime;
	private long times;
	
	private String teamLeftGoals = "";
	private String teamRightGoals = "";

	private String resultLeft   = "0";
	private String resultMiddle = "0";
	private String resultRight  = "0";
	
	public enum F {
		id,
		teamLeft,
		teamRight,
		startTime,
		times,
		teamLeftGoals,
		teamRightGoals,
		resultLeft,
		resultMiddle,
		resultRight
	}
	
	static String[] fields = new String[] {
		F.id.toString(),//0
		F.teamLeft.toString(),//1
		F.teamRight.toString(),//2
		F.startTime.toString(),//3
		F.teamLeftGoals.toString(),//4
		F.teamRightGoals.toString(),//5
		F.resultLeft.toString(),//6
		F.resultMiddle.toString(),//7
		F.resultRight.toString()//8
	};
	
	public static String[] getFields() {
		return fields;
	}
	
	public WorldCupData(String id, String teamLeft, String teamRight, String startTime) {
		super();
		this.id = id;
		this.teamLeft = teamLeft;
		this.teamRight = teamRight;
		this.startTime = startTime;
		try {
			this.times = sdf.parse(startTime).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//this.key = this.key + this.id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTeamLeft() {
		return teamLeft;
	}

	public void setTeamLeft(String teamLeft) {
		this.teamLeft = teamLeft;
	}

	public String getTeamRight() {
		return teamRight;
	}

	public void setTeamRight(String teamRight) {
		this.teamRight = teamRight;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public long getTimes() {
		return times;
	}

	public void setTimes(long times) {
		this.times = times;
	}

	public String getTeamLeftGoals() {
		return teamLeftGoals;
	}

	public void setTeamLeftGoals(String teamLeftGoals) {
		this.teamLeftGoals = teamLeftGoals;
	}

	public String getTeamRightGoals() {
		return teamRightGoals;
	}

	public void setTeamRightGoals(String teamRightGoals) {
		this.teamRightGoals = teamRightGoals;
	}

	public String getResultLeft() {
		return resultLeft;
	}

	public void setResultLeft(String resultLeft) {
		this.resultLeft = resultLeft;
	}

	public String getResultMiddle() {
		return resultMiddle;
	}

	public void setResultMiddle(String resultMiddle) {
		this.resultMiddle = resultMiddle;
	}

	public String getResultRight() {
		return resultRight;
	}

	public void setResultRight(String resultRight) {
		this.resultRight = resultRight;
	}

	public String getKey() {
		return key+id;
	}
	
	@Override
	public String toString() {
		return "WorldCupData [id=" + id + ", teamLeft=" + teamLeft
				+ ", teamRight=" + teamRight + ", startTime=" + startTime
				+ ", times=" + times + ", teamLeftGoals=" + teamLeftGoals
				+ ", teamRightGoals=" + teamRightGoals + ", resultLeft="
				+ resultLeft + ", resultMiddle=" + resultMiddle
				+ ", resultRight=" + resultRight + "]";
	}

	@Override
	public boolean equals(Object obj) {
		WorldCupData data = (WorldCupData) obj;
		return data.getId().equals(id);
	}
}
