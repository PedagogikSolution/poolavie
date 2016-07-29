package com.pedagogiksolution.filtre;

import static com.pedagogiksolution.constants.MessageErreurConstants.NOT_LOG_IN;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.beans.MessageErreurBeans;

public class ConnectUserFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
	
	
	
	HttpServletRequest request = (HttpServletRequest) req;
	HttpServletResponse response = (HttpServletResponse) resp;

	if (request.getSession().getAttribute("connectUser") != null) {
	    chain.doFilter(request, response);

	} else {
	    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
	    mBeanMessageErreur.setErreurNotLogIn(NOT_LOG_IN);
	    request.setAttribute("MessageErreurBeans", mBeanMessageErreur);
	    request.getRequestDispatcher("/login").forward(request, response);
	}

    }

    @Override
    public void destroy() {
	// TODO Auto-generated method stub

    }

}
