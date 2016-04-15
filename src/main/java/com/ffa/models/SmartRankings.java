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
	
	public String getSmartRankingsChart(int LeagueID, int Week){
		List<List<SmartRankings>> llsr = new ArrayList<List<SmartRankings>>();
		for (int i  =1; i <= Week; i++){
			//load all the values for creating json data
			List<SmartRankings> lsr = getSmartRankingsList(LeagueID, i);
			llsr.add(lsr);
		}
		
		System.out.println(llsr);
		//format list for iterating through for proper json format
		List<SmartRankings[]> jllsr = new ArrayList<SmartRankings[]>();
		for (int k = 0; k < llsr.size(); k++){
			SmartRankings[] jlsr = new SmartRankings[llsr.get(k).size()];
			for (int j = 0; j <llsr.get(k).size(); j++){
				SmartRankings tsr = new SmartRankings();
				tsr.teamID = llsr.get(k).get(j).teamID;
				tsr.teamName = llsr.get(k).get(j).teamName;
				tsr.setRankScore(llsr.get(k).get(j).getRankScore());
				jlsr[tsr.teamID - 1] = tsr;
			}
			jllsr.add(jlsr);
		}
		System.out.println(jllsr);
		//now iterate through jllsr and generate json that google charts can read
		String json = "{\"cols\":[{";
		json += "\"type\":\"number\",\"label\":\"Week\",\"id\":\"1\"},";
		for (int p = 0; p < jllsr.get(0).length; p++){
			json += "{\"type\":\"number\",\"label\":\"" + jllsr.get(0)[p].teamName + "\",\"id\":\"" + (p + 2) + "\"},";
		}
		//trim off last comma
		json = json.substring(0, json.length() - 1);
		json += "],\"rows\":[";
		for (int t = 0; t < jllsr.size(); t++){
			json += "{\"c\":[";
			//enter the week number in the first v
			json += "{\"v\":" + (t+1) + "},";
			for (int s = 0; s < jllsr.get(t).length; s++){
				json += "{\"v\":" + jllsr.get(t)[s].getRankScore() + "},";
			}
			//trim off last comma
			json = json.substring(0, json.length() - 1);
			json += "]},";
		}
		//trim off last comma
		json = json.substring(0, json.length() - 1);
		json += "]}";
		System.out.println(json);
		return json;
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