package com.ffa.models;
import java.util.*;

public class SmartRankings extends Rankings implements Comparable<SmartRankings>{
	public int smartRank;
	public SmartRankings(){
		
	}
	
	
	public List<SmartRankings> getSmartRankingsList(int LeagueID, int Week){
		Rankings r = new Rankings();
		List<Rankings> lr = r.Rank(LeagueID, Week);
		List<SmartRankings> lsr = new ArrayList<SmartRankings>();
		//Collections.sort(lr, Rankings.);
		for (int i = 0; i < lr.size(); i++){
			SmartRankings sr = new SmartRankings();
			sr.ffaPoints = lr.get(i).ffaPoints;
			sr.teamID = lr.get(i).teamID;
			sr.teamName = lr.get(i).teamName;
			lsr.add(sr);
		}
		Collections.sort(lsr);
		for (int j = 0; j < lsr.size(); j++){
			lsr.get(j).setRankScore(j + 1);
			this.smartRank = lsr.get(j).getRankScore();
		}
		return lsr;
	}


	public int compare(SmartRankings o1, SmartRankings o2) {
		Integer tempo1 = (Integer) o1.ffaPoints;
		return tempo1.compareTo((Integer) o2.ffaPoints);
	}

	@Override
	public int compareTo(SmartRankings o) {
		// TODO Auto-generated method stub
		return  o.ffaPoints - this.ffaPoints;
	}
}