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
//                .antMatchers("/jquery/**").permitAll()
//                .antMatchers("/css/**").permitAll()
//                .antMatchers("/bootstrap/**").permitAll()
//                .antMatchers("/").authenticated()
//                .antMatchers("/audit/**").hasAnyAuthority("all_modules", "audit")
//                .antMatchers("/ncofi/**").hasAnyAuthority("all_modules", "audit")
//                .antMatchers("/process/**").hasAnyAuthority("all_modules", "process")
//                .antMatchers("/scheduleaudit/**").hasAnyAuthority("all_modules")
//                .antMatchers("/scheduleperiod/**").hasAnyAuthority("all_modules")
//                .antMatchers("/**").hasAnyAuthority("ROLE_ADMIN", "administrator")

                .antMatchers("/user/**").hasAnyAuthority("ROLE_ADMIN", "administrator")
                .antMatchers("/role/**").hasAnyAuthority("ROLE_ADMIN", "administrator")
                .antMatchers("/documents/**").hasAnyAuthority("ROLE_ADMIN", "administrator", "all_modules")
                .antMatchers("/audit/**").hasAnyAuthority("ROLE_ADMIN", "administrator", "all_modules")
                .antMatchers("/ncofi/**").hasAnyAuthority("ROLE_ADMIN", "administrator", "all_modules")
                .antMatchers("/process/**").hasAnyAuthority("ROLE_ADMIN", "administrator", "all_modules")
                .antMatchers("/scheduleaudit/**").hasAnyAuthority("ROLE_ADMIN", "administrator", "all_modules")
                .antMatchers("/scheduleperiod/**").hasAnyAuthority("ROLE_ADMIN", "administrator", "all_modules")
                .antMatchers("/").hasAnyAuthority("ROLE_ADMIN", "administrator", "all_modules")
                .and().formLogin()
                .loginPage("/login")
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
