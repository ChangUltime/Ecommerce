package fr.adaming.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.adaming.model.Agent;

@Stateless
public class AgentDAOImpl implements IAgentDAO {

	@PersistenceContext(unitName = "PU_TP") // Cette annotation sert à injecter
											// un entitymanager, le conteneur
											// ejb va l'instancier et le fournir
											// en cas de besoin
	EntityManager em;

	@Override
	public Agent exists(Agent agent) throws Exception{
		//La requete jpql
		String req="SELECT a from Agent a where a.mail=:pMail AND a.mdp=:pMdp";
		
		//creation de la query a partir de l'em
		Query query = em.createQuery(req);
		
		//passage des paramètres
		query.setParameter("pMail", agent.getMail());
		query.setParameter("pMdp", agent.getMdp());
		
		//envoi de la requete et recuperation du resultat
		Agent agentReq = (Agent)query.getSingleResult();
		return agentReq;
	}

	public Agent addAgent(Agent agent) throws Exception {
		//On verifie si l'agent n'existe pas déjà
		
		em.persist(agent);

		System.out.println(agent);
		return agent;
		
		
	}
}
