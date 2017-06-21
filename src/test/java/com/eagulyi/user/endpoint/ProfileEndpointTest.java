package com.eagulyi.user.endpoint;

import com.eagulyi.security.model.UserContext;
import com.eagulyi.security.model.token.JwtToken;
import com.eagulyi.security.model.token.JwtTokenFactory;
import com.eagulyi.user.entity.User;
import com.eagulyi.user.model.json.signup.SignUpForm;
import com.eagulyi.user.repository.UserRepository;
import com.eagulyi.user.service.UserService;
import io.jsonwebtoken.lang.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by eugene on 6/17/17.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class ProfileEndpointTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    private MockMvc mockMvc;
    private JwtToken token;
    private User user;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private JwtTokenFactory tokenFactory;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        this.user = new User();
        this.user.setFirstName("Eugene");
        this.user.setLastName("Gulyi");
        this.user.setUsername("test@gmail.com");
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
        UserContext userContext = UserContext.create(this.user.getUsername(), authorities);
        this.token = tokenFactory.createAccessJwtToken(userContext);

        userService.createDefaultUser(this.user);
    }

    @After
    public void destroy() {
        userRepository.deleteByUsername("test@gmail.com");
    }


    @Test
    public void incorrectTokenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/profile/get")
                .header("X-Authorization", "Bearer " + this.token.getToken().substring(1)))
                .andExpect(status().is(401));
    }

    @Test
    public void noTokenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/profile/get"))
                .andExpect(status().is(401));
    }

    @Test
    public void readOwnProfile() throws Exception {
        mockMvc.perform(get("/api/profile/get")
                .header("X-Authorization", "Bearer " + this.token.getToken()))
                .andExpect(content().contentType(contentType))
                .andExpect(status().is2xxSuccessful());
    }

    //*
    // Simulate filling sign up form (adding a city of residence)
    // tests if the profile is saved
    // */
    @Test
    public void saveOwnProfile() throws Exception {
        SignUpForm form = new SignUpForm();
        form.setFirstName(this.user.getFirstName());
        form.setLastName(this.user.getLastName());
        form.setEmail(this.user.getUsername());
        form.setCity("Kyiv");

        mockMvc.perform(post("/api/profile/save")
                .header("X-Authorization", "Bearer " + this.token.getToken())
                .content(json(form))
                .contentType(contentType))
                .andExpect(status().is2xxSuccessful());

        User user = userRepository.findByUsername(this.user.getUsername()).get();
        Assert.isTrue(user.getLocation().getCity().getName().equals("Kyiv"));
    }

    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }


}