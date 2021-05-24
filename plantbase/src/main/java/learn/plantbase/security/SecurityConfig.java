package learn.plantbase.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
//
//    @Autowired
//    private PasswordEncoder encoder;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        User.UserBuilder userBuilder = User.withUsername("user")
//                .password("user").passwordEncoder(password -> encoder.encode(password))
//                .roles("USER");
//
//        User.UserBuilder adminBuilder = User.withUsername("admin")
//                .password("admin").passwordEncoder(password -> encoder.encode(password))
//                .roles("ADMIN");
//
//        auth.inMemoryAuthentication()
//                .withUser(userBuilder)
//                .withUser(adminBuilder);
//    }

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
                .antMatchers(HttpMethod.POST, "/api/plants").hasAnyRole("USER")
                .antMatchers(HttpMethod.PUT, "/api/plants/*").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/plants/*").permitAll()

                //my garden
                .antMatchers(HttpMethod.GET, "/api/my-garden", /*Get by id*/ "/api/my-garden/*", "/api/my-garden/from-planter/*").permitAll() // anybody is able to hit this endpoint
                .antMatchers(HttpMethod.POST, "/api/my-garden").hasAnyRole("USER")
                .antMatchers(HttpMethod.PUT, "/api/my-garden/*").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/my-garden/*").permitAll()

                //garden
                .antMatchers(HttpMethod.GET, "/api/garden", /*Get by id*/ "/api/garden/*").permitAll() // anybody is able to hit this endpoint

                //post
                .antMatchers(HttpMethod.GET, "/api/post", /*Get by id*/ "/api/post/*", "/api/post/planter/*", "/api/post/plant/*").permitAll() // anybody is able to hit this endpoint
                .antMatchers(HttpMethod.POST, "/api/post").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/post/*").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/post/*").permitAll()

                //reply
                .antMatchers(HttpMethod.GET, "/api/reply", /*Get by id*/ "/api/reply/*", "/api/reply/post/*").permitAll() // anybody is able to hit this endpoint
                .antMatchers(HttpMethod.POST, "/api/reply").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/reply/*").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/reply/*").permitAll()

                //role
                .antMatchers(HttpMethod.GET, "/api/role", /*Get by id*/ "/api/role/*").permitAll() // anybody is able to hit this endpoint

                //planter
                .antMatchers(HttpMethod.GET, "/api/planter", /*Get by id*/ "/api/planter/*").permitAll() // anybody is able to hit this endpoint
                .antMatchers(HttpMethod.POST, "/api/planter").hasAnyRole("USER")
                .antMatchers(HttpMethod.PUT, "/api/planter/*").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/planter/*").permitAll()

                .antMatchers("/**" /* any route in this path not explicitly defined above is denied */).denyAll()
                .and()
                .addFilter(new JwtRequestFilter(authenticationManager(), converter))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }
}
