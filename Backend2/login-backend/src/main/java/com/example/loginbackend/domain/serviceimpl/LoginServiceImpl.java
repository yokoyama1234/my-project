package com.example.loginbackend.domain.serviceimpl;

import com.example.loginbackend.domain.exception.UnauthorizedException;
import com.example.loginbackend.domain.model.LoginUser;
import com.example.loginbackend.domain.repository.LoginRepository;
import com.example.loginbackend.domain.service.LoginService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    /** ユーザー情報を取得するためのリポジトリ */
    private final LoginRepository loginRepository;

    /**
     * 指定されたユーザーIDとパスワードでログイン認証を行う。
     *
     * @param userId   ユーザーID
     * @param password パスワード
     * @return 認証に成功した場合は {@link LoginUser} オブジェクト
     * @throws UnauthorizedException ユーザーIDまたはパスワードが正しくない場合に発生
     */
    @Override
    public LoginUser login(String userId, String password) {
        try {
            LoginUser user = loginRepository.findByUserIdAndPassword(userId, password);
            if (user == null) {
                throw new UnauthorizedException("login.failure");
            }
            return user;
        } catch (UnauthorizedException e) {
            throw e;
        }
    }
}
