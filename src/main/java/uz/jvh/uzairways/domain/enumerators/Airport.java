package uz.jvh.uzairways.domain.enumerators;

public enum Airport {
    TASHKENT("https://media.istockphoto.com/id/1643986725/photo/aerial-shot-of-tashkent-tv-tower-in-uzbekistan-daytime.jpg?s=612x612&w=0&k=20&c=6ZLMIpVCQQkYf_Ur3TwYTvB7pbATLo1BenZu8R4-8OE="),
    SAMARKAND("https://t4.ftcdn.net/jpg/02/97/55/21/240_F_297552163_uvGPnu50vreeglLTjtDEwY7mqfIFvA0f.jpg"),
    BUKHARA("https://media.istockphoto.com/id/1316899269/photo/mir-i-arab-medressa-aerial-architecture-mir-i-arab-madrasa-bukhara-uzbekistan.webp?a=1&b=1&s=612x612&w=0&k=20&c=Z5bZuCLZsn42QnUiHGR2UwuSoX9ADA74ug6Ri8LJUi0="),
    NAVOIY("https://c4.wallpaperflare.com/wallpaper/164/150/324/austria-cities-houses-lights-wallpaper-preview.jpg"),
    NAMANGAN("https://images.pexels.com/photos/2921137/pexels-photo-2921137.jpeg?auto=compress&cs=tinysrgb&w=600"),
    ANDIJON("https://images.unsplash.com/photo-1690149629537-806bfdab1409?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTF8fFV6YmVraXN0YW58ZW58MHx8MHx8fDA%3D"),
    FERGANA("https://media.istockphoto.com/id/2179497050/photo/kokand.jpg?s=612x612&w=0&k=20&c=0FEdNHC0FRi25nhMmv9cKFsRSzOFhePl4NCrUZUndSw="),
    KARSHI("https://c4.wallpaperflare.com/wallpaper/336/395/764/paris-the-beautiful-city-night-scene-eiffel-tower-aerial-photograph-wallpaper-preview.jpg"),
    NUKUS("https://t3.ftcdn.net/jpg/02/72/21/44/240_F_272214493_zZ8vZ6iuB0vMyGMgW584f35nnEmwjt6Y.jpg"),
    TERMIZ("https://images.unsplash.com/photo-1541336032412-2048a678540d?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxleHBsb3JlLWZlZWR8MXx8fGVufDB8fHx8fA%3D%3D"),
    JIZZAKH("https://c4.wallpaperflare.com/wallpaper/244/947/795/cool-berlin-wallpaper-83635-wallpaper-preview.jpg"),
    KHIVA("https://images.pexels.com/photos/15481205/pexels-photo-15481205/free-photo-of-big-city-great-city.jpeg?auto=compress&cs=tinysrgb&w=600");

    private final String imageUrl;

    Airport(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}


