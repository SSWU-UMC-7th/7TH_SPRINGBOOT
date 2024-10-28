package umc.spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import umc.spring.domain.Member;
import umc.spring.domain.Mission;
import umc.spring.domain.Review;
import umc.spring.service.MemberService.MemberService;
import umc.spring.service.MissionService.MissionService;
import umc.spring.service.StoreService.StoreQueryService;

@SpringBootApplication
@EnableJpaAuditing
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner run(ApplicationContext context) {
		return args -> {
			MemberService memberService = context.getBean(MemberService.class);

			Long memberId = 1L; // 테스트할 사용자 ID
			Pageable pageable = PageRequest.of(0, 5); // 첫 페이지, 5개씩 조회

			// 사용자 정보 조회
			Member member = memberService.getMemberDetails(memberId);
			System.out.println("사용자 정보: " + member);

			// 사용자가 작성한 리뷰 목록 조회
			Page<Review> reviews = memberService.getReviewsByMemberId(memberId, pageable);
			reviews.forEach(System.out::println);
		};
	}
}
