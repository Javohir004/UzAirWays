package uz.jvh.uzairways.domain.enumerators;

public enum ErrorCode {
    USER_NOT_FOUND("USER_001", "Foydalanuvchi topilmadi", "Пользователь не найден", "User not found"),
    TICKET_NOT_FOUND("TICKET_001", "Bilet topilmadi", "Билет не найден", "Ticket not found"),
    TICKET_ALREADY_BRON("TICKET_002", "Bilet allaqachon bron qilindi", "Билет уже забронирован", "Ticket is already Bron"),
    INSUFFICIENT_BALANCE("USER_002", "Booking uchun yetarli mablag' yo'q", "Недостаточно средств для бронирования", "Insufficient balance for the booking");

    private final String code;
    private final String uzbek;
    private final String russian;
    private final String english;

    ErrorCode(String code, String uzbek, String russian, String english) {
        this.code = code;
        this.uzbek = uzbek;
        this.russian = russian;
        this.english = english;
    }

    public String getCode() {
        return code;
    }

    public String getTranslation(String language) {
        switch (language.toLowerCase()) {
            case "uz":
                return english;
            case "ru":
                return russian;
            case "en":
            default:
                return uzbek;
        }
    }

}
