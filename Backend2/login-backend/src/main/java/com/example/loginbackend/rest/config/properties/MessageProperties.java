package com.example.loginbackend.rest.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * メッセージリソース（messages.properties 等）の設定値を保持するプロパティクラス。
 *
 * <p>
 * application.yml の `message.*` の値を自動でマッピングし、
 * {@link com.example.loginbackend.rest.config.MessageConfig} で利用されます。
 * </p>
 * <p>
 * 実務上はメッセージの多言語化（i18n）や例外レスポンスの文言管理に利用されます。
 * </p>
 */
@Configuration
@ConfigurationProperties(prefix = "message")
@Data
public class MessageProperties {
    private String basename;
    private String encoding;
}
