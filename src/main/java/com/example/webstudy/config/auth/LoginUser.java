package com.example.webstudy.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)                      //어노테이션이 생성될 위치를 지정한다. parameter = 파라미터로 선언된 객체에서만 사용할 수 있다.
@Retention(RetentionPolicy.RUNTIME)                 //어노테이션 유지 정책, 언제까지 살이있게 할 건지 설정
public @interface LoginUser {
}
