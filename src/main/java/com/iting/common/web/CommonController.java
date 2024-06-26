package com.iting.common.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriUtils;

import com.iting.common.ExcelView;
import com.iting.common.FileUtil;
import com.iting.common.model.AccountVO;
import com.iting.common.model.FileVO;
import com.iting.common.model.UsersVO;
import com.iting.common.service.CommonService;
import com.iting.common.service.UsersService;
import com.iting.lecture.service.LectureService;
import com.iting.sett.service.SettService;
import com.iting.subsp.service.SubspService;
import com.iting.teacher.service.TeacherService;

import lombok.extern.log4j.Log4j2;

/**
 * 공용 컨트롤 클래스
 * 
 * @author 장효은, 박지웅
 * 
 */

@Controller
@Log4j2
public class CommonController {

	@Autowired
	UsersService userservice;

	@Autowired
	CommonService commonService;

	@Autowired
	TeacherService teacherService;
	
	@Autowired
	LectureService lectureService;
	
	@Autowired
	SubspService subspService;
	
	@Autowired
	SettService settService;
	
	@Value("${file-upload-folder}")
	String filePath;
	
	/* 메인이동 */
	// 관리자
	@RequestMapping("admin/main")
	public String goAdminMain(Model model) {
		model.addAttribute("ingLectureList", lectureService.ingLectureList(null));
		model.addAttribute("settCount", settService.getSettCount());
		return "admin/main";
	}

	// 회원
	@RequestMapping("member/main")
	public ModelAndView goMemberMain(Model model, HttpSession session) {
		
		String usernum =  (String) session.getAttribute("usernum");
		
		model.addAttribute("subspinfo", subspService.getSubspInfo(usernum));
		model.addAttribute("lectMainList", lectureService.getMainLectureList());
		
		ModelAndView mv = new ModelAndView("member/main");
		return mv;
	}

	// 강사
	@RequestMapping("teacher/main")
	public ModelAndView goTeacherMain(Model model) {
		
		//model.addAttribute("lectCount", model);
		
		ModelAndView mv = new ModelAndView("teacher/main");
		return mv;
	}

	/* 첨부 파일 업로드 (싱글) */
	@PostMapping("upload/file")
	@ResponseBody
	public Map<String, Object> uploadFileTest(MultipartFile uFile, String fileCode)
			throws IllegalStateException, IOException {
		int retCode = 0; // 등록 완료 코드

		Map<String, Object> map = new HashMap<String, Object>();

		FileVO fvo = FileUtil.uploadFile(uFile);
		if (fvo != null) {
			// 달라 질수 있는 로직
			retCode = commonService.fileInsert(fvo);

			map.put("retCode", retCode);
			map.put("fvo", fvo);
		}
		return map;

	}

	/* 첨부 파일 업로드 (멀티) */
	@PostMapping("upload/files")
	@ResponseBody
	public List<Map<String, Object>> uploadFileTest(MultipartFile[] uFile, String fileCode)
			throws IllegalStateException, IOException {
		int retCode = 0; // 등록 완료 코드

		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();

		if (uFile != null) {
			for (MultipartFile file : uFile) {

				if (!file.isEmpty()) {
					Map<String, Object> map = new HashMap<String, Object>();

					FileVO fvo = FileUtil.uploadFile(file);
					retCode = commonService.fileInsert(fvo);

					map.put("retCode", retCode);
					map.put("fvo", fvo);

					listMap.add(map);
				}
			}
		}

		return listMap;

	}

	
	/* 첨부 파일 다운로드 */
	@GetMapping("/downloading/{fileNum}")
	@ResponseBody
	public ResponseEntity<Resource> download(@PathVariable String fileNum, HttpServletRequest req) throws Exception{
		FileVO fvo = commonService.getFileInfo(fileNum);
		
		String fileName = fvo.getAtchMarkTtl();
        //String filePath = "D:" + File.separator + "iting_webstorage" + File.separator + fileName;
        String newFilePath = filePath + fileName;

        Resource resource = new UrlResource(Paths.get(newFilePath).toUri());
        
        
        System.out.println(filePath + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(newFilePath+ "==========================>");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + UriUtils.encode(fileName, "UTF-8") + "\"")
                .body(resource);
    }
	
	

	/* 엑셀로 DB 테이블 내려받기 */
	@GetMapping("/empExcel")
	public ModelAndView empExcel() {
		ModelAndView mv = new ModelAndView(new ExcelView());
		/*
		 * mv.addObject("type", EmpVO.class); ObjectMapper objectMapper = new
		 * ObjectMapper(); List<Map> list = empService.getEmpList(null) .stream()
		 * .map(d->objectMapper.convertValue(d, Map.class))
		 * .collect(Collectors.toList());
		 * 
		 * mv.addObject("datas", list);
		 */
		return mv;
	}

	// 페이지 권한 없을때
	@GetMapping("/accessError")
	public String accessDenied(Authentication auth, Model model) {
		log.info("access denied :" + auth);
		model.addAttribute("msg", "Access denied");
		return "common/accessError";
	}

	/* 로그인 및 로그아웃 */

	// 로그인 페이지 이동
	@GetMapping("/commonlogin")
	public String loginForm() {
		return "common/login";
	}

	@GetMapping("/naveraccount")
	public String naverAccForm() {
		return "common/naverAccount";
	}

	// 회원가입 페이지 이동
	@GetMapping("/account")
	public String accountForm() {
		return "common/account";
	}

	// id 중복확인
	@ResponseBody
	@GetMapping("/common/idchk/{id}")
	public UsersVO idchk(@PathVariable String id) {
		return userservice.getUserInfo(id);
	}

	@GetMapping("/send-email")
	public String emailsend() {
		return "common/send-email";
	}

	// 회원 등록
	@ResponseBody
	@PostMapping("/insertLecturer")
	public int insertLecturer(@RequestPart(value = "vo", required = false) AccountVO vo,
			@RequestPart(value = "fileData", required = false) MultipartFile fileData)
			throws IllegalStateException, IOException {
			System.out.println(fileData.getOriginalFilename());
			String origFileName = fileData.getOriginalFilename();
			vo.setFileName(fileData.getOriginalFilename());
			// 확장자
			String exetension = FilenameUtils.getExtension(origFileName);
			// 새 파일명 (중복 덮어쓰기 방지)
			String newFile = origFileName.substring(0, origFileName.lastIndexOf('.')) + "_" + new Date().getSeconds()
					+ "." + exetension;
			vo.setNewFileName(newFile);
			// 파일 경로 및 이름 정의
			File file = new File(filePath, fileData.getOriginalFilename());
			// 정의된 값으로 파일 저장
			try {
				fileData.transferTo(file);
				System.out.println("파일명 : " + fileData.getOriginalFilename());
				System.out.println("파일크기 : " + fileData.getSize());
			} catch (Exception e) {
				e.printStackTrace();
			}
		int ckcnt = userservice.insertUser(vo);
		return ckcnt;
	}
	@ResponseBody
	@PostMapping("/insertMember")
	public int insertMember(@RequestBody AccountVO vo) {
		int ckcnt = userservice.insertUser(vo);
		return ckcnt;
	}
	
	// 회원 메인
	@GetMapping("/")
	public String index() {
		return "member/main";
	}
	
	//로그인 실패
//	@GetMapping("/userlogin")
//	public String logfail() {
//		return "common/loginfail";
//	} 
}