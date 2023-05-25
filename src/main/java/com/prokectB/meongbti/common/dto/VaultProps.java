package com.prokectB.meongbti.common.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("vault.props")
@Data
public class VaultProps {
    private String host;
    private int port;
    private String schema;
    private String roleId;
    private String secretId;
    private String mainPath;
    private String subPath;
}