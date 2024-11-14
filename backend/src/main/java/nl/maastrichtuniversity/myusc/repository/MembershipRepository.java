package nl.maastrichtuniversity.myusc.repository;

import nl.maastrichtuniversity.myusc.model.Membership;
import nl.maastrichtuniversity.myusc.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
    Optional<Membership> findByUser(User user);
    void deleteById(Long id);

}
