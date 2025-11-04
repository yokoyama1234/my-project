package com.example.loginbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot アプリケーションのエントリポイント。
 * <p>
 * このクラスを実行することで、LoginBackend アプリケーションが起動します。
 * </p>
 *
 * <p>
 * 主な役割:
 * </p>
 * <ul>
 * <li>Spring Boot の自動設定を有効化する {@link SpringBootApplication} アノテーション</li>
 * <li>アプリケーションの起動</li>
 * </ul>
 *
 * @see org.springframework.boot.SpringApplication
 * @see org.springframework.boot.autoconfigure.SpringBootApplication
 */
@SpringBootApplication
public class ServerApplication {

	/**
	 * アプリケーションを起動するメインメソッド。
	 *
	 * @param args コマンドライン引数
	 */
	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}
}
