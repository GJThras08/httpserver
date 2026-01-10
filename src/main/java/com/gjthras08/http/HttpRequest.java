package com.gjthras08.http;

public class HttpRequest extends HttpMessage {
    
    private HttpMethod method;
    private String requestTarget;
    private String originalHttpVersion;
    private HttpVersion bestCompatibleVersion;
    
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
}
