package com.example.blog_v1.user;

import com.example.blog_v1.board.BoardDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    // DI 처리
    private final UserRepository userRepository;
    private final HttpSession session;  // 세션 메모리지

    /*
     * 로그인 페이지 요청
     * 주소설계 : http://localhost:8080/login-form
     *
     * @param model
     * @return 문자열
     * 반환되는 문자열을 뷰 리졸버가 처리하며
     * 머스태치 템플릿 엔진을 통해서 뷰 파일을 렌더링 합니다.
     */
    @GetMapping("/login-form")
    public String loginForm(Model model) {
        log.info("로그인 페이지");
        model.addAttribute("name", "로그인 페이지");
        return "user/login-form"; // 템플릿 경로 : user/join-form.mustache
    }

    /*
    * 자원의 요청은 GET 방식이지만 보안 이유로 예외!
    * 로그인 처리 메서드
    * 요청 주소 POST : http://localhost:8080/login
    * @param reqDto
    * @return
    * */
    @PostMapping("/login")
    public String loginForm(UserDTO.LoginDTO reqDto) {
        try {
            User sessionUser = userRepository.findByUsernameAndPassword(reqDto.getUsername(), reqDto.getPassword());
            session.setAttribute("sessionUser", sessionUser);
            return "redirect:/";
        } catch (Exception e) {
            return "redirect:/login-form?error";
        }
    }

    /*
    * 로그아웃 처리 메서드
    * */
    @GetMapping("/logout")
    public String logout() {
        session.invalidate();   // 세션을 무효화(로그아웃)

        return "redirect:/";
    }

    /*
     * 회원가입 페이지 요청
     * 주소설계 : http://localhost:8080/join-form
     *
     * @param model
     * @return 문자열
     * 반환되는 문자열을 뷰 리졸버가 처리하며
     * 머스태치 템플릿 엔진을 통해서 뷰 파일을 렌더링 합니다.
     */
    @GetMapping("/join-form")
    public String joinForm(Model model) {
        model.addAttribute("name", "회원가입 페이지");
        return "user/join-form"; // 템플릿 경로 : user/join-form.mustache
    }

    @PostMapping("/join")
    public String join(@ModelAttribute(name = "joinDTO") UserDTO.JoginDTO reqDto)  {
        userRepository.save(reqDto.toEntity());
        return "redirect:/login-form";
    }

    /*
     * 회원 정보 수정 페이지 요청
     * 주소설계 : http://localhost:8080/user/update-form
     *
     *
     * @return 문자열
     * 반환되는 문자열을 뷰 리졸버가 처리하며
     * 머스태치 템플릿 엔진을 통해서 뷰 파일을 렌더링 합니다.
     */
    @GetMapping("/user/update-form")
    public String updateForm(HttpServletRequest request) {
        log.info("회원 수정 페이지");

        User sessionUser = (User) session.getAttribute("sessionUser");
        if(sessionUser == null) {
            return "redirect:/login-form";
        }
        User user = userRepository.findById(sessionUser.getId());
        request.setAttribute("user", user);

        return "user/update-form"; // 템플릿 경로 : user/join-form.mustache
    }

    @PostMapping("/user/update")
    public String update(@ModelAttribute(name = "updateDTO") UserDTO.UpdateDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/login-form";
        }
        // 유효성 검사는 생략
        // 사용자 정보 수정
        User updatedUser = userRepository.updateById(sessionUser.getId(), reqDTO.getPassword(), reqDTO.getEmail());
        // 세션 정보 동기화 처리
        session.setAttribute("sessionUser", updatedUser);
        return "redirect:/";
    }
}
