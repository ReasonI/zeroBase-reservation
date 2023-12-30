package zerobase.reservation.service;

import com.fasterxml.jackson.databind.util.BeanUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.jdbc.support.CustomSQLErrorCodesTranslation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.reservation.model.StoreDto;
import zerobase.reservation.model.UpdateStore;
import zerobase.reservation.model.constants.StoreStatus;
import zerobase.reservation.persist.StoreRepository;
import zerobase.reservation.persist.entity.Store;

import java.util.HashSet;
import java.util.Set;

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
        if (exists) {
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

    /**
     * 상점 정보 수정
     */
    public StoreDto updateStore(long id, UpdateStore.Request request) {
//        long id, String name, String location, double lat, double lon, String explanation, StoreStatus storeStatus
        //lat, lon, explanation, storestatus null값 확인
        // null의 경우 db에서 데이터 가져와 전달

        StoreDto store = StoreDto.fromEntity(storeRepository.readById(id));
        if (request.getLat() == 0){
            request.setLat(store.getLat());
        }
        if (request.getLon() == 0){
            request.setLon(store.getLon());
        }
        if (request.getExplanation() == null){
            request.setExplanation(store.getExplanation());
        }
        if (request.getStoreStatus() == null){
            request.setStoreStatus(store.getStoreStatus());
        }


        //저장
        return StoreDto.fromEntity(
                storeRepository.save(Store.builder()
                        .id(id)
                        .name(request.getName())
                        .location(request.getLocation())
                        .lat(request.getLat())
                        .lon(request.getLon())
                        .explanation(request.getExplanation())
                        .storeStatus(request.getStoreStatus())
                        .build()

                ));
    }

    /**
     * 상점 삭제
     */


}
