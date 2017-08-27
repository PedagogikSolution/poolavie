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

import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.pedagogiksolution.beans.DraftOnline;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.datastorebeans.Utilisateur;

public class DraftChannelFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

    @SuppressWarnings("deprecation")
	@Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

	HttpServletRequest request = (HttpServletRequest) req;
	HttpServletResponse response = (HttpServletResponse) resp;

	if (request.getSession().getAttribute("DraftOnline") != null) {
	    chain.doFilter(request, response);
	} else {
	    Pool mBeanPool = (Pool) request.getSession().getAttribute("Pool");
	    
	    int cycleAnnuel = mBeanPool.getCycleAnnuel();

	    if (cycleAnnuel == 3) {
		ChannelService channel = ChannelServiceFactory.getChannelService();
		String poolID = mBeanPool.getPoolID();
		Utilisateur mBeanUser = (Utilisateur) request.getSession().getAttribute("Utilisateur");
		int teamId = mBeanUser.getTeamId();
		String tokenString = poolID + "_" +teamId;
		
		String token= channel.createChannel(tokenString);
		
		DraftOnline mBean = new DraftOnline();
		mBean.setToken(token);
		request.getSession().setAttribute("DraftOnline", mBean);
		
		chain.doFilter(request, response);
	    } else {
		chain.doFilter(request, response);
	    }
	}

    }

    @Override
    public void destroy() {

    }

}
