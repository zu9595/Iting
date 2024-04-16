package com.iting.save.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iting.cnq.model.CnqVO;
import com.iting.save.model.SaveVO;
import com.iting.save.service.SaveService;

import lombok.RequiredArgsConstructor;

/**
 * 강의에 대한 커뮤니티 사용.
 * 
 * @author 조혜원
 *
 */

@RequiredArgsConstructor
@Controller
public class SaveController {

	@Autowired
	SaveService saveService ;
	
		// 등록 기능.
		@ResponseBody
		@PostMapping("/member/save/insert")
		public int saveInsert(@RequestBody SaveVO vo) {
			return saveService.saveInsert(vo);
		}
		
		// 이름조회 기능.
		@ResponseBody
		@GetMapping("/member/save/insert/{id}")
		public String memSelect(@PathVariable String id) {
			return saveService.memSelect(id);
		}

	
}
