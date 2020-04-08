package community.mother.about.api;

import community.mother.about.api.dto.AboutResponseDto;
import community.mother.about.service.AboutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/about")
public class AboutController {
    private final AboutService aboutService;

    @GetMapping
    public AboutResponseDto readAbout(){
        return  aboutService.readAbout();
    }
}
