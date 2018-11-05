package com.hlc.security.browser.session;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Liang on 2018/11/5.
 * 当Session并发时，后面Session踢掉前面Session，对前面用户做的处理策略
 */
public class HlcExpiredSessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {

    public HlcExpiredSessionStrategy(String invalidSessionUrl) {
        super(invalidSessionUrl);
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        onSessionInvalid(event.getRequest(), event.getResponse());
    }

    /**
     * 覆盖父类方法并发引起的session过期
     * @return
     */
    @Override
    protected boolean isConcurrency() {
        return true;
    }

}
