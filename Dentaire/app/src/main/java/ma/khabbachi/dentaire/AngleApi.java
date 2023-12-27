package ma.khabbachi.dentaire;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AngleApi {
    @POST("create")
    Call<Void> addAngle(@Body Angle angle);
}
