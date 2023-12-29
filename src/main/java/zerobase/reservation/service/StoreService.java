package zerobase.reservation.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.reservation.model.StoreDto;
import zerobase.reservation.persist.StoreRepository;
import zerobase.reservation.persist.entity.Store;

@Service
@AllArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;


    /**
     * 상점 정보 저장
     */
    @Transactional
    public StoreDto saveStore(String name, String location, double lat, double lon, String explanation) {

        //존재여부 확인
        boolean exists = this.storeRepository.existsByName(name);
        if(exists){
            throw new RuntimeException("already exists name -> " + name);
        }
        //저장
        return StoreDto.fromEntity(
                storeRepository.save(Store.builder()
                        .name(name)
                        .location(location)
                        .lat(lat)
                        .lon(lon)
                        .explanation(explanation)
                        .build()

                ));

    }

}
