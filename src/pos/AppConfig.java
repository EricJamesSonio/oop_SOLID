package pos;

import pos.discount.Discount;
import pos.discount.PWDDiscount;
import pos.discount.SeniorDiscount;
import pos.discount.CombinedDiscount;


public class AppConfig {
    public static final UIMode UI = UIMode.TUI; 
    public enum UIMode { TUI, GUI }

    public static final Discount DISCOUNT_PWD = new PWDDiscount(12.0);
    public static final Discount DISCOUNT_SENIOR = new SeniorDiscount(10.0);
    public static final Discount DISCOUNT_PWD_SENIOR = new CombinedDiscount(DISCOUNT_PWD, DISCOUNT_SENIOR);
}
