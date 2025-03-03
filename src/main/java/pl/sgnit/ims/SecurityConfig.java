package pl.sgnit.ims;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.sgnit.ims.service.AppUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder().encode("admin1234"))
                .roles("ADMIN");
        auth.userDetailsService(appUserDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/setPassword/**").authenticated()
                .antMatchers("/user/**").hasAnyAuthority("ROLE_ADMIN", "administrator")
                .antMatchers("/role/**").hasAnyAuthority("ROLE_ADMIN", "administrator")
                .antMatchers("/documents/**").hasAnyAuthority("ROLE_ADMIN", "administrator", "all_modules")
                .antMatchers("/audit/**").hasAnyAuthority("ROLE_ADMIN", "administrator", "all_modules")
                .antMatchers("/ncofi/**").hasAnyAuthority("ROLE_ADMIN", "administrator", "all_modules")
                .antMatchers("/process/**").hasAnyAuthority("ROLE_ADMIN", "administrator", "all_modules")
                .antMatchers("/scheduleaudit/**").hasAnyAuthority("ROLE_ADMIN", "administrator", "all_modules")
                .antMatchers("/scheduleperiod/**").hasAnyAuthority("ROLE_ADMIN", "administrator", "all_modules")
                .antMatchers("/").authenticated()
                .and().formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/loginSuccess", true)
                .failureUrl("/loginError")
                .and().logout().logoutSuccessUrl("/login");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AppUserDetailsService appUserDetailsService() {
        return new AppUserDetailsService();
    }
}
