package v1.app.com.codenutrient.POJO;

import java.util.Calendar;

public class Constants {
    public static final String app_users = "app_users";
    public static final String calories = "app_user_calories";
    public static final String eaten_nutrients = "eaten_nutrients";
    public static final String has_products = "has_products";
    public static final String info_app_users = "info_app_users";
    public static final String ip_addr = "https://food-api-app.herokuapp.com/";
    //public static final String ip_addr = "http://172.16.10.68/";
    public static final String products = "products/";
    public static final String service_version = "api/v1/";
    public static final String values = "best_nutrient_values";


    public static String GetMonthName(int month){
        switch (month){
            case 0:
                return "Enero";
            case 1:
                return "Febrero";
            case 2:
                return "Marzo";
            case 3:
                return "Abril";
            case 4:
                return  "Mayo";
            case 5:
                return "Junio";
            case 6:
                return "Julio";
            case 7:
                return "Agosto";
            case 8:
                return "Septiembre";
            case 9:
                return "Octubre";
            case 10:
                return "Noviembre";
            default:
                return "Diciembre";
        }
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null)
            return false;
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
                && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }
}
