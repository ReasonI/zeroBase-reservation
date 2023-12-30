package zerobase.reservation.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zerobase.reservation.model.CreateStore;
import zerobase.reservation.model.UpdateStore;
import zerobase.reservation.service.StoreService;

// 2/5
@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    /**
     * 상점 등록
     * body : name, location, lat, lon, explanation
     */
    @PostMapping("/store")
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
    @PutMapping("/store/{storeId}")
    public UpdateStore.Response updateStore(@PathVariable("storeId") long id,@RequestBody @Valid UpdateStore.Request request) {
        return UpdateStore.Response.from(
                storeService.updateStore(id,request));
    }


    /**
     * 상점 삭제
     */

    @DeleteMapping("/store/{storeId}")
    public ResponseEntity<?> deleteStore(@PathVariable("storeId") long id) {
        return null;
    }

    /**
     * 상점 상세정보 조회
     *
     * @param storeName
     * @return
     */
    @GetMapping("/store/{storeName}")
    public ResponseEntity<?> searchStore(@PathVariable String storeName) {
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
