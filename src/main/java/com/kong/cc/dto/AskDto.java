package com.kong.cc.dto;

import java.sql.Date;

import com.kong.cc.entity.Ask;
import com.kong.cc.entity.Member;
import com.kong.cc.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AskDto {
	private Integer askNum;
	
    private String askType;
    private String askTitle;
    private String askContent;
    private Date askDate;
    private String askStatus;
    private String askAnswer;
    private Date askAnswerDate;
    
    private Integer storeCode;
    private String storeName;

    public Ask toEntity() {
        Ask ask = Ask.builder()
                .askNum(this.askNum)
                .askType(this.askType)
                .askTitle(this.askTitle)
                .askContent(this.askContent)
                .askDate(this.askDate)
                .askStatus(this.askStatus)
                .askAnswer(this.askAnswer)
                .askAnswerDate(this.askAnswerDate)
                .build();
		if(storeCode!=null) {
			ask.setStoreAs(Store.builder().storeCode(storeCode).build());
		}
		if(storeName!=null) {
			ask.setStoreAs(Store.builder().storeName(storeName).build());
		}
		
		return ask;
    }
}
