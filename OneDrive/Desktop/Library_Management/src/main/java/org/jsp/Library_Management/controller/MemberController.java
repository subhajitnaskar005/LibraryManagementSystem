package org.jsp.Library_Management.controller;
import org.jsp.Library_Management.dto.Member;
import org.jsp.Library_Management.service.MemberService;
//import com.library.management.entity.Member;
//import com.library.management.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {
	    @Autowired
	    private MemberService memberService;

	    @GetMapping
	    public List<Member> getAllMembers() { 
	    	return memberService.getAllMembers(); 
	    	}

	    @GetMapping("/{id}")
	    public Member getMemberById(@PathVariable Long id) { 
	    	return memberService.getMemberById(id); 
	    	}

	    @PostMapping
	    public Member addMember(@RequestBody Member member) { 
	    	return memberService.addMember(member); 
	    	}

	    @PutMapping("/{id}")
	    public Member updateMember(@PathVariable Long id, @RequestBody Member member) { return memberService.updateMember(id, member); }

	    @DeleteMapping("/{id}")
	    public void deleteMember(@PathVariable Long id) { memberService.deleteMember(id); }
	}
