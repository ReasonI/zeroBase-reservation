package zerobase.reservation.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerobase.reservation.model.constants.StoreStatus;

public class DeleteStore {

    @Getter
    @Setter
    @Builder
    public static class Response{
        private long id;
        private String name;
        private StoreStatus storeStatus;

        public static DeleteStore.Response from (StoreDto storeDto){
            return DeleteStore.Response.builder()
                    .id(storeDto.getId())
                    .name(storeDto.getName())
                    .storeStatus(storeDto.getStoreStatus())
                    .build();
        }

    }
}
