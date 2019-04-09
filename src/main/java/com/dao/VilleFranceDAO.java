package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.blo.VilleFranceBLO;

public class VilleFranceDAO extends DAO<VilleFranceBLO> {

    private static final String ATTRIBUT_CODE_COMMUNE_INSEE = "Code_commune_INSEE";
    private static final String ATTRIBUT_NOM_COMMUNE = "Nom_commune";
    private static final String ATTRIBUT_CODE_POSTAL = "Code_postal";
    private static final String ATTRIBUT_LIBELLE_ACHEMINEMENT = "Libelle_acheminement";
    private static final String ATTRIBUT_LIGNE_5 = "Ligne_5";
    private static final String ATTRIBUT_LATITUDE = "Latitude";
    private static final String ATTRIBUT_LONGITUDE = "Longitude";

    /* Requetes SQL pour Utilisateur */
    private static final String SQL_INSERT = "INSERT INTO ville_france (Code_commune_INSEE, Nom_commune, "
            + "Code_postal, Libelle_acheminement, Ligne_5, Latitude, Longitude) " + "VALUES (";
    private static final String SQL_SELECT_VILLE_FRANCE = "SELECT Code_commune_INSEE, Nom_commune, Code_postal, "
            + "Libelle_acheminement, " + "Ligne_5, Latitude, Longitude FROM ville_france ORDER BY Code_commune_INSEE";

    private static final String SQL_SELECT_WHERE = "SELECT Code_commune_INSEE, Nom_commune, Code_postal, "
            + "Libelle_acheminement, " + "Ligne_5, Latitude, Longitude FROM ville_france";

    /* Constantes pour éviter la duplication de code */
    private static final String DELETE = "DELETE FROM";

    private static final String COUNT = "SELECT Count(code_Commune_INSEE) FROM ville_france WHERE code_Commune_INSEE = '";

    private static final Logger LOGGER = Logger.getLogger(VilleFranceDAO.class.getName());
    private static final String ERREUR = "Echec de la requête";

    /**
     * Constructeur de DAO.
     *
     * @param daoFactory la Factory permettant la création d'une connexion à la BDD.
     */
    public VilleFranceDAO(DAOFactory daoFactory) {
        super(daoFactory);
    }

    @Override
    public List<VilleFranceBLO> lister() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<VilleFranceBLO> villeFranceListe = new ArrayList<VilleFranceBLO>();

        try {
            // création d'une connexion grâce à la DAOFactory placée en attribut de la
            // classe
            connection = this.creerConnexion();
            preparedStatement = connection.prepareStatement(SQL_SELECT_VILLE_FRANCE);
            resultSet = preparedStatement.executeQuery();
            // récupération des valeurs des attributs de la BDD pour les mettre dans une
            // liste
            while (resultSet.next()) {
                villeFranceListe.add(recupererVille(resultSet));
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            LOGGER.log(Level.WARN, ERREUR, e);
        }
        // fermeture des ressources utilisées

        return villeFranceListe;
    }

    // #################################################
    // # Méthodes privées #
    // #################################################

    /**
     * Crée une connexion à la BDD.
     *
     * @return connection la connexion à la BDD.
     * @throws SQLException
     */
    protected Connection creerConnexion() throws SQLException {
        return this.getDaoFactory().getConnection();
    }

