package com.iting.lecture.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iting.lecture.mapper.LectureMapper;
import com.iting.lecture.model.LectureVO;
import com.iting.lecture.service.LectureService;

@Service
public class LectureServiceImpl implements LectureService {

	@Autowired
	LectureMapper lectureMapper;

	@Override
	public List<LectureVO> getLectureList(LectureVO vo) {
		return lectureMapper.getLectureList(vo);
	}

	@Override
	public LectureVO getLectureInfo(String ltNum) {
		return lectureMapper.getLectureInfo(ltNum);
	}

	@Override
	public LectureVO getLectureInfo1(String ltNum) {
		return lectureMapper.getLectureInfo1(ltNum);
	}

  // 신수지
	@Override
	public List<LectureVO> getTcList(String lecturerNum) {
		return lectureMapper.getTcList(lecturerNum);
  }
  
  
  // 도승민
  @Override
	public int ltInsert(LectureVO vo) {
		return lectureMapper.ltInsert(vo);
	}

@Override
public int update(LectureVO vo) {
	
	return lectureMapper.update(vo);
}

@Override
public List<LectureVO> endLectureList(LectureVO vo) {
	return lectureMapper.endLectureList(vo);
}

@Override
public List<LectureVO> ingLectureList(LectureVO vo) {
	
	return lectureMapper.ingLectureList(vo);
}



}
