package com.example.webstudy.config.auth;

import com.example.webstudy.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;


/**
 * HandlerMethodArgumentResolver : 이 인터 페이스는 한가지 기능을 지원한다. 조건에 맞는 메서드가 있다면 구현체가 지정한 값으로 해당 메서드를 파라미터로 넘길 수 있다.
 */
@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;


    /**
     * supportParameter() : 컨트롤러 메서드의 특정 파라미터를 지원하는지 판단한다. 여기서는 @LoginUser 어노테이션이 붙어있고, 파라미터 클래스 타입이 SessionUser.class인 경우 true를 반환한다.
     * supportParameter가 true이면 아래 resolverArgument 메서드가 실행된다.
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        //파라미터로 받은 세션 타입이 널이 아닌지 체크
        boolean isLoginUesrAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;  //LoginUser 어노테이션이 붙은 parameter를 찾는다. 누가? resolver가

        //파라미터로 받은 세션 타입이 같은지 체크
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());

        return isLoginUesrAnnotation &&isUserClass;
    }

    /**
     * resolveArgument() : 파라미터에 전달할 객체를 생성해준다.
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {


        //위 조건이 맞다면 세션에서 user 키로 된 로그인 정보를 반환
        return httpSession.getAttribute("user");
    }
}
