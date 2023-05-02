package com.shoping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// Audutubg 가눙울 사용하기 위한 클래스
@Configuration
@EnableJpaAuditing // JPA의 Auditing 기능 활성화
public class AuditConfig {
    @Bean
    public AuditorAware<String> auditoProvider(){ // 등록자와 수정자를 처리해주는 AuditorAware 빈으로 등록
        return new AuditorAwareImpl();
    }
}
