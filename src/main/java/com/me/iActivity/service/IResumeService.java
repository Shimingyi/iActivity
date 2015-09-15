package com.me.iActivity.service;

import com.me.iActivity.dto.Message;
import com.me.iActivity.model.Resume;

public interface IResumeService {

	public Resume findResume(int rid);
	
	public Resume findResumeByUser(int uid);
	
	public Message addResume(Resume resume);
	
	public Message updateResume(Resume resume);
	
	public Message deleteResume(Resume resume);
	
}
