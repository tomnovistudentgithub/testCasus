package nl.maastrichtuniversity.myusc.service;

import nl.maastrichtuniversity.myusc.model.MembershipType;
import nl.maastrichtuniversity.myusc.model.Price;
import nl.maastrichtuniversity.myusc.model.User;
import nl.maastrichtuniversity.myusc.model.UserType;
import nl.maastrichtuniversity.myusc.repository.PriceRepository;
import nl.maastrichtuniversity.myusc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceService {

    private double basePrice;
    private final PriceRepository priceRepository;

    @Autowired
    public PriceService(PriceRepository priceRepository, UserRepository userRepository) {
        this.priceRepository = priceRepository;

    }

    public double setPrice(User user, MembershipType membershipType, int enrollmentMonth) {

        double basePrice = 0.0;
        double originalBasePrice = 0.0;
        UserType userType = user.getUserType();


        switch (membershipType) {
            case GYM_AND_SPORTS:
                originalBasePrice = 200.0;
                break;
            case GYM:
                originalBasePrice = 150.0;
                break;
            case SPORTS:
                originalBasePrice = 100.0;
                break;
        }

        if (enrollmentMonth >= 9 && enrollmentMonth <= 12) {
            basePrice = originalBasePrice - (originalBasePrice / 10.0) * (enrollmentMonth - 9);
        } else if (enrollmentMonth >= 1 && enrollmentMonth <= 6) {
            basePrice = originalBasePrice - (originalBasePrice / 10.0) * (enrollmentMonth + 3);
        } else if (enrollmentMonth >= 7 && enrollmentMonth <= 8) {
        basePrice = (originalBasePrice - (originalBasePrice / 10.0) * 9) * 0.7;
    }

        if (userType == UserType.STUDENT) {
            basePrice *= 0.8;
        }


        Price price = new Price();
        price.setUserType(userType);
        price.setMembershipType(membershipType);
        price.setBasePrice(basePrice);
        priceRepository.save(price);

        return basePrice;
    }
}