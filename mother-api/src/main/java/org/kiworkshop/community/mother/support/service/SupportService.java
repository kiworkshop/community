package org.kiworkshop.community.mother.support.service;

import lombok.Getter;
import org.kiworkshop.community.mother.support.api.dto.SupportResponseDto;
import org.kiworkshop.community.mother.support.domain.Support;
import org.springframework.stereotype.Service;

@Service
@Getter
public class SupportService {
    private final String title = "광일공방 후원페이지";
    private final String content = "후원 정보 : ";
    public SupportResponseDto readSupport() {
        return SupportResponseDto.from(Support.builder().title(title).content(content).build());
    }
}
