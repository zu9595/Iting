package com.iting.subsp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iting.subsp.mapper.SttlMapper;
import com.iting.subsp.model.SttlVO;
import com.iting.subsp.service.SttlService;

@Service
public class SttlServiceImpl implements SttlService {

	@Autowired
	SttlMapper sttlMapper;

	@Override
	public int sttlInsert(SttlVO vo) {
		return sttlMapper.sttlInsert(vo);
	}

}
