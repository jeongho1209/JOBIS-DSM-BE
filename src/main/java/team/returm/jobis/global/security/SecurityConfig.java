package team.returm.jobis.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import team.returm.jobis.global.security.jwt.JwtTokenProvider;


import static team.returm.jobis.domain.user.domain.enums.Authority.COMPANY;
import static team.returm.jobis.domain.user.domain.enums.Authority.STUDENT;
import static team.returm.jobis.domain.user.domain.enums.Authority.TEACHER;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.
                csrf().disable()
                .cors().and()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()

                .authorizeRequests()

                //bookmarks
                .antMatchers(HttpMethod.POST, "/bookmarks/{recruitment-id}").hasAuthority(STUDENT.name())
                .antMatchers(HttpMethod.DELETE, "/bookmarks/{recruitment-id}").hasAuthority(STUDENT.name())
                .antMatchers(HttpMethod.GET, "/bookmarks").hasAuthority(STUDENT.name())

                //auth
                .antMatchers(HttpMethod.POST, "/auth/code").permitAll()
                .antMatchers(HttpMethod.PATCH, "/auth/code").permitAll()
                .antMatchers(HttpMethod.PUT, "/auth/reissue").permitAll()

                //students
                .antMatchers(HttpMethod.GET, "/students/main").hasAuthority(STUDENT.name())
                .antMatchers(HttpMethod.POST, "/students").permitAll()
                .antMatchers(HttpMethod.PATCH, "/students/password").permitAll()
                .antMatchers(HttpMethod.GET, "/students/exists").permitAll()

                //applications
                .antMatchers(HttpMethod.GET, "/applications/company").hasAuthority(COMPANY.name())
                .antMatchers(HttpMethod.GET, "/applications/students").hasAnyAuthority(STUDENT.name())
                .antMatchers(HttpMethod.POST, "/applications/{company-id}").hasAuthority(STUDENT.name())
                .antMatchers(HttpMethod.DELETE, "/applications/{application-id}").hasAuthority(STUDENT.name())
                .antMatchers(HttpMethod.GET, "/applications/{company-id}").hasAuthority(TEACHER.name())
                .antMatchers(HttpMethod.GET, "/applications/{recruitment-id}").hasAuthority(TEACHER.name())
                .antMatchers(HttpMethod.PATCH, "/applications/status").hasAnyAuthority(TEACHER.name())
                .antMatchers(HttpMethod.PATCH, "applications/train-date").hasAuthority(TEACHER.name())

                //companies
                .antMatchers(HttpMethod.GET, "/companies/teacher").hasAuthority(TEACHER.name())
                .antMatchers(HttpMethod.PATCH, "/companies/type").hasAuthority(TEACHER.name())
                .antMatchers(HttpMethod.PATCH, "/companies/mou").hasAuthority(TEACHER.name())
                .antMatchers(HttpMethod.POST, "/companies").permitAll()
                .antMatchers(HttpMethod.GET, "/companies/my").hasAuthority(COMPANY.name())
                .antMatchers(HttpMethod.PATCH, "/companies").hasAuthority(COMPANY.name())
                .antMatchers(HttpMethod.GET, "/companies/exists/{business-number}").permitAll()
                .antMatchers(HttpMethod.POST, "/companies/recruitment").hasAuthority(COMPANY.name())
                .antMatchers(HttpMethod.GET, "/companies/{company-id}").hasAnyAuthority(STUDENT.name(), TEACHER.name())
                .antMatchers(HttpMethod.GET, "/companies/student").hasAuthority(STUDENT.name())
                .antMatchers(HttpMethod.GET, "/companies/employment").hasAuthority(TEACHER.name())

                //users
                .antMatchers(HttpMethod.POST, "/users/login").permitAll()

                //files
                .antMatchers(HttpMethod.POST, "/files").permitAll()
                .antMatchers(HttpMethod.DELETE, "/files").permitAll()

                //recruitments
                .antMatchers(HttpMethod.POST, "/recruitments").hasAuthority(COMPANY.name())
                .antMatchers(HttpMethod.PATCH, "/recruitments/{recruitment-id}").hasAnyAuthority(COMPANY.name(), TEACHER.name())
                .antMatchers(HttpMethod.PATCH, "/recruitments/area/{recruit-area-id}").hasAnyAuthority(COMPANY.name(), TEACHER.name())
                .antMatchers(HttpMethod.POST, "/recruitments/{recruitment-id}/area").hasAnyAuthority(COMPANY.name(), TEACHER.name())
                .antMatchers(HttpMethod.GET, "/recruitments/student").hasAuthority(STUDENT.name())
                .antMatchers(HttpMethod.GET, "/recruitments/students/{recruitment-id}").hasAuthority(STUDENT.name())
                .antMatchers(HttpMethod.GET, "/recruitments/teacher").hasAuthority(TEACHER.name())
                .antMatchers(HttpMethod.PATCH, "/recruitments/status").hasAuthority(TEACHER.name())
                .antMatchers(HttpMethod.GET, "/recruitments/my").hasAuthority(COMPANY.name())
                .antMatchers(HttpMethod.DELETE, "/recruitments/{recruitment-id}").hasAnyAuthority(COMPANY.name(), TEACHER.name())
                .antMatchers(HttpMethod.DELETE, "/recruitments/area/{recruit-area-id}").hasAnyAuthority(COMPANY.name(), TEACHER.name())

                //code
                .antMatchers(HttpMethod.GET, "/code/tech").permitAll()
                .antMatchers(HttpMethod.GET, "/code/job").permitAll()
                .antMatchers(HttpMethod.GET, "/code/business-area").permitAll()

                //acceptance
                .antMatchers(HttpMethod.GET, "/acceptances/{company-id}").hasAuthority(TEACHER.name())
                .antMatchers(HttpMethod.PATCH, "/acceptances/field-train/{application-id}").hasAnyAuthority(TEACHER.name())
                .antMatchers(HttpMethod.PATCH, "/acceptances/contract-date").hasAuthority(TEACHER.name())
                .antMatchers(HttpMethod.POST, "/acceptances/employment").hasAnyAuthority(TEACHER.name())

                .anyRequest().authenticated()
                .and()
                .apply(new FilterConfig(jwtTokenProvider, objectMapper));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
