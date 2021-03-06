/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.test.ext;

import org.apache.knox.gateway.filter.AbstractGatewayFilter;
import org.apache.knox.gateway.filter.rewrite.impl.UrlRewriteRequest;
import org.apache.knox.gateway.filter.rewrite.impl.UrlRewriteResponse;
import org.apache.knox.gateway.util.MimeTypes;

import javax.activation.MimeType;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyUrlRewriteServletFilter extends AbstractGatewayFilter {

    private static final Logger LOG = LoggerFactory.getLogger(MyUrlRewriteServletFilter.class);

    public static final String REQUEST_URL_RULE_PARAM = "request.url";

    public static final String REQUEST_HEADERS_FILTER_PARAM = "request.headers";

    public static final String REQUEST_COOKIES_FILTER_PARAM = "request.cookies";

    public static final String REQUEST_BODY_FILTER_PARAM = "request.body";

    public static final String RESPONSE_HEADERS_FILTER_PARAM = "response.headers";

    public static final String RESPONSE_COOKIES_FILTER_PARAM = "response.cookies";

    public static final String RESPONSE_BODY_FILTER_PARAM = "response.body";

    public static final MimeType HEADERS_MIME_TYPE = MimeTypes.create("application/x-http-headers", null);

    public static final MimeType COOKIES_MIME_TYPE = MimeTypes.create("application/x-http-cookies", null);

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);

        LOG.warn(" ********* Init: {}", MyUrlRewriteServletFilter.class.getName());
    }

    @Override
    protected void doFilter(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain chain) throws IOException, ServletException {

        FilterConfig config = getConfig();

        HttpSession session = request.getSession();
        LOG.warn(" ********* Session: {}", session);
        if (session != null) {
            LOG.warn(" ********* Session attributes: {}", session.getAttributeNames());
        }

        UrlRewriteRequest rewriteRequest = new UrlRewriteRequest(config, request);
        UrlRewriteResponse rewriteResponse = new UrlRewriteResponse(config, rewriteRequest, response);
        chain.doFilter(rewriteRequest, rewriteResponse);
    }

}
