package com.pedagogiksolution.filtre;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.utils.EMF;

public class SessionBeanUpdate implements Filter {

    @Override
    public void destroy() {
	// TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
	HttpServletRequest request = (HttpServletRequest) req;
	HttpServletResponse response = (HttpServletResponse) resp;
	Pool mBeanPool = (Pool) request.getSession().getAttribute("Pool");
	String poolID = mBeanPool.getPoolID();

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	Key mPoolKey = KeyFactory.createKey("Pool", poolID);

	try {
	    Entity entity = datastore.get(mPoolKey);
	    mBeanPool = mapPoolFromDatastore(entity, mBeanPool);
	    request.getSession().setAttribute("Pool", mBeanPool);

	} catch (EntityNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	chain.doFilter(request, response);

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
	// TODO Auto-generated method stub

    }
    
    private Pool mapPoolFromDatastore(Entity mEntity, Pool mBeanPool) {

	EntityManagerFactory emf = EMF.get();
	EntityManager em = null;

	try {
		em = emf.createEntityManager();
		mBeanPool = em.find(Pool.class, mEntity.getKey());
	} finally {
		if (em != null)
			em.close();
	}

	return mBeanPool;
}

}
