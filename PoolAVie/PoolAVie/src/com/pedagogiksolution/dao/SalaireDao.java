package com.pedagogiksolution.dao;



public interface SalaireDao {
    
    void createSalaireTable(int poolID) throws DAOException;

	int getSalaireTable(int poolId, String m_position, int m_pts) throws DAOException;
    
    

}
