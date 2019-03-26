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

@RestController
@RequestMapping("/path")
public class VilleFranceController {
	
	@RequestMapping(value="/villeFrance", method=RequestMethod.GET)
	@ResponseBody
	
	/* Lien : localhost:8181/path/villeFrance OUI C'EST LE LIEN BIS*/
	
	public List <VilleFranceBLO> get(@RequestParam(required = false, value="value") List<VilleFranceBLO> villeFrance) {
		
		String ville = "";
		DAOFactory factory = new DAOFactory("jdbc:mysql://localhost/integrationcontinue?serverTimezone=Australia/Melbourne", 
				"somanager", "network");
		VilleFranceDAO villeFranceDAO = new VilleFranceDAO(factory);
		villeFrance = villeFranceDAO.lister();
		System.out.println("Appel GET");
		for (int i = 0; i< villeFrance.size(); i++) {
			System.out.println(villeFrance.get(i).toString());
			ville = ville + "Ville " + (i + 1) + " : " + villeFrance.get(i).toString();
		}
		
		return villeFrance;
	}
	
}
