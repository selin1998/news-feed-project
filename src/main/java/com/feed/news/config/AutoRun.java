package com.feed.news.config;

import com.feed.news.entity.News;

import com.feed.news.repository.NewsRepo;
import com.feed.news.repository.UserRepo;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Log4j2
@Configuration
public class AutoRun {

    private final UserRepo userRepo;
    private final NewsRepo newsRepo;

    public AutoRun(UserRepo userRepo, NewsRepo newsRepo) {

        this.userRepo = userRepo;

        this.newsRepo = newsRepo;
    }

    private static String fmt(String format, Object... args) {
        return String.format(format, args);
    }

    @SneakyThrows
    public byte[] convertImagetoByteArray(String path) {
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];
        while ((nRead = fis.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        return buffer.toByteArray();
    }

//    @Bean
//    public RestTemplate buildRestTemplate() {
//        System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,TLSv1");
//        return new RestTemplate();
//    }
//
//
//
//    @Bean
//    @Order(1)
    public CommandLineRunner add_news(){
        log.info("Adding data about all sites to DB ->");
        return args->{
            newsRepo.saveAll(Arrays.asList(
                    new News("TechCrunch","https://techcrunch.com/"
                    ,"TechCrunch - Reporting on the business of technology, startups, venture capital funding, and Silicon Valley"
                    ,convertImagetoByteArray("src/main/resources/templates/img/techcrunch_logo.png")),

                    new News("UberGizmo","https://www.ubergizmo.com/"
                    ,"Opinionated news and reviews of consumer electronics"
                    ,convertImagetoByteArray("src/main/resources/templates/img/ubergizmo_logo_icon_64.png")),

                    new News("DroidLife","https://www.droid-life.com/"
                    ,"We write about the happenings in the Android world, most of which involves Google. " +
                            "We do reviews too, so we got you covered, Samsung guy."
                    , convertImagetoByteArray("src/main/resources/templates/img/droidlife_logo.png")),

                    new News("TechStartups","https://techstartups.com/"
                    ,"TechStartups - Coverage of Technology News, technology startups, Emerging technology, venture capital funding, and Silicon Valley"
                    ,convertImagetoByteArray("src/main/resources/templates/img/techstartups.com-logo-v3.png")),

                    new News("LenovaNews","https://news.lenovo.com/pressroom/press-releases/"
                    ,"Lenovo's official site for press materials and original stories about the vision and passion behind the technology."
                    ,convertImagetoByteArray("src/main/resources/templates/img/lenovo2_logo.png")),

                    new News("Insider","https://www.insider.com/news","What You Need To Know"
                            ,convertImagetoByteArray("src/main/resources/templates/img/insider_logo.png")),

                    new News("HTCNews","https://www.pocket-lint.com/htc"
                    ,"Get all the latest on HTC. All the very best in HTC news, HTC reviews, HTC rumours and HTC buying advice in one place."
                    ,convertImagetoByteArray("src/main/resources/templates/img/HTC_logo.png")),

                    new News("DigitIn","https://www.digit.in/news/"
                            ,"Latest Technology News on mobile phones in India. Read news on computer, apps, games, gadgets and other personal tech."
                    ,convertImagetoByteArray("src/main/resources/templates/img/digit_logo.png")),

                    new News("Policy","https://www.policygenius.com/blog/"
                    ,"A financial newsletter bringing you the money news and the money moves."
                    ,convertImagetoByteArray("src/main/resources/templates/img/policy_logo.png")),

            new News("Habr","https://habr.com/en/flows/develop/"
                    ,"All posts in Development stream on Habr"
                    ,convertImagetoByteArray("src/main/resources/templates/img/Habr_logo.png"))
            ));

        };
    }






}
