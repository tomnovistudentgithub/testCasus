package nl.maastrichtuniversity.myusc.service;

import nl.maastrichtuniversity.myusc.model.Membership;
import nl.maastrichtuniversity.myusc.model.MembershipDto;
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
        membershipDto.setEnrollmentYear(2024);
        membershipDto.setEnrollmentMonth(10);


        if (membershipDto == null) {
            throw new IllegalArgumentException("MembershipDto is required");
        }
        Long userId = membershipDto.getUserId();
        if (userId == null) {
            throw new IllegalArgumentException("User ID is required");
        }
        System.out.println("User id: " + userId);

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
            if (membershipDto.getEnrollmentYear() == 0) {
                throw new IllegalArgumentException("Enrollment year is required");
            }
            if (membershipDto.getEnrollmentMonth() == 0) {
                throw new IllegalArgumentException("Enrollment month is required");
            }
            if (membershipDto.getStartDate() == null) {
                throw new IllegalArgumentException("Start date is required");
            }
            if (membershipDto.getExpirationDate() == null) {
                throw new IllegalArgumentException("Expiration date is required");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            if (isUserAlreadyMember(user)) {
                throw new IllegalArgumentException("User is already a member");
            }

            Membership newMembership = new Membership();
            newMembership.setUser(user);
            newMembership.setMembershipType(membershipDto.getMembershipType());
            newMembership.setEnrollmentYear(membershipDto.getEnrollmentYear());
            newMembership.setEnrollmentMonth(membershipDto.getEnrollmentMonth());
            newMembership.setPrice(priceService.setPrice(user, membershipDto.getMembershipType(), membershipDto.getEnrollmentMonth()));
            newMembership.setStartDate(membershipDto.getStartDate());
            newMembership.setExpirationDate(membershipDto.getExpirationDate());
            newMembership.setActive(isActive(membershipDto.getStartDate(), membershipDto.getExpirationDate()));
            return membershipRepository.save(newMembership);
        }
        catch (IllegalArgumentException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Boolean isUserAlreadyMember(User user) {
        return membershipRepository.findByUser(user).isPresent();
    }

    public Boolean isActive(LocalDate startDate, LocalDate expirationDate) {
        LocalDate now = LocalDate.now();
        return now.isAfter(startDate) && now.isBefore(expirationDate);

    }




}
