

public class HourlyPricingStrategy implements PricingStrategy{
private double ratePerHour;

    public HourlyPricingStrategy(double ratePerHour) {
        this.ratePerHour = ratePerHour;
    }

    @Override
    public double calculatePrice(Ticket ticket) {
        long durationInHours = java.time.Duration.between(ticket.getEntryTime(), ticket.getExitTime()).toHours();
        return durationInHours * ratePerHour;
    }

}