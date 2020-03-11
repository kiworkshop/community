package community.mother.support.api;


import community.mother.support.api.dto.SupportResponseDto;
import community.mother.support.service.SupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/support")
public class SupportController {
    private final SupportService supportService;

    @GetMapping
    public SupportResponseDto readSupport(){
        return supportService.readSupport();
    }
}
