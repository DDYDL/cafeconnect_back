package com.kong.cc.service;

import com.kong.cc.dto.StoreDto;
import com.kong.cc.dto.StoreJoinDto;
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

//    @Override
//    public void joinStore(StoreJoinDto storeJoinDto) throws Exception {
//
//        memberRepository.save(Member.builder()
//                .username(storeJoinDto.getUsername())
//                .password(storeJoinDto.getPassword())
//                .storeCode(storeJoinDto.getStoreCode())
//                .build());
//
//        storeRepository.save()
//    }

    @Override
    public void joinStore(StoreJoinDto storeJoinDto) throws Exception {
        // 1. Member 저장 및 member_num 생성
        Member savedMember = memberRepository.save(
                Member.builder()
                        .username(storeJoinDto.getUsername())
                        .password(storeJoinDto.getPassword())
                        .storeCode(storeJoinDto.getStoreCode())
                        .build()
        );

        // 2. 저장된 Member에서 memberNum 가져오기
//        Integer memberNum = savedMember.getMemberNum();

        // 3. StoreCode와 일치하는 Store 조회
//        Store store = storeRepository.findByStoreCode(storeJoinDto.getStoreCode())
//                .orElseThrow(() -> new Exception("해당 StoreCode를 찾을 수 없습니다."));
//
//        Store saveMemberNum = new Store();
//        saveMemberNum.setMember(store.getMember());
//        storeRepository.save(saveMemberNum);
//
//        System.out.println("saveMemberNum" + saveMemberNum);

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
        Store searchStore = storeRepository.findByStoreCode(storeCode).orElseThrow(
                () -> new Exception("일치하는 storeCode가 없습니다."));



        return searchStore.toDto();
    }


}
