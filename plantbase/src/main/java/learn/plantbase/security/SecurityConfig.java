package learn.plantbase.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConverter converter;

    public SecurityConfig(JwtConverter converter) {
        this.converter = converter;
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors();

        // build antMatchers for each path in your controllers.
        http.authorizeRequests()
                .antMatchers("/authenticate", "/create_account").permitAll()
                .antMatchers(HttpMethod.GET, "/api/plants", /*Get by id*/ "/api/plants/*", "/api/plants/byMyGarden/*").permitAll() // anybody is able to hit this endpoint
                .antMatchers(HttpMethod.POST, "/api/plants").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/plants/*").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/plants/*").hasAnyRole("USER", "ADMIN")

                //my garden
                .antMatchers(HttpMethod.GET, "/api/my-garden", /*Get by id*/ "/api/my-garden/*", "/api/my-garden/from-planter/*").permitAll() // anybody is able to hit this endpoint
                .antMatchers(HttpMethod.POST, "/api/my-garden").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/my-garden/*").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/my-garden/*").hasAnyRole("USER", "ADMIN")

                //garden
                .antMatchers(HttpMethod.GET, "/api/garden", /*Get by id*/ "/api/garden/*").permitAll() // anybody is able to hit this endpoint

                //post
                .antMatchers(HttpMethod.GET, "/api/post", /*Get by id*/ "/api/post/*", "/api/post/planter/*", "/api/post/plant/*").permitAll() // anybody is able to hit this endpoint
                .antMatchers(HttpMethod.POST, "/api/post").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/post/*").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/post/*").hasAnyRole("USER", "ADMIN")

                //reply
                .antMatchers(HttpMethod.GET, "/api/reply", /*Get by id*/ "/api/reply/*", "/api/reply/post/*").permitAll() // anybody is able to hit this endpoint
                .antMatchers(HttpMethod.POST, "/api/reply").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/reply/*").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/reply/*").hasAnyRole("USER", "ADMIN")

                //role
                .antMatchers(HttpMethod.GET, "/api/role", /*Get by id*/ "/api/role/*").permitAll() // anybody is able to hit this endpoint

                //planter
                .antMatchers(HttpMethod.GET, "/api/planter", /*Get by id*/ "/api/planter/*").permitAll() // anybody is able to hit this endpoint
                .antMatchers(HttpMethod.POST, "/api/planter").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/planter/*").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/planter/*").hasAnyRole("USER", "ADMIN")

                .antMatchers("/**" /* any route in this path not explicitly defined above is denied */).denyAll()
                .and()
                .addFilter(new JwtRequestFilter(authenticationManager(), converter))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }
}
