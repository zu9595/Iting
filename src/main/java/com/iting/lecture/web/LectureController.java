package com.iting.lecture.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.iting.lecture.model.LectureVO;
import com.iting.lecture.service.LectureService;

@Controller
public class LectureController {
	@Autowired
	LectureService lectureService;

	/* 회원 */
	// 강의 목록 조회

	// 강의 단건 조회
	@GetMapping("lecture/info/{ltNum}")
	public String info(@PathVariable String ltNum, Model model) {
		model.addAttribute("lecture", lectureService.getLectureInfo(ltNum));
		return "member/lecture/info";
	}

  
	/* 강사 */
	// 강의 목록 조회
	@GetMapping("lecture/lecture")
	public String getLectureList(Model model, LectureVO vo) {
		model.addAttribute("getLectureList", lectureService.getLectureList(vo));
		return "teacher/lecture/getLectureList";
	}

	// 강의 단건 조회
	@GetMapping("lecture/info1/{ltNum}")
	public String info1(@PathVariable String ltNum, Model model) {
		model.addAttribute("lecture", lectureService.getLectureInfo1(ltNum));
		return "teacher/lecture/info1";
	}
	// 강의 수정

	// 강의 등록페이지 이동
	@GetMapping("/teacher/lecture/insert")
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView("teacher/lecture/insert");
		return mv;
	}

	// 등록기능
	@ResponseBody
	@PostMapping("/teacher/lecture/insert1")
	public String ltInsert(@RequestBody LectureVO vo) {
		System.out.println(vo +
				"===============");
		lectureService.ltInsert(vo);
		return "redirect:/admin/lecture/list";
	}
	/* 관리자 */
	// 강의 수정

	//강의 리스트
	@GetMapping("/admin/lecture/list")
	public String list(Model model, LectureVO vo) {
		model.addAttribute("list", lectureService.getLectureList(vo));
		return "/admin/lecture/list";
	}
	
	//승인 목록
	@ResponseBody
	@GetMapping("/admin/lecture/endLectureList")
	public List<LectureVO> endList(LectureVO vo) {
	
		return lectureService.endLectureList(vo);
	
	}
	//승인수정 기능
	@ResponseBody
	@GetMapping("/admin/lecture/update/{ltNum}")
	public LectureVO update(LectureVO vo,@PathVariable String ltNum) {
		vo.setLtNum(ltNum);
		lectureService.update(vo);
		return vo;
	}
	
}