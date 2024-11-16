package nl.maastrichtuniversity.myusc.service;

import nl.maastrichtuniversity.myusc.model.MembershipType;
import nl.maastrichtuniversity.myusc.model.Price;
import nl.maastrichtuniversity.myusc.entities.User;
import nl.maastrichtuniversity.myusc.model.UserType;
import nl.maastrichtuniversity.myusc.repository.PriceRepository;
import nl.maastrichtuniversity.myusc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceService {

    private final PriceRepository priceRepository;

    @Autowired
    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public double calculatePrice(User user, MembershipType membershipType) {
        double basePrice = getBasePrice(membershipType);
        basePrice = applyDiscounts(user, basePrice);
        savePrice(user, membershipType, basePrice);
        return basePrice;
    }

    private double getBasePrice(MembershipType membershipType) {
        switch (membershipType) {
            case GYM_AND_SPORTS:
                return 200.0;
            case GYM:
                return 150.0;
            case SPORTS:
                return 100.0;
            default:
                throw new IllegalArgumentException("Invalid membership type");
        }
    }

    private double applyDiscounts(User user, double basePrice) {
        if (user.getUserType() == UserType.STUDENT) {
            basePrice *= 0.8;
        }
        return basePrice;
    }

    private void savePrice(User user, MembershipType membershipType, double basePrice) {
        Price price = new Price();
        price.setUserType(user.getUserType());
        price.setMembershipType(membershipType);
        price.setPrice(basePrice);
        priceRepository.save(price);
    }
}