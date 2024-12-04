package com.kong.cc.dto;

import com.kong.cc.entity.Member;
import com.kong.cc.entity.Store;
import java.sql.Date;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreJoinDto {
	private String username;
	private String password;
	private Integer storeCode;
}
