package com.pedagogiksolution.dao;

import java.sql.ResultSet;
import java.util.List;

import com.pedagogiksolution.datastorebeans.Pool;


public interface DraftDao {
    
    void createDraftTable(int poolID) throws DAOException;
    void createDraftArchiveTable(int poolID) throws DAOException;
    void populateFirstYearsDraft(int poolID, List<Integer> permutation, Pool mBeanPool) throws DAOException;
    ResultSet getDraftRoundOrder(int poolId)throws DAOException;
    
    

}
