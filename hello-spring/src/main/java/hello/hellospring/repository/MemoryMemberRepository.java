// 구현체

package hello.hellospring.repository;
import hello.hellospring.domain.Member;
import java.util.*;
/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 */
public class MemoryMemberRepository implements MemberRepository {
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;
    @Override
    public Member save(Member member) {
        member.setId(++sequence);  // store에 넣기 전에 id 값 세팅
        store.put(member.getId(), member);  // store에 저잘
        return member;
    }
    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));  // null이어도 optional로 감싸서 반환
    }
    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))  // 파라미터로 넘어온 name과 같은지 확인
                .findAny();  // findAny : 하나 찾는 것, 끝까지 돌고 없으면 optional에 null 포함돼서 반환
    }
    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());  // store에 있는 member들 반환
    }
    public void clearStore() {
        store.clear();
    }
}
