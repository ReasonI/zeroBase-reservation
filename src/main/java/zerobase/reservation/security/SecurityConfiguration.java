package zerobase.reservation.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Slf4j
@Configuration
@EnableWebSecurity //Spring Security 설정을 활성화하는 어노테이션
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter authenticationFilter;

    /**
     * 어떤 경로를 허용하고 어떤 권한을 필요로 할 것인지 설정
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //보안 설정
        http.httpBasic(h -> h.disable())
                .csrf(cs -> cs.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .apply(new MyCustomDs1());

        //권한 허용할 api
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/**/signup", "/**/signin").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(this.authenticationFilter, UsernamePasswordAuthenticationFilter.class);
//                .formLogin(formLogin -> formLogin
//                        .loginPage("/login")
//                        .permitAll()
//                )
//                .rememberMe(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 인증 정보를 필요로 하지 않는 개발 관련 경로 설정
        return (web) -> web.ignoring().requestMatchers("/swagger-ui/**","/v3/api-docs/**");
    }

}
