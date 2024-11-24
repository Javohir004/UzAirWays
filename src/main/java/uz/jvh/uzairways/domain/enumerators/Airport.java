package uz.jvh.uzairways.domain.enumerators;

public enum Airport {
    TASHKENT("https://images.pexels.com/photos/466685/pexels-photo-466685.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"),
    SAMARKAND("https://images.pexels.com/photos/302769/pexels-photo-302769.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"),
    BUKHARA("https://images.pexels.com/photos/618079/pexels-photo-618079.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"),
    NAVOIY("https://images.pexels.com/photos/409127/pexels-photo-409127.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"),
    NAMANGAN("https://images.pexels.com/photos/219692/pexels-photo-219692.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"),
    ANDIJON("https://images.pexels.com/photos/290595/pexels-photo-290595.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"),
    FERGANA("https://images.pexels.com/photos/417192/pexels-photo-417192.jpeg?auto=compress&cs=tinysrgb&w=800"),
    KARSHI("https://images.pexels.com/photos/97906/pexels-photo-97906.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"),
    NUKUS("https://images.pexels.com/photos/7613/pexels-photo.jpg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"),
    TERMIZ("https://images.pexels.com/photos/1209978/pexels-photo-1209978.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"),
    JIZZAKH("https://images.pexels.com/photos/1374377/pexels-photo-1374377.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"),
    KHIVA("https://images.pexels.com/photos/1292843/pexels-photo-1292843.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2");

    private final String imageUrl;

    Airport(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public static String getImageUrlByAirport(String airportName) {
        for (Airport airport : values()) {
            if (airport.name().equalsIgnoreCase(airportName)) {
                return airport.getImageUrl();
            }
        }
        return null; // Return null if not found
    }
}


