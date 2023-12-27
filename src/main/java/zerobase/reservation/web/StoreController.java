package zerobase.reservation.web;

import org.apache.catalina.Store;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// 0/5
@RestController
public class StoreController {

    /**
     * 상점 등록
     */
    @PostMapping("/store")
    public ResponseEntity<?> addStore(@RequestBody Store request){
        return null;
    }

    /**
     * 상점 수정
     * @return
     */

    @PutMapping("/store")
    public ResponseEntity<?> updateStore(){
        return null;
    }


    /**
     * 상점 삭제
     * @return
     */

    @DeleteMapping("/store/{storeName}")
    public ResponseEntity<?> deleteStore(){
        return null;
    }

    /**
     * 상점 상세정보 조회
     * @param storeName
     * @return
     */
    @GetMapping("/store/{storeName}")
    public ResponseEntity<?> searchStore(@PathVariable String storeName){
        return null;
    }

    /**
     * 상점 리스트 조회
     * @return
     */
    @GetMapping("/stores")
    public ResponseEntity<?> searchStores(){
        return null;
    }


}
