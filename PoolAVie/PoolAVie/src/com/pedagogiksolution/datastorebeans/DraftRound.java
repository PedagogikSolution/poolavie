package com.pedagogiksolution.datastorebeans;

import java.io.Serializable;
import java.util.List;

import com.google.appengine.api.datastore.Entity;

public class DraftRound implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4099491969522931254L;

	private String poolId;
	private List<Long> draft_pick_no;
	private List<String> equipe;
	private List<Long> ronde;
	private List<Long> team_id;
	private List<String> from_who;
	private List<Long> team_id_from;
	private List<Long> team_count;
	private List<Long> follow_up;
	private List<String> player_drafted;
	private List<Long> year_of_draft;

	public String getPoolId() {
		return poolId;
	}

	public void setPoolId(String poolId) {
		this.poolId = poolId;
	}

	public List<Long> getDraft_pick_no() {
		return draft_pick_no;
	}

	public void setDraft_pick_no(List<Long> draft_pick_no) {
		this.draft_pick_no = draft_pick_no;
	}

	public List<String> getEquipe() {
		return equipe;
	}

	public void setEquipe(List<String> equipe) {
		this.equipe = equipe;
	}

	public List<Long> getRonde() {
		return ronde;
	}

	public void setRonde(List<Long> ronde) {
		this.ronde = ronde;
	}

	public List<Long> getTeam_id() {
		return team_id;
	}

	public void setTeam_id(List<Long> team_id) {
		this.team_id = team_id;
	}

	public List<String> getFrom_who() {
		return from_who;
	}

	public void setFrom_who(List<String> from_who) {
		this.from_who = from_who;
	}

	public List<Long> getTeam_id_from() {
		return team_id_from;
	}

	public void setTeam_id_from(List<Long> team_id_from) {
		this.team_id_from = team_id_from;
	}

	public List<Long> getTeam_count() {
		return team_count;
	}

	public void setTeam_count(List<Long> team_count) {
		this.team_count = team_count;
	}

	public List<Long> getFollow_up() {
		return follow_up;
	}

	public void setFollow_up(List<Long> follow_up) {
		this.follow_up = follow_up;
	}

	public List<String> getPlayer_drafted() {
		return player_drafted;
	}

	public void setPlayer_drafted(List<String> player_drafted) {
		this.player_drafted = player_drafted;
	}

	public List<Long> getYear_of_draft() {
		return year_of_draft;
	}

	public void setYear_of_draft(List<Long> year_of_draft) {
		this.year_of_draft = year_of_draft;
	}

	@SuppressWarnings("unchecked")
	public DraftRound mapDraftRoundFromDatastore(com.google.appengine.api.datastore.Entity mEntity,
			DraftRound mBeanDraftRound) {

		List<Long> draft_pick_no = (List<Long>) mEntity.getProperty("draft_pick_no");
		mBeanDraftRound.setDraft_pick_no(draft_pick_no);

		List<String> equipe = (List<String>) mEntity.getProperty("equipe");
		mBeanDraftRound.setEquipe(equipe);

		List<Long> ronde = (List<Long>) mEntity.getProperty("ronde");
		mBeanDraftRound.setRonde(ronde);

		List<Long> team_id = (List<Long>) mEntity.getProperty("team_id");
		mBeanDraftRound.setTeam_id(team_id);

		List<String> from_who = (List<String>) mEntity.getProperty("from_who");
		mBeanDraftRound.setFrom_who(from_who);

		List<Long> team_id_from = (List<Long>) mEntity.getProperty("team_id_from");
		mBeanDraftRound.setTeam_id_from(team_id_from);

		List<Long> team_count = (List<Long>) mEntity.getProperty("team_count");
		mBeanDraftRound.setTeam_count(team_count);

		List<Long> follow_up = (List<Long>) mEntity.getProperty("follow_up");
		mBeanDraftRound.setFollow_up(follow_up);

		List<String> player_drafted = (List<String>) mEntity.getProperty("player_drafted");
		mBeanDraftRound.setPlayer_drafted(player_drafted);

		List<Long> year_of_draft = (List<Long>) mEntity.getProperty("year_of_draft");
		mBeanDraftRound.setYear_of_draft(year_of_draft);

		return mBeanDraftRound;
	}

	public Entity mapBeanToEntityForDatastore(DraftRound mBean, String name) {

		Entity mEntity = new Entity("DraftRound", name);

		mEntity.setProperty("draft_pick_no", mBean.getDraft_pick_no());

		mEntity.setProperty("equipe", mBean.getEquipe());

		mEntity.setProperty("ronde", mBean.getRonde());

		mEntity.setProperty("team_id", mBean.getTeam_id());

		mEntity.setProperty("from_who", mBean.getFrom_who());

		mEntity.setProperty("team_id_from", mBean.getTeam_id_from());

		mEntity.setProperty("team_count", mBean.getTeam_count());

		mEntity.setProperty("follow_up", mBean.getFollow_up());

		mEntity.setProperty("player_drafted", mBean.getPlayer_drafted());

		mEntity.setProperty("year_of_draft", mBean.getYear_of_draft());	

		return mEntity;
	}

}
