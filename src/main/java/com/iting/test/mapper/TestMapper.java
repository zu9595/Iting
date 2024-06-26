package com.iting.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.iting.test.model.TestVO;

@Mapper
public interface TestMapper {
	public List<TestVO> getTestList(String ltNum);
	public TestVO getTestInfo(String ltNum, String prblNum);
	public int insertTest(TestVO vo);
	public int updateTest(TestVO testVO);
	public int deleteTest(String ltNum, String prblNum);
	
	// 회원 문제응시
	public int insertExam(TestVO vo);
	public int insertExamDetail(TestVO vo);
	public int deleteExam(TestVO vo);
	public int deleteExamDetail(TestVO vo);
	public List<TestVO> getExamList(TestVO vo);
	public int updateExam(TestVO testVO);
	public int updateResult(TestVO testVO);
	
	// 채점
	public int updateScore(String applexamNum);
	
	// 결과
	public TestVO getExamResult(String applexamNum);
	public List<TestVO> getResultList(TestVO vo);
	
	// 오답노트
	public List<TestVO> getOxList(TestVO vo);
}
