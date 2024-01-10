package cc.ayakurayuki.shortenurl.interfaces.req;

/**
 * @author Ayakura Yuki
 * @date 2024/01/10-16:24
 */
public record SaveUrlReq(
    String url,
    String key,
    String hash,
    Long ttlSecond
) {}
