package com.iting.test.web;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.iting.test.model.TestVO;
import com.iting.test.service.TestService;
import com.iting.tlsn.service.TlsnService;

/**
 * 
 * @author 신수지
 * 강사와 학생의 문제은행
 */

@Controller
public class TestController {

	@Autowired
	TestService testService;
	
	@Autowired
	TlsnService tlsnService;
	

	// 강사 문제은행 목록조회
	@RequestMapping("teacher/test/list/{ltNum}")
	public String getTestList(@PathVariable String ltNum, Model model) {
		model.addAttribute("testList", testService.getTestList(ltNum));
		return "teacher/test/test";
	}
	
	// 학생 문제은행 목록조회
	@RequestMapping("member/test/list")
	public String getTestMemList(Model model, TestVO vo, HttpSession session) {
		String user = (String) session.getAttribute("usernum");
		// model.addAttribute("testList", testService.getTestList(ltNum));
		model.addAttribute("tlsnList", tlsnService.getTlsnList(user));
		return "member/test/list";
	}
	
	// 등록페이지 이동
	@GetMapping("teacher/test/insert/{ltNum}")
	public ModelAndView list(@PathVariable String ltNum, Model model) {
		model.addAttribute("testList", testService.getTestList(ltNum));
		ModelAndView mv = new ModelAndView("teacher/test/insert");
		return mv;
	}

	// 등록처리
	@ResponseBody
	@PostMapping("teacher/test/insert")
	public TestVO insertTest(@RequestBody TestVO vo) {
		testService.insertTest(vo);
		return vo;
	}
	
	// 수정페이지 이동
	@GetMapping("teacher/test/update/{ltNum}/{prblNum}")
	public String update(@PathVariable String ltNum, @PathVariable String prblNum, Model model) {
		model.addAttribute("test", testService.getTestInfo(ltNum, prblNum));
		return "teacher/test/update";
	}
		
	// 수정처리
	@ResponseBody
	@PostMapping("teacher/test/update")
	public TestVO update(@RequestBody TestVO vo) {
		testService.updateTest(vo);
		return vo;
	}
			
	// 삭제처리
	@RequestMapping("teacher/test/delete/{ltNum}/{prblNum}")
	public String delete(@PathVariable String ltNum, @PathVariable String prblNum){ 
		System.out.println("prblNum : " + prblNum);
		testService.deleteTest(ltNum, prblNum);
		return "redirect:/teacher/test/list/{ltNum}";
	}
	
	// 문제 응시등록
	@ResponseBody
	@GetMapping("member/test/list/{ltNum}")
	public List<TestVO> insertExam(@PathVariable String ltNum, TestVO vo, HttpSession session) {
		String user = (String) session.getAttribute("usernum");
		vo.setLtNum(ltNum);
		vo.setMemNum(user);
		
		return testService.insertExam(vo);
	}
	
	// 수정처리
	@ResponseBody
	@PostMapping("member/test/list")
	public String updateExam(@RequestBody List<TestVO> vo, Model model) {
		testService.updateExam(vo);
		System.out.println("들어와" + vo);
		return "redirect:member/test/list";
	}
	
	// 문제결과
	@RequestMapping("member/test/result/{applexamNum}")
	public String getExamResult(@PathVariable String applexamNum, Model model, TestVO vo) {
		model.addAttribute("testResult", testService.getExamResult(applexamNum));
		model.addAttribute("resultList", testService.getResultList(vo));
		return "member/test/result";
	}
	
	// 오답노트
	@RequestMapping("member/test/ox/{ltNum}")
	public String getOxList(@PathVariable String ltNum, Model model, TestVO vo) {
		model.addAttribute("resultList", testService.getResultList(vo));
		model.addAttribute("oxList", testService.getOxList(vo));
		return "member/test/ox";
	}
	
	
}