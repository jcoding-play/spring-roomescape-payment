package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.LoginMember;
import roomescape.dto.*;
import roomescape.service.ReservationService;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationPaymentResponse> saveReservation(@Authenticated LoginMember loginMember,
                                                                      @RequestBody ReservationPaymentRequest request) {
        ReservationPaymentResponse response = reservationService.save(loginMember, request);
        return ResponseEntity.created(URI.create("/reservations/" + response.reservationResponse().id()))
                .body(response);
    }

    @PostMapping("/reservations-waiting")
    public ResponseEntity<ReservationResponse> saveReservationWaiting(@Authenticated LoginMember loginMember,
                                                                      @RequestBody ReservationRequest reservationRequest) {
        ReservationResponse savedReservationResponse = reservationService.saveWaiting(loginMember, reservationRequest);
        return ResponseEntity.created(URI.create("/reservations-waiting/" + savedReservationResponse.id()))
                .body(savedReservationResponse);
    }

    @GetMapping("/reservations")
    public List<ReservationResponse> findAllReservations() {
        return reservationService.findAllReservations();
    }

    @GetMapping("/member/reservation")
    public List<ReservationDetailResponse> findMemberReservations(@Authenticated LoginMember loginMember) {
        return reservationService.findAllByMemberId(loginMember.getId());
    }

    @GetMapping("/reservations/search")
    public List<ReservationResponse> searchReservation(@RequestParam Long themeId,
                                                       @RequestParam Long memberId,
                                                       @RequestParam(defaultValue = "#{T(java.time.LocalDate).now()}")
                                                       LocalDate dateFrom,
                                                       @RequestParam(defaultValue = "#{T(java.time.LocalDate).now()}")
                                                       LocalDate dateTo) {
        return reservationService.searchReservation(themeId, memberId, dateFrom, dateTo);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        reservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/reservations-waiting/{id}")
    public ResponseEntity<Void> deleteReservationWaiting(@Authenticated LoginMember loginMember, @PathVariable long id) {
        reservationService.deleteByMemberIdAndId(loginMember, id);
        return ResponseEntity.noContent().build();
    }
}
