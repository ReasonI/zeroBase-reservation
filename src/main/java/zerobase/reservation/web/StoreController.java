package zerobase.reservation.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zerobase.reservation.model.store.CreateStore;
import zerobase.reservation.model.store.DeleteStore;
import zerobase.reservation.model.store.UpdateStore;
import zerobase.reservation.service.StoreService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;

    /**
     * 상점 등록
     * body : name, location, lat, lon, explanation
     */

    @PostMapping
    @PreAuthorize("hasRole('PARTNER_WRITE')") // write 권한을 가지고 있는 owner만 등록 가능
    public CreateStore.Response addStore(@RequestBody @Valid CreateStore.Request request) {
        return CreateStore.Response.from(
                storeService.saveStore(
                        request.getName(),
                        request.getLocation(),
                        request.getLat(),
                        request.getLon(),
                        request.getExplanation()
                )
        );
    }

    /**
     * 상점 수정
     */
    @PutMapping("/{store-id}")
    @PreAuthorize("hasRole('PARTNER_WRITE')")
    public UpdateStore.Response updateStore(@PathVariable("store-id") long id, @RequestBody @Valid UpdateStore.Request request) {
        return UpdateStore.Response.from(
                storeService.updateStore(id, request)
        );
    }


    /**
     * 상점 삭제
     */

    @DeleteMapping("/{store-id}")
    @PreAuthorize("hasRole('PARTNER_WRITE')")
    public DeleteStore.Response deleteStore(@PathVariable("store-id") long id) {
        return DeleteStore.Response.from(
                storeService.deleteStore(id)
        );
    }

    /**
     * 상점 상세정보 조회
     */
    @GetMapping("/{store-id}")
    public ResponseEntity<?> searchStore(@PathVariable("store-id") Long id) {
        return null;
    }

    /**
     * 상점 리스트 조회
     *
     * @return
     */
    @GetMapping("/stores")
    public ResponseEntity<?> searchStores() {
        return null;
    }


}
