package cn.kai.simple_project.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统工具类
 * Author: chenKai
 * Date: 2023/2/21
 */
@Slf4j
public class SystemUtil {

    /**
     * 获取真实客户端ip
     * @param request
     * @return
     */
    public static String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (org.apache.commons.lang3.StringUtils.isNotBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    /**
     * 方法内获取request
     * @return
     */
    public static HttpServletRequest getRequest(){
        try {
            ServletRequestAttributes request =  (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            return request.getRequest();
        }catch (Exception e){
            log.error("获取request失败，非HTTP请求");
            e.printStackTrace();
            return null;
        }
    }
}
