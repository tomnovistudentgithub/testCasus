package nl.maastrichtuniversity.myusc.service;

import nl.maastrichtuniversity.myusc.model.Membership;
import nl.maastrichtuniversity.myusc.dtos.MembershipDto;
import nl.maastrichtuniversity.myusc.entities.User;
import nl.maastrichtuniversity.myusc.repository.MembershipRepository;
import nl.maastrichtuniversity.myusc.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MembershipService {

    MembershipRepository membershipRepository;
    UserRepository userRepository;
    PriceService priceService;

    public MembershipService(MembershipRepository membershipRepository, PriceService priceService, UserRepository userRepository) {
        this.membershipRepository = membershipRepository;
        this.priceService = priceService;
        this.userRepository = userRepository;
    }


    public Membership createMembership(MembershipDto membershipDto) {

        if (membershipDto == null) {
            throw new IllegalArgumentException("MembershipDto is required");
        }
        Long userId = membershipDto.getUserId();
        if (userId == null) {
            throw new IllegalArgumentException("User ID is required");
        }
        User user = null;

        try {
            user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));
        } catch (IllegalArgumentException e) {
            Throwable cause = e.getCause();
            System.out.println("Exception thrown by findById: " + cause);
        }

        try {
            if (user == null) {
                throw new IllegalArgumentException("User is required");
            }
            if (membershipDto.getMembershipType() == null) {
                throw new IllegalArgumentException("Membership type is required");
            }

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            if (isUserAlreadyActiveMember(user)) {
                throw new IllegalArgumentException("User is already an active member, cannot have multiple active memberships");
            }

            LocalDate startDate = LocalDate.now();
            LocalDate expirationDate = startDate.plusYears(1);

            Membership newMembership = new Membership();
            newMembership.setUser(user);
            newMembership.setMembershipType(membershipDto.getMembershipType());

            newMembership.setPrice(priceService.calculatePrice(user, membershipDto.getMembershipType()));
            newMembership.setStartDate(startDate);
            newMembership.setExpirationDate(expirationDate);
            return membershipRepository.save(newMembership);
        }
        catch (IllegalArgumentException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Boolean isUserAlreadyActiveMember(User user) {
        return membershipRepository.findByUser(user)
                .stream()
                .anyMatch(membership -> isActive(membership.getStartDate(), membership.getExpirationDate()));
    }

    public Boolean isActive(LocalDate startDate, LocalDate expirationDate) {
        if (startDate == null || expirationDate == null) {
            return false;
        }

        LocalDate now = LocalDate.now();
        return (now.isEqual(startDate) || now.isAfter(startDate)) && now.isBefore(expirationDate);

    }


    public void deleteMembership(Long membershipId, Long userId) {

        if (membershipId == null || userId == null) {
            throw new IllegalArgumentException("Membership ID and User ID is required");
        }



        Membership membership = membershipRepository.findById(membershipId)
                .orElseThrow(() -> new IllegalArgumentException("Membership with id " + membershipId + " not found"));

        if (membership.getUser().getId() != userId) {
            throw new IllegalArgumentException("Membership does not belong to selected user");
        }

                membershipRepository.deleteById(membershipId);
    }

}
