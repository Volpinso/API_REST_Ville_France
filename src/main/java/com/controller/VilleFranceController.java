package com.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.blo.VilleFranceBLO;
import com.dao.DAOFactory;
import com.dao.VilleFranceDAO;
import com.config.Application;


@RestController
public class VilleFranceController {
	
	private static final String MOT_DE_PASSE = Application.getString("motDePasse");
	private static final String NOM_UTILISATEUR = Application.getString("nomUtilisateur");
	private static final String URL = Application.getString("url");
	
	/* Lien : localhost:8181/path/villeFrance OUI C'EST LE LIEN BIS*/
	@RequestMapping(value="/villeFrance", method=RequestMethod.GET)
	@ResponseBody
	
	public List<VilleFranceBLO> get(){

		DAOFactory factory = new DAOFactory(URL, 
				NOM_UTILISATEUR, MOT_DE_PASSE);
		VilleFranceDAO villeFranceDAO = new VilleFranceDAO(factory);
		return villeFranceDAO.lister();
	}

	@RequestMapping(value="/villeFranceFind", method=RequestMethod.GET)
	@ResponseBody
	
	public List <VilleFranceBLO> getWhere(@RequestParam(required = false, value="value") String codePostal) {

		DAOFactory factory = new DAOFactory(URL, 
				NOM_UTILISATEUR, MOT_DE_PASSE);
		VilleFranceDAO villeFranceDAO = new VilleFranceDAO(factory);
		VilleFranceBLO villeFranceFind = new VilleFranceBLO();
		villeFranceFind.setCodePostal(codePostal);
		return villeFranceDAO.trouver(villeFranceFind);
	}
	
	@RequestMapping(value="/villeFranceDelete", method=RequestMethod.DELETE)
	@ResponseBody
	
	public String delete(@RequestParam(required = false, value="value") String codeCommuneInsee) {
		
		String deleteReturn;
		DAOFactory factory = new DAOFactory(URL, 
				NOM_UTILISATEUR, MOT_DE_PASSE);
		VilleFranceDAO villeFranceDAO = new VilleFranceDAO(factory);
		VilleFranceBLO villeFranceFind = new VilleFranceBLO();
		villeFranceFind.setCodeCommuneInsee(codeCommuneInsee);
		villeFranceDAO.supprimer(villeFranceFind);
		deleteReturn = "Ville Supprimée de la base";
			
		return deleteReturn;
	}
	
	@RequestMapping(value="/villeFrancePost", method=RequestMethod.POST)
	@ResponseBody
	
	public String post(@RequestParam(required = false, value="value") String ville) {
		String postReturn;
		DAOFactory factory = new DAOFactory(URL, 
				NOM_UTILISATEUR, MOT_DE_PASSE);
		VilleFranceDAO villeFranceDAO = new VilleFranceDAO(factory);
		villeFranceDAO.modifier(splitVille(ville));
		postReturn = "Ville Modifiée de la base";
		
		return postReturn;
	}
	
	
	@RequestMapping(value="/villeFrancePut", method=RequestMethod.PUT)
	@ResponseBody
	
	public String put(@RequestParam(required = false, value="value")
	VilleFranceBLO ville) {
		
		String putReturn;
		DAOFactory factory = new DAOFactory(URL, 
				NOM_UTILISATEUR, MOT_DE_PASSE);
		VilleFranceDAO villeFranceDAO = new VilleFranceDAO(factory);
		villeFranceDAO.creer(ville);
		putReturn = "Ville Créée de la base";
		
		return putReturn;
	}
	
	@RequestMapping(value="/villeFranceDelInsee", method=RequestMethod.POST)
	@ResponseBody
	
	public String deleteInsee(@RequestParam(required = false, value="value") String codeCommuneInsee) {
		
		String deleteInseeReturn;
		DAOFactory factory = new DAOFactory(URL, 
				NOM_UTILISATEUR, MOT_DE_PASSE);
		VilleFranceDAO villeFranceDAO = new VilleFranceDAO(factory);
		VilleFranceBLO villeFranceTrouve = new VilleFranceBLO();
		villeFranceTrouve.setCodeCommuneInsee(codeCommuneInsee);
		villeFranceDAO.supprimer(villeFranceTrouve);
		deleteInseeReturn = "Ville Supprimée de la base";
			
		return deleteInseeReturn;
	}
	
	@RequestMapping(value="/villeFranceAdd", method=RequestMethod.POST)
	@ResponseBody
	
	public String addVille(@RequestParam(required = false, value="value") String ville) {
		
		String putReturn;
		DAOFactory factory = new DAOFactory(URL, 
				NOM_UTILISATEUR, MOT_DE_PASSE);
		VilleFranceDAO villeFranceDAO = new VilleFranceDAO(factory);
		villeFranceDAO.creer(splitVille(ville));
		putReturn = "Ville Créee de la base";
		
		return putReturn;
	}
	//Compter les villes
	@RequestMapping(value="/villeFranceCompte", method=RequestMethod.GET)
	@ResponseBody
	
	public int getCount(@RequestParam(required = false, value="value") String codeCommune
	){
		DAOFactory factory = new DAOFactory(URL, 
				NOM_UTILISATEUR, MOT_DE_PASSE);
		VilleFranceDAO villeFranceDAO = new VilleFranceDAO(factory);
		VilleFranceBLO ville = new VilleFranceBLO();
		ville.setCodeCommuneInsee(codeCommune);
		return villeFranceDAO.compter(ville);
	}
	
	private VilleFranceBLO splitVille(String ville) {
		
		VilleFranceBLO villeFrance = new VilleFranceBLO(); 
		int debutDepart = ville.indexOf("codeCommuneInsee=");
		int finDepart = ville.indexOf(", nomCommune=");
		
		if(debutDepart < 0) {
			debutDepart = -2;
		}
		
		String codeCommuneInsee = ville.substring(debutDepart + 17, finDepart);
		
		debutDepart = ville.indexOf("nomCommune=");
		finDepart = ville.indexOf(", codePostal=");
		
		String nomCommune = ville.substring(debutDepart + 11, finDepart);
		
		debutDepart = ville.indexOf("codePostal=");
		finDepart = ville.indexOf(", libelleAcheminement=");
		
		String codePostal = ville.substring(debutDepart + 11, finDepart);
		
		debutDepart = ville.indexOf("libelleAcheminement=");
		finDepart = ville.indexOf(", ligne5=");
		
		String libelleAcheminement = ville.substring(debutDepart + 20, finDepart);
		
		debutDepart = ville.indexOf("ligne5=");
		finDepart = ville.indexOf(", lattitude=");
		
		String ligne5 = ville.substring(debutDepart + 7, finDepart);
		
		debutDepart = ville.indexOf("lattitude=");
		finDepart = ville.indexOf(", longitude=");
		
		String latitude = ville.substring(debutDepart + 10, finDepart);
		
		debutDepart = ville.indexOf("longitude=");
		finDepart = ville.indexOf("]");
		
		String longitude = ville.substring(debutDepart + 10, finDepart);
		
		villeFrance.setCodeCommuneInsee(codeCommuneInsee);
		villeFrance.setCodePostal(codePostal);
		villeFrance.setLattitude(latitude);
		villeFrance.setLibelleAcheminement(libelleAcheminement);
		villeFrance.setLongitude(longitude);
		villeFrance.setNomCommune(nomCommune);
		villeFrance.setLigne5(ligne5);
		return villeFrance;
		
	}
}
