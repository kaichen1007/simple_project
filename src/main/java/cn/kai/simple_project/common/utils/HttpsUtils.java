package cn.kai.simple_project.common.utils;

/**
 * Author: chenKai
 * Date: 2022/12/31
 */

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * http 工具类
 */
public class HttpsUtils {

    private static HttpClient secureClient = null;
    private static HttpClient insecureClient = null;

    private static Logger logger = LoggerFactory.getLogger(HttpsUtils.class);

    private static final int POOL_MAX_PER_ROUTE = 15;
    private static final int POOL_MAX_TOTAL = 100;
    private static final int CONNECTION_REQUEST_TIMEOUT = 1 * 60 * 1000;//连接池分配连接 timeout
    private static final int CONNECT_TIMEOUT = 30 * 1000;//连接部署 timeout
    private static final int SOCKET_TIMEOUT = 3 * 60 * 1000;//数据传输 timeout

    static {
        //connectionManager
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
        manager.setDefaultMaxPerRoute(POOL_MAX_PER_ROUTE);
        manager.setMaxTotal(POOL_MAX_TOTAL);
        //requestConfig
        RequestConfig config = RequestConfig
                .custom()
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .build();
        //secureClient
        secureClient = HttpClients
                .custom()
                .setConnectionManager(manager)
                .setDefaultRequestConfig(config)
                .disableAutomaticRetries()
                .build();
        try {
            //insecureClient
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {}
                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {}
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[] { tm }, null);
            SSLConnectionSocketFactory sf = new SSLConnectionSocketFactory(ctx, NoopHostnameVerifier.INSTANCE);
            RegistryBuilder<ConnectionSocketFactory> schemeRegistry = RegistryBuilder.create();
            Registry<ConnectionSocketFactory> registry = schemeRegistry
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", sf)
                    .build();
            //insecure connectionManager
            PoolingHttpClientConnectionManager insecureManager = new PoolingHttpClientConnectionManager(registry);
            insecureManager.setDefaultMaxPerRoute(POOL_MAX_PER_ROUTE);
            insecureManager.setMaxTotal(POOL_MAX_TOTAL);
            insecureClient = HttpClients
                    .custom()
                    .setConnectionManager(insecureManager)
                    .setDefaultRequestConfig(config)
                    .disableAutomaticRetries()
                    .build();
        } catch (Exception e) {
            logger.error("init insecureClient fail");
        }
    }

    public static String get(String url) throws IOException {
        return get(url, null);
    }

    /**
     * 模拟GET请求
     *
     * @param url
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static String get(String url, Header header) throws IOException {
        logger.info("GET: {}", url);
        if (header != null) {
            logger.info("header: {}", header);
        }
        HttpGet get = new HttpGet(url);
        String data = null;
        try {
            if (header != null) {
                get.setHeader(header);
            }
            //保持原逻辑，使用insecureClient
            HttpResponse response = insecureClient.execute(get);
            logger.info("status: {}", response.getStatusLine().toString());
            HttpEntity entity = response.getEntity();
            BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
            StringBuffer out = new StringBuffer();
            String buffer = null;
            while ((buffer = reader.readLine()) != null) {
                out.append(buffer);
            }
            data = out.toString();
            logger.info("GET: {} >>> data:{}", url, data);
            get.abort();
        } finally {
            get.releaseConnection();
        }
        return data;
    }

    public static String initGetParams(String... input) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < input.length; i += 2) {
            try {
                result.add(input[i] + "=" + URLEncoder.encode(input[i + 1], "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                //skip
            }
        }
        return StringUtils.join(result, "&");
    }

    public static UrlEncodedFormEntity initUrlEncodedForm(String ...input) {
        List<NameValuePair> list = new ArrayList<>();
        for (int i = 0; i < input.length; i += 2) {
            list.add(new BasicNameValuePair(input[i], input[i+1]));
        }
        try {
            return new UrlEncodedFormEntity(list, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.warn("", e);
        }
        return null;
    }

    @SuppressWarnings({"rawtypes", "unchecked", "resource"})
    public static String post(String targetURL, Object data) {
        HttpPost post = null;
        try {
            logger.info("targetURL: {}", targetURL);

            ObjectMapper jsonOM = new ObjectMapper();
            String reqPkg = jsonOM.writeValueAsString(data);
            logger.info("req: {}", reqPkg);

            // 封装请求数据
            List<NameValuePair> paramList = new ArrayList<NameValuePair>();
            Map dataMap = null;
            if (data instanceof String) {
                NameValuePair param = new BasicNameValuePair("k",
                        String.valueOf(data));
                paramList.add(param);
            } else if (data instanceof Map) {
                dataMap = (HashMap) data;
            } else {
                dataMap = BeanUtils.describe(data);
            }
            // 转换KV
            if (dataMap != null) {
                Iterator<String> ite = dataMap.keySet().iterator();
                while (ite.hasNext()) {
                    String k = ite.next();
                    Object v = dataMap.get(k);
                    if (v == null) {
                        continue;
                    }
                    NameValuePair param = new BasicNameValuePair(k, String.valueOf(v));
                    paramList.add(param);
                }
            }

            if (paramList.isEmpty()) {
                NameValuePair param = new BasicNameValuePair("k", "1");
                paramList.add(param);
            }
            // 初始化POST方式
            post = new HttpPost(targetURL);
            // 设置消息体
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,
                    "UTF-8");
            post.setEntity(entity);

            // 发送消息
            HttpResponse response = secureClient.execute(post);
            int respCode = response.getStatusLine().getStatusCode();
            if (respCode >= 100 && respCode < 300) {
                // 消息应答
                HttpEntity respBody = response.getEntity();
                String respPkg = EntityUtils.toString(respBody, "UTF-8");
                logger.info("resp: {}", respPkg);
                return respPkg;
            } else {
                logger.info("resp statusLine: {}", response.getStatusLine());
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (post != null) {
                post.releaseConnection();
            }
        }
        return null;
    }

    public static String postHead(String targetURL, JSONObject data, JSONObject head) throws IOException {
        HttpPost post = null;
        try {
            logger.info("targetURL: {}", targetURL);
            // 初始化POST方式
            post = new HttpPost(targetURL);
            // 设置请求头
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-Type", "application/json;charset=UTF-8");
            if (head != null) {
                logger.info("head: {}", head.toJSONString());
                for (Map.Entry entry : head.entrySet()) {
                    post.addHeader((String) entry.getKey(), (String) entry.getValue());
                }
            }
            //设置超时信息
            // 封装请求数据
            if (data != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                logger.info("req: {}", data.toJSONString());
                //设置消息体
                HttpEntity entity = new StringEntity(data.toJSONString(), "UTF-8");
                post.setEntity(entity);
            }
            // 发送消息
            HttpResponse response = secureClient.execute(post);
            int respCode = response.getStatusLine().getStatusCode();
            if (respCode >= 100 && respCode < 300) {
                // 消息应答
                HttpEntity respBody = response.getEntity();
                String respPkg = EntityUtils.toString(respBody, "UTF-8");
                logger.info("resp: {}", respPkg);
                return respPkg;
            } else {
                logger.info("resp statusLine: {}", response.getStatusLine()); }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (post != null) {
                post.releaseConnection();
            }
        }
        return null;
    }


    public static Map<String, Object> initPostParams(String... input) {
        Map<String, Object> result = new HashMap<>();
        for (int i = 0; i < input.length; i += 2) {
            result.put(input[i], input[i + 1]);
        }
        return result;
    }

    public static byte[] getBytes(String url) {
        return getBytes(url, null);
    }

    public static byte[] getBytes(String url, JSONObject requestBody) {
        String jsonStr = requestBody == null ? null : JSONObject.toJSONString(requestBody);
        logger.info("download: {}, req: {}", url, jsonStr);
        // 响应内容
        byte[] bs = null;
        try {
            HttpRequestBase req = null;
            if (requestBody == null) {
                req = new HttpGet(url);
            } else {
                req = new HttpPost(url);
                StringEntity reqEntity = new StringEntity(jsonStr, ContentType.APPLICATION_JSON);
                ((HttpPost) req).setEntity(reqEntity);
            }
            //保持原逻辑，使用insecureClient
            HttpResponse response = insecureClient.execute(req);
            // 获取响应实体
            HttpEntity entity = response.getEntity();
            bs = IOUtils.toByteArray(entity.getContent());
            if (null != entity) {
                EntityUtils.consume(entity); // Consume response content
            }
        } catch (Exception e) {
            logger.error("download", e);
        }
        logger.info("download success. size: {}", bs.length);
        return bs;
    }


    public static byte[] doGetCallByteArr(String url, Map<String, String> param) {

        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        byte[] resultString = null;
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 执行请求
            response = httpClient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {

                resultString = EntityUtils.toByteArray(response.getEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

     public static String doGetCallString(String url, Map<String, String> param) {

            // 创建Httpclient对象
            CloseableHttpClient httpClient = HttpClients.createDefault();

            String resultString = null;
            CloseableHttpResponse response = null;
            try {
                // 创建uri
                URIBuilder builder = new URIBuilder(url);
                if (param != null) {
                    for (String key : param.keySet()) {
                        builder.addParameter(key, param.get(key));
                    }
                }
                URI uri = builder.build();

                // 创建http GET请求
                HttpGet httpGet = new HttpGet(uri);

                // 执行请求
                response = httpClient.execute(httpGet);
                // 判断返回状态是否为200
                if (response.getStatusLine().getStatusCode() == 200) {

                    resultString = EntityUtils.toString(response.getEntity());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (response != null) {
                        response.close();
                    }
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return resultString;
        }

    }
