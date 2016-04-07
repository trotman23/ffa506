package com.ffa.scrapers;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.sql.*;
import java.util.*;


public class RunScrape {

	// djdbc driver name and database
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://aaxd0xqcmiqmgi.c0fxx6pghrju.us-west-2.rds.amazonaws.com/ffadb";

	//  Database credentials
	static final String USER = "master";
	static final String PASS = "eric2mad";


	public static void doRunScrape(){
		
		//TODO: break this awful code down into modules for testing, and runnability from the web app
		
		//testing for api
		/*Connection pConn = null;
		Statement pStmt = null;
		try{
			int id = 1;
			Class.forName("com.mysql.jdbc.Driver");
			pConn = DriverManager.getConnection(DB_URL,USER,PASS);
			pStmt = pConn.createStatement();
			//select on team name, given team id

			String sql = "SELECT * FROM NflTeam WHERE NflTeamID = " + id + ";";
			System.out.println(sql);
			ResultSet rs = pStmt.executeQuery(sql);
			System.out.println(rs.toString());
			while(rs.next()){
				String name = rs.getString(2);
				int wins = rs.getInt(3);
				int loss = rs.getInt(4);
				int ties = rs.getInt(5);
			}
			pConn.close();
			rs.close();

		} catch (Exception e){
			e.printStackTrace();
		} finally{

		}*/

		// commenting out for testing/filling localdb
		//List<NflTeam> teams = CrawlNflTeams("http://espn.go.com/nfl/standings");
		//storeNflTeams(teams);
		/*for (int i = 0; i <= 1700; i+=40){
			String url = "http://games.espn.go.com/ffl/tools/projections?&week=1&scoringPeriodId=1&seasonId=2015&startIndex=" + i;
			List<Players> playerList = CrawlPlayers(url);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int j = 0; j < playerList.size(); j++){
				storePlayer(playerList.get(j));
			}
		}*/
		
		/*for (int l = 0; l < 17; l++){
			for (int n = 0; n <= 300; n+=50){
				try {
					String wsUrl = "http://games.espn.go.com/ffl/leaders?&scoringPeriodId="+l+"&seasonId=2015&startIndex=" + n;
					List<WeeklyScores> wstemp = CrawlWeeklyScores(wsUrl, l, 2015);
					for(int k = 0; k < wstemp.size(); k++){
						try{
							storeWeeklyScores(wstemp.get(k));
						} catch (Exception e){
							e.printStackTrace();;
						}
					}
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//System.out.println("\n\n\n\n\n\n END OF WEEK " + l + " scrape \n\n\n\n\n\n ");
		}*/

		/*try {
			String wsUrl = "http://games.espn.go.com/ffl/leaders?&scoringPeriodId=1&seasonId=2015&startIndex=0";
			List<WeeklyScores> wstemp = CrawlWeeklyScores(wsUrl, 1, 2015);
			for(int k = 0; k < wstemp.size(); k++){
				try{
					storeWeeklyScores(wstemp.get(k));
				} catch (Exception e){
					e.printStackTrace();;
				}
			}
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/



		//D/ST Crawlers
		/*for (int f = 1; f < 17; f++){
			String dsturl = "http://games.espn.go.com/ffl/leaders?&scoringPeriodId=" + f + "&seasonId=2015&slotCategoryId=16";
			crawlDST(dsturl, f, 2015);
		}

		System.exit(0);*/
		//Roster Crawlers
		for (int w = 1; w <= 17; w++){
			for (int t = 1; t <= 12; t++){
				int leagueID = 1124587;
				int seasonID = 2015;
				String url = "http://games.espn.go.com/ffl/boxscorequick?leagueId=" + leagueID +"&teamId="+ t + 
						"&scoringPeriodId=" + w + "&seasonId=" + seasonID + "&view=scoringperiod&version=quick"; 
				List<Roster> lr = crawlRoster(url, leagueID, t, seasonID, w);
				System.out.println(lr.toString());
				for (int r = 0; r < lr.size(); r++){
					storeRoster(lr.get(r));
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private static List<Roster> crawlRoster(String url, int leagueID, int teamID, int seasonID, int weekID){
		List<Roster> lr = new ArrayList<Roster>();
		try {
			Document doc = Jsoup.connect(url).get();
			Elements table = doc.select("table.tableBody");
			
			for (int h = 0; h < table.size(); h ++){
				// h=0,2 for the current teams data, 1,3 for the opponents data , which we grab later in this process. 
				if(h == 0 || h == 1){
					System.out.println(table.get(h).text());
					Elements trList = table.get(h).select("tr.pncPlayerRow");
					int numRows = 0;
					int numCols = 0;

					for (int i = 0; i < trList.size(); i++){
						Element row = trList.get(i);
						Elements cols = row.children();
						Roster r = new Roster();
						r.Teams_Leagues_LeagueID = leagueID;
						r.WeekID = weekID;
						r.Teams_FFATeamID = teamID;
						r.SeasonID = seasonID;
						r.Teams_Users_UserID = teamID;
						NflTeamNicknames nn = new NflTeamNicknames();
						int colsize = cols.size();
						for (int j = 0; j < cols.size(); j++){
							if (j == 0){
								if (cols.get(j).text().equals("Bench")){
									r.Starter = false;
									r.Slot = "Bench";
								} else if (cols.get(j).text().equals("IR")){
									r.Starter = false;
									r.Slot = "IR";
								} else {
									r.Starter = true;
									r.Slot = cols.get(j).text();
								}
								if (cols.get(j).text().equals("D/ST")){
									//handle what to do with D/ST need to come up with a good strategy for this
									break;
								}
							}
							if (j == 1){
								String pname = cols.get(j).select("a").text();
								System.out.println(pname);
								int pid = getPlayerID(pname);
								System.out.println(pid);
								r.Players_PlayerID = pid;
								String[] sps = cols.get(j).text().split("\\p{Z}");
								if (sps.length > 4){
									String tempInj = null;
									try{
										tempInj = sps[sps.length - 1];
									} catch (Exception e){
										//do nothing, array index out of bounds
									}
									
									if (tempInj != null){
										r.InjuryCode = tempInj;
									} else {
										r.InjuryCode = "N";
									}
								} else {
									r.InjuryCode = "N";
								}
							}
							numCols++;
						}
						//dstlist.add(ws);
						//System.out.println(p.toString());
						numCols = 0;
						numRows++;
						//System.out.println("################################################################");
						//pList.add(p);
						
						//finaly add out player to the list of Players lr
						lr.add(r);
					}
					
				} else {
					//do nothing, we're looking at the opponents Roster lineup for that week
				};
				
			}
			//while ()
			//System.out.println(teamList.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lr;
	}

	private static void storeRoster(Roster r){
		Connection conn = null;
		Statement stmt = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			//buil query
			String sql = "";
			/*
			 * java's string.format method is a not nice, i'm just going to do it manually
			 * sql += "INSERT INTO Players VALUES " + String.format("(%1$i,'%2$s','%3$s','%4$s',%5$b,%6$i);",
					Players.PlayerID,
					Players.Name,
					Players.Position,
					Players.NflTeamName,
					Players.Injured,
					Players.NflTeam_NflTeamID
					);*/
			sql += "INSERT INTO Roster VALUES (" +
					r.Players_PlayerID + "," + 
					r.Teams_FFATeamID + "," + 
					r.Teams_Users_UserID + "," +
					r.Teams_Leagues_LeagueID + "," +
					"'" + r.InjuryCode + "'," +
					"'" + r.Slot + "'," +
					r.WeekID + "," +
					r.Starter + "," +
					r.SeasonID + ");";
			System.out.println(sql);
			stmt.executeUpdate(sql);
			//close up
			stmt.close();
			conn.close();
		}catch(Exception e){
			//Handle errors for JDBC
			e.printStackTrace();
		}
	}
	private static void crawlDST(String url, int week, int year){
		/*List<Players> dstlist = new ArrayList<Players>();
		List<WeeklyScores> wslist = new ArrayList<WeeklyScores>();
		try {
			Document doc = Jsoup.connect(url).get();
			Elements table = doc.select("table");
			Elements trList = table.select("tr.pncPlayerRow");
			int numRows = 0;
			int numCols = 0;

			for (int i = 0; i < trList.size(); i++){
				Element row = trList.get(i);
				Elements cols = row.children();
				WeeklyScores ws = new WeeklyScores();
				NflTeamNicknames nn = new NflTeamNicknames();
				Players p = new Players();
				boolean isPlayer = false;
				int colsize = cols.size();
				for (int j = 0; j < cols.size(); j++){
					if (j == 0){
						Element playername = cols.get(0).select("a.flexpop").first();
						if (playername != null){
							//there might be a better way to assign these two values
							ws.Week = week;
							ws.Season = year;
							//System.out.println(cols.text());
						}
					}
					try {
						if(isPlayer){
							//should really switch this to case:break but w/e
							//System.out.println("j =" + j + "   value= " + cols.get(j).text());
							if (j == 21){
								ws.MiscTDs = Integer.parseInt(cols.get(j).text());
							}
							if (j == 23){
								ws.FantasyPointsScored = Double.parseDouble(cols.get(j).text());
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					numCols++;

				}
				//dstlist.add(ws);
				//System.out.println(p.toString());
				numCols = 0;
				numRows++;
				//System.out.println("################################################################");
				//pList.add(p);
			}
			for (int o = 0; o < pList.size(); o++){
				System.out.println(pList.get(o).toString());
			}
			//while ()
			//System.out.println(teamList.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 */
	}

	private static List<NflTeam> CrawlNflTeams(String url){

		List<NflTeam> NflTeams = new ArrayList<NflTeam>();
		//scrape from espn, store it in a list of objects that mimics the NflTeams table in the db
		try {
			Document doc = Jsoup.connect(url).get();
			//System.out.println(doc.toString());
			Elements teamsTable = doc.select("table");
			Elements teamList = teamsTable.select("tr");
			int numRows = 0;
			int numCols = 0;

			for (int i = 0; i < teamList.size(); i++){
				Element row = teamList.get(i);
				Elements cols = row.children();
				NflTeam temp = new NflTeam();
				boolean isTeam = false;
				for (int j = 0; j < cols.size(); j++){

					Element teamname = cols.get(j).select("span.team-names").first();
					if (teamname != null){
						temp.NFLTeamName = teamname.text();
						temp.NFLTeamID = i;
						//System.out.println("teamname = " + teamname.text());
						isTeam = true;
					}
					if (j == 1 && isTeam){
						temp.Wins = Integer.parseInt(cols.get(j).text());
					}
					if (j == 2 && isTeam){
						temp.Losses = Integer.parseInt(cols.get(j).text());
					}
					if (j == 3 && isTeam){
						temp.Ties = Integer.parseInt(cols.get(j).text());
					}
					numCols++;

				}
				numCols = 0;
				numRows++;
				if (temp.NFLTeamName != null){
					NflTeams.add(temp);
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NflTeams;



	}
	private static List<Players> CrawlPlayers(String url){

		List<Players> pList = new ArrayList<Players>();

		try {
			Document doc = Jsoup.connect(url).get();
			//System.out.println(doc.toString());
			Elements table = doc.select("table");
			Elements trList = table.select("tr");
			int numRows = 0;
			int numCols = 0;

			for (int i = 0; i < trList.size(); i++){
				Element row = trList.get(i);
				Elements cols = row.children();
				Players p = new Players();
				//WeeklyScores ws = new WeeklyScores();
				NflTeamNicknames nn = new NflTeamNicknames();
				//might not need this, but we'll see, link between the tables. i don't know how the
				//java hash lookup functions works on non-primitive object types
				//Map<Players, WeeklyScores> map = new HashMap<Players, WeeklyScores>();
				boolean isPlayer = false;
				for (int j = 0; j < cols.size(); j++){

					Element playername = cols.get(j).select("td.playertablePlayerName").first();
					if (playername != null){
						String[] rName = playername.text().split(",");	
						//TODO: Figure out what to do with D/ST
						if (rName.length <= 1){
							break;
						}
						p.Name= rName[0];
						String fuck = rName[1].trim();
						String[] extra = rName[1].split("\\p{Z}");
						p.NFLTeamName = nn.nicknames.get(extra[1]);
						p.Position = extra[2];
						p.Injured = false;
						p.NFLTeam_NFLTeamID = getNflTeamID(p.NFLTeamName);
						//using java hashcode for playerid right now, can change in the future but
						// idk what else to use. thought it was a good idea
						p.PlayerID = Math.abs(p.Name.hashCode());
					}
					//System.out.println("----------------------------------------");
					numCols++;

				}
				//System.out.println(p.toString());
				numCols = 0;
				numRows++;
				//System.out.println("################################################################");
				pList.add(p);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pList;

	}
	private static List<WeeklyScores> CrawlWeeklyScores(String url, int week, int season){
		List<WeeklyScores> wsList = new ArrayList<WeeklyScores>();
		try {
			Document doc = Jsoup.connect(url).get();
			Elements table = doc.select("table");
			Elements trList = table.select("tr.pncPlayerRow");
			int numRows = 0;
			int numCols = 0;

			for (int i = 0; i < trList.size(); i++){
				Element row = trList.get(i);
				Elements cols = row.children();
				WeeklyScores ws = new WeeklyScores();
				NflTeamNicknames nn = new NflTeamNicknames();
				//might not need this, but we'll see, link between the tables. i don't know how the
				//java hash lookup functions works on non-primitive object types
				//Map<Players, WeeklyScores> map = new HashMap<Players, WeeklyScores>();
				boolean isPlayer = false;
				int colsize = cols.size();
				for (int j = 0; j < cols.size(); j++){
					if (j == 0){
						Element playername = cols.get(0).select("a.flexpop").first();
						if (playername != null){
							isPlayer = true;
							//skipping D/ST for now. sick of parsing strings in hacky code. will revisit
							// now thinking of it, it's probably easier to do those from a separate page
							//TODO: Figure out what to do with D/ST
							try{
								String testing = playername.text().trim();
								int tempID = getPlayerID(testing);
								System.out.println("Player name: " +testing +  "...... player id: " + tempID);
								ws.Players_PlayerID = tempID;
							} catch (Exception e){
								System.out.println(" !!!!!!!!!!!!!!!!!!!!!!!!!!!!!most likely caused by D/ST");
								e.printStackTrace();
							}
							//there might be a better way to assign these two values
							ws.Week = week;
							ws.Season = season;
							//System.out.println(cols.text());
						}
					}

					try {
						if(isPlayer){
							//should really switch this to case:break but w/e
							//System.out.println("j =" + j + "   value= " + cols.get(j).text());
							if (j == 5){
								//passing c/a
								String[] passing = cols.get(j).text().split("/");
								ws.PassingCompletions = Integer.parseInt(passing[0]);
								ws.PassingAttempts = Integer.parseInt(passing[1]);
							}
							if (j == 6){
								ws.PassingAttempts = Integer.parseInt(cols.get(j).text());
							}
							if (j == 7){
								ws.PassingTDs = Integer.parseInt(cols.get(j).text());
							}
							if (j == 8){
								ws.Interceptions = Integer.parseInt(cols.get(j).text());
							}
							if (j == 10){
								ws.RushingAttempts = Integer.parseInt(cols.get(j).text());
							}
							if (j == 11){
								ws.RushingYards = Integer.parseInt(cols.get(j).text());
							}
							if (j == 12){
								ws.RushingTDs = Integer.parseInt(cols.get(j).text());
							}
							if (j == 14){
								ws.Receptions = Integer.parseInt(cols.get(j).text());
							}
							if (j == 15){
								ws.ReceivingYards = Integer.parseInt(cols.get(j).text());
							}
							if (j == 16){
								ws.ReceivingTDs = Integer.parseInt(cols.get(j).text());
							}
							if (j == 17){
								ws.ReceivingTargets = Integer.parseInt(cols.get(j).text());
							}
							if (j == 19){
								ws.Misc2PC = Integer.parseInt(cols.get(j).text());
							}
							if (j == 20){
								ws.MiscFumbles = Integer.parseInt(cols.get(j).text());
							}
							if (j == 21){
								ws.MiscTDs = Integer.parseInt(cols.get(j).text());
							}
							if (j == 23){
								ws.FantasyPointsScored = Double.parseDouble(cols.get(j).text());
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					numCols++;

				}
				wsList.add(ws);
				//System.out.println(p.toString());
				numCols = 0;
				numRows++;
				//System.out.println("################################################################");
				//pList.add(p);
			}
			/*for (int o = 0; o < pList.size(); o++){
				System.out.println(pList.get(o).toString());
			}*/
			//while ()
			//System.out.println(teamList.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wsList;
	}
	public static void CrawlLeague(String url, int LeagueID){

	}
	//method to grab NflTeamid from the nfl table based on the full nfl team name
	private static int getNflTeamID(String teamNameFull){
		//connect to db and grab the nfl team id to assign it in the Players crawl
		Connection nConn = null;
		Statement nStmt = null;
		try{

			Class.forName("com.mysql.jdbc.Driver");
			nConn = DriverManager.getConnection(DB_URL,USER,PASS);
			nStmt = nConn.createStatement();
			//select on team name, given team id
			ResultSet rs = nStmt.executeQuery("SELECT NflTeamID FROM NflTeam WHERE NflTeamName = '" + teamNameFull + "';");
			int id = -1;
			while(rs.next()){
				id = rs.getInt("NflTeamID");
			}
			nConn.close();
			rs.close();
			if (id > 0){
				return id;
			} else return 0;

		} catch (Exception e){
			e.printStackTrace();

			return 0;
		} finally{

		}
	}
	private static void storeNflTeams(List<NflTeam> NflTeams){
		//insert our list of clean values into the localdb

		Connection conn = null;
		Statement stmt = null;

		//testing inserting nfl teams into the NflTeams table

		try{
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			//STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql = "";
			sql += "INSERT INTO NflTeam VALUES";
			//build the large insert query
			for (int o = 0; o < NflTeams.size(); o++){
				//System.out.println(NflTeams.get(o).toString());

				sql += "(" + NflTeams.get(o).NFLTeamID + ",'" + 
						NflTeams.get(o).NFLTeamName + "'," +
						NflTeams.get(o).Wins + "," + 
						NflTeams.get(o).Losses + "," + 
						NflTeams.get(o).Ties + "),";
				if (o == (NflTeams.size() - 1)){
					sql = sql.substring(0, sql.length() - 1) + ";";
				}

			}
			//System.out.println(sql);
			stmt.executeUpdate(sql);
			//rs.close();
			System.out.println("finished insert");
			stmt.close();
			conn.close();
		}catch(Exception e){
			//Handle errors for JDBC
			e.printStackTrace();
		}
	}
	private static void storePlayer(Players p){

		Connection conn = null;
		Statement stmt = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			//buil query
			String sql = "";
			/*
			 * java's string.format method is a not nice, i'm just going to do it manually
			 * sql += "INSERT INTO Players VALUES " + String.format("(%1$i,'%2$s','%3$s','%4$s',%5$b,%6$i);",
					Players.PlayerID,
					Players.Name,
					Players.Position,
					Players.NflTeamName,
					Players.Injured,
					Players.NflTeam_NflTeamID
					);*/
			sql += "INSERT INTO Players VALUES (" +
					p.PlayerID + "," + 
					"'" + p.Name + "'," + 
					"'" + p.Position + "'," +
					"'" + p.NFLTeamName + "'," +
					p.Injured + "," +
					p.NFLTeam_NFLTeamID + ");";
			System.out.println(sql);
			stmt.executeUpdate(sql);
			//close up
			stmt.close();
			conn.close();
		}catch(Exception e){
			//Handle errors for JDBC
			e.printStackTrace();
		}
	}
	private static void storeWeeklyScores(WeeklyScores ws) throws SQLException{
		Connection conn = null;
		Statement stmt = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			//buil query
			String sql = "";
			sql += "INSERT INTO WeeklyScores VALUES (" +
					ws.Week + "," + 
					ws.Season + "," + 
					ws.PassingCompletions + "," + 
					ws.PassingAttempts + "," + 
					ws.PassingYards + "," + 
					ws.PassingTDs + "," + 
					ws.Interceptions + "," + 
					ws.RushingAttempts + "," + 
					ws.RushingYards + "," + 
					ws.RushingTDs + "," + 
					ws.Receptions + "," + 
					ws.ReceivingYards + "," + 
					ws.ReceivingTDs + "," +
					ws.ReceivingTargets + "," + 
					ws.Misc2PC + "," + 
					ws.MiscFumbles + "," + 
					ws.MiscTDs + "," + 
					ws.FantasyPointsScored + "," + 
					ws.Players_PlayerID + ");"; 


			System.out.println(sql);
			stmt.executeUpdate(sql);
			//close up
			stmt.close();
			conn.close();
		}catch(Exception e){
			//Handle errors for JDBC
			e.printStackTrace();
		} finally{
			conn.close();
			stmt.close();
		}

	}

	private static void storeDST(WeeklyScores ws, Players p ){


	}
	//gets the player ID from the Players table given the player name in string form
	private static int getPlayerID(String name){
		//this and getNflTeamID can probably be combined to one method, but don't want to do it right now
		// ideas for the method are to pass in the values that make the queries unique, eg nfl team name
		// player name, and return the ID of that player, its all simple select X from Y where Z sql
		Connection pConn = null;
		Statement pStmt = null;
		try{

			Class.forName("com.mysql.jdbc.Driver");
			pConn = DriverManager.getConnection(DB_URL,USER,PASS);
			pStmt = pConn.createStatement();
			//select on team name, given team id

			String sql = "SELECT PlayerID FROM Players WHERE Name = '" + name + "';";
			System.out.println(sql);
			ResultSet rs = pStmt.executeQuery("SELECT PlayerID FROM Players WHERE Name = '" + name + "';");
			int id = -1;
			while(rs.next()){
				id = rs.getInt("PlayerID");
			}
			pConn.close();
			rs.close();
			if (id > 0){
				return id;
			} else return 0;

		} catch (Exception e){
			e.printStackTrace();

			return 0;
		} finally{

		}
	}

}
