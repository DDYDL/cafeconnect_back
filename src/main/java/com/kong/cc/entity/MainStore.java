package com.kong.cc.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MainStore {
	@Id
	private String username;
	
	private String password;
	private String deptName;
	
	@OneToMany(mappedBy="mainStore", fetch=FetchType.LAZY)
	   private List<Notice> noticeList = new ArrayList<>();
}
