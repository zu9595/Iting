package com.iting.common.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.iting.common.model.FileVO;

@Mapper
public interface CommonMapper {
	int fileInsert(FileVO fvo); // 첨부파일 등록
}