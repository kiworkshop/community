package org.kiworkshop.community.mother.support.service;

import lombok.Getter;
import org.kiworkshop.community.mother.dtos.SupportResponseDto;
import org.kiworkshop.community.mother.support.domain.Support;
import org.springframework.stereotype.Service;

@Service
@Getter
public class SupportService {
    private static final String title = "광일공방 후원페이지";
    private static final String content = "후원 정보 : ";
    public SupportResponseDto readSupport() {
        return SupportConverter.toResponseDto(Support.builder().title(title).content(content).build());
    }
}
