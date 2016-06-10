package com.pedagogiksolution.dao;

import java.util.List;


public interface DraftDao {
    
    void createDraftTable(int poolID) throws DAOException;
    void createDraftArchiveTable(int poolID) throws DAOException;
    void populateFirstYearsDraft(int poolID, List<Integer> permutation) throws DAOException;
    
    

}
