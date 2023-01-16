package cn.kai.simple_project.config.aop;

import cn.kai.simple_project.domain.SysLog;
import cn.kai.simple_project.mapper.SysLogMapper;
import cn.kai.simple_project.service.SysLogService;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * aop记录日志
 * 两层 第一层为controller 第二层为service
 * Author: chenKai
 * Date: 2023/1/14
 */
@Slf4j
@Aspect
@Component
public class AopByLog {

    /**
     * 最大入参长度
     */
    private static final int MAX_ARG_PRINT_LENGTH = 1024 * 200;
    private static final int LONG_ARG_PREVIEW_PRINT_LENGTH = 200;

    /**
     * controller 前缀
     */
    private static final String CONTROLLER_PREFIX = "C_";
    /**
     * service 前缀
     */
    private static final String SERVICE_PREFIX = "S_";

    /**
     * 黄色
     */
    private static final String ANSI_YELLOW = "\u001B[33m%s\u001B[0m";
    /**
     * 绿色
     */
    private static final String ANSI_GREEN = "\u001B[32m%s\u001B[0m";
    /**
     * 红色
     */
    private static final String ANSI_RED = "\u001B[31m%s\u001B[0m";
    private static final String LINE_TITLE = "[%s%s][%s]%s ";

    private static final String MARK_INPUT = String.format(ANSI_YELLOW, "<<< 参数");
    private static final String MARK_OUTPUT_SUCCESS = String.format(ANSI_GREEN, ">>> 成功");
    private static final String MARK_OUTPUT_FAILED = String.format(ANSI_RED, ">>> 失败");

    @Resource
    private ObjectMapper outputJackson;

    @Resource
    private SysLogService sysLogService;

    @Resource
    private SysLogMapper sysLogMapper;

    private final ObjectMapper inoutJson = initInputJackson();


    @Pointcut("within(cn.kai..controller..*)")
    public void controllerPointcut(){

    }

    @Pointcut("within(cn.kai..service..*)")
    public void servicePointcut(){

    }


    @Around("controllerPointcut()")
    public Object controllerLog(ProceedingJoinPoint joinPoint) throws Throwable {
        return doInputLog(joinPoint,CONTROLLER_PREFIX);
    }

    private Object doInputLog(ProceedingJoinPoint joinPoint, String prefix) throws Throwable {

        Date now = new Date();
        /**
         * 获取目标方法类
         */
        MethodSignature signature =  (MethodSignature)joinPoint.getSignature();

        String requestUrl = null; //请求url
        String targetClass = joinPoint.getTarget().getClass().getName(); //目标类
        String targetMethod = signature.getName(); //目标方法
        String requestParam = null; //请求参数
        String operateOs = null; //操作系统
        String operateBrowser = null; //操作浏览器
        String operateBrowserVersion = null; //操作浏览器版本
        String operateClientType = null; //客户端类型  手机、电脑、平板
        String clientIp = null; //客户端ip

        HttpServletRequest request = getRequest();
        if (Objects.nonNull(request)){
            //获取请求url
            requestUrl = request.getRequestURI();
            //获取客户端真实地址
            clientIp = getRealIp(request);
            UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("user-agent"));
            //获取客户端类型
            operateClientType = userAgent.getOperatingSystem().getDeviceType().getName();
            //获取客户端系统
            operateOs = userAgent.getOperatingSystem().getName();
            //获取浏览器
            operateBrowser =  userAgent.getBrowser().toString();
            //获取浏览器版本
//            operateBrowserVersion = StringUtils.isNotEmpty(userAgent.getBrowserVersion().toString()) ? userAgent.getBrowserVersion().toString() : "";
        }

        String typeName = signature.getDeclaringType().getName();
        String method = String.format(ANSI_YELLOW, "[" + targetMethod + "]").concat(".").concat(typeName);

        //开始获取入参
        String[] parameterNames = signature.getParameterNames();
        Class[] parameterTypes = signature.getParameterTypes();
        Class returnType = signature.getReturnType();
        Object[] args = joinPoint.getArgs();
        List<String> argsList = new ArrayList<>();

        for (int i = 0; i < parameterNames.length; i++) {
            String argValue = inoutJson.writeValueAsString(args[i]);
            if (argValue != null) {
                int argValueLength = argValue.length();
                if (argValueLength > MAX_ARG_PRINT_LENGTH) {
                    argValue = argValue.substring(0, LONG_ARG_PREVIEW_PRINT_LENGTH) + "...(length: " + argValueLength + ")";
                }
            }
            argsList.add("[" + parameterTypes[i].getSimpleName() + "]" + parameterNames[i] + "=" + argValue);
        }

        String title = String.format(LINE_TITLE,prefix,now,"操作类型",method);

        requestParam = String.join(",", argsList);

        log.info(title + MARK_INPUT +"{{}}",requestParam);

        //可把日志信息记录进 mongodb 或者 clickHouse
        SysLog sysLog = new SysLog();
        sysLog.setClientType(operateClientType);
        sysLog.setClientIp(clientIp);
        sysLog.setCreateTime(now);
        sysLog.setOperateBrowser(operateBrowser);
        sysLog.setOperateOs(operateOs);
        sysLog.setRequestUrl(requestUrl);
        sysLog.setTargetClass(targetClass);
        sysLog.setTargetMethod(targetMethod);
        sysLogMapper.saveLog(sysLog);

        //获取返回数据
        try {
            Object result = joinPoint.proceed();
            log.info(title + MARK_OUTPUT_SUCCESS + ". 返回结果: [{}]{}", returnType.getSimpleName(), outputJackson.writeValueAsString(result));
            return result;
        }catch (Throwable throwable){
            log.info(title + MARK_OUTPUT_FAILED + ". {}", throwable.toString());
            throw throwable;
        }
    }

    /**
     * 获取客户端真实地址
     * @param request
     * @return
     */
    private String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
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
    private HttpServletRequest getRequest(){
        try {
            ServletRequestAttributes request =  (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
            return request.getRequest();
        }catch (Exception e){
            log.error("获取request失败，非HTTP请求");
            e.printStackTrace();
            return null;
        }
    }


    private ObjectMapper initInputJackson() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        //custom serialize
        builder.serializerByType(MultipartFile.class, new JsonSerializer<MultipartFile>() {
            @Override
            public void serialize(MultipartFile value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeStartObject();
                gen.writeStringField("originalFilename", value.getOriginalFilename());
                gen.writeNumberField("size", value.getSize());
                gen.writeStringField("contentType", value.getContentType());
                gen.writeEndObject();
            }
        });
        Stream.of(
                ServletRequest.class,
                ServletResponse.class,
                byte[].class,
                Workbook.class,
                HSSFWorkbook.class,
                XSSFWorkbook.class,
                Throwable.class
        ).forEach(clazz -> builder.serializerByType(clazz, new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeString("[...]");
            }
        }));

        ObjectMapper result = builder.build();
        result.disable(MapperFeature.USE_ANNOTATIONS);
        return result;
    }
}
