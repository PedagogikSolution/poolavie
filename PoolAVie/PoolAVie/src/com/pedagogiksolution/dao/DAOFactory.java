package com.pedagogiksolution.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.google.appengine.api.utils.SystemProperty;

public class DAOFactory {

    private static final String FICHIER_PROPERTIES = "dao.properties";
    private static final String PROPERTY_URLDEV = "urldev";
    private static final String PROPERTY_DRIVERDEV = "driverdev";
    private static final String PROPERTY_NOM_UTILISATEURDEV = "nomutilisateurdev";
    private static final String PROPERTY_MOT_DE_PASSEDEV = "motdepassedev";
    private static final String PROPERTY_URLPROD = "urlprod";
    private static final String PROPERTY_DRIVERPROD = "driverprod";
    private static final String PROPERTY_NOM_UTILISATEURPROD = "nomutilisateurprod";
    private static final String PROPERTY_MOT_DE_PASSEPROD = "motdepasseprod";

    private String url;
    private String username;
    private String password;

    DAOFactory(String url, String username, String password) {
	this.url = url;
	this.username = username;
	this.password = password;
    }

    /*
     * Méthodes de récupération de l'implémentation des différents DAO (un seul
     * pour le moment)
     */
    public ClassementDao getClassementDao() {
	return new ClassementDaoImpl(this);
    }
    public PlayersDao getPlayersDao() {
   	return new PlayersDaoImpl(this);
       }
    public DraftDao getDraftDao() {
   	return new DraftDaoImpl(this);
       }
    public SalaireDao getSalaireDao() {
   	return new SalaireDaoImpl(this);
       }
    public TradeMadeDao getTradeMadeDao() {
   	return new TradeMadeDaoImpl(this);
       }
    public TradeOfferDao getTradeOfferDao() {
   	return new TradeOfferDaoImpl(this);
       }
    
    public DraftPickDao getDraftPickDao() {
   	return new DraftPickDaoImpl(this);
       }

    /*
     * Méthode chargée de récupérer les informations de connexion à la base de
     * données, charger le driver JDBC et retourner une instance de la Factory
     */
    public static DAOFactory getInstance() throws DAOConfigurationException {
	Properties properties = new Properties();
	String url;
	String driver;
	String nomUtilisateur;
	String motDePasse;

	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	InputStream fichierProperties = classLoader.getResourceAsStream(FICHIER_PROPERTIES);

	if (fichierProperties == null) {
	    throw new DAOConfigurationException("Le fichier properties " + FICHIER_PROPERTIES + " est introuvable.");
	}

	try {
	    properties.load(fichierProperties);

	    if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
		url = properties.getProperty(PROPERTY_URLPROD);
		driver = properties.getProperty(PROPERTY_DRIVERPROD);
		nomUtilisateur = properties.getProperty(PROPERTY_NOM_UTILISATEURPROD);
		motDePasse = properties.getProperty(PROPERTY_MOT_DE_PASSEPROD);
	    } else {
		url = properties.getProperty(PROPERTY_URLDEV);
		driver = properties.getProperty(PROPERTY_DRIVERDEV);
		nomUtilisateur = properties.getProperty(PROPERTY_NOM_UTILISATEURDEV);
		motDePasse = properties.getProperty(PROPERTY_MOT_DE_PASSEDEV);
	    }

	} catch (IOException e) {
	    throw new DAOConfigurationException("Impossible de charger le fichier properties " + FICHIER_PROPERTIES, e);
	}

	try {

	    Class.forName(driver);

	} catch (ClassNotFoundException e) {
	    throw new DAOConfigurationException("Le driver est introuvable dans le classpath.", e);
	}

	DAOFactory instance = new DAOFactory(url, nomUtilisateur, motDePasse);
	return instance;
    }

    /* Méthode chargée de fournir une connexion à la base de données */
    /* package */Connection getConnection() throws SQLException {
	return DriverManager.getConnection(url, username, password);
    }

}
