package com.pedagogiksolution.poolavie.update;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import com.pedagogiksolution.poolavie.utils.DatabaseConnector;

public class UpdatePlayersStats {

	DatabaseConnector dbHelper;
	Connection conn;
	String statement11,statement22,statement33,statement44;
	int pts_att_0,pts_def_0,pts_goal_0,pts_total_0;
	int pj_att_0,pj_def_0,pj_goal_0,pj_total_0;
	int but_att_0,but_def_0,but_goal_0,but_total_0;
	int passe_att_0,passe_def_0,passe_goal_0,passe_total_0;
	ResultSet rs11,rs22,rs33;
	double moyenne_pts;
	
	
	public void updateClassement() {
		
		dbHelper = new DatabaseConnector();
		
		conn = dbHelper.open();
		try {
			
			try {
				
				for(int i=0; i<10; i++){	
			
		statement11 = "SELECT SUM(sub_att.pts) AS pts,SUM(sub_att.pj) AS pj,SUM(sub_att.but_victoire) AS but,SUM(sub_att.aide_overtime) AS passe FROM (SELECT * FROM players WHERE position='attaquant' AND team_id=" + i + " AND club_ecole=0 ORDER BY pts DESC LIMIT 8) AS sub_att";
		statement22 = "SELECT SUM(sub_att.pts) AS pts,SUM(sub_att.pj) AS pj,SUM(sub_att.but_victoire) AS but,SUM(sub_att.aide_overtime) AS passe FROM (SELECT * FROM players WHERE position='defenseur' AND team_id=" + i + " AND club_ecole=0 ORDER BY pts DESC LIMIT 5) AS sub_att";
		statement33 = "SELECT SUM(sub_att.pts) AS pts,SUM(sub_att.pj) AS pj,SUM(sub_att.but_victoire) AS but,SUM(sub_att.aide_overtime) AS passe FROM (SELECT * FROM players WHERE position='gardien' AND team_id=" + i + " AND club_ecole=0 ORDER BY pts DESC LIMIT 2) AS sub_att";
		rs11 = conn.createStatement().executeQuery(statement11);
		if (rs11.next()) {
			pts_att_0 = rs11.getInt(1);
			pj_att_0 = rs11.getInt(2);
			but_att_0 = rs11.getInt(3);
			passe_att_0 = rs11.getInt(4);
		}
		rs11.close();

		rs22 = conn.createStatement().executeQuery(statement22);
		if (rs22.next()) {
			pts_def_0 = rs22.getInt(1);
			pj_def_0 = rs22.getInt(2);
			but_def_0 = rs22.getInt(3);
			passe_def_0 = rs22.getInt(4);
		}
		rs22.close();
		rs33 = conn.createStatement().executeQuery(statement33);
		if (rs33.next()) {
			pts_goal_0 = rs33.getInt(1);
			pj_goal_0 = rs33.getInt(2);
			but_goal_0 = rs33.getInt(3);
			passe_goal_0 = rs33.getInt(4);
		}
		rs33.close();
		pts_total_0 = pts_att_0 + pts_def_0 + pts_goal_0;
		pj_total_0 = pj_att_0 + pj_def_0 + pj_goal_0;
		but_total_0 = but_att_0 + but_def_0;
		passe_total_0 = passe_att_0 + passe_def_0;
		
		moyenne_pts = (double)((double)pts_total_0/(double)pj_total_0);
		DecimalFormat df = new DecimalFormat("0.00");
	    String moyenne = df.format(moyenne_pts);
	    
		

		statement44 = "UPDATE classement SET moyenne = '" + moyenne + "', points=" + pts_total_0
				+ ", pj=" + pj_total_0
				+ ", but=" + but_total_0
				+ ", passe=" + passe_total_0
				+ " WHERE team_id=" + i + "";

		conn.createStatement().executeUpdate(statement44);
		
		
		}
		
				
		}finally {
			dbHelper.close(conn);
		}

		}catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
