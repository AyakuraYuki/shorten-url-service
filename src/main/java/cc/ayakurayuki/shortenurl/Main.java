package cc.ayakurayuki.shortenurl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Ayakura Yuki
 * @date 2024/01/10-10:27
 */
@SpringBootApplication
@ServletComponentScan
@EnableAsync
public class Main {

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(Main.class);
    app.run(args);
  }

}
