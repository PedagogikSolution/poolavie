package com.pedagogiksolution.constants;

public class MessageErreurConstants {

    public static String REGISTRATION_USERNAME_PASSWORD_MATCH = "Ce nom d'utilisateur existe d�j�, veuillez choisir un autre identifiant pour cr�er un nouveau pool" +
    		" ou connectez-vous en utilisant le formulaire � votre gauche";
    public static String REGISTRATION_USERNAME_MATCH = "Ce nom d'utilisateur existe d�j�, veuillez choisir un autre identifiant pour cr�er un nouveau pool" +
    		" ou connectez-vous en utilisant le formulaire � votre gauche";
    public static String REGISTRATION_ERREUR_PARAM_NULL = "Visitez la page Beta Test pour des informations sur la mani�re de vous inscrire." +
    		" La plateforme �tant pr�sentement en construction nous n'acceptons des nouveaux pools que via la Beta Test";
    public static String REGISTRATION_ERREUR_PASSWORD_ENCRYPTION = "Une erreur innatendue s'est produite, veuillez r�-essayer ou nous contacter si l'erreur persiste";
    public static String VALIDATION_CODE_ERREUR_PAS_BON = "Votre code n'est pas bon. Veuillez r�-essayer ou g�n�rer un nouveau code en remplissant le formulaire si bas ";
    public static String VALIDATION_CODE_ERREUR_PAS_ENCORE_FAIT = "Ce compte n'est pas encore valid�. V�rifiez votre courriel et enter votre code ou remplissez le formulaire pour recevoir un nouveau code";
    public static String LOGIN_USERNAME_DONT_EXIST = "Ce nom d'utilisateur n'existe pas. Pour cr�er un nouveau pool, remplissez le formualire situ� juste � droite," +
    		" si vous avez perdu votre nom d'utilisateur, appuyez ci-bas pour le r�cup�rer via courriel";
    public static String LOGIN_PASSWORD_NOT_GOOD = "Mauvais mot de passe. Veuillez r�-essayer. Cliquez ci-bas si vous avez ouli� votre mot de passe";
    public static String RECUPERATION_COURRIEL_INEXISTANT = "Ce courriel n'est associ� � aucun compte";
    public static String NOT_LOG_IN ="Vous devez �tre connect� pour acc�der � cette section. Merci de vous connecter ci-bas";
    public static String CREATE_NEW_USER_NO_GOOD ="Une erreur s'est produite. Essayez de nouveau en vous assurant de cliquer sur le lien ou de copier celui-ci en entier. Si l'erreur persiste, contacter votre Commissaire/Administrateur de Pool";
    public static String CREATE_POOL_PAS_FINI ="Vous devez finir la cr�ation du pool pour continuer";
    private MessageErreurConstants() {
	// empeche les classes native d'Appeler le constructeur

	throw new AssertionError();
	
    }
}

