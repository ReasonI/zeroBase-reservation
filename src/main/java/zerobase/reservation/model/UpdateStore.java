package zerobase.reservation.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import zerobase.reservation.model.constants.StoreStatus;

@DynamicUpdate
public class UpdateStore {

    @Getter
    @Setter
    public static class Request{

        @NotNull
        private String name;

        @NotNull
        private String location;

        private double lat;
        private double lon;

        private String explanation;

        private StoreStatus storeStatus;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response{
        private long id;
        private String name;
        private String location;

        private double lat;
        private double lon;

        private String explanation;
        private StoreStatus storeStatus;

        public static UpdateStore.Response from(StoreDto storeDto){
            return UpdateStore.Response.builder()
                    .id(storeDto.getId())
                    .name(storeDto.getName())
                    .location(storeDto.getLocation())
                    .lat(storeDto.getLat())
                    .lon(storeDto.getLon())
                    .explanation(storeDto.getExplanation())
                    .storeStatus(storeDto.getStoreStatus())
                    .build();
        }
    }
}
