package com.pedagogiksolution.dao;

import java.util.List;

import com.pedagogiksolution.datastorebeans.DraftRound;
import com.pedagogiksolution.datastorebeans.Pool;


public interface DraftDao {
    
    void createDraftTable(int poolID) throws DAOException;
    void createDraftArchiveTable(int poolID) throws DAOException;
    void populateFirstYearsDraft(int poolID, List<Integer> permutation, Pool mBeanPool) throws DAOException;
    DraftRound setDraftRoundOrder(int poolId)throws DAOException;
    
    

}
