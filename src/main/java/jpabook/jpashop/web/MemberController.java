package jpabook.jpashop.web;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){
        return "members/join-form";
    }

    // bindingResult 통해 어떤 에러가 발생했는지를 처리
    // @Valid 뒤에 바로 bindingResult를 붙여줘야한다.
    @PostMapping("/members/new")
    public String create(@Valid MemberForm memberForm, BindingResult result, Model model) {
        if(result.hasErrors()){
            Map<String, String> errorMap = new HashMap<String, String>();
            result.getFieldErrors().stream().forEach(fieldError -> {
                String filedName = fieldError.getField();
                String errorMsg = fieldError.getDefaultMessage();
                errorMap.put(filedName, errorMsg);
            });
            model.addAttribute("error", errorMap);
            model.addAttribute("memberForm", memberForm);
            return "/members/join-form";
        }
        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());

        Member member = new Member();
        member.setName(memberForm.getName());
        member.setAddress(address);
        memberService.join(member);
        
        // 홈으로 간다는 뜻
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        // OSIV를 끄면 lazy로딩이 veiw controller에서 사용할 수 없기에 에러가 발생한다.
//        System.out.println("order data = " + members.get(0).getOrders().get(0).getOrderDate());
//        model.addAttribute("members", members);
        return "members/member-list";
    }
}
