package com.hlc.security.core.social.view;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Liang on 2018/11/2.
 * 覆盖connect/status 实例
 * AbstractView实现了render方法，主要做的操作是将model中的参数和request中的参数全部都放到Request中，然后就转发Request就可以了。
 */
@Component("connect/status")
public class HlcConnectionStatusView extends AbstractView {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, List<Connection<?>>> connections = (Map<String, List<Connection<?>>>) map.get("connectionMap");
        Map<String, Boolean> statusmap = new HashMap<String, Boolean>();
        for (String key : connections.keySet()) {
            statusmap.put(key, CollectionUtils.isNotEmpty(connections.get(key)));
        }
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(mapper.writeValueAsString(statusmap));
    }

}
