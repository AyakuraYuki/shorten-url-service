package cc.ayakurayuki.shortenurl.interfaces.rsp;

/**
 * @author Ayakura Yuki
 * @date 2024/01/10-16:23
 */
public record SaveUrlRsp(
    String key,
    Long expireAt
) {}
