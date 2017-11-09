package fr.adaming.dao;

import javax.persistence.NonUniqueResultException;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Agent;

@Repository
public class AgentDAOImpl implements IAgentDAO {

	@Autowired
	private SessionFactory sf;
	
	public void setSf(SessionFactory sf) {
		this.sf=sf ;
	}

	@Override
	public Agent createAgent(Agent agent) {
		Agent agentOut = agentExists(agent);
		if (agentOut == null) {
			sf.getCurrentSession().save(agent);
			return agent;
		} else {
			return null;
		}
	}

	@Override
	public Agent agentExists(Agent agent) {
		// Cette fonction "récupère" l'agent par son mail et password
		
		String req = "FROM Agent a WHERE a.mail=:pMail AND a.password=:pPassword";

		Query query = sf.getCurrentSession().createQuery(req);
		query.setParameter("pMail", agent.getMail());
		query.setParameter("pPassword", agent.getPassword());
		
		Agent outAgent;
		try {
			outAgent = (Agent) query.uniqueResult();
		} catch (NonUniqueResultException ex) {
			outAgent = (Agent) query.list().get(0);
		}
		return outAgent;
	}

	@Override
	public Agent getAgent(Agent agent) {
		// Cette fonction ne récupère l'agent que par son ID
		
		Agent outAgent = (Agent) sf.getCurrentSession().get(Agent.class, agent);

		return outAgent;
	}

	@Override
	public Agent updateAgent(Agent agent) {
		// Cette fonction ne recupere l'agent que par son ID
		if(getAgent(agent)!=null){
			sf.getCurrentSession().update(agent);
			return agent;
		} else {
			return null;
		}
		
		
	}
}
