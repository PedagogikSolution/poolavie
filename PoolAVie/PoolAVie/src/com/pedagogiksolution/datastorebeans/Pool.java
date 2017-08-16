package com.pedagogiksolution.datastorebeans;

import java.io.Serializable;

import com.google.appengine.api.datastore.Entity;

public class Pool implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5746403353888742475L;

	private String poolID;
	private String poolName;
	private int poolType;
	private int draftType;
	private int tradeType;
	private int numberTeam;
	private int poolYear;
	private int cycleAnnuel;
	private String creationDate;
	private int numTeamCreate;
	private String nomTeam1;
	private String nomTeam2;
	private String nomTeam3;
	private String nomTeam4;
	private String nomTeam5;
	private String nomTeam6;
	private String nomTeam7;
	private String nomTeam8;
	private String nomTeam9;
	private String nomTeam10;
	private String nomTeam11;
	private String nomTeam12;
	private String logoTeam1;
	private String logoTeam2;
	private String logoTeam3;
	private String logoTeam4;
	private String logoTeam5;
	private String logoTeam6;
	private String logoTeam7;
	private String logoTeam8;
	private String logoTeam9;
	private String logoTeam10;
	private String logoTeam11;
	private String logoTeam12;
	private String derniereMAJ;
	private String codeValidationPool;
	private String draftDate;
	private String firstYear;
	private String thisYear;
	private String secondYear;
	private String thirdYear;
	private String fourthYear;
	private String fifthYear;

	public String getPoolID() {
		return poolID;
	}

	public void setPoolID(String poolID) {
		this.poolID = poolID;
	}

	public int getPoolType() {
		return poolType;
	}

	public void setPoolType(int poolType) {
		this.poolType = poolType;
	}

	public String getPoolName() {
		return poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

	public int getDraftType() {
		return draftType;
	}

	public void setDraftType(int draftType) {
		this.draftType = draftType;
	}

	public int getTradeType() {
		return tradeType;
	}

	public void setTradeType(int tradeType) {
		this.tradeType = tradeType;
	}

	public int getNumberTeam() {
		return numberTeam;
	}

	public void setNumberTeam(int numberTeam) {
		this.numberTeam = numberTeam;
	}

	public int getPoolYear() {
		return poolYear;
	}

	public void setPoolYear(int poolYear) {
		this.poolYear = poolYear;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public int getNumTeamCreate() {
		return numTeamCreate;
	}

	public void setNumTeamCreate(int numTeamCreate) {
		this.numTeamCreate = numTeamCreate;
	}

	public String getNomTeam1() {
		return nomTeam1;
	}

	public void setNomTeam1(String nomTeam1) {
		this.nomTeam1 = nomTeam1;
	}

	public String getNomTeam2() {
		return nomTeam2;
	}

	public void setNomTeam2(String nomTeam2) {
		this.nomTeam2 = nomTeam2;
	}

	public String getNomTeam3() {
		return nomTeam3;
	}

	public void setNomTeam3(String nomTeam3) {
		this.nomTeam3 = nomTeam3;
	}

	public String getNomTeam4() {
		return nomTeam4;
	}

	public void setNomTeam4(String nomTeam4) {
		this.nomTeam4 = nomTeam4;
	}

	public String getNomTeam5() {
		return nomTeam5;
	}

	public void setNomTeam5(String nomTeam5) {
		this.nomTeam5 = nomTeam5;
	}

	public String getNomTeam6() {
		return nomTeam6;
	}

	public void setNomTeam6(String nomTeam6) {
		this.nomTeam6 = nomTeam6;
	}

	public String getNomTeam7() {
		return nomTeam7;
	}

	public void setNomTeam7(String nomTeam7) {
		this.nomTeam7 = nomTeam7;
	}

	public String getNomTeam8() {
		return nomTeam8;
	}

	public void setNomTeam8(String nomTeam8) {
		this.nomTeam8 = nomTeam8;
	}

	public String getNomTeam9() {
		return nomTeam9;
	}

	public void setNomTeam9(String nomTeam9) {
		this.nomTeam9 = nomTeam9;
	}

	public String getNomTeam10() {
		return nomTeam10;
	}

	public void setNomTeam10(String nomTeam10) {
		this.nomTeam10 = nomTeam10;
	}

	public String getNomTeam11() {
		return nomTeam11;
	}

	public void setNomTeam11(String nomTeam11) {
		this.nomTeam11 = nomTeam11;
	}

	public String getNomTeam12() {
		return nomTeam12;
	}

	public void setNomTeam12(String nomTeam12) {
		this.nomTeam12 = nomTeam12;
	}

	public String getDerniereMAJ() {
		return derniereMAJ;
	}

	public void setDerniereMAJ(String derniereMAJ) {
		this.derniereMAJ = derniereMAJ;
	}

	public String getCodeValidationPool() {
		return codeValidationPool;
	}

	public void setCodeValidationPool(String codeValidationPool) {
		this.codeValidationPool = codeValidationPool;
	}

	public int getCycleAnnuel() {
		return cycleAnnuel;
	}

	public void setCycleAnnuel(int cycleAnnuel) {
		this.cycleAnnuel = cycleAnnuel;
	}

	public String getDraftDate() {
		return draftDate;
	}

	public void setDraftDate(String dt) {
		this.draftDate = dt;
	}

	public String getFirstYear() {
		return firstYear;
	}

	public void setFirstYear(String firstYear) {
		this.firstYear = firstYear;
	}

	public String getThisYear() {
		return thisYear;
	}

	public void setThisYear(String thisYear) {
		this.thisYear = thisYear;
	}

	public String getLogoTeam1() {
		return logoTeam1;
	}

	public void setLogoTeam1(String logoTeam1) {
		this.logoTeam1 = logoTeam1;
	}

	public String getLogoTeam2() {
		return logoTeam2;
	}

	public void setLogoTeam2(String logoTeam2) {
		this.logoTeam2 = logoTeam2;
	}

	public String getLogoTeam3() {
		return logoTeam3;
	}

	public void setLogoTeam3(String logoTeam3) {
		this.logoTeam3 = logoTeam3;
	}

	public String getLogoTeam4() {
		return logoTeam4;
	}

	public void setLogoTeam4(String logoTeam4) {
		this.logoTeam4 = logoTeam4;
	}

	public String getLogoTeam5() {
		return logoTeam5;
	}

	public void setLogoTeam5(String logoTeam5) {
		this.logoTeam5 = logoTeam5;
	}

	public String getLogoTeam6() {
		return logoTeam6;
	}

	public void setLogoTeam6(String logoTeam6) {
		this.logoTeam6 = logoTeam6;
	}

	public String getLogoTeam7() {
		return logoTeam7;
	}

	public void setLogoTeam7(String logoTeam7) {
		this.logoTeam7 = logoTeam7;
	}

	public String getLogoTeam8() {
		return logoTeam8;
	}

	public void setLogoTeam8(String logoTeam8) {
		this.logoTeam8 = logoTeam8;
	}

	public String getLogoTeam9() {
		return logoTeam9;
	}

	public void setLogoTeam9(String logoTeam9) {
		this.logoTeam9 = logoTeam9;
	}

	public String getLogoTeam10() {
		return logoTeam10;
	}

	public void setLogoTeam10(String logoTeam10) {
		this.logoTeam10 = logoTeam10;
	}

	public String getLogoTeam11() {
		return logoTeam11;
	}

	public void setLogoTeam11(String logoTeam11) {
		this.logoTeam11 = logoTeam11;
	}

	public String getLogoTeam12() {
		return logoTeam12;
	}

	public void setLogoTeam12(String logoTeam12) {
		this.logoTeam12 = logoTeam12;
	}

	public String getSecondYear() {
		return secondYear;
	}

	public void setSecondYear(String secondYear) {
		this.secondYear = secondYear;
	}

	public String getThirdYear() {
		return thirdYear;
	}

	public void setThirdYear(String thirdYear) {
		this.thirdYear = thirdYear;
	}

	public String getFourthYear() {
		return fourthYear;
	}

	public void setFourthYear(String fourthYear) {
		this.fourthYear = fourthYear;
	}

	public String getFifthYear() {
		return fifthYear;
	}

	public void setFifthYear(String fifthYear) {
		this.fifthYear = fifthYear;
	}

	public Pool mapPoolFromDatastore(Entity mEntity, Pool mBeanPool) {

		String codeValidationPool = (String) mEntity.getProperty("codeValidationPool");
		mBeanPool.setCodeValidationPool(codeValidationPool);

		String creationDate = (String) mEntity.getProperty("creationDate");
		mBeanPool.setCreationDate(creationDate);

		Long cycleAnnuel_long = (Long) mEntity.getProperty("cycleAnnuel");
		int cycleAnnuel = cycleAnnuel_long.intValue();
		mBeanPool.setCycleAnnuel(cycleAnnuel);

		String derniereMAJ = (String) mEntity.getProperty("derniereMAJ");
		mBeanPool.setDerniereMAJ(derniereMAJ);

		String draftDate = (String) mEntity.getProperty("draftDate");
		mBeanPool.setDraftDate(draftDate);

		Long draftType_long = (Long) mEntity.getProperty("draftType");
		int draftType = draftType_long.intValue();
		mBeanPool.setDraftType(draftType);

		String fifthYear = (String) mEntity.getProperty("fifthYear");
		mBeanPool.setFifthYear(fifthYear);

		String firstYear = (String) mEntity.getProperty("firstYear");
		mBeanPool.setFirstYear(firstYear);

		String fourthYear = (String) mEntity.getProperty("fourthYear");
		mBeanPool.setFourthYear(fourthYear);

		String logoTeam1 = (String) mEntity.getProperty("logoTeam1");
		mBeanPool.setLogoTeam1(logoTeam1);

		String logoTeam2 = (String) mEntity.getProperty("logoTeam2");
		mBeanPool.setLogoTeam2(logoTeam2);

		String logoTeam3 = (String) mEntity.getProperty("logoTeam3");
		mBeanPool.setLogoTeam3(logoTeam3);

		String logoTeam4 = (String) mEntity.getProperty("logoTeam4");
		mBeanPool.setLogoTeam4(logoTeam4);

		String logoTeam5 = (String) mEntity.getProperty("logoTeam5");
		mBeanPool.setLogoTeam5(logoTeam5);

		String logoTeam6 = (String) mEntity.getProperty("logoTeam6");
		mBeanPool.setLogoTeam6(logoTeam6);

		String logoTeam7 = (String) mEntity.getProperty("logoTeam7");
		mBeanPool.setLogoTeam7(logoTeam7);

		String logoTeam8 = (String) mEntity.getProperty("logoTeam8");
		mBeanPool.setLogoTeam8(logoTeam8);

		String logoTeam9 = (String) mEntity.getProperty("logoTeam9");
		mBeanPool.setLogoTeam9(logoTeam9);

		String logoTeam10 = (String) mEntity.getProperty("logoTeam10");
		mBeanPool.setLogoTeam10(logoTeam10);

		String logoTeam11 = (String) mEntity.getProperty("logoTeam11");
		mBeanPool.setLogoTeam11(logoTeam11);

		String logoTeam12 = (String) mEntity.getProperty("logoTeam12");
		mBeanPool.setLogoTeam12(logoTeam12);

		String nomTeam1 = (String) mEntity.getProperty("nomTeam1");
		mBeanPool.setNomTeam1(nomTeam1);

		String nomTeam2 = (String) mEntity.getProperty("nomTeam2");
		mBeanPool.setNomTeam2(nomTeam2);

		String nomTeam3 = (String) mEntity.getProperty("nomTeam3");
		mBeanPool.setNomTeam3(nomTeam3);

		String nomTeam4 = (String) mEntity.getProperty("nomTeam4");
		mBeanPool.setNomTeam4(nomTeam4);

		String nomTeam5 = (String) mEntity.getProperty("nomTeam5");
		mBeanPool.setNomTeam5(nomTeam5);

		String nomTeam6 = (String) mEntity.getProperty("nomTeam6");
		mBeanPool.setNomTeam6(nomTeam6);

		String nomTeam7 = (String) mEntity.getProperty("nomTeam7");
		mBeanPool.setNomTeam7(nomTeam7);

		String nomTeam8 = (String) mEntity.getProperty("nomTeam8");
		mBeanPool.setNomTeam8(nomTeam8);

		String nomTeam9 = (String) mEntity.getProperty("nomTeam9");
		mBeanPool.setNomTeam9(nomTeam9);

		String nomTeam10 = (String) mEntity.getProperty("nomTeam10");
		mBeanPool.setNomTeam10(nomTeam10);

		String nomTeam11 = (String) mEntity.getProperty("nomTeam11");
		mBeanPool.setNomTeam11(nomTeam11);

		String nomTeam12 = (String) mEntity.getProperty("nomTeam12");
		mBeanPool.setNomTeam12(nomTeam12);

		Long numberTeam_long = (Long) mEntity.getProperty("numberTeam");
		int numberTeam = numberTeam_long.intValue();
		mBeanPool.setNumberTeam(numberTeam);

		Long numTeamCreate_long = (Long) mEntity.getProperty("numTeamCreate");
		int numTeamCreate = numTeamCreate_long.intValue();
		mBeanPool.setNumTeamCreate(numTeamCreate);

		String poolID = (String) mEntity.getKey().getName();
		mBeanPool.setPoolID(poolID);

		String poolName = (String) mEntity.getProperty("poolName");
		mBeanPool.setPoolName(poolName);

		Long poolType_long = (Long) mEntity.getProperty("poolType");
		int poolType = poolType_long.intValue();
		mBeanPool.setPoolType(poolType);

		Long poolYear_long = (Long) mEntity.getProperty("poolYear");
		int poolYear = poolYear_long.intValue();
		mBeanPool.setPoolYear(poolYear);

		String secondYear = (String) mEntity.getProperty("secondYear");
		mBeanPool.setSecondYear(secondYear);

		String thirdYear = (String) mEntity.getProperty("thirdYear");
		mBeanPool.setThirdYear(thirdYear);

		String thisYear = (String) mEntity.getProperty("thisYear");
		mBeanPool.setThisYear(thisYear);

		Long tradeType_long = (Long) mEntity.getProperty("tradeType");
		int tradeType = tradeType_long.intValue();
		mBeanPool.setTradeType(tradeType);

		return mBeanPool;
	}

	public Entity mapBeanToEntityForDatastore(Pool mBean, String name) {

		Entity mEntity = new Entity("Pool", name);

		mEntity.setProperty("codeValidationPool", mBean.getCodeValidationPool());

		mEntity.setProperty("creationDate", mBean.getCreationDate());

		mEntity.setProperty("cycleAnnuel", mBean.getCycleAnnuel());

		mEntity.setProperty("derniereMAJ", mBean.getDerniereMAJ());

		mEntity.setProperty("draftDate", mBean.getDraftDate());

		mEntity.setProperty("draftType", mBean.getDraftType());

		mEntity.setProperty("fifthYear", mBean.getFifthYear());

		mEntity.setProperty("firstYear", mBean.getFirstYear());

		mEntity.setProperty("fourthYear", mBean.getFourthYear());

		mEntity.setProperty("logoTeam1", mBean.getLogoTeam1());

		mEntity.setProperty("logoTeam2", mBean.getLogoTeam2());

		mEntity.setProperty("logoTeam3", mBean.getLogoTeam3());

		mEntity.setProperty("logoTeam4", mBean.getLogoTeam4());

		mEntity.setProperty("logoTeam5", mBean.getLogoTeam5());

		mEntity.setProperty("logoTeam6", mBean.getLogoTeam6());

		mEntity.setProperty("logoTeam7", mBean.getLogoTeam7());

		mEntity.setProperty("logoTeam8", mBean.getLogoTeam8());

		mEntity.setProperty("logoTeam9", mBean.getLogoTeam9());

		mEntity.setProperty("logoTeam10", mBean.getLogoTeam10());

		mEntity.setProperty("logoTeam11", mBean.getLogoTeam11());

		mEntity.setProperty("logoTeam12", mBean.getLogoTeam12());

		mEntity.setProperty("nomTeam1", mBean.getNomTeam1());

		mEntity.setProperty("nomTeam2", mBean.getNomTeam2());

		mEntity.setProperty("nomTeam3", mBean.getNomTeam3());

		mEntity.setProperty("nomTeam4", mBean.getNomTeam4());

		mEntity.setProperty("nomTeam5", mBean.getNomTeam5());

		mEntity.setProperty("nomTeam6", mBean.getNomTeam6());

		mEntity.setProperty("nomTeam7", mBean.getNomTeam7());

		mEntity.setProperty("nomTeam8", mBean.getNomTeam8());

		mEntity.setProperty("nomTeam9", mBean.getNomTeam9());

		mEntity.setProperty("nomTeam10", mBean.getNomTeam10());

		mEntity.setProperty("nomTeam11", mBean.getNomTeam11());

		mEntity.setProperty("nomTeam12", mBean.getNomTeam12());

		mEntity.setProperty("numberTeam", mBean.getNumberTeam());

		mEntity.setProperty("numTeamCreate", mBean.getNumTeamCreate());

		mEntity.setProperty("poolName", mBean.getPoolName());

		mEntity.setProperty("poolType", mBean.getPoolType());

		mEntity.setProperty("poolYear", mBean.getPoolYear());

		mEntity.setProperty("secondYear", mBean.getSecondYear());
		
		mEntity.setProperty("thirdYear", mBean.getThirdYear());

		mEntity.setProperty("thisYear", mBean.getThisYear());

		mEntity.setProperty("tradeType", mBean.getTradeType());


		return mEntity;
	}

}
