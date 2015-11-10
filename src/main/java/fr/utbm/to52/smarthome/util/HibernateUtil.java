package fr.utbm.to52.smarthome.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.TransactionManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.ogm.cfg.OgmConfiguration;
import org.jboss.jandex.Main;

import fr.utbm.to52.smarthome.controller.events.AddRingEvent;
import fr.utbm.to52.smarthome.controller.events.StorableEvent;
import fr.utbm.to52.smarthome.model.calendar.DateCalendar;
import fr.utbm.to52.smarthome.model.calendar.Event;
import fr.utbm.to52.smarthome.model.location.Location;
import fr.utbm.to52.smarthome.model.note.Note;
import fr.utbm.to52.smarthome.model.note.Tag;
import fr.utbm.to52.smarthome.model.task.Task;

/**
 * 
 * @author Hibernate
 * @author Alexandre Guyon 
 *
 */
public class HibernateUtil {
    
	private static final SessionFactory sessionFactory = buildSessionFactory();
    
    
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("smarthome-persistance");
    
    @SuppressWarnings("deprecation")
	private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
        	OgmConfiguration cfg = new OgmConfiguration();
        	cfg.configure();
        	
        	cfg.addAnnotatedClass(Note.class)
    			.addAnnotatedClass(Location.class)
        		.addAnnotatedClass(Tag.class)
        		.addAnnotatedClass(Event.class)
        		.addAnnotatedClass(Task.class)
        		.addAnnotatedClass(StorableEvent.class);
            return cfg.buildSessionFactory();
        }catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed.\n" + ex);
        	throw new ExceptionInInitializerError(ex);
        }
    }
    /**
     * @return Session factiry to create or get back hibernate session
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    /**
     * @return Return the entity manager factory
     */
    public static EntityManagerFactory getEntityManager(){
    	return emf;
    }
    
    private static final String JBOSS_TM_CLASS_NAME = "com.arjuna.ats.jta.TransactionManager";

	/**
	 * @return Get Transaction manager
	 * @throws Exception error
	 */
	public static TransactionManager getTransactionManager() throws Exception {
	    Class<?> tmClass = Main.class.getClassLoader().loadClass(JBOSS_TM_CLASS_NAME);
	    return (TransactionManager) tmClass.getMethod("transactionManager").invoke(null);
	}	
    
	/**
	 * Test
	 * @param args no args
	 */
    @SuppressWarnings("unused")
	public static void main(String[] args){
    	Tag t = new Tag("testTag");
    	
    	Note n = new Note("coucou");
    	n.setTag(t);
    	
    	Location l = new Location();
		l.setLoc("Belfort");
		
		Task tk = new Task();
		tk.setTitle("TestEvent");
		tk.setBegin(new DateCalendar());
		tk.setEnd(new DateCalendar());
		tk.setLoc(l);
		tk.setNote(n);
		
		Session hbmSess = HibernateUtil.getSessionFactory().openSession();
		
		AddRingEvent a = new AddRingEvent(hbmSess, null);
		try{
			a.inform("AddRing {\"date\" : \"2015-11-11H12M00\"}");
		}catch(java.lang.NullPointerException e){
			// do nothing
		}
		
		/*hbmSess.beginTransaction();
		
		hbmSess.save(t);
		hbmSess.save(n);
		hbmSess.save(l);
		hbmSess.save(tk);
		hbmSess.flush();
		
		hbmSess.getTransaction().commit();
		*/
		
		hbmSess.close();
		
		/*EntityManagerFactory emf = HibernateUtil.getEntityManager();
		EntityManager em = emf.createEntityManager();
		
		try {
			TransactionManager tm = getTransactionManager();
			
			tm.begin();
			
			em.persist(n);
			em.flush();
			
			tm.commit();
			
			em.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}*/
    }
    
}