package cc.ayakurayuki.shortenurl.infrastructure.persistence.po;

/**
 * @author Ayakura Yuki
 * @date 2024/01/10-14:16
 */
public record LinkPo(
    String url,
    Long createAt,
    Long expireAt,
    Boolean fromAdmin
) {}
