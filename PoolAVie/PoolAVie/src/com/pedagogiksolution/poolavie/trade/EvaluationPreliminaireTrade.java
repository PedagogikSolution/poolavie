package com.pedagogiksolution.poolavie.trade;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pedagogiksolution.poolavie.utils.DatabaseConnector;

public class EvaluationPreliminaireTrade {
	
	DatabaseConnector dbHelper;
	Connection conn;
	String statement1,statement2,statement3,statement4;
	ResultSet rs1,rs2,rs3,rs4;
	
	public EvaluationPreliminaireTrade() {
		
	}

	public boolean evaluationResult(int team_id_1, int team_id_2, String[] player_trade_by,int cash_trade_by,String[] pick_trade_by,
			String[] player_trade_for,int cash_trade_for,String[] pick_trade_for) {
		
		
		int nb_att_team_1 = 0;
		int nb_att_team_2 = 0;
		int nb_def_team_1 = 0;
		int nb_def_team_2 = 0;
		int nb_goal_team_1 = 0;
		int nb_goal_team_2 = 0;
		
		int nb_att_team_1_before = 0;
		int nb_att_team_2_before = 0;
		int nb_def_team_1_before = 0;
		int nb_def_team_2_before = 0;
		int nb_goal_team_1_before = 0;
		int nb_goal_team_2_before = 0;
		
		int plafond_team_1 = 0;
		int plafond_team_2 = 0;
		int masse_team_1 = 0;
		int masse_team_2 = 0;
		
		int cash_recu_team_1 = 0;
		int cash_recu_team_2 = 0;
		
		

		
		statement1 = "SELECT * FROM equipes WHERE team_id=" + team_id_2;
		
		statement2 = "SELECT * FROM equipes WHERE team_id=" + team_id_1;
		
		
		/*** vérification si argent versus pick uniquement  ***/
		if((player_trade_by==null && player_trade_for==null)||(cash_trade_by>0&&cash_trade_for>0)){
			return true;
			
		/*** vérification si minimum de joueur par position  ***/	
		
		} else {
					
		dbHelper = new DatabaseConnector();
		conn = dbHelper.open();
		
		if(conn != null){
			
			if(player_trade_for!=null){
			/* vérification si joueurs existe dans team + récupération du nombre de joueurs par position ajouter lors de la transaction */
			for (String s: player_trade_for)
		    {
				
				statement3 = "SELECT * FROM players WHERE _id=" + s;
				try {
					rs3 = conn.createStatement().executeQuery(statement3);
					
					if (rs3.next()) {
					
						String mPosition = rs3.getString("position");
						int mSalaire = rs3.getInt("salaire_contrat");
						
						cash_recu_team_1 = cash_recu_team_1 + mSalaire;
						
						if (mPosition.equalsIgnoreCase("attaquant")){
							nb_att_team_1 = nb_att_team_1 +1;
						} else if (mPosition.equalsIgnoreCase("defenseur")) {
							nb_def_team_1 = nb_def_team_1 +1;
						} else  {
							nb_goal_team_1 = nb_goal_team_1 +1;
						}
					} else {
						
						return true;
					}
					
					
					
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				
		    /** fin de la boucle for **/ 
		    }
			/** fin du if player exist **/
			}
			
			if(player_trade_by!=null){
			for (String s: player_trade_by)
		    {
				
				statement4 = "SELECT * FROM players WHERE _id=" + s;
				try {
					rs4 = conn.createStatement().executeQuery(statement4);
				
					if (rs4.next()) {
						
						String mPosition = rs4.getString("position");
						int mSalaire = rs4.getInt("salaire_contrat");
						
						cash_recu_team_2 = cash_recu_team_2 + mSalaire;
						
						if (mPosition.equalsIgnoreCase("attaquant")){
							nb_att_team_2 = nb_att_team_2 +1;
						} else if (mPosition.equalsIgnoreCase("defenseur")) {
							nb_def_team_2 = nb_def_team_2 +1;
						} else  {
							nb_goal_team_2 = nb_goal_team_2 +1;
						}
					} else {
						
						return true;
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		    /** fin de la boucle for **/ 
		    }	
			/** fin du if player_trade_by exist **/
			}
		
		cash_recu_team_1 = cash_recu_team_1 + cash_trade_for;
				
				
		
		cash_recu_team_2 = cash_recu_team_2 + cash_trade_by;
				
		
		
		
		try {
			rs1 = conn.createStatement().executeQuery(statement1);
		
			if (rs1.next()) {
			
				nb_att_team_1_before = rs1.getInt("nb_attaquant");
				nb_def_team_1_before = rs1.getInt("nb_defenseur");
				nb_goal_team_1_before = rs1.getInt("nb_gardien");
				
				plafond_team_1 = rs1.getInt("max_salaire_begin");
				masse_team_1 = rs1.getInt("total_salaire_now");
			
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs2 = conn.createStatement().executeQuery(statement2);
			
			if (rs2.next()) {
				
				nb_att_team_2_before = rs2.getInt("nb_attaquant");
				nb_def_team_2_before = rs2.getInt("nb_defenseur");
				nb_goal_team_2_before = rs2.getInt("nb_gardien");
				
				plafond_team_2 = rs2.getInt("max_salaire_begin");
				masse_team_2 = rs2.getInt("total_salaire_now");
				
				
			}
			
									
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		nb_att_team_1_before = nb_att_team_1_before - nb_att_team_2 + nb_att_team_1;
		nb_def_team_1_before = nb_def_team_1_before - nb_def_team_2 + nb_def_team_1;
		nb_goal_team_1_before = nb_goal_team_1_before - nb_goal_team_2 + nb_goal_team_1;
		nb_att_team_2_before = nb_att_team_2_before - nb_att_team_1 + nb_att_team_2 ;
		nb_def_team_2_before = nb_def_team_2_before - nb_def_team_1 + nb_def_team_2;
		nb_goal_team_2_before = nb_goal_team_2_before - nb_goal_team_1 + nb_goal_team_2;
		
		if(nb_att_team_1_before<8 || nb_att_team_2_before<8 || nb_def_team_1_before<5 || nb_def_team_2_before<5 || nb_goal_team_1_before<2 || nb_goal_team_2_before<2 ){
			return true;
		}
		
		masse_team_1 = masse_team_1 + cash_recu_team_1 - cash_recu_team_2;
		
		if(masse_team_1>plafond_team_1){
			return true;
		}
		
		masse_team_2 = masse_team_2 + cash_recu_team_2 - cash_recu_team_1;
		
		if(masse_team_2>plafond_team_2){
			return true;
		}
		
			
			
		/* fin du if conn !=null */	
		}
		
		dbHelper.close(conn);
		/* fin du else */
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		return false;
	}

}