    @Override
    public void creer(VilleFranceBLO villeFranceBLO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // création d'une connexion grâce à la DAOFactory placée en attribut de la
            // classe
            connection = this.creerConnexion();
            preparedStatement = connection.prepareStatement(SQL_INSERT + "'" + villeFranceBLO.getCodeCommuneInsee()
                    + "', " + "'" + villeFranceBLO.getNomCommune() + "', " + "'" + villeFranceBLO.getCodePostal()
                    + "', " + "'" + villeFranceBLO.getLibelleAcheminement() + "', " + "'" + villeFranceBLO.getLigne5()
                    + "', " + "'" + villeFranceBLO.getLattitude() + "', " + "'" + villeFranceBLO.getLongitude() + "')");

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            LOGGER.log(Level.WARN, ERREUR, e);
        }
    }

    @Override
    public List<VilleFranceBLO> trouver(VilleFranceBLO villeFranceBLO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<VilleFranceBLO> villeFranceListe = new ArrayList<VilleFranceBLO>();
        try {
            // création d'une connexion grâce à la DAOFactory placée en attribut de la
            // classe
            connection = this.creerConnexion();
            preparedStatement = connection.prepareStatement(
                    SQL_SELECT_WHERE + " WHERE Code_Postal LIKE '%" + villeFranceBLO.getCodePostal() + "%'");
            resultSet = preparedStatement.executeQuery();
            // récupération des valeurs des attributs de la BDD pour les mettre dans une
            // liste
            while (resultSet.next()) {
                villeFranceListe.add(recupererVille(resultSet));
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            LOGGER.log(Level.WARN, ERREUR, e);
        }
        // fermeture des ressources utilisées

        return villeFranceListe;
    }

    @Override
    public void modifier(VilleFranceBLO villeFranceBLO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // création d'une connexion grâce à la DAOFactory placée en attribut de la
            // classe
            connection = this.creerConnexion();
            String requete = "UPDATE ville_france SET Nom_Commune = '" + villeFranceBLO.getNomCommune()
                    + "', Code_postal = '" + villeFranceBLO.getCodePostal() + "', Libelle_acheminement = '"
                    + villeFranceBLO.getLibelleAcheminement() + "', Ligne_5 = '" + villeFranceBLO.getLigne5()
                    + "', Latitude = '" + villeFranceBLO.getLattitude() + "', Longitude = '"
                    + villeFranceBLO.getLongitude() + "' WHERE Code_Commune_INSEE LIKE '%"
                    + villeFranceBLO.getCodeCommuneInsee() + "%'";

            preparedStatement = connection.prepareStatement(requete);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            LOGGER.log(Level.WARN, ERREUR, e);
        }
    }

    @Override
    public void supprimer(VilleFranceBLO villeFranceBLO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // création d'une connexion grâce à la DAOFactory placée en attribut de la
            // classe
            connection = this.creerConnexion();
            preparedStatement = connection.prepareStatement(DELETE + " ville_france WHERE "
                    + "Code_Commune_INSEE LIKE '%" + villeFranceBLO.getCodeCommuneInsee() + "%'");

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            LOGGER.log(Level.WARN, ERREUR, e);
        }
    }

    public int compter(VilleFranceBLO villeFranceBLO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;

        try {
            // création d'une connexion grâce à la DAOFactory placée en attribut de la
            // classe
            connection = this.creerConnexion();
            preparedStatement = connection.prepareStatement(COUNT + villeFranceBLO.getCodeCommuneInsee() + "'");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            LOGGER.log(Level.WARN, ERREUR, e);
        }
        return count;
    }

    public static VilleFranceBLO recupererVille(ResultSet resultSet) throws SQLException {
        VilleFranceBLO villeFrance = new VilleFranceBLO();
        villeFrance.setCodeCommuneInsee(resultSet.getString(ATTRIBUT_CODE_COMMUNE_INSEE));
        villeFrance.setNomCommune(resultSet.getString(ATTRIBUT_NOM_COMMUNE));
        villeFrance.setCodePostal(resultSet.getString(ATTRIBUT_CODE_POSTAL));
        villeFrance.setLibelleAcheminement(resultSet.getString(ATTRIBUT_LIBELLE_ACHEMINEMENT));
        villeFrance.setLigne5(resultSet.getString(ATTRIBUT_LIGNE_5));
        villeFrance.setLattitude(resultSet.getString(ATTRIBUT_LATITUDE));
        villeFrance.setLongitude(resultSet.getString(ATTRIBUT_LONGITUDE));
        return villeFrance;
    }

}
