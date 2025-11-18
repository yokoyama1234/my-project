package com.example.loginbackend.rest.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import com.example.loginbackend.rest.config.properties.MessageProperties;

/**
 * メッセージリソース（messages.properties など）を管理するための設定クラス。
 * <p>
 * 本設定により {@link MessageSource} Bean がアプリケーション全体に提供され、
 * コントローラ・サービス・例外ハンドラーなどから国際化（i18n）メッセージを
 * 取得できるようになります。
 * </p>
 */
@Configuration
@RequiredArgsConstructor
public class MessageConfig {

    /** application.yml の message.* をマッピングしたプロパティクラス */
    private final MessageProperties messageProperties;

    /**
     * メッセージプロパティファイルを扱うための MessageSource Bean を生成。
     * <p>
     * {@link ReloadableResourceBundleMessageSource} はメッセージファイルを
     * キャッシュしつつ変更があればリロードできるため、開発効率が高い。
     * </p>
     *
     * <p>
     * Spring が各所で自動的にこの Bean を参照し、
     * 例外メッセージ・バリデーションメッセージ・画面文言などに利用される。
     * </p>
     *
     * @return MessageSource メッセージリソース管理用 Bean
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename(messageProperties.getBasename());

        messageSource.setDefaultEncoding(messageProperties.getEncoding());

        return messageSource;
    }
}
