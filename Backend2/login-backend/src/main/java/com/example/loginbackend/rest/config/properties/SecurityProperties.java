package com.example.loginbackend.rest.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.http.SessionCreationPolicy;

import java.util.List;

/**
 * Spring Security の設定値を application.yml から読み込むためのプロパティクラス。
 *
 * <p>
 * このクラスの値は {@link com.example.loginbackend.rest.config.SecurityConfig} で利用され、
 * 以下のようなセキュリティ設定を外部ファイルから制御できるようになります。
 * </p>
 * <ul>
 * <li>認証不要でアクセスを許可する URL の一覧（permitAll）</li>
 * <li>セッションの生成ポリシー（sessionPolicy）</li>
 * </ul>
 * <p>
 * 外部化することで、環境ごとに異なるセキュリティポリシーへ柔軟に対応できます。
 * </p>
 */
@Configuration
@ConfigurationProperties(prefix = "security")
@Data
public class SecurityProperties {
    private List<String> permitAll;
    private SessionCreationPolicy sessionPolicy;
}
