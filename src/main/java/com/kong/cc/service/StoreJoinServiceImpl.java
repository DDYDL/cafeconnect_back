package com.kong.cc.service;

import com.kong.cc.dto.StoreDto;
import com.kong.cc.entity.Member;
import com.kong.cc.entity.Store;
import com.kong.cc.repository.AskRepository;
import com.kong.cc.repository.ComplainRepository;
import com.kong.cc.repository.MemberRepository;
import com.kong.cc.repository.NoticeRepository;
import com.kong.cc.repository.StoreRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreJoinServiceImpl implements StoreJoinService {

    private final JPAQueryFactory jpaQueryFactory;
    private final NoticeRepository noticeRepository;
    private final AskRepository askRepository;
    private final StoreRepository storeRepository;
    private final ComplainRepository complainRepository;
    private final MemberRepository memberRepository;

    @Override
    public void joinStore(StoreDto storeDto) throws Exception {

        // storeCode를 기반으로 Store 엔티티 조회
//        Store store = storeRepository.findById(askDto.getStoreCode())
//                .orElseThrow(() -> new IllegalArgumentException("해당 storeCode에 해당하는 Store가 존재하지 않습니다."));
//        System.out.println("StoreCode dto " + askDto.getStoreCode());
//        System.out.println("StoreCode repo" + store.getStoreCode());
//        Integer storeCode = store.getStoreCode();
//
//        askRepository.save(AskDto.builder()
//                .askType(askDto.getAskType())
//                .askTitle(askDto.getAskTitle())
//                .askContent(askDto.getAskContent())
//                .askStatus("1")
//                .storeCode(storeCode)
//                .build()
//                .toEntity());
    }

    @Override
    public Boolean checkDoubleId(String username) throws Exception {
        Optional<Member> checkDoubleId = this.memberRepository.findByUsername(username);
        System.out.println("checkDoubleId" + checkDoubleId);

        //값이 있다면 중복
        return checkDoubleId.isPresent();
    }

    @Override
    public StoreDto checkStoreCode(Integer storeCode) throws Exception {
        Store searchStore = this.storeRepository.findByStoreCode(storeCode).orElseThrow(
                () -> new Exception("일치하는 storeCode가 없습니다."));

        return searchStore.toDto();
    }


}
