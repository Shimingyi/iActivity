package com.me.Activity.util;

import java.io.IOException;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

//import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

public class EncodingFilter implements Filter {
	protected String charSet = null;

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		@SuppressWarnings("unused")
		HttpServletRequest req = (HttpServletRequest) request;

		StringBuffer sb = new StringBuffer();
		Map<String, String[]> keyMap = request.getParameterMap();
		Set<String> keySet = keyMap.keySet();
		Iterator<String> it = keySet.iterator();
		while (it.hasNext()) {
			String s = it.next();
			String[] ss = keyMap.get(s);
			// System.out.print("key:"+s);
			sb.append("key:" + s);
			// System.out.print("\t values:");
			sb.append("\t values:");
			for (String str : ss) {
				sb.append(str + " ");
			}
			// System.out.println();
			// System.out.println();
			sb.append("\n");

		}
		// System.out.println(sb);
		// req.setRequestHeader("Content-Type","text/html; encoding=gb18030");
		response.setCharacterEncoding("utf-8");

		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.charSet = filterConfig.getInitParameter("chSet");

	}

}
