package com.pedagogiksolution.dao;


public interface DraftDao {
    
    void createDraftTable(int poolID) throws DAOException;
    void createDraftArchiveTable(int poolID) throws DAOException;
    
    

}
