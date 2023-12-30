package zerobase.reservation.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

public class CreateStore {

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
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private long id;
        private String name;
        private String location;

        private double lat;
        private double lon;

        private String explanation;

        public static Response from(StoreDto storeDto){
            return Response.builder()
                    .id(storeDto.getId())
                    .name(storeDto.getName())
                    .location(storeDto.getLocation())
                    .lat(storeDto.getLat())
                    .lon(storeDto.getLon())
                    .explanation(storeDto.getExplanation())
                    .build();
        }
    }

}
