package zerobase.reservation.model;

import lombok.*;
import zerobase.reservation.persist.entity.Store;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreDto {

    private String name;
    private String location;

    private double lat;
    private double lon;

    private String explanation;

    public static StoreDto fromEntity(Store storeEntity){
        return StoreDto.builder()
                .name(storeEntity.getName())
                .location(storeEntity.getLocation())
                .lat(storeEntity.getLat())
                .lon(storeEntity.getLon())
                .explanation(storeEntity.getExplanation())
                .build();
    }
}
