package zerobase.reservation.model;

import lombok.*;
import zerobase.reservation.model.constants.StoreStatus;
import zerobase.reservation.persist.entity.Store;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreDto {

    private long id;

    private String name;
    private String location;

    private double lat;
    private double lon;

    private String explanation;
    private StoreStatus storeStatus;

    public static StoreDto fromEntity(Store store){
        return StoreDto.builder()
                .id(store.getId())
                .name(store.getName())
                .location(store.getLocation())
                .lat(store.getLat())
                .lon(store.getLon())
                .explanation(store.getExplanation())
                .storeStatus(store.getStoreStatus())
                .build();
    }
}
