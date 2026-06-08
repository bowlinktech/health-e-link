package com.ut.healthelink.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BotProbeBlockFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;

    String uri = req.getRequestURI();
    String ctx = req.getContextPath();
    if (ctx != null && !ctx.isEmpty() && uri.startsWith(ctx)) {
      uri = uri.substring(ctx.length());
    }

    String path = uri.toLowerCase();
    
    // Block obvious probes (any depth)
    if (path.endsWith(".php") || path.endsWith(".asp") || path.endsWith(".aspx") || path.endsWith(".gz")
        || path.endsWith(".cgi") || path.endsWith(".env") || path.endsWith("wsman") || path.endsWith(".map")
        || path.equals("/xmlrpc.php") || path.equals("/wp-login.php")
        || path.startsWith("/wp-") || path.startsWith("/wordpress/")
        || path.startsWith("/.git/") || path.equals("/.env") || path.equals("/files/upload")) {

      // Important: do NOT touch session, do NOT forward to a view
      res.setStatus(HttpServletResponse.SC_NOT_FOUND);
      res.setContentType("text/plain");
      res.getWriter().write("Not Found");
      return;
    }

    chain.doFilter(request, response);
  }
}