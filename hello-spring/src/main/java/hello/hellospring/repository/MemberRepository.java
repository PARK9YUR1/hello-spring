package hello.hellospring.repository;
import hello.hellospring.domain.Member;
import java.util.List;
import java.util.Optional;
public interface MemberRepository {
    Member save(Member member);  // 회원 저장
    // optional -> 값이 없을 때 null 대신 optional로 감싸서 반환
    Optional<Member> findById(Long id);  // 아이디로 회원 찾기
    Optional<Member> findByName(String name);  // 이름으로 회원 찾기
    List<Member> findAll();  // findAll : 지금까지 저장된 회원 리스트 모두 반환
}