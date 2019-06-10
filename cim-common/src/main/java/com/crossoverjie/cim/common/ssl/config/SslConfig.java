package com.crossoverjie.cim.common.ssl.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Function: ssl配置
 *
 * @author xuliang
 * Date: 17/04/2018 15:48
 * @since JDK 1.8
 */
@Configuration
public class SslConfig {
    @Value("${ssl.useSsl}")
    private boolean useSsl;

    @Value("${ssl.keyStorePassword}")
    private String keyStorePassword;

    @Value("${ssl.keyManagerPassword}")
    private String keyManagerPassword;

    @Value("${ssl.certFile}")
    private String certFile;

    @Value("${ssl.sslEngineConfig.useOpenSsl}")
    private boolean useOpenSsl;


    public boolean isUseSsl() {
        return useSsl;
    }

    public void setUseSsl(boolean useSsl) {
        this.useSsl = useSsl;
    }

    public String getKeyStorePassword() {
        return keyStorePassword;
    }

    public void setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    public String getKeyManagerPassword() {
        return keyManagerPassword;
    }

    public void setKeyManagerPassword(String keyManagerPassword) {
        this.keyManagerPassword = keyManagerPassword;
    }

    public String getCertFile() {
        return certFile;
    }

    public void setCertFile(String certFile) {
        this.certFile = certFile;
    }

    public boolean isUseOpenSsl() {
        return useOpenSsl;
    }

    public void setUseOpenSsl(boolean useOpenSsl) {
        this.useOpenSsl = useOpenSsl;
    }
}
