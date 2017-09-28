package com.pedagogiksolution.filtre;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserServiceFactory;
import com.pedagogiksolution.beans.DraftOnline;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.datastorebeans.Utilisateur;
import com.pedagogiksolution.firebase.FirebaseChannel;

public class DraftChannelFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

	@Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

	HttpServletRequest request = (HttpServletRequest) req;
	HttpServletResponse response = (HttpServletResponse) resp;

	
	    Pool mBeanPool = (Pool) request.getSession().getAttribute("Pool");
	    
	    int cycleAnnuel = mBeanPool.getCycleAnnuel();

	    if (cycleAnnuel == 3) {
		
		String poolID = mBeanPool.getPoolID();
		Utilisateur mBeanUser = (Utilisateur) request.getSession().getAttribute("Utilisateur");
		int teamId = mBeanUser.getTeamId();
		String tokenString = poolID + "_" +teamId;
		DraftOnline mBean = new DraftOnline();
		//String userId = UserServiceFactory.getUserService().getCurrentUser().getUserId();
		// recupere un token 
		String token = FirebaseChannel.getInstance(request.getSession().getServletContext())
		        .createFirebaseToken(mBean, tokenString);
		
		
		mBean.setToken(token);
		mBean.setChannelId(tokenString);
		request.setAttribute("DraftOnline", mBean);
		
		chain.doFilter(request, response);
	    } else {
		chain.doFilter(request, response);
	    }
	}

  

    @Override
    public void destroy() {

    }

}
