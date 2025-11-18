package com.example.loginbackend.rest.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.loginbackend.rest.config.properties.WebProperties;

/**
 * Web アプリケーションにおける CORS 設定を行うコンフィグクラス。
 * <p>
 * このクラスで設定した CORS ポリシーは Spring MVC のコントローラに対して適用され、
 * フロントエンド（SPA 等）からのクロスオリジンアクセスを制御します。
 * </p>
 *
 * <p>
 * 设置値は {@link WebProperties} から外部設定として読み込まれるため、
 * application.yml の編集のみで CORS 設定を切り替えることができます。
 * </p>
 *
 * <p>
 * CORS が正しく設定されていないと、ブラウザがリクエストをブロックし
 * API が通信できないため、SPA 構成では必須の設定です。
 * </p>
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig {

    /**
     * 空の String 配列。
     * <p>
     * List から String 配列へ変換する際の toArray() のために使用する定数。
     * 可読性と GC 削減のために定義している。
     * </p>
     */
    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    /** application.yml の web.* をマッピングしたプロパティクラス */
    private final WebProperties webProperties;

    /**
     * CORS 設定を Spring MVC に適用するための WebMvcConfigurer Bean。
     * 
     * @return WebMvcConfigurer CORS 設定を含む設定オブジェクト
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping(webProperties.getMapping())
                        .allowedOrigins(webProperties.getAllowedOrigins()
                                .toArray(EMPTY_STRING_ARRAY))
                        .allowedMethods(webProperties.getAllowedMethods()
                                .toArray(EMPTY_STRING_ARRAY))
                        .allowedHeaders(webProperties.getAllowedHeaders()
                                .toArray(EMPTY_STRING_ARRAY))
                        .allowCredentials(webProperties.isAllowCredentials());
            }
        };
    }
}
