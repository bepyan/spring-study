//package jpabook.jpashop.study.controller;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.List;
//import java.util.Optional;
//
//@Slf4j
//@RestController
//@RequestMapping("/member") // 공통된 uri
//public class MemberController {
//    private MemberRepository memberRepository = new MemberRepository();
//
//    @RequestMapping({"/new", "/new2"})
//    public String newMember(){
////        return new ModelAndView("new-form");
//        return "new-form"; // 모델이 필요 없는 View
//    }
//
////    @RequestMapping(value = "/add", method = RequestMethod.POST)
////    @PostMapping("/add")
////    public ModelAndView addMember(HttpServletRequest req, HttpServletResponse res){
////        String username = req.getParameter("name");
////        int age = Integer.parseInt(req.getParameter("age"));
////
////        Member member = new Member(username, age);
////        memberRepository.save(member);
////        ModelAndView mv = new ModelAndView("add-result");
////        return mv;
////    }
////    @PostMapping("/add")
////    public String addMember(@RequestParam("name") String username, // 네이밍이 같으면 생략가능
////                            int age,
////                            Model model){
////        Member member = new Member(username, age);
////        memberRepository.save(member);
////        model.addAttribute("member", member);
////        return "add-result";
////    }
//    @PostMapping("/add")
//    // Member에 setter가 있어야 한다. @ModelAttribute 생략가능하지만 적어두는게 정석
//    public String addMember(@ModelAttribute Member member, Model model){
//        memberRepository.save(member);
//        model.addAttribute("member", member);
//        return "add-result";
//    }
//
//    @RequestMapping("/all")
//    public ModelAndView allMember(){
//        List<Member> members = memberRepository.findAll();
//        ModelAndView mv = new ModelAndView("member-list");
//        mv.addObject("members", members);
//        return mv;
//    }
//
//    @RequestMapping("/hello")
//    @ResponseBody
//    public String hello(){
//        return "<html><body><h1>ResponseBody</h1></body></html>";
//    }
//
//    @GetMapping("/api/name/{id}")
//    @ResponseBody
//    public String getMemberNameById(@PathVariable Long id){
//        return memberRepository.findById(id).getName();
//    }
//
//    @GetMapping(value = {"/api/{id}/{age}", "api/{id}"})
//    @ResponseBody
//    public String getMemberByIdAge(@PathVariable Long id,  //@PathVariable(value = "age", required = false) Integer age){
//                                   @PathVariable Optional<Integer> age){
//        if(age.isPresent())
//            return "id: " + id + ", age: " + age.get();
//        return "id: " + id;
//    }
//
//    @PostMapping("/requestBody")
//    public String requestBodyHandler(@RequestBody Member member){
//        log.info("member={}", member);
//        return "ok";
//    }
//
//    @PostMapping("/requestBody2")
//    public HttpEntity<String> requestBodyHandler2(HttpEntity<String> httpEntity){
//        String body = httpEntity.getBody();
//        System.out.println("body = " + body);
//        return new HttpEntity<>("ok2");
//    }
//}
