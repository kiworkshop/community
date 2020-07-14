package org.kiworkshop.community.mother.about.service;

import lombok.Getter;
import org.kiworkshop.community.mother.about.converter.AboutConverter;
import org.kiworkshop.community.mother.about.domain.About;
import org.kiworkshop.community.mother.dtos.AboutResponseDto;
import org.springframework.stereotype.Service;

@Service
@Getter
public class AboutService {

    private final String title = "광일공방 소개페이지";
    private final String content = "광일공방 소개내용";

    public AboutResponseDto readAbout() {
        return AboutConverter.from(About.builder().title(title).content(content).build());
    }
}
