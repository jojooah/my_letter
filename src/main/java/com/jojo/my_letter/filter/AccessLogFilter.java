package com.jojo.my_letter.filter;

import com.jojo.my_letter.model.entity.AccessLog;
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

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long startTime = System.currentTimeMillis(); // 시작시간
        LocalDateTime requestDate = LocalDateTime.now();
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String userAgentStr = httpRequest.getHeader("User-Agent");

        AccessLog accessLog = new AccessLog();
        accessLog.setHost(httpRequest.getRemoteHost());
        accessLog.setClientIp(httpRequest.getRemoteAddr());
        accessLog.setUserAgent(userAgentStr);
        accessLog.setUri(httpRequest.getRequestURI());
        accessLog.setMethod(httpRequest.getMethod());
        accessLog.setRequestAt(requestDate);
        accessLog.setReferer(httpRequest.getHeader("Referer"));

        // 다음 필터 실행
        chain.doFilter(request, response);

        long timeGap = System.currentTimeMillis() - startTime;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        accessLog.setElapsed(timeGap);
        accessLog.setResponseAt(LocalDateTime.now());
        accessLog.setStatus(String.valueOf(httpServletResponse.getStatus()));

        // todo 1: 꼭 필요한 정보만 남기도록 해주세요. (모든 URL을 남길필요는 없겠지요?)
        // todo 2: DB에 저장하도록 해주세요.
        log.info("AccessLog ==> {}", toJson(accessLog));
    }
}
