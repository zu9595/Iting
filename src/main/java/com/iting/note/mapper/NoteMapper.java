package com.iting.note.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.iting.note.model.NoteVO;

@Mapper
public interface NoteMapper {
	public List<NoteVO> getRecList(NoteVO vo);
	public List<NoteVO> getMemNoteList(String user);
	public List<NoteVO> getSentList(NoteVO vo);
	public NoteVO getRecInfo(String noteNum);
	public NoteVO getSentInfo(String noteNum);
	public int insertNote(NoteVO vo);
}
