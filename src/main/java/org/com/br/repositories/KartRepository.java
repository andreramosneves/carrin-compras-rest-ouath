package org.com.br.repositories;

import java.util.List;
import java.util.Optional;
import org.com.br.bo.Kart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface KartRepository extends JpaRepository<Kart, Long> {

    @Query("SELECT p FROM kart p WHERE p.user.id = :idUser AND p.order.id is null AND p.dttermino is null")
    List<Kart> listItensCarrinho(int idUser);

    @Query("SELECT p FROM kart p WHERE p.id =:idKart AND p.user.id = :idUser AND p.order.id is null AND p.dttermino is null")
    Optional<Kart> findKartAndUser(int idKart, int idUser);

}

/*
public class KartRepository implements IDao<Kart> {

	
	@Override
	public void save(Kart objeto) {
	    Session session = HibernateUtil.createSessionFactory().openSession();
	    session.beginTransaction();
	    session.saveOrUpdate(objeto);
	    session.getTransaction().commit();
	    session.close();
		
	}

	@Override
	public void delete(Kart objeto) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public List<Kart> list() {
		// TODO Auto-generated method stub
	    Session session = HibernateUtil.createSessionFactory().openSession();
	    Criteria cr = session.createCriteria(Kart.class);
	    cr.add(Restrictions.isNull("dt_termino"));
	    @SuppressWarnings("unchecked")
		List<Kart> l = cr.list();
	    session.close();
	    return l;  
	}

	
	public List<Kart> listUser(int idUser) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.createSessionFactory().openSession();
		Query q = session.createQuery("SELECT p FROM kart p WHERE p.user.id = :idUser AND p.order.id is null" );
		q.setParameter("idUser", idUser);
	    @SuppressWarnings("unchecked")
		List<Kart> l = q.list();
	    session.close();
	    return l;
	}	
	
	
	@Override
	public Kart find(int Id) {
		// TODO Auto-generated method stub
		return null;
	}

}
*/
