package com.sunkuet02.micro.apigateway.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public class LoggingFilter extends ZuulFilter {

    private final static Logger logger = LogManager.getLogger(LoggingFilter.class);

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        String requestId = request.getAttribute("request_id") == null ? UUID.randomUUID().toString().replace("-", "") : (String) request.getAttribute("REQUEST_ID");
        if (request.getAttribute("request_id") == null) {
            ctx.addZuulRequestHeader("request_id", requestId);
        }

        ThreadContext.put("requestId", requestId);

        logger.info("Request from : {}, uri : {}, type: {}", request.getRemoteHost(), request.getRequestURI(), request.getMethod());
        return null;
    }
}
