package com.kong.cc.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.kong.cc.dto.CartDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cart {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cartNum;
	private Integer cartItemCount;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="storeCode")
	private Store storeCa;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="itemCode")
	private Item itemCa;

	public CartDto toDto() {
		return CartDto.builder()
				.cartNum(cartNum)
				.cartItemCount(cartItemCount)
				.storeCode(storeCa.getStoreCode())
				.itemCode(itemCa.getItemCode())
				.build();
	}
}
