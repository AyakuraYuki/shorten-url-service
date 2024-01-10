package cc.ayakurayuki.shortenurl.application.dto;

import cc.ayakurayuki.shortenurl.infrastructure.persistence.po.LinkPo;

/**
 * @author Ayakura Yuki
 * @date 2024/01/10-14:41
 */
public record SaveResultDto(
    LinkPo linkPo,
    String key,
    Long expireAt
) {}
