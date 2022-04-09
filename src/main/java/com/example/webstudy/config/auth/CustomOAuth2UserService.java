package com.example.webstudy.config.auth;

import com.example.webstudy.config.auth.dto.OAuthAttributes;
import com.example.webstudy.config.auth.dto.SessionUser;
import com.example.webstudy.domain.user.Role;
import com.example.webstudy.domain.user.User;
import com.example.webstudy.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Optional;

//설정을 통해 가져온 사용자의 정보들을 기반으로 동작을 처리하는 서비스

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    /**
     * 네이버와 구글을 동시에 사용하려면 이를 구별해줄 수 있는 서비스를 구현해야 함.
     * 유저를 불러오면 판단해야 하기 때문에 loadUser를 재정의 해야함.
     * @throws OAuth2AuthenticationException : 인증이 실패 되어도 동작하는 서비스의 지장을 주면 안되기 때문에 로그인 실패 관련 Exception은 throw 함.
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {


        OAuth2UserService delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        //registrationId : 현재 로그인 진행중인 서비스를 구분하는 코드, 이후 네이버 로그인과 구글 로그인을 동시 지원할 때 사용된다.
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        /**
         * userNameAttributeName : OAuth2 로그인 진행 시 키가 되는 필드 값을 이야기 한다.
         * PK와 같은 의미.
         * 구글은 기본적으로 코드를 지원하지만 카카오나 네이버 등은 지원하지 않는다. 구글의 기본 코드는 'sub'이다.
         * 이후 구글 네이버를 동시 지원할 때 사용된다.
         */
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        /**
         * OAuthAttribute : OAUthUserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스이다. Map 형태이다.
         * 사용자 정보를 담을 Class 정의.
         * 이후 네이버 등 다른 소셜 로그인도 이 클래스를 사용한다.
         * (서비스 코드: 구글, PK, 로그인 유저 정보)
         */
        OAuthAttributes authAttributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        //가져온 데이터를 User 도메인에 저장한다.
        User user = saveOrUpdate(authAttributes);

        /**
         * SessionUser : 세션에 사용자 정보를 저장하기 위해 만든 Dto 클래스이다.
         * User 클래스를 쓰지 않는 이유 :
         */
        httpSession.setAttribute("user", new SessionUser(user));

        //로그인한 유저를 리턴함.
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                authAttributes.getAttributes(),
                authAttributes.getNameAttributeKey());
    }

    //User 저장하고 이미 있는 데이터명 Update
    private User saveOrUpdate(OAuthAttributes authAttributes){
        User user = userRepository.findByEmail(authAttributes.getEmail())
                .map(entity -> entity.update(authAttributes.getName(), authAttributes.getPicture()))
                .orElse(authAttributes.toEntity());

        return userRepository.save(user);
    }


}
