package nl.maastrichtuniversity.myusc.repository;

import nl.maastrichtuniversity.myusc.model.MembershipType;
import nl.maastrichtuniversity.myusc.model.Price;
import nl.maastrichtuniversity.myusc.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long> {
    Price findByUserTypeAndMembershipType(UserType userType, MembershipType membershipType);

}
