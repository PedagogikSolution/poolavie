package com.pedagogiksolution.beans;

import java.util.List;

public class Article implements java.io.Serializable {

    /**
     * 
     * 
     */
    private static final long serialVersionUID = 8073876698631264439L;
    private List<String> titre;   
    private List<String> body;
    private List<String> dateCreation;
    private List<String> writerName;
    private List<String> writerLogo;
    
    public List<String> getTitre() {
	return titre;
    }
    public void setTitre(List<String> titre) {
	this.titre = titre;
    }
    public List<String> getBody() {
	return body;
    }
    public void setBody(List<String> body) {
	this.body = body;
    }
   
   
    public List<String> getDateCreation() {
	return dateCreation;
    }
    public void setDateCreation(List<String> list) {
	this.dateCreation = list;
    }
    public List<String> getWriterLogo() {
	return writerLogo;
    }
    public void setWriterLogo(List<String> writerLogo) {
	this.writerLogo = writerLogo;
    }
    public List<String> getWriterName() {
	return writerName;
    }
    public void setWriterName(List<String> writerName) {
	this.writerName = writerName;
    }
    
}
