package com.gjthras08.http;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Set;

public class HttpRequest extends HttpMessage {
    
    private HttpMethod method;
    private String requestTarget;
    private String originalHttpVersion;
    private HttpVersion bestCompatibleVersion;
    private HashMap<String, String> headers = new HashMap<>();
    private String body = "";
    
    HttpRequest() {
    
    }
    
    public HttpMethod getMethod() {
        return method;
    }
    
    public String getRequestTarget() {
        return requestTarget;
    }
    
    public HttpVersion getBestCompatibleVersion() {
        return bestCompatibleVersion;
    }
    
    public String getOriginalHttpVersion() {
        return originalHttpVersion;
    }
    
    public Set<String> getHeaderNames() {
        return headers.keySet();
    }
    
    public String getHeader(String headerName) {
        return headers.get(headerName.toLowerCase());
    }
    
    public String getBody() {
        return body;
    }
    
    public byte[] getBodyBytes() {
        return body.getBytes(StandardCharsets.UTF_8);
    }
    
    public void setBody(String body) {
        this.body = body;
    }
    
    void setMethod(String methodName) throws HttpParsingException {
        for (HttpMethod method : HttpMethod.values()) {
           if (methodName.equals(method.name())) {
               this.method = method;
               return;
           }
        }
        throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
    }
    
    void setRequestTarget(String requestTarget) throws HttpParsingException {
        if (requestTarget == null || requestTarget.length() == 0) {
            throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_500_INTERNAL_SERVER_ERROR);
        }
        this.requestTarget = requestTarget;
    }
    
    void setHttpVersion(String originalHttpVersion) throws BadHttpVersionException, HttpParsingException {
        this.originalHttpVersion = originalHttpVersion;
        this.bestCompatibleVersion = HttpVersion.getBestCompatibleVerison(originalHttpVersion);
        if (this.bestCompatibleVersion == null) {
            throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED);
        }
    }
    
    void addHeader(String headerName, String headerField) {
        headers.put(headerName.toLowerCase(), headerField);
    }
}
