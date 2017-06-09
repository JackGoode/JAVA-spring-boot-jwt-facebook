package com.eagulyi.facebook.service;


import com.eagulyi.facebook.model.entity.UserToken;
import com.eagulyi.facebook.repository.UserTokenRepository;
import com.eagulyi.facebook.util.Field;
import com.eagulyi.user.model.json.facebook.FacebookUserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by eugene on 1/20/17.
 */
@Service
public class DataProviderServiceImpl {
    @Value("${com.eagulyi.appId}")
    private String appId;

    @Value("${com.eagulyi.appSecret}")
    private String appSecret;

    @Value("${com.eagulyi.facebook-api-url}")
    private String facebookUrl;

    @Value("${com.eagulyi.facebook-api-version}")
    private String facebookApiVersion;

    private final RestTemplate template = new RestTemplate();
    private final UserTokenRepository userTokenRepository;

    @Autowired
    public DataProviderServiceImpl(UserTokenRepository userTokenRepository) {
        this.userTokenRepository = userTokenRepository;
    }

    FacebookUserData getForToken(UserToken userToken, Field... fields) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(facebookUrl)
                .path(facebookApiVersion + "/" + userToken.getFbId())
                .queryParam("fields", Arrays.stream(fields).map(Field::getName).collect(Collectors.joining(",")))
                .queryParam("access_token", userToken.getToken());

        ResponseEntity<FacebookUserData> response = template.getForEntity(builder.build().toUri(), FacebookUserData.class);

        if (!userTokenRepository.findByFbId(userToken.getFbId()).isPresent()) {
            userTokenRepository.save(userToken);
        }

        return response.getBody();
    }

    public FacebookUserData getForUsername(String username) throws IllegalArgumentException {
        Optional<UserToken> userToken = userTokenRepository.findByUsername(username);
        if (userToken.isPresent()) {
            return getForToken(userToken.get(), Field.FIRST_NAME, Field.LAST_NAME, Field.EMAIL, Field.EDUCATION, Field.WORK, Field.LOCATION);
        } else throw new IllegalArgumentException("token not in database, need to request new one");
    }

    public FacebookUserData getProfileForUsername(String username) throws IllegalArgumentException {
        Optional<UserToken> userToken = userTokenRepository.findByUsername(username);
        if (userToken.isPresent()) {
            return getForToken(userToken.get(), Field.FIRST_NAME, Field.LAST_NAME, Field.EMAIL, Field.LOCATION);
        } else throw new IllegalArgumentException("token not in database, need to request new one");
    }


//    private String getJsonFromUrl(String url) throws IOException {
//        InputStream is = new URL(url).openStream();
//        BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
//        return readAll(rd);
//    }
//
//    private static String readAll(Reader rd) throws IOException {
//        StringBuilder sb = new StringBuilder();
//        int cp;
//        while ((cp = rd.read()) != -1) {
//            sb.append((char) cp);
//        }
//        return sb.toString();
//    }


//    public FacebookUserData fetch(String facebookId) {
//        return new RestTemplate().getForObject(
//                createUrlOther(facebookId, user.getAccessToken())
//                , FacebookUserData.class);
//    }


//    List<Like> fetchLikes(String userId) throws IOException {
//        List<Like> likes = new ArrayList<>();
//        String url = createUrlLikes(userId);
//        ObjectMapper mapper = new ObjectMapper();
//
//        likes.addAll(mapper.readValue(getJsonFromUrl(url), ProfileLikes.class).getLikes());
//        String next = mapper.readValue(getJsonFromUrl(url), ProfileLikes.class).getPaging().getNext();
//        while (next != null && !next.isEmpty()) {
//            System.out.println("Fetched " + likes.size() + ". Next url is " + next);
//            likes.addAll(mapper.readValue(getJsonFromUrl(next), ProfileLikes.class).getLikes());
//            next = mapper.readValue(getJsonFromUrl(next), ProfileLikes.class).getPaging().getNext();
//        }
//
//        return likes;
//    }

//    private String createUrlLikes(String userId) {
//        String url = "";
//        url += facebookUrl;
//        url += userId;
//        url += "/likes?";
//        url += "access_token=";
//        url += appId + "|" + appSecret;
//        return url;
//    }


//    @RequestMapping("/me")
//    public @ResponseBody String serve() throws IOException {
//        String result = "";
//        result+= fetchOtherData("347759925585713", "EAATX5IE0C3gBAFbbIFX7AKkUwYllvEhbJflYCMnfFKo6xTDdZBPl5BOlZAO7YK6E9qxIgCrDlZAczw3bYRUh85R3GGamc51gCBiFTrPcNF3bwDXhGjoRp0dTqbqwiPkCcx2opkMG47dto7RuNEOXyh6vKZCpr4UZD");
//        result+=fetchLikes("347759925585713");
//        return result;
//    }

//    @RequestMapping("/likes")
//    public @ResponseBody String fetchLikes(@RequestParam(name = "user") String userId) throws IOException {
//        String result = "";
//        List<Like> likes = service.fetchLikes(userId);
//        for (Like like : likes) {
//            result += like.getName() + "</br>";
//        }
//        return result;
//    }
//
//    @RequestMapping("/other")
//    public @ResponseBody String fetchOtherData(@RequestParam(name = "user") String userId,
//                                               @RequestParam(name = "user") String accessToken) throws IOException {
//        return service.fetchOtherData(userId, accessToken).toString();
//    }
//

}
