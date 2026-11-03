import java.util.List;

public class DefaultPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(List<ShowSeat> seats) {
        double totalPrice = 0.0;
        for (ShowSeat seat : seats) {
            totalPrice += seat.getPrice();
        }
        return totalPrice;
    }
}
