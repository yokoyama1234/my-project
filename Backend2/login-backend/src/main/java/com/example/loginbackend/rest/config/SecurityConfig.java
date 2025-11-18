package com.example.loginbackend.rest.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.example.loginbackend.rest.config.properties.SecurityProperties;

/**
 * Spring Security の全体設定を行うコンフィグクラス。
 * <p>
 * セキュリティポリシー（CSRF / CORS / 認可設定 / セッション管理など）を
 * まとめて構成し、アプリケーション全体の HTTP セキュリティ挙動を制御する。
 * </p>
 *
 * <p>
 * 設定値は {@link SecurityProperties} により外部設定化されており、
 * application.yml で柔軟に変更可能。
 * </p>
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

        /** application.yml の security.* をマッピングした設定プロパティ */
        private final SecurityProperties securityProperties;

        /**
         * HTTP リクエストに対するセキュリティ設定を構築する。
         *
         * <p>
         * 主な設定内容:
         * <ul>
         * <li>CSRF の無効化（API では一般的）</li>
         * <li>CORS を有効化（設定は CorsConfig 側に委譲）</li>
         * <li>permitAll の URL を外部設定から読み込んで許可</li>
         * <li>その他の URL はすべて認証必須</li>
         * <li>フォームログインを無効化（REST API のため）</li>
         * <li>セッションポリシーの外部設定（STATELESS / IF_REQUIREDなど）</li>
         * </ul>
         * </p>
         *
         * @param http HttpSecurity オブジェクト（Spring が DI で提供）
         * @return SecurityFilterChain 構築済みのフィルタチェーン
         * @throws Exception Spring Security の設定処理で発生しうる例外
         */
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                http
                                .csrf(csrf -> csrf.disable())
                                .cors(cors -> {
                                })
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(securityProperties.getPermitAll()
                                                                .toArray(new String[0]))
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .formLogin(form -> form.disable())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(securityProperties.getSessionPolicy()));
                return http.build();
        }
}
