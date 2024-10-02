package com.jojo.my_letter.filter;

import com.jojo.my_letter.controller.service.AccessLogService;
import com.jojo.my_letter.controller.service.LoginService;
import com.jojo.my_letter.model.entity.AccessLog;
import com.jojo.my_letter.model.entity.UserContext;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.jojo.my_letter.utils.Utils.toJson;

@Slf4j
@RequiredArgsConstructor
public class AccessLogFilter implements Filter {

    private final AccessLogService accessLogService;
    private final LoginService loginService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long startTime = System.currentTimeMillis(); // 시작시간
        LocalDateTime requestDate = LocalDateTime.now();
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String uri = httpRequest.getRequestURI();
        // 정적 파일 요청 제외
        if (uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".png") ||
                uri.endsWith(".jpg") || uri.endsWith(".jpeg") || uri.endsWith(".gif") || uri.endsWith(".ico")) {

            // 정적 파일 요청은 필터링만 하고 다음 필터로 넘김
            chain.doFilter(request, response);
            return;
        }

        AccessLog accessLog = new AccessLog();
        accessLog.setHost(httpRequest.getRemoteHost());
        accessLog.setClientIp(httpRequest.getRemoteAddr());
        accessLog.setUserAgent(httpRequest.getHeader("User-Agent"));
        accessLog.setUri(uri);
        accessLog.setMethod(httpRequest.getMethod());
        accessLog.setRequestAt(requestDate);
        accessLog.setReferer(httpRequest.getHeader("Referer"));

        /**
         * 사용자 request
         * 1 accessLogFilter
         * 2
         * 3
         *  -- controller return (json, html)
         */
        // 실행전
        // 다음 필터 실행
        chain.doFilter(request, response);

        // 실행후
        /**
         *  -- controller return (json, html)
         * 3
         * 2
         * 1 accessLogFilter
         * 사용자 response
         */

        long timeGap = System.currentTimeMillis() - startTime;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String memberId = (String)UserContext.getUserContext().get("memberId");
        Integer errorStatus = (Integer) UserContext.getUserContext().getOrDefault("errorStatus",0);

        accessLog.setMemberId(memberId);
        accessLog.setErrorStatus(errorStatus);
        accessLog.setElapsed(timeGap);
        accessLog.setResponseAt(LocalDateTime.now());
        accessLog.setStatus(String.valueOf(httpServletResponse.getStatus()));

        accessLogService.saveAccessLog(accessLog);

        log.info("AccessLog ==> {}", toJson(accessLog));
    }
}
