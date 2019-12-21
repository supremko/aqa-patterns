import lombok.*;
import com.github.javafaker.*;

import java.util.Locale;

public class Info {
    private Info() {}
    private static Faker faker = new Faker(new Locale("ru"));

    @Value
    public static class AuthInfo {
        private String url;
        private String city;
        private String name;
        private String phone;
    }
    public static AuthInfo getAuthInfo() {
        return new AuthInfo("http://localhost:9999","Санкт-Петербург", faker.name().fullName(),
                faker.phoneNumber().phoneNumber());
    }
}
