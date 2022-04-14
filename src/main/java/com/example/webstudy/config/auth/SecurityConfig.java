package com.example.webstudy.config.auth;


import com.example.webstudy.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//설정 코드 : 설정 코드에서 구글 로긍니 이후 가져온 사용자의 정보들을 기반으로 가입 및 정보 수정, 세션 저장 등의 기능을 지원한다.

@RequiredArgsConstructor
@EnableWebSecurity  //스프링 시큐리티 설정들을 활성화 시켜준다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;


    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable().headers().frameOptions().disable()//h2 콘솔화면을 이용하기 위해 해당 옵션들을 disable 해준다.
                .and()
                    .authorizeRequests()//Url별 권한 관리를 설정하는 옵션의 시작점, 시작점을 선언해야만 AndMatcher를 선언할 수 있다.
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/app/**").permitAll()//권한 관리 대상을 지정하는 옵션, permitAll() 로 전체 열람 권한을 줌
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())//지정한 주소를 가진 API는 USER 권한을 가진 사람만 가능하도록 함
                    .anyRequest().authenticated()//설정 이외 나머지 URL을 나타냄, authenticated() : 나머지 URL 들은 모두 인증된 사용자들에게만 허용된다.
                .and()
                    .logout()//로그아웃 기능에 대한  여러 설정의 진입점
                        .logoutSuccessUrl("/")//로그아웃 성공시 '/' 주소로 이동
                .and()
                    .oauth2Login()//Oauth2 로그인 기능에 대한 여러 설정의 진입점이다.
                        .userInfoEndpoint()//Oauth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당한다, 로그인 성공시 아래 service 진입하겠다
                            .userService(customOAuth2UserService);  //소셜 로그인 성공 시 후속 조치를 진행할 userService인터페이스의 구현체를 등록한다.
                                                                    //리소스 서버(소셜 서비스)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시할 수 있다.

        //설정

    }
}
