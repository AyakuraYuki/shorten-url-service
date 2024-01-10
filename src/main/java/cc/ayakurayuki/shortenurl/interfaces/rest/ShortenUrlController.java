package cc.ayakurayuki.shortenurl.interfaces.rest;

import cc.ayakurayuki.shortenurl.application.service.LinkAppService;
import cc.ayakurayuki.shortenurl.infrastructure.persistence.po.LinkPo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Ayakura Yuki
 * @date 2024/01/10-16:22
 */
@Controller
public class ShortenUrlController {

  @Resource
  private LinkAppService linkAppService;

  @RequestMapping(value = "/**", method = {RequestMethod.OPTIONS})
  public void options(HttpServletRequest request, HttpServletResponse response) {
    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    response.setContentType(MediaType.TEXT_HTML_VALUE);
  }

  @GetMapping("/{key}")
  public void visit(HttpServletRequest request, HttpServletResponse response, @PathVariable("key") String key) throws IOException {
    if (key == null || key.isBlank()) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.setContentType(MediaType.TEXT_HTML_VALUE);
      response.getWriter().println("Bad Request");
      return;
    }

    LinkPo link = linkAppService.loadURL(key);
    if (link == null || link.url() == null || link.url().isBlank()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      response.setContentType(MediaType.TEXT_HTML_VALUE);
      response.getWriter().println("Not Found");
      return;
    }

    response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
    response.sendRedirect(link.url());
  }

}
