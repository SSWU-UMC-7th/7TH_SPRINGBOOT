package umc.spring.service.MemberService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.apiPayLoad.code.exception.handler.ErrorStatus;
import umc.spring.apiPayLoad.code.exception.handler.FoodCategoryHandler;
import umc.spring.converter.MemberConverter;
import umc.spring.converter.MemberPreferConverter;
import umc.spring.domain.FoodCategory;
import umc.spring.domain.Member;
import umc.spring.domain.mapping.MemberPrefer;
import umc.spring.repository.FoodCategoryRepository.FoodCategoryRepository;
import umc.spring.repository.MemberRepository.MemberRepository;
import umc.spring.web.dto.member.MemberRequestDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Member joinMember(MemberRequestDTO.JoinDto request) {
        System.out.println("회원 요청 데이터: " + request);

        Member newMember = MemberConverter.toMember(request);
        System.out.println("변환된 Member 객체: " + newMember);

        List<FoodCategory> foodCategoryList = request.getPreferCategory().stream()
                .map(categoryId -> foodCategoryRepository.findById(categoryId)
                        .orElseThrow(() -> new FoodCategoryHandler(ErrorStatus.FOOD_CATEGORY_NOT_FOUND)))
                .collect(Collectors.toList());
        System.out.println("연결된 FoodCategory: " + foodCategoryList);

        List<MemberPrefer> memberPreferList = foodCategoryList.stream()
                .map(foodCategory -> {
                    MemberPrefer memberPrefer = MemberPrefer.builder()
                            .member(newMember)
                            .foodCategory(foodCategory)
                            .build();
                    memberPrefer.setMember(newMember);
                    memberPrefer.setFoodCategory(foodCategory);
                    return memberPrefer;
                }).collect(Collectors.toList());
        System.out.println("생성된 MemberPrefer: " + memberPreferList);

        newMember.setMemberPreferList(memberPreferList);

        Member savedMember = memberRepository.save(newMember);
        System.out.println("저장된 Member: " + savedMember);

        return savedMember;
    }
}
