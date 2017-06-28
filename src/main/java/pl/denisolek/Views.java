package pl.denisolek;

public class Views {
	public static interface Base {}
	public static interface Restaurant extends Base, Reservation {}
	public static interface RestaurantDetails extends Restaurant {}
	public static interface Reservation extends Base, Customer {}
	public static interface ReservationDetails extends Reservation, Restaurant {}
	public static interface Customer extends Base {}
}
