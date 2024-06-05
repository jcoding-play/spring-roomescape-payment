package roomescape.dto;

import java.time.LocalDate;

import roomescape.domain.ReservationStatus;
import roomescape.entity.Member;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;
import roomescape.entity.Theme;

public record ReservationRequest(LocalDate date, long timeId, long themeId) {
    public Reservation toReservation(Member member, ReservationTime reservationTime, Theme theme, ReservationStatus reservationStatus) {
        return new Reservation(this.date, reservationTime, theme, member, reservationStatus);
    }

    public static ReservationRequest from(AdminReservationRequest adminReservationRequest) {
        return new ReservationRequest(adminReservationRequest.date(), adminReservationRequest.timeId(), adminReservationRequest.themeId());
    }
    public static ReservationRequest from(ReservationPaymentRequest reservationPaymentRequest){
        return new ReservationRequest(reservationPaymentRequest.date(), reservationPaymentRequest.timeId(), reservationPaymentRequest.themeId());
    }
}
